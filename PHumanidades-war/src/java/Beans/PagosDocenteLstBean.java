/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Egresos.PagosDocente;
import RN.PagosDocenteRNLocal;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class PagosDocenteLstBean implements Serializable {

    @EJB
    private PagosDocenteRNLocal pagosDocenteRNLocal;//hacemos la referencia para poder utilizar el metodo findall

    @ManagedProperty(value = "#{usuarioLogerBean}")
    private UsuarioLogerBean usuarioLogerBean;

    private List<PagosDocente> lstPagosDocente; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSIPagoDocente;//Aca se guarda el item seleccionado de la lista

    private DataTable tablaPagoDocente;

    private int iTipoBoton;

    private String dni;
    private Date fechaFin;
    private Date fechaIni;

    /**
     * Creates a new instance of DocenteLstBean
     */
    public PagosDocenteLstBean() {

    }

    public UsuarioLogerBean getUsuarioLogerBean() {
        return usuarioLogerBean;
    }

    public void setUsuarioLogerBean(UsuarioLogerBean usuarioLogerBean) {
        this.usuarioLogerBean = usuarioLogerBean;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @PostConstruct
    private void init() {
        this.cargarPagosDocenteAnioActual();
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

    public void initialize() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            cargarPagosDocenteAnioActual();
        }
    }

    public void cargarPagosDocenteAnioActual() {
        try {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            this.setLstPagosDocente(this.pagosDocenteRNLocal.findAllDescAnio(year));
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
                this.setLstPagosDocente(pagosDocenteRNLocal.findPagosGeneralXFecha(this.fechaIni, this.fechaFin));
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
