/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import DAO.TarjetaDeCreditoFacadeLocal;
import Entidades.Ingresos.TarjetaDeCredito;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ruben
 */
//@ManagedBean(name="conversorTarjetaDeCredito")
//@RequestScoped
@FacesConverter(value = "conversorTarjetaDeCredito")
public class ConversorTarjetaDeCredito implements Converter{
    
    @EJB
    private TarjetaDeCreditoFacadeLocal tarjetaDeCreditoFacadeLocal;

    public TarjetaDeCreditoFacadeLocal getTarjetaDeCreditoFacadeLocal() {
        return tarjetaDeCreditoFacadeLocal;
    }

    public void setTarjetaDeCreditoFacadeLocal(TarjetaDeCreditoFacadeLocal tarjetaDeCreditoFacadeLocal) {
        this.tarjetaDeCreditoFacadeLocal = tarjetaDeCreditoFacadeLocal;
    }



    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       
        try{
             if(value == null){
            return null;
        }
            TarjetaDeCredito tc = tarjetaDeCreditoFacadeLocal.find(Long.parseLong(value));
            return tc;
        }catch(Exception ex){
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((TarjetaDeCredito) value).getId());
        } catch (Exception e) {
            return "";
        }
    }
    
    
}
