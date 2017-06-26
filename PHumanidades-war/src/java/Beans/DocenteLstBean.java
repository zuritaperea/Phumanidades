/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Persona.Docente;
import RN.DocenteRNLocal;
import java.io.Serializable;
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
public class DocenteLstBean implements Serializable {

    @EJB
    private DocenteRNLocal docenteRNLocal;//hacemos la referencia para poder utilizar el metodo findall
    private List<Docente> lstDocente; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSIDocente;//Aca se guarda el item seleccionado de la lista

    private DataTable tablaDocente;

    public Docente docenteSeleccionado;

    /**
     * Creates a new instance of DocenteLstBean
     */
    public DocenteLstBean() {

    }

    @PostConstruct
    private void init() {
        this.cargarDocente();
    }

    public DocenteRNLocal getDocenteRNLocal() {
        return docenteRNLocal;
    }

    public void setDocenteRNLocal(DocenteRNLocal docenteRNLocal) {
        this.docenteRNLocal = docenteRNLocal;
    }

    public List<Docente> getLstDocente() {
        return lstDocente;
    }

    public void setLstDocente(List<Docente> lstDocente) {
        this.lstDocente = lstDocente;
    }

    public List<SelectItem> getLstSIDocente() {
        return lstSIDocente;
    }

    public void setLstSIDocente(List<SelectItem> lstSIDocente) {
        this.lstSIDocente = lstSIDocente;
    }

    public DataTable getTablaDocente() {
        return tablaDocente;
    }

    public void setTablaDocente(DataTable tablaDocente) {
        this.tablaDocente = tablaDocente;
    }

    public Docente getDocenteSeleccionado() {
        return docenteSeleccionado;
    }

    public void setDocenteSeleccionado(Docente docenteSeleccionado) {
        this.docenteSeleccionado = docenteSeleccionado;
    }

    public void cargarDocente() {
        try {
            this.setLstDocente(this.docenteRNLocal.findAll());
            //this.setLstDocente(new ArrayList<Docente>());
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }//FIN CARGAR DOCENTES

    public void findDocenteDni(String dni) {

        this.setLstDocente(docenteRNLocal.findByDocenteDni(dni));
    }

    public void findDocenteApellido(String apellido) throws Exception {

        this.setLstDocente(docenteRNLocal.findLikeNombreApellido(apellido));
    }

    public void findAllDocentes() throws Exception {
        this.setLstDocente(docenteRNLocal.findAll());
    }

    public Docente getDocenteSeleccionadoDeTabla() {
        return (Docente) this.tablaDocente.getRowData();
    }

}
