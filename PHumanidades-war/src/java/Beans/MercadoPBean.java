/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cohorte;
import Entidades.Persona.Alumno;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author victo
 */
@ManagedBean(name = "mercadoPBean")
@RequestScoped
public class MercadoPBean implements Serializable {

    /**
     * Creates a new instance of MercadoPBean
     */
    private String preferenceId;
    private String initPoint;

    public String getInitPoint() {
        return initPoint;
    }

    public void setInitPoint(String initPoint) {
        this.initPoint = initPoint;
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public MercadoPBean() {
        MercadoPagoConfig.setAccessToken("TEST-1576757908614312-022716-3193c51969313e661e2b166e757795a9-200964240");
    }

    public void cargarPreferenciaMP(Cohorte cohorte, Alumno alumno) {
        MercadoPagoConfig.setAccessToken("TEST-1576757908614312-022716-3193c51969313e661e2b166e757795a9-200964240");
        System.out.println(" Entro cargarPreferenciaMP== "+MercadoPagoConfig.getAccessToken());
   
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Test producto")
                .quantity(1)
                .unitPrice(new BigDecimal("1000"))
                .currencyId("BR")
                .categoryId("others")
                .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(item);
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .externalReference("TEST-1234")
                .autoReturn("approved")
                .build();
        PreferenceClient client = new PreferenceClient();

        try {
            Preference pref = client.create(preferenceRequest);
            this.setPreferenceId(pref.getId());
            this.setInitPoint(pref.getInitPoint());
        } catch (MPException ex) {
            Logger.getLogger(MercadoPBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MPApiException ex) {
            System.out.println("==== MPApiException ====");
            System.out.println("Status Code: " + ex.getApiResponse().getStatusCode());
            System.out.println("Response: " + ex.getApiResponse().getContent());
            System.out.println("Full Exception: " + ex.toString());
            ex.printStackTrace();  // Esto te da la traza completa en el log
        }
    }

}
