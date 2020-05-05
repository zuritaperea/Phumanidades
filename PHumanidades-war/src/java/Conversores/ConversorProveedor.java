/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import Beans.ProveedorBean;
import Entidades.Carreras.Cohorte;
import Entidades.Persona.Proveedor;
import javax.faces.application.FacesMessage;
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
@FacesConverter(value = "ConversorProveedor")
public class ConversorProveedor implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value == null) {
                return null;
            }
            ProveedorBean proveedorBean = (ProveedorBean) context.getApplication().evaluateExpressionGet(
                    context, "#{proveedorBean}", ProveedorBean.class);

            return proveedorBean.findById(Long.parseLong(value));
        } catch (Exception ex) {
//            FacesMessage facesMessage = new FacesMessage();
//            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
//            facesMessage.setSummary("Error del conversor: " + ex);
//            facesMessage.setDetail("Error conversor proveedor");
//            FacesContext fc = context.getCurrentInstance();
//            fc.addMessage(null, facesMessage);
            return null;
        }//catch
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((Proveedor) value).getId());
        } catch (Exception e) {
            return "";
        }
    }

}
