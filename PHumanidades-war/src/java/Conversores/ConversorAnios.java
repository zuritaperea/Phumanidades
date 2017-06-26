/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import Beans.AnioBean;
import Entidades.Carreras.Anio;
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
@FacesConverter(value = "ConversorAnios")
public class ConversorAnios implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null) {
            return null;
        }
        AnioBean anioBean = (AnioBean) context.getApplication().evaluateExpressionGet(context, "#{anioBean}", AnioBean.class);

        return anioBean.findById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        return String.valueOf(((Anio) value).getId());
    }

}
