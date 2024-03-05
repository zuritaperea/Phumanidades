/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Persona.Alumno;
import RN.AlumnoRNLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author victo
 */
@ManagedBean
@RequestScoped
public class LoginAlumnoBean {

    @EJB
    private AlumnoRNLocal alumnoRNLocal;
    private String documento;
    private Alumno alumno;
    private String recaptchaResponse;

    public LoginAlumnoBean() {
        this.setDocumento(new String());
        this.setRecaptchaResponse(new String());
        this.setAlumno(new Alumno());
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRecaptchaResponse() {
        return recaptchaResponse;
    }

    public void setRecaptchaResponse(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public AlumnoRNLocal getAlumnoRNLocal() {
        return alumnoRNLocal;
    }

    public void setAlumnoRNLocal(AlumnoRNLocal alumnoRNLocal) {
        this.alumnoRNLocal = alumnoRNLocal;
    }

//    public String ingresar() {
//        System.err.println(this.getDocumento());
//        System.out.println("entro ingresar");
//        recaptchaResponse = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("g-recaptcha-response");
//        System.out.println(recaptchaResponse);
//        if (this.getDocumento().isEmpty()) {
//            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar un Dni", null);
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage(null, fm);
//        }
//        if (recaptchaResponse.isEmpty()) {
//            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion de Captcha Incorrecta", null);
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage(null, fm);
//        }
//        //return "InformePagoAlumno.xhtml?faces-redirect=true";
//        return "InformePagoAlumno.xhtml";
//
//    }

    public String ingresar() {
            //recaptchaResponse="";
        try {
            recaptchaResponse = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("g-recaptcha-response");
            alumno = alumnoRNLocal.findByAlumnoDni(documento);
            if(recaptchaResponse.isEmpty()){
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion de Captcha Incorrecta", null);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
                return "";
            }
            if (alumno != null) {
                System.out.println("imprimo alumno0");
                System.out.println(alumno);
                return "/paginas/informePagoAlumno/List.xhtml?faces-redirect=true";
            } else {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alumno no encontrado", null);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
                return "";
            }
        } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alumno no encontrado", null);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
        }
        return "";
    }

}
