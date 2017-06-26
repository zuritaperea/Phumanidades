/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cuenta;
import Entidades.Ingresos.Ingreso;
import RN.CuentaRNLocal;
import RN.IngresoRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CobroCuotasAlumnosLstBean implements Serializable {

    @EJB
    private IngresoRNLocal ingresoCuotaRNLocal;//hacemos la referencia para poder utilizar el metodo findall
    @EJB
    private CuentaRNLocal cuentaRNLocal;//hacemos la referencia para poder utilizar el metodo get cuenta

    private List<Ingreso> lstCobroCuotas; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSICobroCuota;//Aca se guarda el item seleccionado de la lista

    private DataTable tablaIngreso;
    private Ingreso ingreso;
    private Cuenta cuenta;
    private int iTipoBoton;
    private Date fechaIni;
    private Date fechaFin;
    private Date fechaPago;

    /**
     * Creates a new instance of DocenteLstBean
     */
    public CobroCuotasAlumnosLstBean() {

    }

    public CuentaRNLocal getCuentaRNLocal() {
        return cuentaRNLocal;
    }

    public void setCuentaRNLocal(CuentaRNLocal cuentaRNLocal) {
        this.cuentaRNLocal = cuentaRNLocal;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public IngresoRNLocal getIngresoCuotaRNLocal() {
        return ingresoCuotaRNLocal;
    }

    public void setIngresoCuotaRNLocal(IngresoRNLocal ingresoCuotaRNLocal) {
        this.ingresoCuotaRNLocal = ingresoCuotaRNLocal;
    }

    public Ingreso getIngreso() {
        return ingreso;
    }

    public void setIngreso(Ingreso ingreso) {
        this.ingreso = ingreso;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
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

    @PostConstruct
    private void init() {
        ingreso = new Ingreso();
        this.cargarIngresos();
    }

    public List<Ingreso> getLstCobroCuotas() {
        return lstCobroCuotas;
    }

    public void setLstCobroCuotas(List<Ingreso> lstCobroCuotas) {
        this.lstCobroCuotas = lstCobroCuotas;
    }

    public List<SelectItem> getLstSICobroCuota() {
        return lstSICobroCuota;
    }

    public void setLstSICobroCuota(List<SelectItem> lstSICobroCuota) {
        this.lstSICobroCuota = lstSICobroCuota;
    }

    public DataTable getTablaIngreso() {
        return tablaIngreso;
    }

    public void setTablaIngreso(DataTable tablaIngreso) {
        this.tablaIngreso = tablaIngreso;
    }

    public int getiTipoBoton() {
        return iTipoBoton;
    }

    public void setiTipoBoton(int iTipoBoton) {
        this.iTipoBoton = iTipoBoton;
    }

    public void cargarIngresos() {
        if (cuenta == null) {
            {
                try {
                    this.setLstCobroCuotas(new ArrayList<Ingreso>());
                    this.setLstCobroCuotas(this.ingresoCuotaRNLocal.findAllDesc());
                    //this.setLstDocente(new ArrayList<Docente>());
                } catch (Exception ex) {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex, null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }
            }
        } else {
            try {
                cargarIngresos(Integer.parseInt(cuenta.getCodigo()));
            } catch (NumberFormatException numberFormatException) {

            }
        }
    }//FIN CARGAR DOCENTES

    public void cargarIngresos(int cuenta) {
        try {
            Cuenta cuentaParametro = new Cuenta();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            try {
                cuentaParametro = cuentaRNLocal.findAllByCodigo(String.format("%03d", cuenta));
                this.setCuenta(cuentaParametro);
            } catch (Exception e) {
                System.out.println("Error: Cargando cuentas: " + e);
            }
            this.setLstCobroCuotas(new ArrayList<Ingreso>());
            this.setLstCobroCuotas(this.ingresoCuotaRNLocal.findAllByCuenta(cuentaParametro, year));

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
}
