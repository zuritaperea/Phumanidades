/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.InformePagoAlumnoFacade;
import Entidades.Ingresos.InformePagoAlumno;
import Entidades.Ingresos.Ingreso;
import Entidades.Persona.Alumno;
import RN.AlumnoRNLocal;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;

/**
 *
 * @author hugo
 */
@ManagedBean(name = "informePagoAlumnoBean")
@SessionScoped
public class InformePagoAlumnoBean implements Serializable {

    /**
     * Creates a new instance of InformePagoAlumnoBean
     */
    @EJB
    private InformePagoAlumnoFacade informePagoAlumnoFacade;
    private List<InformePagoAlumno> items = null;
    private InformePagoAlumno selected;
    private List<Alumno> lstAlumno;
    private String textoBusqueda;
    @EJB
    private AlumnoRNLocal alumnoRNLocal;

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

    public void abrirDlgFindAlumnoConsulta() {
        // btnSelect = (CommandButton) e.getSource();
        this.setItems(new ArrayList<InformePagoAlumno>());
        this.setLstAlumno(new ArrayList<Alumno>());
        RequestContext.getCurrentInstance().execute("PF('dlgFindAlumnoPago').show();");
    }

    public void buscarAlumnoDni() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        System.out.println("entroo buscar dni consulta");
        try {
            if (textoBusqueda.equals("")) {
                System.out.println("cadena vacia" + textoBusqueda);
                sMensaje = "Ingrese un numero de Documento ";
                severity = FacesMessage.SEVERITY_INFO;
            } else {
                System.out.println("cpor el else " + textoBusqueda);
                this.findAlumnoDniPago(textoBusqueda);
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

        this.setLstAlumno(new ArrayList<Alumno>());
        this.lstAlumno.add(alumnoRNLocal.findByAlumnoDni(dni));
        
    }
    
    
}
