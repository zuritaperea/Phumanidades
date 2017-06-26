/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cohorte;
import Entidades.Carreras.InscripcionAlumnos;
import Entidades.Persona.Alumno;
import RN.InscripcionAlumnosRNLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class InscripcionAlumnosBean implements Serializable {

    @EJB
    private InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal;

    @ManagedProperty(value = "#{alumnoLstBean}")
    private AlumnoLstBean alumnoLstBean;

    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;

    @ManagedProperty(value = "#{inscripcionAlumnosLstBean}")
    private InscripcionAlumnosLstBean inscripcionAlumnosLstBean;

    private InscripcionAlumnos inscripcion;

    public InscripcionAlumnosRNLocal getInscripcionAlumnosRNLocal() {
        return inscripcionAlumnosRNLocal;
    }

    public void setInscripcionAlumnosRNLocal(InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal) {
        this.inscripcionAlumnosRNLocal = inscripcionAlumnosRNLocal;
    }

    public InscripcionAlumnosLstBean getInscripcionAlumnosLstBean() {
        return inscripcionAlumnosLstBean;
    }

    public void setInscripcionAlumnosLstBean(InscripcionAlumnosLstBean inscripcionAlumnosLstBean) {
        this.inscripcionAlumnosLstBean = inscripcionAlumnosLstBean;
    }

    public AlumnoLstBean getAlumnoLstBean() {
        return alumnoLstBean;
    }

    public void setAlumnoLstBean(AlumnoLstBean alumnoLstBean) {
        this.alumnoLstBean = alumnoLstBean;
    }

    public CohorteLstBean getCohorteLstBean() {
        return cohorteLstBean;
    }

    public void setCohorteLstBean(CohorteLstBean cohorteLstBean) {
        this.cohorteLstBean = cohorteLstBean;
    }

    public InscripcionAlumnos getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(InscripcionAlumnos inscripcion) {
        this.inscripcion = inscripcion;
    }

    /**
     * Creates a new instance of DocenteBean
     */
    @PostConstruct
    private void init() {
        inscripcion = new InscripcionAlumnos();
    }

    /**
     * define el action
     *
     * @param nuevo si es nuevo o modificar*
     */
    public void definirActionBoton(boolean nuevo) {
        if (nuevo) {
            this.getInscripcionAlumnosLstBean().setiTipoBoton(0);
        } else {
            try {
                this.getInscripcionAlumnosLstBean().setiTipoBoton(1);
//                System.out.println("getAlumno: "+this.getInscripcionAlumnosLstBean().getInscripcion().getAlumno());
//                System.out.println("getCohorte: "+this.getInscripcionAlumnosLstBean().getInscripcion().getCohorte());
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String path = context.getRequestContextPath() + "/InscripcionAlumnosEdit.xhtml?faces-redirect=true";
                context.redirect(path);
            } catch (IOException ex) {
                Logger.getLogger(CohorteBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }// fin definirActionBoton

    public InscripcionAlumnosBean() {
    }

    public void alta() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            if (this.getAlumnoLstBean().getAlumnoSelect() != null 
                    && this.getAlumnoLstBean().getAlumnoSelect().getId() != null) {
                if (this.getCohorteLstBean().getCohorteSelect() != null 
                        && this.getCohorteLstBean().getCohorteSelect().getId() != null) {
                    if (validar(this.getAlumnoLstBean().getAlumnoSelect().getDni(), this.getCohorteLstBean().getCohorteSelect().getId())) {
                        sMensaje = "El Alumno ya se encuentra inscripto el la Cohorte Seleccionada";
                        severity = FacesMessage.SEVERITY_ERROR;
                    } else {
                        inscripcion.setAlumno(this.getAlumnoLstBean().getAlumnoSelect());
                        inscripcion.setCohorte(this.getCohorteLstBean().getCohorteSelect());
                        inscripcion.setActivo(true);
                        inscripcionAlumnosRNLocal.create(inscripcion);
                        inscripcionAlumnosLstBean.getInscripcionesAlumnos().add(inscripcion);
                        RequestContext.getCurrentInstance().update("frmPri:otAlumno");
                        RequestContext.getCurrentInstance().update("frmPri:otCohorte");
                        RequestContext.getCurrentInstance().update("frmPri:dInscripcion");
                        RequestContext.getCurrentInstance().update("frmPri:dtInscripciones");
                        System.out.println("Inscripcion Registrada");
                        sMensaje = "Inscripcion Registrada";
                        severity = FacesMessage.SEVERITY_INFO;
                    }
                } else {
                    sMensaje = "Debe seleccionar una cohorte";
                    severity = FacesMessage.SEVERITY_ERROR;
                }
            } else {
                sMensaje = "Debe seleccionar un alumno";
                severity = FacesMessage.SEVERITY_ERROR;
            }
        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    public void modificar(InscripcionAlumnos i) {
        this.inscripcion = i;
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            inscripcionAlumnosRNLocal.edit(inscripcion);
            inscripcionAlumnosLstBean.cargarInscripciones();

            this.setInscripcion(new InscripcionAlumnos());

            //RequestContext.getCurrentInstance().update("frmPri:itDescripcionCohorte");
            //RequestContext.getCurrentInstance().update("frmPri:itCantidadCuotas");
            //RequestContext.getCurrentInstance().update("frmPri:itImporteCuota");
            RequestContext.getCurrentInstance().update("frmPri:dtInscripciones");

            sMensaje = "La Inscripcion del Alumno seleccionado ha sido Modificada, Click en Salir";
            severity = FacesMessage.SEVERITY_INFO;

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    /**
     * elimina una cohorte
     *
     * @param ia inscripcion alumnos
     * @param bEstado estado borrado o no
     */
    public void eliminar(InscripcionAlumnos ia) {
        //System.out.println("entro Eliminar");
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            inscripcionAlumnosRNLocal.remove(ia);
            this.inscripcionAlumnosLstBean.cargarInscripciones();
            RequestContext.getCurrentInstance().update("frmPri:otAlumno");
            RequestContext.getCurrentInstance().update("frmPri:otCohorte");
            RequestContext.getCurrentInstance().update("frmPri:dInscripcion");
            RequestContext.getCurrentInstance().update("frmPri:dtInscripciones");
            severity = FacesMessage.SEVERITY_INFO;
            sMensaje = "Se eliminó la inscripcion";

        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al eliminar la inscripcion: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin eliminar

    public void limpiar() {
        this.setInscripcion(new InscripcionAlumnos());
        this.alumnoLstBean.setAlumnoSelect(new Alumno());
        this.cohorteLstBean.setCohorteSelect(new Cohorte());
    }//fin limpiar

    private boolean validar(String dni, Long id) {
        boolean existeInscripto = false;
        try {
            if (!this.inscripcionAlumnosRNLocal.findAlumnoCohorte(dni, id).isEmpty()) {
                //System.out.println("Existe Inscripcion"+this.inscripcionAlumnosRNLocal.findAlumnoCohorte(dni, id));
                existeInscripto = true;
            } else {
                existeInscripto = false;
                //System.out.println("Not Exist Inscripcion");
            }
        } catch (Exception ex) {
            Logger.getLogger(InscripcionAlumnosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existeInscripto;

    }

}
