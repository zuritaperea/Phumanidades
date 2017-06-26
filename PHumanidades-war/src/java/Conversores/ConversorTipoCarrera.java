/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import Beans.TipoCarreraBean;
import Entidades.Carreras.TipoCarrera;
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
@FacesConverter(value = "ConversorTipoCarrera")
public class ConversorTipoCarrera implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null) {
            return null;
        }
        TipoCarreraBean tipoCarreraBean = (TipoCarreraBean) context.getApplication().evaluateExpressionGet(
                context, "#{tipoCarreraBean}", TipoCarreraBean.class);

        return tipoCarreraBean.findById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        return String.valueOf(((TipoCarrera) value).getId());
    }

}
