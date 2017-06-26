/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.InscripcionAlumnos;
import RN.InscripcionAlumnosRNLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@SessionScoped
public class InscripcionAlumnosLstBean implements Serializable {

    @EJB
    private InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal;

    @ManagedProperty(value = "#{alumnoLstBean}")
    private AlumnoLstBean alumnoLstBean;

    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;

    private InscripcionAlumnos inscripcion;

    private List<InscripcionAlumnos> inscripcionesAlumnos; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal

    private List<InscripcionAlumnos> filtroInscripcionesAlumnos;
    private List<SelectItem> lstSICohorte;//lo usamos para cargar la lista de cohortes 

    private String sMensaje;

    CommandButton btnSelect;

    private DataTable tablaInscripciones;
    
    private int iTipoBoton;

    /**
     * Creates a new instance of UsuarioLstBean
     */
    public InscripcionAlumnosLstBean() {
    }

    @PostConstruct
    private void init() {
        cargarInscripciones();
    }

    public List<InscripcionAlumnos> getFiltroInscripcionesAlumnos() {
        return filtroInscripcionesAlumnos;
    }

    public void setFiltroInscripcionesAlumnos(List<InscripcionAlumnos> filtroInscripcionesAlumnos) {
        this.filtroInscripcionesAlumnos = filtroInscripcionesAlumnos;
    }

    public InscripcionAlumnosRNLocal getInscripcionAlumnosRNLocal() {
        return inscripcionAlumnosRNLocal;
    }

    public void setInscripcionAlumnosRNLocal(InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal) {
        this.inscripcionAlumnosRNLocal = inscripcionAlumnosRNLocal;
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

    public List<InscripcionAlumnos> getInscripcionesAlumnos() {
        return inscripcionesAlumnos;
    }

    public void setInscripcionesAlumnos(List<InscripcionAlumnos> inscripcionesAlumnos) {
        this.inscripcionesAlumnos = inscripcionesAlumnos;
    }

    public CommandButton getBtnSelect() {
        return btnSelect;
    }

    public void setBtnSelect(CommandButton btnSelect) {
        this.btnSelect = btnSelect;
    }

    public DataTable getTablaInscripciones() {
        return tablaInscripciones;
    }

    public void setTablaInscripciones(DataTable tablaInscripciones) {
        this.tablaInscripciones = tablaInscripciones;
    }

    public List<SelectItem> getLstSICohorte() {
        return lstSICohorte;
    }

    public void setLstSICohorte(List<SelectItem> lstSICohorte) {
        this.lstSICohorte = lstSICohorte;
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public AlumnoLstBean getAlumnoLstBean() {
        return alumnoLstBean;
    }

    public void setAlumnoLstBean(AlumnoLstBean alumnoLstBean) {
        this.alumnoLstBean = alumnoLstBean;
    }

    public void definirActionBoton(ActionEvent e) {
        btnSelect = (CommandButton) e.getSource();
    }

    public int getiTipoBoton() {
        return iTipoBoton;
    }

    public void setiTipoBoton(int iTipoBoton) {
        this.iTipoBoton = iTipoBoton;
    }
    
    

    public void cargarInscripciones() {
        try {
            this.setInscripcionesAlumnos(this.inscripcionAlumnosRNLocal.findAll()); 
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

}
