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
import javax.faces.event.ActionEvent;
import org.primefaces.component.commandbutton.CommandButton;
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

    @ManagedProperty(value = "#{inscripcionAlumnosLstBean}")
    private InscripcionAlumnosLstBean inscripcionAlumnosLstBean;

    private InscripcionAlumnos inscripcion;
    private int iActionBtnSelect;
    private CommandButton cbAction;

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

    public InscripcionAlumnos getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(InscripcionAlumnos inscripcion) {
        this.inscripcion = inscripcion;
    }

    public InscripcionAlumnosBean() {
        inscripcion = new InscripcionAlumnos();

    }

    public int getiActionBtnSelect() {
        return iActionBtnSelect;
    }

    public void setiActionBtnSelect(int iActionBtnSelect) {
        this.iActionBtnSelect = iActionBtnSelect;
    }

    public CommandButton getCbAction() {
        return cbAction;
    }

    public void setCbAction(CommandButton cbAction) {
        this.cbAction = cbAction;
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

    public void alta() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            if (this.getInscripcionAlumnosLstBean().getAlumnoLstBean().getAlumnoSelect() != null
                    && this.getInscripcionAlumnosLstBean().getAlumnoLstBean().getAlumnoSelect().getId() != null) {
                if (this.getInscripcionAlumnosLstBean().getCohorteLstBean().getCohorteSelect() != null
                        && this.getInscripcionAlumnosLstBean().getCohorteLstBean().getCohorteSelect().getId() != null) {
                    if (validar(this.getInscripcionAlumnosLstBean().getAlumnoLstBean().getAlumnoSelect().getDni(),
                            this.getInscripcionAlumnosLstBean().getCohorteLstBean().getCohorteSelect().getId())) {
                        sMensaje = "El Alumno ya se encuentra inscripto el la Cohorte Seleccionada";
                        severity = FacesMessage.SEVERITY_ERROR;
                    } else {
                        inscripcion.setAlumno(this.getInscripcionAlumnosLstBean().getAlumnoLstBean().getAlumnoSelect());
                        inscripcion.setCohorte(this.getInscripcionAlumnosLstBean().getCohorteLstBean().getCohorteSelect());

                        inscripcion.setActivo(true);
                        inscripcionAlumnosRNLocal.create(inscripcion);

                        this.inscripcionAlumnosLstBean.cargarInscripciones();

                        sMensaje = "Inscripcion Registrada";

                        severity = FacesMessage.SEVERITY_INFO;
                        limpiar();
                        this.setInscripcion(new InscripcionAlumnos());

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

    public void actionBtn() {

        switch (this.getiActionBtnSelect()) {
            case 0:
                //System.out.println("Entro opcion 0 create()");
                this.alta();
                break;
            case 1:
                this.modificar(inscripcion);
                break;
            case 2:
                //borra el campo
                this.eliminar(inscripcion);
                //this.delete(Boolean.TRUE);
                break;
        }//fin switch

    }//fin actionBtn

    public void setBtnSelect(ActionEvent e) {
        CommandButton btnSelect = (CommandButton) e.getSource();

        //activo el boton
        try {
            this.getCbAction().setDisabled(false);
            switch (btnSelect.getId()) {
                case "cbCreate":
                    this.setiActionBtnSelect(0);
                    this.getCbAction().setValue("Guardar");
                    break;
                case "cbDelete":
                    this.setiActionBtnSelect(2);
                    this.getCbAction().setValue("Eliminar");
                    break;
                case "cbEdit":
                    this.setiActionBtnSelect(1);
                    this.getCbAction().setValue("Modificar");
                    break;
            }
        } catch (Exception ex) {
        }

    }

    public void modificar(InscripcionAlumnos i) {
        this.inscripcion = i;
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            inscripcion.setAlumno(this.getInscripcionAlumnosLstBean().getAlumnoLstBean().getAlumnoSelect());
            inscripcion.setCohorte(this.getInscripcionAlumnosLstBean().getCohorteLstBean().getCohorteSelect());

            inscripcionAlumnosRNLocal.edit(inscripcion);
            inscripcionAlumnosLstBean.cargarInscripciones();
            limpiar();

            sMensaje = "La Inscripcion del Alumno seleccionado ha sido Modificada";
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
            limpiar();
            severity = FacesMessage.SEVERITY_INFO;
            sMensaje = "Se eliminó la inscripcion";
            this.getCbAction().setValue("Eliminar");
            this.getCbAction().setDisabled(true);

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
        this.getInscripcionAlumnosLstBean().getAlumnoLstBean().setAlumnoSelect(new Alumno());
        this.getInscripcionAlumnosLstBean().getCohorteLstBean().setCohorteSelect(new Cohorte());
        RequestContext.getCurrentInstance().update("frmPri:otAlumno");
        RequestContext.getCurrentInstance().update("frmPri:otCohorte");
        RequestContext.getCurrentInstance().update("frmPri:dInscripcion");
        RequestContext.getCurrentInstance().update("frmPri:dtInscripciones");
        RequestContext.getCurrentInstance().execute("PF('dtInscripciones').filter();");

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
