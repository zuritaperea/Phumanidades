/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cohorte;
import Entidades.Ingresos.InformePagoAlumno;
import javax.inject.Named;
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
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import java.util.Date;
import javax.annotation.PostConstruct;
//import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

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

    public void cargarPreferencia(Cohorte cohorte) {
        InformePagoAlumno informePagoAlumno= new InformePagoAlumno();
        informePagoAlumno.setFecha(new Date());
        //RequestContext.getCurrentInstance().execute("eliminarBotonMercadoPago();");
        this.setPreferenceId(new String());
        System.out.println("Entro CargarPreferencia");
        System.out.print("Cohorte nombre = " + cohorte.getDescripcion());
        System.out.println("CohorteID= " + cohorte.getId());
        System.out.println("Token configurado: " + MercadoPagoConfig.getAccessToken());
//        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
//                .success("http://localhost:8080/PHumanidades-war/paginas/informePagoAlumno/List.xhtml")
//                .pending("http://localhost:8080/PHumanidades-war/paginas/informePagoAlumno/List.xhtml")
//                .failure("http://localhost:8080/PHumanidades-war/paginas/informePagoAlumno/List.xhtml")
//                .build();
        //configuramos ACCESS TOKEN (PRIVATE KEY)
        //MercadoPagoConfig.setAccessToken("TEST-1576757908614312-022716-3193c51969313e661e2b166e757795a9-200964240");

        PreferenceItemRequest itemRequest = null;
        itemRequest = PreferenceItemRequest.builder()
                .id("1234")
                .title(cohorte.getCarrera().getDescripcion())
                .description(cohorte.getDescripcion())
                .pictureUrl("http://picture.com/PS5")
                .categoryId(cohorte.getImporteCuota().toString())
                .quantity(1)
                .currencyId("AR")
                .unitPrice(cohorte.getImporteCuota())
                .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                //.backUrls(backUrls)
                //.autoReturn("approved") 
                .build();
        PreferenceClient client = new PreferenceClient();
        try {
            Preference preference = client.create(preferenceRequest);
            this.setPreferenceId(preference.getId());
            this.setCohorteId(cohorte.getId());
            System.out.println("Cargo preferencia metodo cargarPreferencia;: " + this.getPreferenceId());
            System.out.println("lista de preferencias=== " + preferenceRequest.getItems());

        } catch (MPException ex) {
            Logger.getLogger(MercadoPagoBean.class.getName()).log(Level.SEVERE, null, ex);
            this.setPreferenceId("");
        } catch (MPApiException ex) {
            Logger.getLogger(MercadoPagoBean.class.getName()).log(Level.SEVERE, null, ex);
            this.setPreferenceId("");
        }

    }

}
