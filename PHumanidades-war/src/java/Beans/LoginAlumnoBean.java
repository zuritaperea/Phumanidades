/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Persona.Alumno;
import Entidades.Usuarios.Usuarios;
import RN.AlumnoRNLocal;
import RN.InscripcionAlumnosRNLocal;
import RN.UsuariosRNLocal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author victo
 */
@ManagedBean(name = "loginAlumnoBean")
@SessionScoped
public class LoginAlumnoBean {

    @EJB
    private AlumnoRNLocal alumnoRNLocal;
    private String documento;
    private Alumno alumno;
    private String recaptchaResponse;
    @EJB
    private UsuariosRNLocal usuariosRNLocal;
    private final String userAlumno = "alumno";
    @ManagedProperty(value = "#{usuarioLogerBean}")
    private UsuarioLogerBean usuarioLogerBean;
    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;
    @EJB
    private InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal;

//    public LoginAlumnoBean() {
//        this.setDocumento(new String());
//        this.setRecaptchaResponse(new String());
//        this.setAlumno(new Alumno());
//    }

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

    public UsuarioLogerBean getUsuarioLogerBean() {
        return usuarioLogerBean;
    }

    public void setUsuarioLogerBean(UsuarioLogerBean usuarioLogerBean) {
        this.usuarioLogerBean = usuarioLogerBean;
    }

    public CohorteLstBean getCohorteLstBean() {
        return cohorteLstBean;
    }

    public void setCohorteLstBean(CohorteLstBean cohorteLstBean) {
        this.cohorteLstBean = cohorteLstBean;
    }

    public String ingresar() {

        try {
            recaptchaResponse = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("g-recaptcha-response");
            alumno = alumnoRNLocal.findByAlumnoDni(documento);
            
            Usuarios usuAlumno = usuariosRNLocal.buscarPorNombre(userAlumno);
            if(recaptchaResponse.isEmpty()){
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validacion de Captcha Incorrecta", null);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
                return "";
            }
            if (alumno != null) {
                System.out.println("imprimo alumno0");
                System.out.println(alumno);
                //guardamos la sesion para el alumno
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("userLogged", 1);
                usuarioLogerBean.setUsuario(usuAlumno);
                System.out.println("USUARIO ALUMNOOO");
                System.out.println(usuAlumno.getUsuario());
                System.out.println(usuAlumno.getGrupo());
                //LA POSTA JAJAJAJA
                this.cohorteLstBean.setLstCohortesAlumnosConsulta(this.inscripcionAlumnosRNLocal.alumnoFindCohortes(alumno));
                this.cohorteLstBean.setLstCohortesAlumnos(this.inscripcionAlumnosRNLocal.alumnoFindCohortes(alumno));
                //System.out.println(usuarioLogerBean.getUsuario().getGrupo().getDescripcion());
                System.out.println(cohorteLstBean.getLstCohortesAlumnos());
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
