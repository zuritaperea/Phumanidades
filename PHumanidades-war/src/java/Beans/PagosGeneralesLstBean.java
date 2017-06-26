/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Egresos.PagosDocente;
import RN.PagosDocenteRNLocal;
import java.io.Serializable;
import java.util.Date;
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
public class PagosGeneralesLstBean implements Serializable {

    @EJB
    private PagosDocenteRNLocal pagoGeneralRNLocal;//hacemos la referencia para poder utilizar el metodo findall
    private List<PagosDocente> lstGastoGeneral; //Cargamos la lista de pagos retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSIGastoGeneral;//Aca se guarda el item seleccionado de la lista

    private DataTable tablaGastoGeneral;

    private int iTipoBoton;

    private Date fechaIni;
    private Date fechaFin;

    /**
     * Creates a new instance of DocenteLstBean
     */
    public PagosGeneralesLstBean() {

    }

    @PostConstruct
    private void init() {
        // this.cargarPagosGeneral();
        fechaIni = new Date();
        fechaFin = new Date();
       // this.cargarPagosGeneral();
    }

    public List<PagosDocente> getLstGastoGeneral() {
        return lstGastoGeneral;
    }

    public void setLstGastoGeneral(List<PagosDocente> lstGastoGeneral) {
        this.lstGastoGeneral = lstGastoGeneral;
    }

    public List<SelectItem> getLstSIGastoGeneral() {
        return lstSIGastoGeneral;
    }

    public void setLstSIGastoGeneral(List<SelectItem> lstSIGastoGeneral) {
        this.lstSIGastoGeneral = lstSIGastoGeneral;
    }

    public DataTable getTablaGastoGeneral() {
        return tablaGastoGeneral;
    }

    public void setTablaGastoGeneral(DataTable tablaGastoGeneral) {
        this.tablaGastoGeneral = tablaGastoGeneral;
    }

    public int getiTipoBoton() {
        return iTipoBoton;
    }

    public void setiTipoBoton(int iTipoBoton) {
        this.iTipoBoton = iTipoBoton;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void cargarPagosGeneral() {
        try {
            this.setLstGastoGeneral(this.pagoGeneralRNLocal.findAllDesc());

        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }//FIN CARGAR Pagos

    public void cargarPagosGeneralXFecha() {
        try {
            if (fechaIni != null && fechaFin != null) {
                this.setLstGastoGeneral(this.pagoGeneralRNLocal.findPagosGeneralXFecha(fechaIni, fechaFin));

            } else {
                this.setLstGastoGeneral(pagoGeneralRNLocal.findAll());
            }
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }//FIN CARGAR Pagos

    public PagosDocente getGastoGeneralSeleccionado() {
        return (PagosDocente) this.tablaGastoGeneral.getRowData();
    }

    /*   public Docente getDocenteSeleccionadoDeTabla() {
     return (Docente) this.tablaDocente.getRowData();
     }*/
}
