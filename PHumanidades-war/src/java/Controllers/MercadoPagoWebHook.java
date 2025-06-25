/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.InformePagoAlumnoFacade;
import Entidades.Ingresos.InformePagoAlumno;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
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

/**
 *
 * @author victo
 */
@Path("/api/webhooks/mercado-pago")
@Produces(MediaType.APPLICATION_JSON)
public class MercadoPagoWebHook {

    @EJB
    private InformePagoAlumnoFacade informePagoAlumnoFacade;
    private InformePagoAlumno informePagoAlumno;

    @POST
    public Response handleWebhook(
            @HeaderParam("X-Signature") String signature,
            String payload) {

        // 1. Validar firma (seguridad crítica)
        if (!validarFirma(signature, payload)) {
            return Response.status(401).build();
        }

        // 2. Parsear JSON
        JsonObject json = Json.createReader(new StringReader(payload)).readObject();
        String eventType = json.getString("type"); // "payment" o "merchant_order"
        String paymentId = json.getJsonObject("data").getString("id");

        // 3. Procesar solo eventos de pago aprobados
        if ("payment".equals(eventType)) {
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
        try {
            Payment payment = new PaymentClient().get(Long.parseLong(paymentId));

            if ("approved".equals(payment.getStatus())) {
                // Buscar por referencia externa (ej: "ALU-123-C1")
                informePagoAlumno = informePagoAlumnoFacade.findByExternalRef(
                        payment.getExternalReference()
                );

                if (informe != null) {
                    informe.setEstado("APROBADO");
                    informe.setPaymentId(paymentId);
                    informe.setComprobantePago(downloadComprobante(payment));
                    informeDAO.update(informe);
                }
            }
        } catch (Exception e) {
            // Loggear error
        }
    }

}
