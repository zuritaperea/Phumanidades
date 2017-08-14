/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import DAO.TipoEgresoFacadeLocal;
import Entidades.Egresos.TipoEgreso;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author ruben
 */
@ManagedBean(name="conversorTipoEgreso")
@RequestScoped
public class ConversorTipoEgreso implements Converter{
    
    @EJB
    private TipoEgresoFacadeLocal tipoEgresoFacadeLocal;

    public TipoEgresoFacadeLocal getTipoEgresoFacadeLocal() {
        return tipoEgresoFacadeLocal;
    }

    public void setTipoEgresoFacadeLocal(TipoEgresoFacadeLocal tipoEgresoFacadeLocal) {
        this.tipoEgresoFacadeLocal = tipoEgresoFacadeLocal;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null){
            return null;
        }
        System.out.println("converter te");
        System.out.println(value);
        try{
            System.out.println(Long.parseLong(value));
            TipoEgreso te = tipoEgresoFacadeLocal.find(Long.parseLong(value));
            System.out.println(te);
            return te;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            System.out.println("converter te 2");
            System.out.println(value);
            return String.valueOf(((TipoEgreso) value).getId());
        } catch (Exception e) {
            return "";
        }
    }
    
    
}
