/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Recursos.GeneradorComprobanteMP;
import DAO.InformePagoAlumnoFacade;
import Entidades.Ingresos.InformePagoAlumno;
import Recursos.GeneradorComprobanteMP;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import java.io.IOException;
import javax.ws.rs.POST;          // Import clave
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;
import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 *
 * @author victo
 */
@Path("/webhooks/mercado-pago")
@Produces(MediaType.APPLICATION_JSON)
public class MercadoPagoWebHook {

    @EJB
    private InformePagoAlumnoFacade informePagoAlumnoFacade;
    private InformePagoAlumno informePagoAlumno;
    private String paymentId;
    private String status;
    private String externalReference;
    private String eventType;
    private String date_created;
    private GeneradorComprobanteMP generadorComprobanteMP;
    private ByteArrayOutputStream comprobante;

    @POST
    public Response handleWebhook(
            @HeaderParam("X-Signature") String signature,
            String payload) {
        System.out.print("ENTRO HandleWebbhook con payload= " + payload);
        // 1. Validar firma (seguridad crítica)
//        if (!validarFirma(signature, payload)) {
//            return Response.status(401).build();
        //}

        // 2. Parsear JSON
        JsonObject json = Json.createReader(new StringReader(payload)).readObject();
        eventType = json.getString("type"); // "payment" o "merchant_order"
        paymentId = json.getJsonObject("data").getString("id");
        //status = json.getJsonObject("data").getString("status");
        //externalReference = json.getString("external_reference");

        // 3. Procesar solo eventos de pago aprobados
        if ("payment".equals(eventType)) {
            System.out.print("ENTRO IF Payment con pymentId= " + paymentId);
            procesarPago(paymentId);
        }

        return Response.ok().build();
    }

    private boolean validarFirma(String signature, String payload) {
        String secret = "TU_WEBHOOK_SECRET"; // Configurar en variables de entorno
        String hash = DigestUtils.sha256Hex(payload + secret);
        return hash.equals(signature);
    }

    private void procesarPago(String paymentId) {

        MercadoPagoConfig.setAccessToken("TEST-1576757908614312-022716-3193c51969313e661e2b166e757795a9-200964240");

        System.out.print("ENTRO Metodo procesarPago");
        PaymentClient client = new PaymentClient();
        Payment payment = new Payment();
        System.out.println("Payment ID recibido: " + paymentId);
        System.out.println("Access Token en uso: " + MercadoPagoConfig.getAccessToken());

        try {
            payment = client.get(Long.parseLong(paymentId));
        } catch (MPException ex) {
            System.out.println("Error al Obtener payment JSON MP");
            Logger.getLogger(MercadoPagoWebHook.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MPApiException ex) {
            System.out.println("Error API al Obtener payment JSON MP");
            Logger.getLogger(MercadoPagoWebHook.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(" FLUJO COMPLETADO!!! Respuesta get mercado pago =:" + payment);

        if ("approved".equals(payment.getStatus())) {
            System.out.print("ENTRO IF Approved Metodo procesarPago con externalReference =" + externalReference);
            // Buscar por referencia externa (ej: "ALU-123-C1")
            informePagoAlumno = informePagoAlumnoFacade.findByExternalRef(payment.getExternalReference());
            System.out.println("InformePagoAlumno encontrado EXTERNALREFFF = " + informePagoAlumno);
            try {
                comprobante = generadorComprobanteMP.generarComprobante(payment);
            } catch (Exception ex) {
                comprobante = null;
            }

            if (informePagoAlumno != null) {
                informePagoAlumno.setEstado("APROBADO");
                informePagoAlumno.setPaymentId(paymentId);
                informePagoAlumno.setNombreComprobantePago("MercadoPago_" + paymentId + ".pdf");
                informePagoAlumno.setComprobantePago(comprobante.toByteArray());

                informePagoAlumnoFacade.edit(informePagoAlumno);
            }
        }
        if ("rejected".equals(payment.getStatus())) {
            informePagoAlumno = informePagoAlumnoFacade.findByExternalRef(payment.getExternalReference());
            if (informePagoAlumno != null) {
                informePagoAlumno.setEstado("RECHAZADO");
                informePagoAlumno.setPaymentId(paymentId);
                informePagoAlumnoFacade.edit(informePagoAlumno);
            }
        }
    }

    private byte[] downloadComprobante(Payment payment) throws IOException {
        // 1. Obtener URL del comprobante (PDF)
        String comprobanteUrl = payment.getTransactionDetails().getExternalResourceUrl();

        if (comprobanteUrl == null) {
            throw new IOException("El pago no tiene comprobante asociado");
        }

        // 2. Configurar conexión HTTP
        URL url = new URL(comprobanteUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 3. Descargar el PDF
        try (InputStream in = connection.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            // 4. Retornar como byte[] para guardar en BD
            return out.toByteArray();

        } finally {
            connection.disconnect();
        }
    }

}
