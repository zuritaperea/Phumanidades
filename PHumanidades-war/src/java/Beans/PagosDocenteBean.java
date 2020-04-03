/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.TipoEgresoFacadeLocal;
import Entidades.Carreras.Carrera;
import Entidades.Egresos.FormaPago;
import Entidades.Egresos.PagosDocente;
import Entidades.Egresos.RubroPresupuestario;
import Entidades.Egresos.TipoComprobante;
import Entidades.Egresos.TipoEgreso;
import Entidades.Persona.Docente;
import Entidades.Persona.Proveedor;
import RN.PagosDocenteRNLocal;
import RN.ProveedorRNLocal;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class PagosDocenteBean implements Serializable {

    @EJB
    private PagosDocenteRNLocal pagosDocenteRNLocal;
    @EJB
    private ProveedorRNLocal proveedorRNLocal;//hacemos la referencia para poder utilizar el metodo findall

    @ManagedProperty(value = "#{usuarioLogerBean}")
    private UsuarioLogerBean usuarioLogerBean;

    @ManagedProperty(value = "#{docenteLstBean}")
    private DocenteLstBean docenteLstBean;

    @ManagedProperty(value = "#{carreraLstBean}")
    private CarreraLstBean carreraLstBean;

    @ManagedProperty(value = "#{navegarBean}")
    private NavegarBean navegarBean;

    @ManagedProperty(value = "#{docenteBean}")
    private DocenteBean docenteBean;

    @ManagedProperty(value = "#{pagosDocenteLstBean}")
    private PagosDocenteLstBean pagosDocenteLstBean;

    @ManagedProperty(value = "#{proveedorLstBean}")
    private ProveedorLstBean proveedorLstBean;

    private String busquedaCarrera;

    private PagosDocente pagoDocente;
    private PagosDocente pagoDocenteAux;
    //Pagos docentes para comprobantes
    private PagosDocente pagoDocente2;
    private PagosDocente pagoDocente3;
    private PagosDocente pagoDocente4;
    private PagosDocente pagoDocente5;
    private PagosDocente pagoDocente6;
    List<PagosDocente> pagosDocentes;
//Inicio Busqueda Carrera   
    private CommandButton cbAction;

//Fin Busqueda Carrera    
    private List<SelectItem> lstTipoComprobante;

    @Enumerated(EnumType.STRING)
    private FormaPago formapago;
    private List<SelectItem> lstFormaPago;

    @Enumerated(EnumType.STRING)
    private RubroPresupuestario rubroPresupuestario;
    private List<SelectItem> lstRubroPresupuestario;

    private List<SelectItem> lstProveedor;

    @EJB
    private TipoEgresoFacadeLocal tipoEgresoFacadeLocal;

    private TipoEgreso tipoEgreso;
    private List<SelectItem> lstTipoEgreso;

    /**
     * Creates a new instance of DocenteBean
     */
    @PostConstruct
    private void init() {
        pagosDocentes = new ArrayList<>();

        pagoDocente = new PagosDocente();
        pagoDocente2 = new PagosDocente();
        pagoDocente3 = new PagosDocente();
        pagoDocente4 = new PagosDocente();
        pagoDocente5 = new PagosDocente();
        pagoDocente6 = new PagosDocente();
        cargarPagosDocentes();
        cargarLstFormaPago();
        cargarLstRubroPresupuestario();
        cargarLstProveedor();
        cargarLstTipoComprobante();
        cargarLstTipoEgresos();
    }

    public List<PagosDocente> getPagosDocentes() {
        return pagosDocentes;
    }

    public void setPagosDocentes(List<PagosDocente> pagosDocentes) {
        this.pagosDocentes = pagosDocentes;
    }

    public PagosDocente getPagoDocente6() {
        return pagoDocente6;
    }

    public void setPagoDocente6(PagosDocente pagoDocente6) {
        this.pagoDocente6 = pagoDocente6;
    }

    public PagosDocente getPagoDocente2() {
        return pagoDocente2;
    }

    public void setPagoDocente2(PagosDocente pagoDocente2) {
        this.pagoDocente2 = pagoDocente2;
    }

    public PagosDocente getPagoDocente3() {
        return pagoDocente3;
    }

    public void setPagoDocente3(PagosDocente pagoDocente3) {
        this.pagoDocente3 = pagoDocente3;
    }

    public PagosDocente getPagoDocente4() {
        return pagoDocente4;
    }

    public void setPagoDocente4(PagosDocente pagoDocente4) {
        this.pagoDocente4 = pagoDocente4;
    }

    public PagosDocente getPagoDocente5() {
        return pagoDocente5;
    }

    public void setPagoDocente5(PagosDocente pagoDocente5) {
        this.pagoDocente5 = pagoDocente5;
    }

    public TipoEgresoFacadeLocal getTipoEgresoFacadeLocal() {
        return tipoEgresoFacadeLocal;
    }

    public void setTipoEgresoFacadeLocal(TipoEgresoFacadeLocal tipoEgresoFacadeLocal) {
        this.tipoEgresoFacadeLocal = tipoEgresoFacadeLocal;
    }

    public TipoEgresoFacadeLocal getEgresoFacadeLocal() {
        return tipoEgresoFacadeLocal;
    }

    public void setEgresoFacadeLocal(TipoEgresoFacadeLocal egresoFacadeLocal) {
        this.tipoEgresoFacadeLocal = egresoFacadeLocal;
    }

    public TipoEgreso getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(TipoEgreso tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }

    public List<SelectItem> getLstTipoEgreso() {
        return lstTipoEgreso;
    }

    public void setLstTipoEgreso(List<SelectItem> lstTipoEgreso) {
        this.lstTipoEgreso = lstTipoEgreso;
    }

    public PagosDocenteRNLocal getPagosDocenteRNLocal() {
        return pagosDocenteRNLocal;
    }

    public void setPagosDocenteRNLocal(PagosDocenteRNLocal PagosDocenteRNLocal) {
        this.pagosDocenteRNLocal = PagosDocenteRNLocal;
    }

    public ProveedorRNLocal getProveedorRNLocal() {
        return proveedorRNLocal;
    }

    public void setProveedorRNLocal(ProveedorRNLocal proveedorRNLocal) {
        this.proveedorRNLocal = proveedorRNLocal;
    }

    public UsuarioLogerBean getUsuarioLogerBean() {
        return usuarioLogerBean;
    }

    public void setUsuarioLogerBean(UsuarioLogerBean usuarioLogerBean) {
        this.usuarioLogerBean = usuarioLogerBean;
    }

    public PagosDocenteBean() {
    }

    public DocenteLstBean getDocenteLstBean() {
        return docenteLstBean;
    }

    public void setDocenteLstBean(DocenteLstBean docenteLstBean) {
        this.docenteLstBean = docenteLstBean;
    }

    public ProveedorLstBean getProveedorLstBean() {
        return proveedorLstBean;
    }

    public void setProveedorLstBean(ProveedorLstBean proveedorLstBean) {
        this.proveedorLstBean = proveedorLstBean;
    }

    public CarreraLstBean getCarreraLstBean() {
        return carreraLstBean;
    }

    public void setCarreraLstBean(CarreraLstBean carreraLstBean) {
        this.carreraLstBean = carreraLstBean;
    }

    public NavegarBean getNavegarBean() {
        return navegarBean;
    }

    public void setNavegarBean(NavegarBean navegarBean) {
        this.navegarBean = navegarBean;
    }

    public String getBusquedaCarrera() {
        return busquedaCarrera;
    }

    public void setBusquedaCarrera(String busquedaCarrera) {
        this.busquedaCarrera = busquedaCarrera;
    }

    public PagosDocente getPagoDocente() {
        return pagoDocente;
    }

    public void setPagoDocente(PagosDocente pagoDocente) {
        this.pagoDocente = pagoDocente;
    }

    public PagosDocente getPagoDocenteAux() {
        return pagoDocenteAux;
    }

    public void setPagoDocenteAux(PagosDocente pagoDocenteAux) {
        this.pagoDocenteAux = pagoDocenteAux;
    }

    public List<SelectItem> getLstTipoComprobante() {
        return lstTipoComprobante;
    }

    public void setLstTipoComprobante(List<SelectItem> lstTipoComprobante) {
        this.lstTipoComprobante = lstTipoComprobante;
    }

    public List<SelectItem> getLstFormaPago() {
        return lstFormaPago;
    }

    public void setLstFormaPago(List<SelectItem> lstFormaPago) {
        this.lstFormaPago = lstFormaPago;
    }

    public List<SelectItem> getLstRubroPresupuestario() {
        return lstRubroPresupuestario;
    }

    public void setLstRubroPresupuestario(List<SelectItem> lstRubroPresupuestario) {
        this.lstRubroPresupuestario = lstRubroPresupuestario;
    }

    public List<SelectItem> getLstProveedor() {
        return lstProveedor;
    }

    public void setLstProveedor(List<SelectItem> lstProveedor) {
        this.lstProveedor = lstProveedor;
    }

    public FormaPago getFormapago() {
        return formapago;
    }

    public void setFormapago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public RubroPresupuestario getRubroPresupuestario() {
        return rubroPresupuestario;
    }

    public void setRubroPresupuestario(RubroPresupuestario rubroPresupuestario) {
        this.rubroPresupuestario = rubroPresupuestario;
    }

    public DocenteBean getDocenteBean() {
        return docenteBean;
    }

    public void setDocenteBean(DocenteBean docenteBean) {
        this.docenteBean = docenteBean;
    }

    public CommandButton getCbAction() {
        return cbAction;
    }

    public void setCbAction(CommandButton cbAction) {
        this.cbAction = cbAction;
    }

    public PagosDocenteLstBean getPagosDocenteLstBean() {
        return pagosDocenteLstBean;
    }

    public void setPagosDocenteLstBean(PagosDocenteLstBean pagosDocenteLstBean) {
        this.pagosDocenteLstBean = pagosDocenteLstBean;
    }

    public void cargarLstFormaPago() {
        lstFormaPago = new ArrayList<SelectItem>();
        for (FormaPago fp : FormaPago.values()) {
            lstFormaPago.add(new SelectItem(fp, fp.toString()));
        }
    }

    public void cargarLstProveedor() {
        lstProveedor = new ArrayList<SelectItem>();
        for (Proveedor p : proveedorLstBean.getLstProveedor()) {
            lstProveedor.add(new SelectItem(p, p.toString()));
        }
    }

    public List<Proveedor> completeText(String query) {
        List<Proveedor> results = new ArrayList<Proveedor>();
        try {
            results = proveedorRNLocal.buscarProveedorRazonSocial(query);
        } catch (Exception exception) {
        }
        return results;
    }

    public void cargarLstTipoComprobante() {
        lstTipoComprobante = new ArrayList<SelectItem>();
        for (TipoComprobante tc : TipoComprobante.values()) {
            lstTipoComprobante.add(new SelectItem(tc, tc.toString()));
        }
    }

    public void definirActionBoton(ActionEvent e) {

        CommandButton btnSelect = (CommandButton) e.getSource();

        this.getCbAction().setDisabled(false);

        if (btnSelect.getId().equals("btnEdit")) {
            if (this.pagoDocente.getId() != null) {
                limpiar();
                cargar();
                this.getCbAction().setValue("Editar");
                this.getPagosDocenteLstBean().setiTipoBoton(1);
                if (this.docenteLstBean.getDocenteSeleccionado() != null) {
                    this.pagosDocenteLstBean.setDocProv(1);
                }
                if (this.proveedorLstBean.getProveedorSelect() != null) {
                    this.pagosDocenteLstBean.setDocProv(2);
                }
                RequestContext.getCurrentInstance().update("frmPri:dRegistrarPagosDocentes");
                RequestContext.getCurrentInstance().update("frmPri:pnComprobante");
                RequestContext.getCurrentInstance().update("frmPri:pPriPagoDocente");
                RequestContext.getCurrentInstance().execute("PF('dlgPagosDocentes').show();");
            }
        }
        if (btnSelect.getId().equals("btnDelete")) {
            limpiar();
            cargar();
            this.getCbAction().setValue("Eliminar");

            try {
                this.carreraLstBean.setCarreraSeleccionada(this.getPagoDocente().getCarrera());
            } catch (Exception ex) {
            }
            try {
                this.docenteLstBean.setDocenteSeleccionado(this.getPagoDocente().getDocente());
            } catch (Exception ex) {
            }
            try {
                this.proveedorLstBean.setProveedorSelect(this.getPagoDocente().getProveedor());
            } catch (Exception ex) {
            }
            try {
                this.carreraLstBean.setLstCarrerasDocente(this.getPagoDocente().getDocente().getCarreras());
            } catch (Exception ex) {
            }
            this.getPagosDocenteLstBean().setiTipoBoton(2);

        }

        if (btnSelect.getId().equals("btnNuevoPago")) {
            this.getCbAction().setValue("Guardar");
            this.getPagosDocenteLstBean().setiTipoBoton(0);

            this.setPagoDocente(new PagosDocente());

            limpiar();

            cargarUltimoNumero();
            if (getPagoDocente().getNumeroOrdenPago() > 0) {
                RequestContext.getCurrentInstance().update("frmPri:dRegistrarPagosDocentes");
                RequestContext.getCurrentInstance().update("frmPri:otProveedor");
                RequestContext.getCurrentInstance().update("frmPri:pnComprobante");
                RequestContext.getCurrentInstance().update("frmPri:pPriPagoDocente");
                RequestContext.getCurrentInstance().execute("PF('dlgPagosDocentes').show();");
            }

        }//fin if

        if (btnSelect.getId().equals("cbRecuperarBorrado")) {
            limpiar();
            cargar();
            this.getPagosDocenteLstBean().setiTipoBoton(3);
            this.getCbAction().setValue("Recuperar");

        }//fin if

    }// fin definirActionBoton

    public void actionButton(ActionEvent e) {
        Integer iBoton = this.getPagosDocenteLstBean().getiTipoBoton();
        //0 guardar
        //1 modificar
        //2 eliminar

        switch (iBoton) {
            case 0:
                //System.out.println("Case alta");
                alta();
                break;
            case 1:
                try {
                    modificar();
                } catch (Exception ex) {
                }
                break;
            case 2:
                this.eliminar(Boolean.TRUE);
                break;
            case 3:
                this.eliminar(Boolean.FALSE);
                break;
        }//fin switch

    }//fin actionButton

    private void modificar() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            if (this.proveedorLstBean.getProveedorSelect() == null && this.docenteLstBean.getDocenteSeleccionado() == null) {
                pagoDocenteAux = this.pagosDocenteRNLocal.buscarPagosDocenteId(this.pagoDocente.getId());
                if (this.pagoDocenteAux.getProveedor() != null) {
                    this.proveedorLstBean.setProveedorSelect(this.pagoDocenteAux.getProveedor());
                }
                if (this.pagoDocenteAux.getDocente() != null) {
                    this.docenteLstBean.setDocenteSeleccionado(pagoDocenteAux.getDocente());
                }
            }

            if (this.pagosDocenteLstBean.getDocProv() == 1) {
                this.proveedorLstBean.setProveedorSelect(null);
            }

            if (this.pagosDocenteLstBean.getDocProv() == 2) {
                this.docenteLstBean.setDocenteSeleccionado(null);
            }

            if (this.getDocenteLstBean().getDocenteSeleccionado() != null) {
                pagoDocente.setDocente(this.getDocenteLstBean().getDocenteSeleccionado());
            }

            if (this.getProveedorLstBean().getProveedorSelect() != null) {
                pagoDocente.setProveedor(this.getProveedorLstBean().getProveedorSelect());
            }
            if (this.getCarreraLstBean().getCarreraSeleccionada() != null) {
                pagoDocente.setCarrera(this.getCarreraLstBean().getCarreraSeleccionada());
            }

            BigDecimal montoDesc = new BigDecimal(0);
            try {
                montoDesc = montoDesc.add(pagoDocente.getMonto());
            } catch (Exception e) {
            }
            try {
                montoDesc = montoDesc.subtract(pagoDocente.getIva());
            } catch (Exception e) {
            }
            try {
                montoDesc = montoDesc.subtract(pagoDocente.getRetencionIB());
            } catch (Exception e) {
            }
            try {
                montoDesc = montoDesc.subtract(pagoDocente.getImpuestoGanancia());
            } catch (Exception e) {
            }
            try {
                montoDesc = montoDesc.subtract(pagoDocente.getSuss());
            } catch (Exception e) {
            }
            pagoDocente.setMontoConDescuentos(montoDesc);
            pagoDocente.setFechaModificado(new Date());
            pagoDocente.setModificadoPor(this.getUsuarioLogerBean().getUsuario().getUsuario());
            pagosDocenteRNLocal.edit(pagoDocente);
            crearModificarOtrosComprobantes(false);
            this.setPagoDocente(new PagosDocente());

            limpiar();
            this.pagosDocenteLstBean.setDocProv(0);

            pagosDocenteLstBean.cargarPagosDocente();

            RequestContext.getCurrentInstance().update("frmPri:otDocente");
            RequestContext.getCurrentInstance().update("frmPri:pnDetalle");
            RequestContext.getCurrentInstance().update("frmPri:otProveedor");
            RequestContext.getCurrentInstance().update("frmPri:pnInformacion");

            sMensaje = "Los Datos del Egreso fueron modificados";
            severity = FacesMessage.SEVERITY_INFO;
            //elimino y agrego  a la lista

//            int iPos = this.getPagosDocenteLstBean().getLstPagosDocente().indexOf(this.getPagoDocente());
//            this.getPagosDocenteLstBean().getLstPagosDocente().remove(iPos);
//            this.getPagosDocenteLstBean().getLstPagosDocente().add(iPos, this.getPagoDocente());
            RequestContext.getCurrentInstance().update("frmPri:dtPagosDocente");
            RequestContext.getCurrentInstance().execute("PF('dlgPagosDocentes').hide();");

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
        try {
            BigDecimal montoDesc = new BigDecimal(0);
            if (this.pagosDocenteLstBean.getDocProv() == 1) {
                this.proveedorLstBean.setProveedorSelect(null);
            }

            if (this.pagosDocenteLstBean.getDocProv() == 2) {
                this.docenteLstBean.setDocenteSeleccionado(null);
            }

            if (docenteLstBean.getDocenteSeleccionado() != null) {
                pagoDocente.setDocente(docenteLstBean.getDocenteSeleccionado());
            }
            if (carreraLstBean.getCarreraSeleccionada() != null) {
                pagoDocente.setCarrera(carreraLstBean.getCarreraSeleccionada());
            }

            if (proveedorLstBean.getProveedorSelect() != null) {
                pagoDocente.setProveedor(proveedorLstBean.getProveedorSelect());
            }

            pagoDocente.setAnulado(false);
            pagoDocente.setBorrado(false);
            montoDesc = montoDesc.add(pagoDocente.getMonto());
            try {
                montoDesc = montoDesc.subtract(pagoDocente.getIva());
            } catch (Exception e) {
            }
            try {
                montoDesc = montoDesc.subtract(pagoDocente.getRetencionIB());
            } catch (Exception e) {
            }
            try {
                montoDesc = montoDesc.subtract(pagoDocente.getImpuestoGanancia());
            } catch (Exception e) {
            }
            try {
                montoDesc = montoDesc.subtract(pagoDocente.getSuss());
            } catch (Exception e) {
            }
            pagoDocente.setMontoConDescuentos(montoDesc);
            pagoDocente.setFechaCreado(new Date());
            pagoDocente.setCreadoPor(this.getUsuarioLogerBean().getUsuario().getUsuario());
            if (docenteLstBean.getDocenteSeleccionado() == null && proveedorLstBean.getProveedorSelect() == null) {
                throw new Exception("Debe escoger un Docente o un Proveedor");
            }
            if (montoDesc.equals(0)) {
                throw new Exception("Debe consignar el monto");
            }
            if (pagoDocente.getCuenta() == null) {
                throw new Exception("Debe seleccionar una cuenta");
            }
            if (pagoDocente.getNumeroOrdenPago() < 1) {
                throw new Exception("Debe corroborar el número de la orden de pago");
            }
            if (comprobanteDuplicado(pagoDocente.getDocente(),
                    pagoDocente.getProveedor(), pagoDocente.getNumeroComprobante(),
                    pagoDocente.getFechaComprobante())) {
                throw new Exception("El comprobante N° " + pagoDocente.getNumeroComprobante() + "ya se encuentra cargado en el sistema");
            }

            if (pagoDocente.getDocente() != null) {
                if (pagoDocente.getCarrera() != null) {
                    pagosDocenteRNLocal.create(pagoDocente);
                    crearModificarOtrosComprobantes(true);
                    sMensaje = "El Pago fue Registrado";
                    severity = FacesMessage.SEVERITY_INFO;
                } else {
                    throw new Exception("Si selecciono un Docente debe seleccionar su carrera");
                }
            } else {
                pagosDocenteRNLocal.create(pagoDocente);
                crearModificarOtrosComprobantes(true);
                sMensaje = "El Pago fue Registrado";
                severity = FacesMessage.SEVERITY_INFO;
            }

            //Agregar el pago a la lista
            this.getPagosDocenteLstBean().getLstPagosDocente().add(pagoDocente);
            this.setPagoDocente(new PagosDocente());

            limpiar();

            this.pagosDocenteLstBean.setDocProv(0);

            pagosDocenteLstBean.cargarPagosDocente();

            RequestContext.getCurrentInstance().update("frmPri:otDocente");
            RequestContext.getCurrentInstance().update("frmPri:pnDetalle");
            RequestContext.getCurrentInstance().update("frmPri:otProveedor");
            RequestContext.getCurrentInstance().update("frmPri:pnInformacion");
            RequestContext.getCurrentInstance().update("frmPri:dtPagosDocente");

            RequestContext.getCurrentInstance().execute("PF('dlgPagosDocentes').hide();");

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    /**
     * elimina un Pago
     */
    private void eliminar(Boolean b) {
        //System.out.println("entro Eliminar");
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(pagoDocente.getFechaRegistro());
            int year = cal.get(Calendar.YEAR);
            List<PagosDocente> findPagosByNumeroOrdenPago;
            if (b) {
                findPagosByNumeroOrdenPago = pagosDocenteRNLocal.findPagosByNumeroOrdenPagoAnio(pagoDocente.getNumeroOrdenPago(), year);
            } else {
                findPagosByNumeroOrdenPago = pagosDocenteRNLocal.findPagosByNumeroOrdenPagoAnioBorrado(pagoDocente.getNumeroOrdenPago(), year);
            }
            for (PagosDocente pg : findPagosByNumeroOrdenPago) {
                pg.setFechaModificado(new Date());
                pg.setModificadoPor(this.usuarioLogerBean.getUsuario().getUsuario());
                pg.setBorrado(b);
                this.pagosDocenteRNLocal.edit(pg);
            }
            //envia el mensaje segun sea true o false bEstado
            if (b) {
                sMensaje = "El dato fue eliminado, Click salir";
            } else {
                sMensaje = "El dato fue recuperado, Click salir";
            }

            this.getPagosDocenteLstBean().cargarPagosDocente();
            RequestContext.getCurrentInstance().update("frmPri:dtPagosDocente");

            this.getCbAction().setValue("Eliminar");
            this.getCbAction().setDisabled(true);

            severity = FacesMessage.SEVERITY_INFO;
        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al eliminar el pago: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin eliminar

    public void anular(Boolean b) {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = FacesMessage.SEVERITY_INFO;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(pagoDocente.getFechaRegistro());
            int year = cal.get(Calendar.YEAR);
            List<PagosDocente> findPagosByNumeroOrdenPago;
            if (b) {
                findPagosByNumeroOrdenPago = pagosDocenteRNLocal.findPagosByNumeroOrdenPagoAnio(pagoDocente.getNumeroOrdenPago(), year);
            } else {
                findPagosByNumeroOrdenPago = pagosDocenteRNLocal.findPagosByNumeroOrdenPagoAnioAnulado(pagoDocente.getNumeroOrdenPago(), year);
            }
            for (PagosDocente pg : findPagosByNumeroOrdenPago) {
                pg.setFechaModificado(new Date());
                pg.setModificadoPor(this.usuarioLogerBean.getUsuario().getUsuario());
                pg.setAnulado(b);
                this.pagosDocenteRNLocal.edit(pg);
            }
            if (b) {
                sMensaje = "El dato fue anulado";
            } else {
                sMensaje = "El dato fue recuperado";
            }
            this.getPagosDocenteLstBean().cargarPagosDocente();
            RequestContext.getCurrentInstance().update("frmPri:dtPagosDocente");

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

    public void cargar() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = FacesMessage.SEVERITY_INFO;
        List<PagosDocente> findPagosByNumeroOrdenPago = new ArrayList<>();
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(pagoDocente.getFechaRegistro());
            int year = cal.get(Calendar.YEAR);
            findPagosByNumeroOrdenPago = pagosDocenteRNLocal.findPagosByNumeroOrdenPagoAnio(pagoDocente.getNumeroOrdenPago(), year);
            setPagoDocente(new PagosDocente());
            setPagosDocentes(findPagosByNumeroOrdenPago);
            try {
                setPagoDocente(findPagosByNumeroOrdenPago.get(0));
            } catch (Exception e) {
                setPagoDocente(new PagosDocente());
            }
            try {
                setPagoDocente2(findPagosByNumeroOrdenPago.get(1));
            } catch (Exception e) {
                setPagoDocente2(new PagosDocente());
            }
            try {
                setPagoDocente3(findPagosByNumeroOrdenPago.get(2));
            } catch (Exception e) {
                setPagoDocente3(new PagosDocente());
            }
            try {
                setPagoDocente4(findPagosByNumeroOrdenPago.get(3));
            } catch (Exception e) {
                setPagoDocente4(new PagosDocente());
            }
            try {
                setPagoDocente5(findPagosByNumeroOrdenPago.get(4));
            } catch (Exception e) {
                setPagoDocente5(new PagosDocente());
            }
            try {
                setPagoDocente6(findPagosByNumeroOrdenPago.get(5));
            } catch (Exception e) {
                setPagoDocente6(new PagosDocente());
            }

            try {
                getDocenteLstBean().setDocenteSeleccionado(findPagosByNumeroOrdenPago.get(0).getDocente());
            } catch (Exception e) {
                getDocenteLstBean().setDocenteSeleccionado(new Docente());
            }
            try {
                getProveedorLstBean().setProveedorSelect(findPagosByNumeroOrdenPago.get(0).getProveedor());
            } catch (Exception e) {
                getProveedorLstBean().setProveedorSelect(new Proveedor());
            }
            try {
                getCarreraLstBean().setCarreraSelect(findPagosByNumeroOrdenPago.get(0).getCarrera());
            } catch (Exception e) {
                getCarreraLstBean().setCarreraSelect(new Carrera());

            }

            try {
                getCarreraLstBean().setCarreraSeleccionada(findPagosByNumeroOrdenPago.get(0).getCarrera());
            } catch (Exception e) {
                getCarreraLstBean().setCarreraSeleccionada(new Carrera());

            }

            try {
                getCarreraLstBean().setLstCarrera(findPagosByNumeroOrdenPago.get(0).getDocente().getCarreras());
            } catch (Exception e) {
                getCarreraLstBean().setLstCarrera(new ArrayList<Carrera>());

            }

            try {
                getCarreraLstBean().setLstCarrerasAsoc(findPagosByNumeroOrdenPago.get(0).getDocente().getCarreras());
            } catch (Exception e) {
                getCarreraLstBean().setLstCarrerasAsoc(new ArrayList<Carrera>());

            }
            try {
                getCarreraLstBean().setLstCarrerasDocente(findPagosByNumeroOrdenPago.get(0).getDocente().getCarreras());
            } catch (Exception e) {
                getCarreraLstBean().setLstCarrerasDocente(new ArrayList<Carrera>());

            }
            RequestContext.getCurrentInstance().update("frmPri:otDocente");
            RequestContext.getCurrentInstance().update("frmPri:pnDetalle");
            RequestContext.getCurrentInstance().update("frmPri:otProveedor");
            RequestContext.getCurrentInstance().update("frmPri:pnInformacion");
            RequestContext.getCurrentInstance().update("frmPri:dtPagosDocente");
            RequestContext.getCurrentInstance().update("frmPri:pPriPagoDocente");
            RequestContext.getCurrentInstance().update("frmPri:pnComprobante");

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

    public void limpiar() {
        try {
            this.getCarreraLstBean().setCarreraSeleccionada(new Carrera());
        } catch (Exception ex) {
        }
        try {
            this.getDocenteLstBean().setDocenteSeleccionado(new Docente());
        } catch (Exception ex) {
        }
        try {
            this.getProveedorLstBean().setProveedorSelect(new Proveedor());
        } catch (Exception ex) {
        }
        try {
            this.getCarreraLstBean().setLstCarrerasDocente(new ArrayList<Carrera>());
        } catch (Exception ex) {
        }
        try {
            this.getDocenteLstBean().setTablaDocente(new DataTable());
        } catch (Exception ex) {
        }
        try {
            this.getProveedorLstBean().setTablaProveedor(new DataTable());
        } catch (Exception ex) {
        }
        
        cargarPagosDocentes();
        
         }//fin limpiar

    private void cargarUltimoNumero() {
        int numeroOrdenPago = pagosDocenteRNLocal.findUltimoNumero();
        //System.out.println("llama metodo cargar ultimo numero directo de la query"+numeroOrdenPago);
        numeroOrdenPago++;
        pagoDocente.setNumeroOrdenPago(numeroOrdenPago);

    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.newPage();
        pdf.setPageSize(PageSize.A4);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "Imagenes" + File.separator + "LogoFacultadHumanidades70x70.png";
        //SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        // pdf.add(new Phrase("Fecha: " + formato.format(new Date())));
        pdf.add(Image.getInstance(logo));
    }

    private void cargarLstRubroPresupuestario() {
        lstRubroPresupuestario = new ArrayList<SelectItem>();
        for (RubroPresupuestario rp : RubroPresupuestario.values()) {
            lstRubroPresupuestario.add(new SelectItem(rp, rp.toString()));
        }
    }

    private void cargarLstTipoEgresos() {
        lstTipoEgreso = new ArrayList<SelectItem>();
        for (TipoEgreso t : tipoEgresoFacadeLocal.findNoBorrados()) {
            lstTipoEgreso.add(new SelectItem(t, t.getDescripcion()));
        }
    }

    private void crearModificarOtroComprobante(PagosDocente pd, boolean crear) throws Exception {
        if (pd.getFechaComprobante() != null && !pd.getNumeroComprobante().isEmpty()) {
            pd.setNumeroOrdenPago(pagoDocente.getNumeroOrdenPago());
            if (crear) {
                pd.setFechaCreado(pagoDocente.getFechaCreado());
                pd.setCreadoPor(pagoDocente.getCreadoPor());
            } else {
                pd.setModificadoPor(pagoDocente.getModificadoPor());
                pd.setFechaModificado(pagoDocente.getFechaModificado());
            }
            pd.setAnulado(false);
            pd.setBorrado(false);
            pd.setFechaRegistro(pagoDocente.getFechaRegistro());
            pd.setFormapago(pagoDocente.getFormapago());
            pd.setCuenta(pagoDocente.getCuenta());
            pd.setRubroPresupuestario(pagoDocente.getRubroPresupuestario());
            if (pd.getProveedor() == null) {
                if (pagoDocente.getDocente() != null) {
                    pd.setDocente(pagoDocente.getDocente());
                    if (pagoDocente.getCarrera() != null) {
                        pd.setCarrera(pagoDocente.getCarrera());
                    }
                } else {
                    pd.setProveedor(pagoDocente.getProveedor());
                }
            }
            if (crear) {
                pagosDocenteRNLocal.create(pd);
            } else {
                pagosDocenteRNLocal.edit(pd);
            }
        } else if (pd.getId() != null) {
            try {
                pagosDocenteRNLocal.removeTotal(pd);
            } catch (Exception exception) {
            }
        }
    }

    private void crearModificarOtrosComprobantes(boolean crear) throws Exception {
        crearModificarOtroComprobante(pagoDocente2, crear);
        crearModificarOtroComprobante(pagoDocente3, crear);
        crearModificarOtroComprobante(pagoDocente4, crear);
        crearModificarOtroComprobante(pagoDocente5, crear);
        crearModificarOtroComprobante(pagoDocente6, crear);
    }

    private boolean comprobanteDuplicado(Docente docente, Proveedor proveedor, String numeroComprobante, Date fechaComprobante) {
        return pagosDocenteRNLocal.comprobanteDuplicado(docente, proveedor, numeroComprobante, fechaComprobante);
    }

    private void cargarPagosDocentes() {
        pagosDocentes = new ArrayList<>();

        pagoDocente = new PagosDocente();
        pagoDocente2 = new PagosDocente();
        pagoDocente3 = new PagosDocente();
        pagoDocente4 = new PagosDocente();
        pagoDocente5 = new PagosDocente();
        pagoDocente6 = new PagosDocente();

        pagosDocentes.add(pagoDocente);
        pagosDocentes.add(pagoDocente2);
        pagosDocentes.add(pagoDocente3);
        pagosDocentes.add(pagoDocente4);
        pagosDocentes.add(pagoDocente5);
        pagosDocentes.add(pagoDocente6);
    }
}
