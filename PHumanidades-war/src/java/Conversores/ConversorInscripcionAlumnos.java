/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import Beans.InscripcionAlumnosBean;
import Entidades.Carreras.InscripcionAlumnos;
import RN.InscripcionAlumnosRN;
import RN.InscripcionAlumnosRNLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ruben
 */
@ManagedBean(name="conversorInscripcionAlumnos")
@RequestScoped
public class ConversorInscripcionAlumnos implements Converter{
    
    @Inject
    private InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null){
            return null;
        }
        try{
            InscripcionAlumnos ia = inscripcionAlumnosRNLocal.find(Long.parseLong(value));
            return ia;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
           return String.valueOf(((InscripcionAlumnos) value).getId());
        } catch (Exception e) {
            return "";
        }
    }

    public InscripcionAlumnosRNLocal getInscripcionAlumnosRNLocal() {
        return inscripcionAlumnosRNLocal;
    }

    public void setInscripcionAlumnosRNLocal(InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal) {
        this.inscripcionAlumnosRNLocal = inscripcionAlumnosRNLocal;
    }
    
}
