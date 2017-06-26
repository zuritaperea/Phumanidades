/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import Beans.CohorteBean;
import Entidades.Carreras.Cohorte;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Clase usada para convertir un tipo string (id de la localidad) a una clase
 * localidad
 *
 * @author operador
 */
@FacesConverter(value = "ConversorCohorte")
public class ConversorCohorte implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null) {
            return null;
        }
        CohorteBean cohorteBean = (CohorteBean) context.getApplication().evaluateExpressionGet(
                context, "#{cohorteBean}", CohorteBean.class);

        try {
            return cohorteBean.findById(Long.parseLong(value));
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        try {
            return String.valueOf(((Cohorte) value).getId());
        } catch (Exception e) {
            return "";
        }
    }

}
