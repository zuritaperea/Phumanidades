/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import Beans.CarreraBean;
import Entidades.Carreras.Carrera;
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
@FacesConverter(value = "ConversorCarrera")
public class ConversorCarrera implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null) {
            return null;
        }
        CarreraBean carreraBean = (CarreraBean) context.getApplication().evaluateExpressionGet(
                context, "#{carreraBean}", CarreraBean.class);

        try {
            return carreraBean.findById(Long.parseLong(value));
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        try {
            return String.valueOf(((Carrera) value).getId());
        } catch (Exception e) {
            return "";
        }
    }

}
