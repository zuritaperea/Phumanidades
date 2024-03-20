/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.InformePagoAlumnoFacade;
import Entidades.Carreras.Cohorte;
import Entidades.Ingresos.InformePagoAlumno;
import Entidades.Ingresos.Ingreso;
import Entidades.Persona.Alumno;
import RN.AlumnoRNLocal;
import RN.InscripcionAlumnosRNLocal;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;

/**
 *
 * @author hugo
 */
@ManagedBean
@RequestScoped
public class InformePagoAlumnoBean implements Serializable {

    /**
     * Creates a new instance of InformePagoAlumnoBean
     */
    @EJB
    private InformePagoAlumnoFacade informePagoAlumnoFacade;
    private List<InformePagoAlumno> items = null;
    private InformePagoAlumno selected;
    private List<Alumno> lstAlumno;
    private Alumno alumno;
    private String textoBusqueda;
    private String documento;
    @EJB
    private AlumnoRNLocal alumnoRNLocal;
    @EJB
    private InscripcionAlumnosRNLocal inscripcionAlumnoRNLocal;
    
    private List<Cohorte> lstCohorteAlumnoPago;

    public InformePagoAlumnoBean() {
    }

    public InformePagoAlumnoFacade getInformePagoAlumnoFacade() {
        return informePagoAlumnoFacade;
    }

    public void setInformePagoAlumnoFacade(InformePagoAlumnoFacade informePagoAlumnoFacade) {
        this.informePagoAlumnoFacade = informePagoAlumnoFacade;
    }

    public List<InformePagoAlumno> getItems() {
        return items;
    }

    public void setItems(List<InformePagoAlumno> items) {
        this.items = items;
    }

    public InformePagoAlumno getSelected() {
        return selected;
    }

    public void setSelected(InformePagoAlumno selected) {
        this.selected = selected;
    }

    public List<Alumno> getLstAlumno() {
        return lstAlumno;
    }

    public void setLstAlumno(List<Alumno> lstAlumno) {
        this.lstAlumno = lstAlumno;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
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

    public InscripcionAlumnosRNLocal getInscripcionAlumnoRNLocal() {
        return inscripcionAlumnoRNLocal;
    }

    public void setInscripcionAlumnoRNLocal(InscripcionAlumnosRNLocal inscripcionAlumnoRNLocal) {
        this.inscripcionAlumnoRNLocal = inscripcionAlumnoRNLocal;
    }

    public List<Cohorte> getLstCohorteAlumnoPago() {
        return lstCohorteAlumnoPago;
    }

    public void setLstCohorteAlumnoPago(List<Cohorte> lstCohorteAlumnoPago) {
        this.lstCohorteAlumnoPago = lstCohorteAlumnoPago;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    

    public void abrirDlgFindAlumnoConsulta() {
        // btnSelect = (CommandButton) e.getSource();
        this.setItems(new ArrayList<InformePagoAlumno>());
        this.setLstAlumno(new ArrayList<Alumno>());
        //agregados para probar
        this.setAlumno(new Alumno());
        this.setTextoBusqueda("");
        RequestContext.getCurrentInstance().execute("PF('dlgFindAlumnoPago').show();");
    }

    public void buscarAlumnoDni() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        System.out.println("entroo buscar dni consulta: " + this.getTextoBusqueda());
        try {
            if (this.getTextoBusqueda()==null) {
                System.out.println("cadena vacia" + this.getTextoBusqueda());
                sMensaje = "Ingrese un numero de Documento ";
                severity = FacesMessage.SEVERITY_INFO;
            } else {
                System.out.println("cpor el else " + textoBusqueda);
                this.findAlumnoDniPago(this.getTextoBusqueda());
            }

        } catch (Exception e) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + e.getMessage();
            //this.getMensajeBean().setMensaje("Error: " + e.getMessage());
            // RequestContext.getCurrentInstance().update("dMensaje");
            // RequestContext.getCurrentInstance().execute("dlgMensaje.show()");
        }
    }

    public void findAlumnoDniPago(String dni) throws Exception {
        System.out.println("entroo FIND ALUMNO DNI PAGO");
        this.setLstAlumno(new ArrayList<Alumno>());
        this.lstAlumno.add(alumnoRNLocal.findByAlumnoDni(dni));

    }

    public void devolverAlumnoDialog() {
        obtenerCohortesAlumnosPago();
        RequestContext.getCurrentInstance().update("frmPri:pnAlumno");//outPutText Consulta Publica Alumnos

    }

    public void obtenerCohortesAlumnosPago() {
        try {
            //para obtener las cohorte del alumno seleccionado primero tengo que buscar las cohortes en las que se inncribi
            //el alumno y en las que esta activo solamente
            //System.out.println(this.inscripcionAlumnosRNLocal.alumnoFindCohortes(this.alumnoLstBean.getAlumnoSelect()));
            this.setLstCohorteAlumnoPago(this.inscripcionAlumnoRNLocal.alumnoFindCohortes(this.getAlumno()));
            RequestContext.getCurrentInstance().update("frmPri:pnCohortesPago");

        } catch (Exception ex) {
            Logger.getLogger(AlumnoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
