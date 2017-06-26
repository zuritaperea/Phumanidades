/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cohorte;
import Entidades.Carreras.Contador005;
import Entidades.Carreras.Contador025;
import Entidades.Carreras.Cuenta;
import Entidades.Ingresos.Ingreso;
import Entidades.Persona.Alumno;
import RN.Contador005RNLocal;
import RN.Contador025RNLocal;
import RN.IngresoRNLocal;
import RN.InscripcionAlumnosRNLocal;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class CobroCuotasAlumnosBean implements Serializable {

    @EJB
    private IngresoRNLocal ingresoCuotaRNLocal;

    @EJB
    private Contador005RNLocal contador005RNLocal;

    @EJB
    private Contador025RNLocal contador025RNLocal;

    @ManagedProperty(value = "#{usuarioLogerBean}")
    private UsuarioLogerBean usuarioLogerBean;

    @ManagedProperty(value = "#{navegarBean}")
    private NavegarBean navegarBean;

    @EJB
    private InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal;

    @ManagedProperty(value = "#{cobroCuotasAlumnosLstBean}")
    private CobroCuotasAlumnosLstBean cobroCuotasAlumnosLstBean;

    @ManagedProperty(value = "#{alumnoLstBean}")
    private AlumnoLstBean alumnoLstBean;

    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;

    @ManagedProperty(value = "#{cuentaLstBean}")
    private CuentaLstBean cuentaLstBean;

    @ManagedProperty(value = "#{numeroCuentaBean}")
    private NumeroCuentaBean numeroCuentaBean;

    private Ingreso ingreso;
    private int iActionBtnSelect;
    private CommandButton cbAction;

    private Contador005 contador005;
    private Contador025 contador025;
    private Date fechaIni;
    private Date fechaFin;
    private Date fechaPago;
    int ultimaCuota;
//Inicio Busqueda Carrera   
    String dni; //USADO PARA LA CONSULTA INICIAL - MEDIANTE ESTE DNI TRAEMOS LOS PAGOS
    private List<Ingreso> ingresos;
    private DataTable tablaIngresos;

    /**
     * Creates a new instance of DocenteBean
     */
    @PostConstruct
    private void init() {
        ultimaCuota = 0;
        ingreso = new Ingreso();
        cbAction = new CommandButton();
        //cargarLstFormaPago();
        //cargarLstTipoComprobante();
        dni = "";
        fechaPago = new Date();
        contador005 = new Contador005();
        contador025 = new Contador025();
        if (cobroCuotasAlumnosLstBean.getCuenta() != null && cobroCuotasAlumnosLstBean.getCuenta().getCodigo() != null) {
            try {
                cobroCuotasAlumnosLstBean.cargarIngresos(Integer.parseInt(cobroCuotasAlumnosLstBean.getCuenta().getCodigo()));
            } catch (NumberFormatException numberFormatException) {
                cobroCuotasAlumnosLstBean.cargarIngresos();
            }
        } else {
            cobroCuotasAlumnosLstBean.cargarIngresos();
        }

        ingresos = new ArrayList<>();
    }

    public DataTable getTablaIngresos() {
        return tablaIngresos;
    }

    public void setTablaIngresos(DataTable tablaIngresos) {
        this.tablaIngresos = tablaIngresos;
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    public IngresoRNLocal getIngresoCuotaRNLocal() {
        return ingresoCuotaRNLocal;
    }

    public void setIngresoCuotaRNLocal(IngresoRNLocal ingresoCuotaRNLocal) {
        this.ingresoCuotaRNLocal = ingresoCuotaRNLocal;
    }

    public Contador005RNLocal getContador005RNLocal() {
        return contador005RNLocal;
    }

    public void setContador005RNLocal(Contador005RNLocal contador005RNLocal) {
        this.contador005RNLocal = contador005RNLocal;
    }

    public Contador025RNLocal getContador025RNLocal() {
        return contador025RNLocal;
    }

    public void setContador025RNLocal(Contador025RNLocal contador025RNLocal) {
        this.contador025RNLocal = contador025RNLocal;
    }

    public UsuarioLogerBean getUsuarioLogerBean() {
        return usuarioLogerBean;
    }

    public void setUsuarioLogerBean(UsuarioLogerBean usuarioLogerBean) {
        this.usuarioLogerBean = usuarioLogerBean;
    }

    public InscripcionAlumnosRNLocal getInscripcionAlumnosRNLocal() {
        return inscripcionAlumnosRNLocal;
    }

    public void setInscripcionAlumnosRNLocal(InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal) {
        this.inscripcionAlumnosRNLocal = inscripcionAlumnosRNLocal;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public CobroCuotasAlumnosBean() {
    }

    public CobroCuotasAlumnosLstBean getCobroCuotasAlumnosLstBean() {
        return cobroCuotasAlumnosLstBean;
    }

    public void setCobroCuotasAlumnosLstBean(CobroCuotasAlumnosLstBean cobroCuotasAlumnosLstBean) {
        this.cobroCuotasAlumnosLstBean = cobroCuotasAlumnosLstBean;
    }

    public int getUltimaCuota() {
        return ultimaCuota;
    }

    public void setUltimaCuota(int ultimaCuota) {
        this.ultimaCuota = ultimaCuota;
    }

    public Ingreso getIngreso() {
        return ingreso;
    }

    public void setIngreso(Ingreso ingreso) {
        this.ingreso = ingreso;
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

    public int getiActionBtnSelect() {
        return iActionBtnSelect;
    }

    public void setiActionBtnSelect(int iActionBtnSelect) {
        this.iActionBtnSelect = iActionBtnSelect;
    }

    public CommandButton getCbAction() {
        return cbAction;
    }

    public void setCbAction(CommandButton cbAction) {
        this.cbAction = cbAction;
    }

    public AlumnoLstBean getAlumnoLstBean() {
        return alumnoLstBean;
    }

    public void setAlumnoLstBean(AlumnoLstBean alumnoLstBean) {
        this.alumnoLstBean = alumnoLstBean;
    }

    public CohorteLstBean getCohorteLstBean() {
        return cohorteLstBean;
    }

    public void setCohorteLstBean(CohorteLstBean cohorteLstBean) {
        this.cohorteLstBean = cohorteLstBean;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public Contador005 getContador005() {
        return contador005;
    }

    public void setContador005(Contador005 contador005) {
        this.contador005 = contador005;
    }

    public Contador025 getContador025() {
        return contador025;
    }

    public void setContador025(Contador025 contador025) {
        this.contador025 = contador025;
    }

    public NavegarBean getNavegarBean() {
        return navegarBean;
    }

    public void setNavegarBean(NavegarBean navegarBean) {
        this.navegarBean = navegarBean;
    }

    public void nuevoCobroGeneral() {
        this.alumnoLstBean.setAlumnoSelect(new Alumno());
        this.alumnoLstBean.setAlumnoSelectConsulta(new Alumno());
        this.setIngreso(new Ingreso());
    }

    public void nuevoCobroCuota() {
        try {
            this.cohorteLstBean.setCohorte(null);
            this.cohorteLstBean.setCantidadCuotas(1);
            this.getCbAction().setValue("Guardar");
            this.getCobroCuotasAlumnosLstBean().setiTipoBoton(0);
            this.setIngreso(new Ingreso());
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String path = context.getRequestContextPath() + "/CobroCuotas.xhtml?faces-redirect=true";
            context.redirect(path);

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void cargarCohorte() {
        try {
            this.getCobroCuotasAlumnosLstBean().getIngreso().setCohorte(this.cohorteLstBean.getCohorteSeleccionada());
        } catch (Exception e) {
        }
        try {

            this.getCobroCuotasAlumnosLstBean().getIngreso().setCuota(this.cohorteLstBean.getUltimaCuota());
        } catch (Exception e) {
        }
        try {
            this.getCobroCuotasAlumnosLstBean().getIngreso().setImporte(this.getCobroCuotasAlumnosLstBean().getIngreso().getCohorte().getImporteCuota());
        } catch (Exception e) {
        }
        try {
            this.getCobroCuotasAlumnosLstBean().getIngreso().setCuenta(this.getCobroCuotasAlumnosLstBean().getIngreso().getCohorte().getCarrera().getCuenta());
        } catch (Exception e) {
        }

        RequestContext.getCurrentInstance().update("frmPri:otCuenta");
        RequestContext.getCurrentInstance().update("frmPri:itNumeroCuenta");
        RequestContext.getCurrentInstance().update("frmPri:itImporte");
        RequestContext.getCurrentInstance().update("frmPri:itCuota");
        RequestContext.getCurrentInstance().update("frmPri:itCuotaInicial");
    }

    public void definirActionBoton(ActionEvent e) {

        CommandButton btnSelect = null;
        try {
            btnSelect = (CommandButton) e.getSource();

            this.getCbAction().setDisabled(false);

            switch (btnSelect.getId()) {
                case "btnEditCA":
                    this.getCbAction().setValue("Editar");
                    this.getCobroCuotasAlumnosLstBean().setiTipoBoton(1);
                    break;
                case "btnDeleteCA":
                    this.getCbAction().setValue("Eliminar");
                    this.getCobroCuotasAlumnosLstBean().setiTipoBoton(2);
                    break;
                case "btnNuevoCobro":
                    this.getCbAction().setValue("Guardar");
                    this.getCobroCuotasAlumnosLstBean().setiTipoBoton(0);
                    this.setIngreso(new Ingreso());
                    this.getIngreso().setAnulado(Boolean.FALSE);
                    this.getIngreso().setBorrado(Boolean.FALSE);
                    this.getAlumnoLstBean().setAlumnoSelect(new Alumno());
                    this.getAlumnoLstBean().setAlumnoSelectConsulta(new Alumno());
                    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                    context.redirect(context.getRequestContextPath() + "CobroCuotas.xhtml");
                    break;
                case "cbRecuperarBorradoCA":
                    this.getCobroCuotasAlumnosLstBean().setiTipoBoton(3);
                    this.getCbAction().setValue("Recuperar");
                    break;
                default:
                    this.getCbAction().setValue("Guardar");
                    this.getCobroCuotasAlumnosLstBean().setiTipoBoton(0);
                    this.setIngreso(new Ingreso());
                    break;
            }
        } catch (Exception ex) {

        }

    }// fin definirActionBoton

    public void actionBtn(ActionEvent e) {
        Integer iBoton = this.getCobroCuotasAlumnosLstBean().getiTipoBoton();
        //0 guardar
        //1 modificar
        //2 eliminar
        switch (iBoton) {
            case 0:
                alta();
                break;
            case 1:
                modificar();
                break;
            case 2:
                this.eliminar(Boolean.TRUE);
                break;
            case 3:
                this.eliminar(Boolean.FALSE);
                break;
            case 4:
                altaGeneral();
                break;
        }//fin switch
    }

    /*
     public void cargarLstFormaPago() {
     lstFormaPago = new ArrayList<SelectItem>();
     for (FormaPago fp : FormaPago.values()) {
     lstFormaPago.add(new SelectItem(fp, fp.toString()));
     }
     }

     public void cargarLstTipoComprobante() {
     lstTipoComprobante = new ArrayList<SelectItem>();
     for (TipoComprobante tc : TipoComprobante.values()) {
     lstTipoComprobante.add(new SelectItem(tc, tc.toString()));
     }
     }*/
    public void modificar(Ingreso i) {
        this.ingreso = i;
        modificar();
    }

    public void modificarGeneral(Ingreso i) {
        this.ingreso = i;
        modificarGeneral();
    }

    private void modificarGeneral() {

        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;

        try {
            if (ingreso.getFechaPago() != null) {
                ingreso.setFechaModificado(new Date());
                ingreso.setModificadoPor(this.getUsuarioLogerBean().getUsuario().getUsuario());
                ingreso.setCohorte(null);
                ingresoCuotaRNLocal.edit(ingreso);

                cobroCuotasAlumnosLstBean.cargarIngresos();

                sMensaje = "Los Datos del Ingreso fueron modificados. Click Salir";
                severity = FacesMessage.SEVERITY_INFO;
                limpiar();

                RequestContext.getCurrentInstance().update("frmPri:dtCobroCuotas");
            } else {
                sMensaje = "No se ha Seleccionado la fecha del pago";
                severity = FacesMessage.SEVERITY_ERROR;
            }

            //Agregar el pago a la lista
            this.cobroCuotasAlumnosLstBean.getLstCobroCuotas().add(ingreso);
            this.setIngreso(new Ingreso());
            this.cohorteLstBean.setCohorteSelect(new Cohorte());
            this.alumnoLstBean.setAlumnoSelect(new Alumno());

            RequestContext.getCurrentInstance().update("frmPri:dCobroCuotasAlumnos");
            RequestContext.getCurrentInstance().update("frmPri:otProveedor");

            cobroCuotasAlumnosLstBean.cargarIngresos();

            this.getCbAction().setValue("Editar");
            this.getCbAction().setDisabled(true);
            RequestContext.getCurrentInstance().update(":frmPri:dtCobroCuotas");
            //RequestContext.getCurrentInstance().execute("PF('dlgCobroAlumnos').hide();");

        } catch (Exception ex) {

            if (ex.getMessage().trim().toLowerCase().equals("transaction aborted")) {
                sMensaje = "Error: No se puede modificar";
            } else {
                sMensaje = "Error: " + ex.getMessage();
            }

            severity = FacesMessage.SEVERITY_ERROR;

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }

    private void modificar() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;

        try {
            if (ingreso.getAlumno() != null) {
                if (ingreso.getCohorte() != null && ingreso.getCohorte().getId() != null) {
                    if (ingreso.getFechaPago() != null) {
                        ingreso.setFechaModificado(new Date());
                        ingreso.setModificadoPor(this.getUsuarioLogerBean().getUsuario().getUsuario());
                        ingreso.setNombre(null);
                        ingresoCuotaRNLocal.edit(ingreso);

                        cobroCuotasAlumnosLstBean.cargarIngresos();

                        sMensaje = "Los Datos del Ingreso fueron modificados";
                        severity = FacesMessage.SEVERITY_INFO;
                        limpiar();

                        //RequestContext.getCurrentInstance().update("frmPri:dCobroCuotasAlumnos");
                        RequestContext.getCurrentInstance().update("frmPri:dtCobroCuotas");
                    } else {
                        sMensaje = "No se ha Seleccionado la fecha del pago";
                        severity = FacesMessage.SEVERITY_ERROR;
                    }
                } else {
                    sMensaje = "No se ha Seleccionado una cohorte";
                    severity = FacesMessage.SEVERITY_ERROR;
                }
            } else {
                sMensaje = "No se ha Seleccionado un alumno";
                severity = FacesMessage.SEVERITY_ERROR;
            }

            //Agregar el pago a la lista
            this.cobroCuotasAlumnosLstBean.getLstCobroCuotas().add(ingreso);
            this.setIngreso(new Ingreso());
            this.cohorteLstBean.setCohorteSelect(new Cohorte());
            this.alumnoLstBean.setAlumnoSelect(new Alumno());

            RequestContext.getCurrentInstance().update("frmPri:otAlumno2");
            RequestContext.getCurrentInstance().update("frmPri:dCobroCuotasAlumnos");
            RequestContext.getCurrentInstance().update("frmPri:otProveedor");

            cobroCuotasAlumnosLstBean.cargarIngresos();

            //elimino y agrego  a la lista
//            int iPos = this.cobroCuotasAlumnosLstBean.getLstCobroCuotas().indexOf(this.getIngreso());
//            this.cobroCuotasAlumnosLstBean.getLstCobroCuotas().remove(iPos);
//            this.cobroCuotasAlumnosLstBean.getLstCobroCuotas().add(iPos, this.getIngreso());
            this.getCbAction().setValue("Editar");
            this.getCbAction().setDisabled(true);
            RequestContext.getCurrentInstance().update(":frmPri:dtCobroCuotas");

        } catch (Exception ex) {

            if (ex.getMessage().trim().toLowerCase().equals("transaction aborted")) {
                sMensaje = "Error: No se puede modificar";
            } else {
                sMensaje = "Error: " + ex.getMessage();
            }

            severity = FacesMessage.SEVERITY_ERROR;

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }

    public void alta() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        Alumno alumno = this.getAlumnoLstBean().getAlumnoSelect();
        Cohorte cohorte = this.cohorteLstBean.getCohorteSeleccionada();
        try {
            int cuota = ingresoCuotaRNLocal.findUltimaCuotaAlumnoCohorte(alumno, cohorte);
            cuota++;
            int ultimaCuotaAPagar = cuota + cohorteLstBean.getCantidadCuotas() - 1;
            if (alumno != null) {
                if (cohorte != null && cohorte.getId() != null) {
                    if (this.cobroCuotasAlumnosLstBean.getFechaPago() != null) {
                        for (int i = 0; i < cohorteLstBean.getCantidadCuotas(); i++) {
                            if (!cohorteLstBean.isFlag()) {
                                ingreso.setCuota(cuota);
                                ingreso.setFechaPago(cobroCuotasAlumnosLstBean.getFechaPago());
                                ingreso.setAlumno(alumno);
                                ingreso.setBorrado(false);
                                ingreso.setAnulado(false);
                                ingreso.setCohorte(cohorte);
                                if (cuota == ultimaCuotaAPagar) {
                                    ingreso.setImporte(cohorte.getImporteCuota());
                                } else {
                                    ingreso.setImporte(BigDecimal.ZERO);
                                }
                                ingreso.setCuenta(cohorte.getCarrera().getCuenta());
                                ingreso.setNumeroRecibo(this.cohorteLstBean.getNumeroRecibo());
                                ingreso.setFechaCreado(new Date());
                                ingreso.setCreadoPor(this.getUsuarioLogerBean().getUsuario().getUsuario());
                                if (i == 0) {
                                    ingresoCuotaRNLocal.create(ingreso);
                                } else {
                                    ingresoCuotaRNLocal.create(ingreso, true);
                                }

                                switch (cohorte.getCarrera().getCuenta().getCodigo()) {
                                    case "005":
                                        this.getContador005().setNumero(this.cohorteLstBean.getNumeroRecibo());
                                        contador005RNLocal.create(this.contador005);
                                        break;
                                    case "025":
                                        this.getContador025().setNumero(this.cohorteLstBean.getNumeroRecibo());
                                        contador025RNLocal.create(this.contador025);
                                        break;
                                }
                                cobroCuotasAlumnosLstBean.cargarIngresos();
                                sMensaje = "El Cobro de Cuota al Alumno fue Registrado";
                                severity = FacesMessage.SEVERITY_INFO;
                                cuota++;
                            } else {
                                sMensaje = "Ya se ha abonado la totalidad de las cuotas";
                                severity = FacesMessage.SEVERITY_ERROR;
                            }
                        }
                        // limpiar();
                        RequestContext.getCurrentInstance().update("frmPri:dtCobroCuotas");
                        RequestContext.getCurrentInstance().update("frmPri:growl");
                        salir();

                    } else {
                        sMensaje = "No se ha Seleccionado la fecha del pago";
                        severity = FacesMessage.SEVERITY_ERROR;
                    }
                } else {
                    sMensaje = "No se ha Seleccionado una cohorte";
                    severity = FacesMessage.SEVERITY_ERROR;
                }
            } else {
                sMensaje = "No se ha Seleccionado un alumno";
                severity = FacesMessage.SEVERITY_ERROR;
            }
        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();
        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    public void borrar(Boolean b) {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = FacesMessage.SEVERITY_INFO;
        try {
            ingreso.setFechaModificado(new Date());
            ingreso.setModificadoPor(this.usuarioLogerBean.getUsuario().getUsuario());
            this.ingresoCuotaRNLocal.edit(ingreso);
            this.ingresoCuotaRNLocal.remove(ingreso, b);
            if (b) {
                sMensaje = "El dato fue borrado";
            } else {
                sMensaje = "El dato fue recuperado";
            }
            cobroCuotasAlumnosLstBean.cargarIngresos();
            RequestContext.getCurrentInstance().update("frmPri:dCobroCuotasAlumnos");

        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();
            System.out.println(sMensaje);

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);

        }
    }

    public void anular() {
        this.ingresoCuotaRNLocal.anular(ingreso, this.usuarioLogerBean.getUsuario());
    }

    public void anular(Boolean b) {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = FacesMessage.SEVERITY_INFO;
        try {
            ingreso.setFechaModificado(new Date());
            ingreso.setModificadoPor(this.usuarioLogerBean.getUsuario().getUsuario());
            ingreso.setAnulado(b);
            this.ingresoCuotaRNLocal.edit(ingreso);
            if (b) {
                sMensaje = "El dato fue anulado";
            } else {
                sMensaje = "El dato fue recuperado";
            }
            cobroCuotasAlumnosLstBean.cargarIngresos();
            RequestContext.getCurrentInstance().update("frmPri:dCobroCuotasAlumnos");

        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();
            System.out.println(sMensaje);

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);

        }
    }

    public void altaGeneral() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            if (this.ingreso.getFechaPago() != null) {
                ingreso.setAlumno(this.getAlumnoLstBean().getAlumnoSelect());
                ingreso.setBorrado(false);
                ingreso.setAnulado(false);
                ingreso.setCuenta(this.getCuentaLstBean().getCuenta());
                ingreso.setNumeroRecibo(this.numeroCuentaBean.getNumero());
                ingreso.setCuota(0);
                ingreso.setFechaCreado(new Date());
                ingreso.setCreadoPor(this.usuarioLogerBean.getUsuario().getUsuario());
                ingresoCuotaRNLocal.create(ingreso);

                switch (this.cuentaLstBean.getCuenta().getCodigo()) {
                    case "005":
                        this.getContador005().setNumero(this.numeroCuentaBean.getNumero());
                        contador005RNLocal.create(this.contador005);
                        break;
                    case "025":
                        this.getContador025().setNumero(this.numeroCuentaBean.getNumero());
                        contador025RNLocal.create(this.contador025);
                        break;
                }

                cobroCuotasAlumnosLstBean.cargarIngresos();
                sMensaje = "El Cobro de Cuota al Alumno fue Registrado";
                severity = FacesMessage.SEVERITY_INFO;

                limpiar();

                //RequestContext.getCurrentInstance().update("frmPri:dCobroCuotasAlumnos");
                RequestContext.getCurrentInstance().update("frmPri:dtCobroCuotas");

            } else {
                sMensaje = "No se ha Seleccionado la fecha del pago";
                severity = FacesMessage.SEVERITY_ERROR;
            }

        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();
            System.out.println(sMensaje);

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    public void cuotasIniciales() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        Alumno alumno = this.getAlumnoLstBean().getAlumnoSelect();
        Cohorte cohorte = this.cohorteLstBean.getCohorteSeleccionada();
        try {
            int cuota = ingresoCuotaRNLocal.findUltimaCuotaAlumnoCohorte(alumno, cohorte);
            cuota++;
            if (ultimaCuota >= cuota) {
                for (int i = cuota; i <= ultimaCuota; i++) {
                    if (alumno != null) {
                        if (cohorte != null && cohorte.getId() != null) {
                            if (this.cobroCuotasAlumnosLstBean.getFechaPago() != null) {
                                if (!cohorteLstBean.isFlag()) {
                                    Ingreso ingresoInicial = new Ingreso();
                                    ingresoInicial.setCuota(i);
                                    ingresoInicial.setFechaPago(cobroCuotasAlumnosLstBean.getFechaPago());
                                    ingresoInicial.setAlumno(alumno);
                                    ingresoInicial.setBorrado(false);
                                    ingresoInicial.setAnulado(false);
                                    ingresoInicial.setCuenta(cohorte.getCarrera().getCuenta());
                                    ingresoInicial.setCohorte(cohorte);
                                    ingresoCuotaRNLocal.create(ingresoInicial);
                                    sMensaje = "El Cobro de Cuota al Alumno fue Registrado";
                                    severity = FacesMessage.SEVERITY_INFO;
                                } else {
                                    sMensaje += "Ya se ha abonado la totalidad de las cuotas";
                                    severity = FacesMessage.SEVERITY_ERROR;
                                }

                                limpiar();

                                //RequestContext.getCurrentInstance().update("frmPri:dCobroCuotasAlumnos");
                                RequestContext.getCurrentInstance().update("frmPri:dtCobroCuotas");
                            } else {
                                sMensaje = "No se ha Seleccionado la fecha del pago";
                                severity = FacesMessage.SEVERITY_ERROR;
                            }
                        } else {
                            sMensaje = "No se ha Seleccionado una cohorte";
                            severity = FacesMessage.SEVERITY_ERROR;
                        }
                    } else {
                        sMensaje = "No se ha Seleccionado un alumno";
                        severity = FacesMessage.SEVERITY_ERROR;
                    }
                }
            } else {
                sMensaje = "La cuota a pagar debe ser mayor";
                severity = FacesMessage.SEVERITY_ERROR;
            }
            cobroCuotasAlumnosLstBean.cargarIngresos();

        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();
            System.out.println(sMensaje);

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    public void salir() {
        try {
            limpiar();
            String url = null;

            try {
                String codigo = cobroCuotasAlumnosLstBean.getCuenta().getCodigo();
                int cuenta = Integer.parseInt(codigo);

                url = this.getNavegarBean().frmIngresos(cuenta);
            } catch (Exception e) {
                url = this.getNavegarBean().frmIngresos();

            }
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String path = context.getRequestContextPath() + url;
            context.redirect(path);
        } catch (IOException ex) {
            Logger.getLogger(CobroCuotasAlumnosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiar() {
        this.getAlumnoLstBean().setAlumnoSelect(new Alumno());
        this.getAlumnoLstBean().setAlumnoSelectConsulta(new Alumno());
        this.getCohorteLstBean().setUltimaCuota(1);
        this.getCohorteLstBean().setCohorteSeleccionada(new Cohorte());
        this.setContador005(new Contador005());
        this.setContador025(new Contador025());
        this.cuentaLstBean.setCuenta(new Cuenta());
        this.numeroCuentaBean.setNumero(0);
        this.cohorteLstBean.setNumeroRecibo(0);

    }//fin limpiar

    public void cargarCobrosGeneralXFecha() {
        if (this.fechaFin != null && this.fechaIni != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(this.fechaFin);
            c.add(Calendar.DATE, 1);  // number of days to add
            this.fechaFin = c.getTime();  // fechaFin is now the new date
            List<Ingreso> findCobrosXFecha = ingresoCuotaRNLocal.findCobrosXFecha(this.fechaIni, this.fechaFin);
            this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(findCobrosXFecha);
        } else {
            try {
                this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(ingresoCuotaRNLocal.findAll());
            } catch (Exception ex) {
                Logger.getLogger(CobroCuotasAlumnosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void eliminar(Boolean bEstado) {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            ingresoCuotaRNLocal.remove(ingreso, bEstado);//cambia el estado del pago segun el valor bEstado

            //obtiene la posicion en la lista
            int iPos = this.getCobroCuotasAlumnosLstBean().getLstCobroCuotas().indexOf(this.getIngreso());
            //remueve de la lista el pago segun la posicion de esta
            //SE DEBE REMOVER EL ITEM YA QUE LUEGO DEBE VOLVER A SER AGREGADO CON EL NUEVO VALOR 'TRUE OR FALSE'
            this.getCobroCuotasAlumnosLstBean().getLstCobroCuotas().remove(iPos);

            //envia el mensaje segun sea true o false bEstado
            if (bEstado) {
                ingreso.setBorrado(Boolean.TRUE);
                sMensaje = "El dato fue bloqueado, Click salir";
            } else {
                ingreso.setBorrado(Boolean.FALSE);
                sMensaje = "El dato fue recuperado, Click salir";
            }

            this.getCobroCuotasAlumnosLstBean().getLstCobroCuotas().add(iPos, this.getIngreso());
            this.getCobroCuotasAlumnosLstBean().cargarIngresos();
            this.getCbAction().setValue("Eliminar");
            this.getCbAction().setDisabled(true);

            severity = FacesMessage.SEVERITY_INFO;
        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al eliminar el cobro: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin eliminar

    public void buscarCobroXDni() {
        FacesMessage fm;
        try {
            //System.out.println("dni Alumno: "+this.dni);
            if (!this.dni.equals("")) {
                this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(ingresoCuotaRNLocal.findCobrosByDni(this.dni));

                if (this.getAlumnoLstBean().getLstAlunmo().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if

            } else {
                this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(ingresoCuotaRNLocal.findAll());

            }

        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:btnDniPD", fm);
        }//fin catch

    }

    public void buscarCobroXDniOTexto() {
        FacesMessage fm;
        try {
            //System.out.println("dni Alumno: "+this.dni);
            if (!this.dni.equals("")) {
                this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(ingresoCuotaRNLocal.findCobrosByDniOTexto(this.dni));

                if (this.getAlumnoLstBean().getLstAlunmo().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if

            } else {
                this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(ingresoCuotaRNLocal.findAll());

            }

        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:btnDniPD", fm);
        }//fin catch

    }

    public void cargarIngresosRecibo(Ingreso i) {
        if (i.getNumeroRecibo() > 0) {
            Calendar c = Calendar.getInstance();
            c.setTime(i.getFechaPago());
            int anio = c.get(Calendar.YEAR);
            List<Ingreso> findAllByNumeroRecibo = ingresoCuotaRNLocal.findAllByNumeroRecibo(i.getCuenta(), i.getNumeroRecibo(), anio);
            ingresos = findAllByNumeroRecibo;
        }
    }
}
