/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Egresos.PagosDocente;
import RN.PagosDocenteRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@ViewScoped
public class PagosDocenteLstBean implements Serializable {

    @EJB
    private PagosDocenteRNLocal pagosDocenteRNLocal;//hacemos la referencia para poder utilizar el metodo findall
    private List<PagosDocente> lstPagosDocente; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSIPagoDocente;//Aca se guarda el item seleccionado de la lista

    private DataTable tablaPagoDocente;

    private int iTipoBoton;

    private int docProv; //docente=1, proveedor=2  en alta pagos docente
    private Date fechaFin;
    private Date fechaIni;
    private String dni;

    /**
     * Creates a new instance of DocenteLstBean
     */
    public PagosDocenteLstBean() {

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    @PostConstruct
    private void init() {
        this.cargarPagosDocente();
    }

    public PagosDocenteRNLocal getPagosDocenteRNLocal() {
        return pagosDocenteRNLocal;
    }

    public void setPagosDocenteRNLocal(PagosDocenteRNLocal pagosDocenteRNLocal) {
        this.pagosDocenteRNLocal = pagosDocenteRNLocal;
    }

    public List<PagosDocente> getLstPagosDocente() {
        return lstPagosDocente;
    }

    public void setLstPagosDocente(List<PagosDocente> lstPagosDocente) {
        this.lstPagosDocente = lstPagosDocente;
    }

    public List<SelectItem> getLstSIPagoDocente() {
        return lstSIPagoDocente;
    }

    public void setLstSIPagoDocente(List<SelectItem> lstSIPagoDocente) {
        this.lstSIPagoDocente = lstSIPagoDocente;
    }

    public DataTable getTablaPagoDocente() {
        return tablaPagoDocente;
    }

    public void setTablaPagoDocente(DataTable tablaPagoDocente) {
        this.tablaPagoDocente = tablaPagoDocente;
    }

    public int getiTipoBoton() {
        return iTipoBoton;
    }

    public void setiTipoBoton(int iTipoBoton) {
        this.iTipoBoton = iTipoBoton;
    }

    public int getDocProv() {
        return docProv;
    }

    public void setDocProv(int docProv) {
        this.docProv = docProv;
    }

    public void cargarPagosDocente() {
        try {
            this.setLstPagosDocente(this.pagosDocenteRNLocal.findAllDesc());
            //this.setLstDocente(new ArrayList<Docente>());
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }//FIN CARGAR DOCENTES

    /*   public Docente getDocenteSeleccionadoDeTabla() {
     return (Docente) this.tablaDocente.getRowData();
     }*/
    /**
     * Buscar historial de operaciones entre dos fechas
     */
    public void buscarEgresosFechas() {
        //System.out.println("entro buscarFechaCohortes");
        FacesMessage fm;
        try {
            //aumento un dia a la fecha fin para que la busqueda sea menor o igual
            if (fechaIni != null && fechaFin != null) {
                this.setLstPagosDocente(pagosDocenteRNLocal.findPagosGeneralXFecha(this.getFechaIni(), this.getFechaFin()));
                tablaPagoDocente = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmPri:dtPagosDocente");
                tablaPagoDocente.resetValue();
                if (this.getLstPagosDocente().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if
            }
        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:cbConsultarGastos", fm);
        }//fin catch
    }

    public void buscarPagoXDni() {
        FacesMessage fm;
        try {
            if (!this.dni.equals("")) {
                this.setLstPagosDocente(pagosDocenteRNLocal.findPagosByDni(this.dni));
                tablaPagoDocente = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmPri:dtPagosDocente");
                tablaPagoDocente.resetValue();

                if (this.getLstPagosDocente().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if

            } else {
                this.setLstPagosDocente(pagosDocenteRNLocal.findAll());
            }

        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:btnDniPD", fm);
        }//fin catch

    }
}
