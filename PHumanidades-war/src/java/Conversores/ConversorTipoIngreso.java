/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import DAO.TipoIngresoFacadeLocal;
import Entidades.Ingresos.TipoIngreso;
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
@ManagedBean(name="conversorTipoIngreso")
@RequestScoped
public class ConversorTipoIngreso implements Converter{
    
    @EJB
    private TipoIngresoFacadeLocal tipoIngresoFacadeLocal;

    public TipoIngresoFacadeLocal getTipoIngresoFacadeLocal() {
        return tipoIngresoFacadeLocal;
    }

    public void setTipoIngresoFacadeLocal(TipoIngresoFacadeLocal tipoIngresoFacadeLocal) {
        this.tipoIngresoFacadeLocal = tipoIngresoFacadeLocal;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null){
            return null;
        }
        try{
            TipoIngreso te = tipoIngresoFacadeLocal.find(Long.parseLong(value));
            return te;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((TipoIngreso) value).getId());
        } catch (Exception e) {
            return "";
        }
    }
    
    
}
