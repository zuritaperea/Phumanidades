/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Persona.Alumno;
import RN.AlumnoRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@SessionScoped
public class AlumnoLstBean implements Serializable {

    @EJB
    private AlumnoRNLocal alumnoRNLocal;//hacemos la referencia para poder utilizar el metodo findall
    private List<Alumno> lstAlumno; //Cargamos la lista de Alumnos retornada por el metodo findAll del alumnoRNLocal
    private List<SelectItem> lstSIAlumno;//Aca se guarda el item alumno seleccionado de la lista
    private List<Alumno> filtroAlumnos;
    private DataTable tablaAlumno;

    private Alumno alumnoSelect;
    
    private List<Alumno> lstAlumnoConsulta;
    private Alumno alumnoSelectConsulta;
    
    /**
     * Creates a new instance of AlumnoLstBean
     */
    public AlumnoLstBean() {
    }

    @PostConstruct
    private void init() {
        cargarAlumnos();
    }

    public List<Alumno> getLstAlumno() {
        return lstAlumno;
    }

    public void setLstAlumno(List<Alumno> lstAlumno) {
        this.lstAlumno = lstAlumno;
    }

    public List<Alumno> getLstAlumnoConsulta() {
        return lstAlumnoConsulta;
    }

    public void setLstAlumnoConsulta(List<Alumno> lstAlumnoConsulta) {
        this.lstAlumnoConsulta = lstAlumnoConsulta;
    }
    
    

    public List<Alumno> getFiltroAlumnos() {
        return filtroAlumnos;
    }

    public void setFiltroAlumnos(List<Alumno> filtroAlumnos) {
        this.filtroAlumnos = filtroAlumnos;
    }

    public AlumnoRNLocal getAlumnoRNLocal() {
        return alumnoRNLocal;
    }

    public void setAlumnoRNLocal(AlumnoRNLocal alumnoRNLocal) {
        this.alumnoRNLocal = alumnoRNLocal;
    }

    public List<Alumno> getLstAlunmo() {
        return lstAlumno;
    }

    public void setLstAlunmo(List<Alumno> lstAlunmo) {
        this.lstAlumno = lstAlunmo;
    }

    public List<SelectItem> getLstSIAlumno() {
        return lstSIAlumno;
    }

    public void setLstSIAlumno(List<SelectItem> lstSIAlumno) {
        this.lstSIAlumno = lstSIAlumno;
    }

    public DataTable getTablaAlumno() {
        return tablaAlumno;
    }

    public void setTablaAlumno(DataTable tablaAlumno) {
        this.tablaAlumno = tablaAlumno;
    }

    public Alumno getAlumnoSelect() {
        return alumnoSelect;
    }

    public void setAlumnoSelect(Alumno alumnoSelect) {
        this.alumnoSelect = alumnoSelect;
    }

    public Alumno getAlumnoSelectConsulta() {
        return alumnoSelectConsulta;
    }

    public void setAlumnoSelectConsulta(Alumno alumnoSelectConsulta) {
        this.alumnoSelectConsulta = alumnoSelectConsulta;
    }
    
    

    public void cargarAlumnos() {
        try {
            this.setLstAlunmo(this.alumnoRNLocal.findAll());
            //this.setLstDocente(new ArrayList<Docente>());
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }//FIN CARGAR ALUMNOS
    }

    public Alumno getAlumnoSeleccionado() {
        return (Alumno) this.tablaAlumno.getRowData();
    }

    //INICIO BUSQUEDA ALUMNOS
    public void findAllAlumnos() throws Exception {
        this.setLstAlunmo(alumnoRNLocal.findAll());
    }

    public void findAlumnoDni(String dni) throws Exception {
       
        this.setLstAlunmo(new ArrayList<Alumno>());
        this.lstAlumno.add(alumnoRNLocal.findByAlumnoDni(dni));
    }

        public void findAlumnoDniConsulta(String dni) throws Exception {
       
            this.setLstAlumnoConsulta(new ArrayList<Alumno>());
            this.lstAlumnoConsulta.add(alumnoRNLocal.findByAlumnoDni(dni));
    }
    public void findDocenteApellido(String apellido) throws Exception {

        this.setLstAlunmo(alumnoRNLocal.findLikeNombreApellido(apellido));
    }

}
