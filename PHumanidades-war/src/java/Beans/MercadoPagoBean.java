/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

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
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

/**
 *
 * @author victo
 */
@ManagedBean
@RequestScoped
public class MercadoPagoBean {

    /**
     * Creates a new instance of MercadoPagoBean
     */
    private String preferenceId;

    public MercadoPagoBean() {
        MercadoPagoConfig.setAccessToken("APP_USR-4894752058482135-100616-edcf471749fa8fe077079a7d0850474b-2022571850");

        PreferenceItemRequest itemRequest
                = PreferenceItemRequest.builder()
                .id("1234")
                .title("Games")
                .description("PS5")
                .pictureUrl("http://picture.com/PS5")
                .categoryId("games")
                .quantity(1)
                .currencyId("BRL")
                .unitPrice(new BigDecimal("4000"))
                .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items).build();
        PreferenceClient client = new PreferenceClient();
        try {
            Preference preference = client.create(preferenceRequest);
            this.setPreferenceId(preference.getId());
        } catch (MPException ex) {
            Logger.getLogger(MercadoPagoBean.class.getName()).log(Level.SEVERE, null, ex);
            this.setPreferenceId("");
        } catch (MPApiException ex) {
            Logger.getLogger(MercadoPagoBean.class.getName()).log(Level.SEVERE, null, ex);
            this.setPreferenceId("");
        }
        

    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

}
