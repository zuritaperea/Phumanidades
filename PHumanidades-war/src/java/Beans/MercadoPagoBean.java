/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.InformePagoAlumnoFacade;
import Entidades.Carreras.Cohorte;
import Entidades.Ingresos.EstadoComprobanteAlumno;
import Entidades.Ingresos.InformePagoAlumno;
import Entidades.Persona.Alumno;
import com.google.gson.Gson;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.RequestScoped;
//MERCADO PAGO
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author victo
 */
@ManagedBean(name = "mercadoPagoBean")
@RequestScoped
public class MercadoPagoBean implements Serializable {

    /**
     * Creates a new instance of MercadoPagoBean
     */
    private String preferenceId;
    private Long cohorteId;
    @EJB
    private InformePagoAlumnoFacade informePagoAlumnoFacade;

    @PostConstruct
    void init() {
        MercadoPagoConfig.setAccessToken("TEST-1576757908614312-022716-3193c51969313e661e2b166e757795a9-200964240");
        this.setPreferenceId(new String());
    }

    public MercadoPagoBean() {
        MercadoPagoConfig.setAccessToken("TEST-1576757908614312-022716-3193c51969313e661e2b166e757795a9-200964240");
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public Long getCohorteId() {
        return cohorteId;
    }

    public void setCohorteId(Long cohorteId) {
        this.cohorteId = cohorteId;
    }

    public void cargarPreferencia(Cohorte cohorte, Alumno alumno) {
        //seteamos datos iniciales para el Pago del alumno previo pago desde MP
        InformePagoAlumno informePagoAlumno = new InformePagoAlumno();
        informePagoAlumno.setFecha(new Date());
        informePagoAlumno.setExternalReference("ALU-" + alumno.getDni() + "-T" + System.currentTimeMillis());
        //RequestContext.getCurrentInstance().execute("eliminarBotonMercadoPago();");
        this.setPreferenceId(new String());
        System.out.println("Entro CargarPreferencia");
        System.out.print("Cohorte nombre = " + cohorte.getDescripcion());
        System.out.println("CohorteID= " + cohorte.getId());
        System.out.println("Token configurado: " + MercadoPagoConfig.getAccessToken());

        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Test producto")
                .quantity(1)
                .unitPrice(new BigDecimal("1000.00"))
                .currencyId("ARS")
                .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(item);
        System.out.println("items requesssstt: " + items.get(0).getUnitPrice());
        System.out.println("External referenssssceet: " + informePagoAlumno.getExternalReference());
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .externalReference("TEST-1234")
                .autoReturn("approved")
                .build();
        System.out.println("Preferencia a enviar: " + new Gson().toJson(preferenceRequest));
        PreferenceClient client = new PreferenceClient();
        Preference pref;
        try {
            pref = client.create(preferenceRequest);
            this.setPreferenceId(pref.getId());
            this.setCohorteId(cohorte.getId());
            System.out.println("Cargo preferencia metodo cargarPreferencia;: " + this.getPreferenceId());
            System.out.println("lista de preferencias=== " + preferenceRequest.getItems());
            // 3. Pre-guardar el registro
            informePagoAlumno.setEstado("PENDIENTE"); // Agrega este campo a tu entidad
            informePagoAlumno.setAlumno(alumno);
            informePagoAlumno.setCohorte(cohorte);
            informePagoAlumno.setEstadoComprobanteAlumno(EstadoComprobanteAlumno.PROCESANDO);
            informePagoAlumno.setDescripcion("Pago MercadoPago: " + cohorte.getDescripcion());
            informePagoAlumno.setCantidadCuotas(1);
            informePagoAlumnoFacade.create(informePagoAlumno);
        } catch (MPException ex) {
            Logger.getLogger(MercadoPagoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MPApiException ex) {
            System.out.println("=== ERROR MercadoPago ===");
            System.out.println("Status code: " + ex.getApiResponse().getStatusCode());
            System.out.println("Contenido del error:");
            System.out.println(ex.getApiResponse().getContent()); // ✅ Este es el JSON con el mensaje real
            ex.printStackTrace(); // Opcional
            Logger.getLogger(MercadoPagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
