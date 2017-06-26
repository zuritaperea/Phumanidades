/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import Beans.CuentaBean;
import Entidades.Carreras.Cuenta;
import javax.el.ELException;
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
@FacesConverter(value = "ConversorCuenta")
public class ConversorCuenta implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null || value.equals("Select") || value.equals("null")) {

            return null;
        }
        try {
            long parseLong = Long.parseLong(value);

//        if(value==null){
//            return null;
//        }
            CuentaBean cuentaBean = (CuentaBean) context.getApplication().evaluateExpressionGet(
                    context, "#{cuentaBean}", CuentaBean.class);

            return cuentaBean.findById(parseLong);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.equals("null")) {
            return null;
        }
        return String.valueOf(((Cuenta) value).getId());
    }

}
