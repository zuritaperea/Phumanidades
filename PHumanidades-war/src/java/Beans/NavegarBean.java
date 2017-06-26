/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Carrera;
import Entidades.Carreras.Cohorte;
import Entidades.Carreras.Cuenta;
import Entidades.Egresos.PagosDocente;
import Entidades.Ingresos.Ingreso;
import Entidades.Persona.Alumno;
import Entidades.Persona.CorreoElectronico;
import Entidades.Persona.Docente;
import Entidades.Persona.Proveedor;
import Entidades.Persona.Telefono;
import RN.IngresoRNLocal;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class NavegarBean {

    @ManagedProperty(value = "#{docenteLstBean}")
    private DocenteLstBean docenteLstBean;

    @ManagedProperty(value = "#{proveedorLstBean}")
    private ProveedorLstBean proveedorLstBean;

    @ManagedProperty(value = "#{alumnoLstBean}")
    private AlumnoLstBean alumnoLstBean;

    @ManagedProperty(value = "#{carreraLstBean}")
    private CarreraLstBean carreraLstBean;

    @ManagedProperty(value = "#{pagosDocenteLstBean}")
    private PagosDocenteLstBean pagosDocenteLstBean;

    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;

    @ManagedProperty(value = "#{listadoTelefonosBean}")
    private ListadoTelefonosBean listadoTelefonosBean;

    @ManagedProperty(value = "#{listadoEmailBean}")
    private ListadoEmailBean listadoEmailBean;

    @ManagedProperty(value = "#{cobroCuotasAlumnosLstBean}")
    private CobroCuotasAlumnosLstBean cobroCuotasAlumnosLstBean;

    @ManagedProperty(value = "#{pagosGeneralesLstBean}")
    private PagosGeneralesLstBean pagosGeneralesLstBean;

    @ManagedProperty(value = "#{cuentaLstBean}")
    private CuentaLstBean cuentaLstBean;

    @ManagedProperty(value = "#{numeroCuentaBean}")
    private NumeroCuentaBean numeroCuentaBean;

    @EJB
    IngresoRNLocal ingresoCuotaRNLocal;

    public ProveedorLstBean getProveedorLstBean() {
        return proveedorLstBean;
    }

    public void setProveedorLstBean(ProveedorLstBean proveedorLstBean) {
        this.proveedorLstBean = proveedorLstBean;
    }

    public PagosGeneralesLstBean getPagosGeneralesLstBean() {
        return pagosGeneralesLstBean;
    }

    public void setPagosGeneralesLstBean(PagosGeneralesLstBean pagosGeneralesLstBean) {
        this.pagosGeneralesLstBean = pagosGeneralesLstBean;
    }

    /**
     * Creates a new instance of NavegarBean
     */
    public NavegarBean() {
        docenteLstBean = new DocenteLstBean();
    }

    public DocenteLstBean getDocenteLstBean() {
        return docenteLstBean;
    }

    public void setDocenteLstBean(DocenteLstBean docenteLstBean) {
        this.docenteLstBean = docenteLstBean;
    }

    public AlumnoLstBean getAlumnoLstBean() {
        return alumnoLstBean;
    }

    public void setAlumnoLstBean(AlumnoLstBean alumnoLstBean) {
        this.alumnoLstBean = alumnoLstBean;
    }

    public CarreraLstBean getCarreraLstBean() {
        return carreraLstBean;
    }

    public void setCarreraLstBean(CarreraLstBean carreraLstBean) {
        this.carreraLstBean = carreraLstBean;
    }

    public PagosDocenteLstBean getPagosDocenteLstBean() {
        return pagosDocenteLstBean;
    }

    public void setPagosDocenteLstBean(PagosDocenteLstBean pagosDocenteLstBean) {
        this.pagosDocenteLstBean = pagosDocenteLstBean;
    }

    public CohorteLstBean getCohorteLstBean() {
        return cohorteLstBean;
    }

    public void setCohorteLstBean(CohorteLstBean cohorteLstBean) {
        this.cohorteLstBean = cohorteLstBean;
    }

    public ListadoTelefonosBean getListadoTelefonosBean() {
        return listadoTelefonosBean;
    }

    public void setListadoTelefonosBean(ListadoTelefonosBean listadoTelefonosBean) {
        this.listadoTelefonosBean = listadoTelefonosBean;
    }

    public ListadoEmailBean getListadoEmailBean() {
        return listadoEmailBean;
    }

    public void setListadoEmailBean(ListadoEmailBean listadoEmailBean) {
        this.listadoEmailBean = listadoEmailBean;
    }

    public CobroCuotasAlumnosLstBean getCobroCuotasAlumnosLstBean() {
        return cobroCuotasAlumnosLstBean;
    }

    public void setCobroCuotasAlumnosLstBean(CobroCuotasAlumnosLstBean cobroCuotasAlumnosLstBean) {
        this.cobroCuotasAlumnosLstBean = cobroCuotasAlumnosLstBean;
    }

    public CuentaLstBean getCuentaLstBean() {
        return cuentaLstBean;
    }

    public void setCuentaLstBean(CuentaLstBean cuentaLstBean) {
        this.cuentaLstBean = cuentaLstBean;
    }

    public NumeroCuentaBean getNumeroCuentaBean() {
        return numeroCuentaBean;
    }

    public void setNumeroCuentaBean(NumeroCuentaBean numeroCuentaBean) {
        this.numeroCuentaBean = numeroCuentaBean;
    }

    public String frmUsuario() {
        return "/usuario.xhtml?faces-redirect=true";
    }

    public String frmDocente() {
        docenteLstBean.cargarDocente();
        carreraLstBean.setLstCarrerasAsoc(new ArrayList<Carrera>());
        listadoTelefonosBean.setLstTelefonos(new ArrayList<Telefono>());
        listadoTelefonosBean.setTelefono(new Telefono());
        listadoEmailBean.setLstCorreoElectronico(new ArrayList<CorreoElectronico>());
        listadoEmailBean.setCorreoElectronico(new CorreoElectronico());
        return "/Docente.xhtml?faces-redirect=true";
    }

    public String frmAlumno() {
        alumnoLstBean.cargarAlumnos();
        listadoTelefonosBean.setLstTelefonos(new ArrayList<Telefono>());
        listadoTelefonosBean.setTelefono(new Telefono());
        listadoEmailBean.setLstCorreoElectronico(new ArrayList<CorreoElectronico>());
        listadoEmailBean.setCorreoElectronico(new CorreoElectronico());
        return "/Alumno.xhtml?faces-redirect=true";
    }

    public String frmCarrera() {
        carreraLstBean.cargarCarrera();
        return "/Carrera.xhtml?faces-redirect=true";
    }

    public String frmProveedor() {
        listadoTelefonosBean.setLstTelefonos(new ArrayList<Telefono>());
        listadoTelefonosBean.setTelefono(new Telefono());
        listadoEmailBean.setLstCorreoElectronico(new ArrayList<CorreoElectronico>());
        listadoEmailBean.setCorreoElectronico(new CorreoElectronico());
        return "/Proveedor.xhtml?faces-redirect=true";
    }

    public String frmInscripcionAlumnos() {
        alumnoLstBean.setAlumnoSelect(new Alumno());
        return "/InscripcionAlumnos.xhtml?faces-redirect=true";
    }

    public String frmCohorte() {
        carreraLstBean.setLstCarrerasAsoc(new ArrayList<Carrera>());
        return "/Cohorte.xhtml?faces-redirect=true";
    }

    public String frmPagosDocentes() {
        this.getDocenteLstBean().setDocenteSeleccionado(new Docente());
        this.getProveedorLstBean().setProveedorSelect(new Proveedor());
        this.getPagosGeneralesLstBean().setFechaIni(new Date());
        this.getPagosGeneralesLstBean().setFechaFin(new Date());
        this.cuentaLstBean.setCuenta(new Cuenta());
        this.numeroCuentaBean.setNumero(0);
        carreraLstBean.setLstCarrerasDocente(new ArrayList<Carrera>());
        this.pagosDocenteLstBean.setFechaIni(new Date());
        this.pagosDocenteLstBean.setFechaFin(new Date());
        this.pagosDocenteLstBean.setLstPagosDocente(new ArrayList<PagosDocente>());
        RequestContext.getCurrentInstance().update(":frmPri:dtPagosDocente");
        return "/PagosDocentes.xhtml?faces-redirect=true";
    }

    public String frmIngresos() {
        cohorteLstBean.setLstCohortesAlumnos(new ArrayList<Cohorte>());
        this.getAlumnoLstBean().setAlumnoSelect(new Alumno());
        this.getCobroCuotasAlumnosLstBean().setFechaIni(new Date());
        this.getCobroCuotasAlumnosLstBean().setFechaFin(new Date());
        this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(new ArrayList<Ingreso>());
        this.getCobroCuotasAlumnosLstBean().setFechaPago(new Date());
        this.cuentaLstBean.setCuenta(new Cuenta());
        this.numeroCuentaBean.setNumero(0);
        this.cohorteLstBean.setNumeroRecibo(0);
        this.cohorteLstBean.setUltimaCuota(0);
        this.getCobroCuotasAlumnosLstBean().setCuenta(null);
        getCobroCuotasAlumnosLstBean().cargarIngresos();
        RequestContext.getCurrentInstance().update(":frmPri:dtCobroCuotas");
        return "/CobrosAlumnos.xhtml?faces-redirect=true";
    }

    public String frmIngresos(int cuenta) {
        cohorteLstBean.setLstCohortesAlumnos(new ArrayList<Cohorte>());
        this.getAlumnoLstBean().setAlumnoSelect(new Alumno());
        this.getCobroCuotasAlumnosLstBean().setFechaIni(new Date());
        this.getCobroCuotasAlumnosLstBean().setFechaFin(new Date());
        this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(new ArrayList<Ingreso>());
        this.getCobroCuotasAlumnosLstBean().setFechaPago(new Date());
        this.cuentaLstBean.setCuenta(new Cuenta());
        this.numeroCuentaBean.setNumero(0);
        this.cohorteLstBean.setNumeroRecibo(0);
        this.cohorteLstBean.setUltimaCuota(0);
        getCobroCuotasAlumnosLstBean().cargarIngresos(cuenta);
        RequestContext.getCurrentInstance().update(":frmPri:dtCobroCuotas");
        return "/CobrosAlumnos.xhtml?faces-redirect=true";
    }

    public String frmConsultaPagosDocentes() {
        pagosDocenteLstBean.cargarPagosDocente();
        return "/ConsultaPagosDocente.xhtml?faces-redirect=true";
    }

    public String frmConsultaCobrosAlumnos() {
        cohorteLstBean.cargarCohorte();
        return "/ConsultaCobrosAlumno.xhtml?faces-redirect=true";
    }

    public String frmConsultaAlumnosPorCohorte() {
        cohorteLstBean.cargarCohorte();
        return "/ConsultaAlumnosPorCohorte.xhtml?faces-redirect=true";
    }

    public String frmConsultaIngresoAlumnos() {
        alumnoLstBean.setAlumnoSelect(new Alumno());
        return "/ConsultaAlumnosInterna.xhtml?faces-redirect=true";
    }

    public String frmGastosGenerales() {
        this.getDocenteLstBean().setDocenteSeleccionado(new Docente());
        this.getProveedorLstBean().setProveedorSelect(new Proveedor());
        this.getPagosGeneralesLstBean().setFechaIni(new Date());
        this.getPagosGeneralesLstBean().setFechaFin(new Date());
        this.getPagosGeneralesLstBean().setLstGastoGeneral(new ArrayList<PagosDocente>());
        this.cuentaLstBean.setCuenta(new Cuenta());
        this.numeroCuentaBean.setNumero(0);
        RequestContext.getCurrentInstance().update(":frmPri:dtPagosGenerales");
        return "/PagosGenerales.xhtml?faces-redirect=true";
    }

    public String frmCarrerasDistancia() {
        return "/CarrerasDistancia.xhtml?faces-redirect=true";
    }

    public String frmConsultaCobrosGenerales() {

        return "/ConsultaCobrosGenerales.xhtml?faces-redirect=true";
    }

    public String frmConsultaPagosGenerales() {

        return "/ConsultaPagosGenerales.xhtml?faces-redirect=true";
    }

}
