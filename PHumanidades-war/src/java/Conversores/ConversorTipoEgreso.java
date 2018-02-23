/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import Beans.TipoEgresoController;
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
@ManagedBean(name = "conversorTipoEgreso")
@RequestScoped
public class ConversorTipoEgreso implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }

        TipoEgresoController controller = (TipoEgresoController) context.getApplication().getELResolver().
                getValue(context.getELContext(), null, "tipoEgresoController");
        try {
            return controller.getEjbFacade().find(getKey(value));
        } catch (Exception e) {
            return null;
        }
    }

    java.lang.Long getKey(String value) {
        java.lang.Long key;
        key = Long.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Long value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((TipoEgreso) value).getId());
        } catch (Exception e) {
            return "";
        }
    }

}
