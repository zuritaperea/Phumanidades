/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cuenta;
import Entidades.Egresos.FormaPago;
import Entidades.Egresos.PagosDocente;
import Entidades.Persona.Docente;
import Entidades.Persona.Proveedor;
import RN.PagosDocenteRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class PagosGeneralesBean implements Serializable {

    @EJB
    private PagosDocenteRNLocal pagoGeneralRNLocal ;

    @ManagedProperty(value = "#{pagosGeneralesLstBean}")
    private PagosGeneralesLstBean pagosGeneralesLstBean;

    @ManagedProperty(value = "#{docenteLstBean}")
    private DocenteLstBean docenteLstBean;

    @ManagedProperty(value = "#{proveedorLstBean}")
    private ProveedorLstBean proveedorLstBean;

    @ManagedProperty(value = "#{cuentaLstBean}")
    private CuentaLstBean cuentaLstBean;

    @ManagedProperty(value = "#{numeroCuentaBean}")
    private NumeroCuentaBean numeroCuentaBean;

    private PagosDocente gastoGeneral;
//Inicio Busqueda Carrera   

    private CommandButton cbAction;
    @Enumerated(EnumType.STRING)
    private FormaPago formapago;
    private List<SelectItem> lstFormaPago;

    /**
     * Creates a new instance of DocenteBean
     */
    @PostConstruct
    private void init() {
        gastoGeneral = new PagosDocente();
        cargarLstFormaPago();
        pagosGeneralesLstBean.cargarPagosGeneral();
    }

    public void cargarLstFormaPago() {
        lstFormaPago = new ArrayList<SelectItem>();
        for (FormaPago fp : FormaPago.values()) {
            lstFormaPago.add(new SelectItem(fp, fp.toString()));
        }
    }

    public FormaPago getFormapago() {
        return formapago;
    }

    public void setFormapago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public List<SelectItem> getLstFormaPago() {
        return lstFormaPago;
    }

    public void setLstFormaPago(List<SelectItem> lstFormaPago) {
        this.lstFormaPago = lstFormaPago;
    }

    public PagosGeneralesBean() {
    }

    public DocenteLstBean getDocenteLstBean() {
        return docenteLstBean;
    }

    public void setDocenteLstBean(DocenteLstBean docenteLstBean) {
        this.docenteLstBean = docenteLstBean;
    }

    public CommandButton getCbAction() {
        return cbAction;
    }

    public void setCbAction(CommandButton cbAction) {
        this.cbAction = cbAction;
    }

    public PagosGeneralesLstBean getPagosGeneralesLstBean() {
        return pagosGeneralesLstBean;
    }

    public void setPagosGeneralesLstBean(PagosGeneralesLstBean pagosGeneralesLstBean) {
        this.pagosGeneralesLstBean = pagosGeneralesLstBean;
    }

    public ProveedorLstBean getProveedorLstBean() {
        return proveedorLstBean;
    }

    public void setProveedorLstBean(ProveedorLstBean proveedorLstBean) {
        this.proveedorLstBean = proveedorLstBean;
    }

    public PagosDocente getGastoGeneral() {
        return gastoGeneral;
    }

    public void setGastoGeneral(PagosDocente gastoGeneral) {
        this.gastoGeneral = gastoGeneral;
    }

    public PagosDocenteRNLocal getPagoGeneralRNLocal() {
        return pagoGeneralRNLocal;
    }

    public void setPagoGeneralRNLocal(PagosDocenteRNLocal pagoGeneralRNLocal) {
        this.pagoGeneralRNLocal = pagoGeneralRNLocal;
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

    public void definirActionBoton(ActionEvent e) {

        CommandButton btnSelect = (CommandButton) e.getSource();

        this.getCbAction().setDisabled(false);

        if (btnSelect.getId().equals("btnEditGG")) {

            this.getCbAction().setValue("Editar");
            this.getPagosGeneralesLstBean().setiTipoBoton(1);

            //Inicio- traemos el pago general seleccionado de la tabla
            FacesContext context = FacesContext.getCurrentInstance();
            PagosGeneralesLstBean lstPagosGenerales = (PagosGeneralesLstBean) context.getApplication().evaluateExpressionGet(context, "#{pagosGeneralesLstBean}", PagosGeneralesLstBean.class);
            this.gastoGeneral = lstPagosGenerales.getGastoGeneralSeleccionado();

            if (this.gastoGeneral.getDocente() != null) {
                this.getDocenteLstBean().setDocenteSeleccionado(this.gastoGeneral.getDocente());
                RequestContext.getCurrentInstance().update("frmPri:otDocente4");
            }
            if (this.gastoGeneral.getProveedor() != null) {
                this.getProveedorLstBean().setProveedorSelect(this.gastoGeneral.getProveedor());
                RequestContext.getCurrentInstance().update("frmPri:otProveedor");
            }
        }
        if (btnSelect.getId().equals("btnDeleteGG")) {
            this.getCbAction().setValue("Eliminar");
            this.getPagosGeneralesLstBean().setiTipoBoton(2);
        }

        if (btnSelect.getId().equals("btnNuevoPagoGeneralDocente")) {
            this.getCbAction().setValue("Guardar");
            this.getPagosGeneralesLstBean().setiTipoBoton(0);
            this.setGastoGeneral(new PagosDocente());
            this.getDocenteLstBean().setDocenteSeleccionado(new Docente());
            this.getProveedorLstBean().setProveedorSelect(new Proveedor());
            RequestContext.getCurrentInstance().update("frmPri:otDocente4");
            RequestContext.getCurrentInstance().update("frmPri:otProveedor");
        }//fin if

        if (btnSelect.getId().equals("cbRecuperarBorradoGG")) {
            this.getPagosGeneralesLstBean().setiTipoBoton(3);
            this.getCbAction().setValue("Recuperar");

        }//fin if

    }// fin definirActionBoton

    public void actionButton(ActionEvent e) {
        Integer iBoton = this.getPagosGeneralesLstBean().getiTipoBoton();
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
        }//fin switch

    }//fin actionButton

    public void alta() {
        System.out.println("metodo alta proveedor" + proveedorLstBean.getProveedorSelect());
        System.out.println("metodo alta Docente" + docenteLstBean.getDocenteSeleccionado());
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            gastoGeneral.setDocente(docenteLstBean.getDocenteSeleccionado());

            gastoGeneral.setProveedor(proveedorLstBean.getProveedorSelect());

            gastoGeneral.setBorrado(false);

            this.pagoGeneralRNLocal.create(gastoGeneral);

            this.getPagosGeneralesLstBean().getLstGastoGeneral().add(gastoGeneral);
            this.setGastoGeneral(new PagosDocente());
            this.getDocenteLstBean().setDocenteSeleccionado(new Docente());
            this.getProveedorLstBean().setProveedorSelect(new Proveedor());

            sMensaje = "El Pago General fue Registrado";
            severity = FacesMessage.SEVERITY_INFO;
            pagosGeneralesLstBean.cargarPagosGeneral();

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    private void eliminar(Boolean bEstado) {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            pagoGeneralRNLocal.remove(gastoGeneral, bEstado);//cambia el estado del Pago segun el valor bEstado

            //obtiene la posicion en la lista
            int iPos = this.getPagosGeneralesLstBean().getLstGastoGeneral().indexOf(this.getGastoGeneral());

//remueve de la lista el pago segun la posicion de esta
            //SE DEBE REMOVER EL ITEM YA QUE LUEGO DEBE VOLVER A SER AGREGADO CON EL NUEVO VALOR 'TRUE OR FALSE'
            this.getPagosGeneralesLstBean().getLstGastoGeneral().remove(iPos);

            //envia el mensaje segun sea true o false bEstado
            if (bEstado) {
                gastoGeneral.setBorrado(Boolean.TRUE);
                sMensaje = "El dato fue eliminado, Click salir";
            } else {
                gastoGeneral.setBorrado(Boolean.FALSE);
                sMensaje = "El dato fue recuperado, Click salir";
            }
            this.getPagosGeneralesLstBean().getLstGastoGeneral().add(iPos, this.getGastoGeneral());
            this.getPagosGeneralesLstBean().cargarPagosGeneral();
            this.getCbAction().setValue("Eliminar");
            this.getCbAction().setDisabled(true);

            severity = FacesMessage.SEVERITY_INFO;
        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al Bloquear el Pago: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin eliminar

    /**
     * elimina un Pago
     */
    /*    private void eliminar(Boolean bEstado) {
     //System.out.println("entro Eliminar");
     String sMensaje = "";
     FacesMessage fm;
     FacesMessage.Severity severity = null;
     try {
            
     PagosDocenteRNLocal.remove(pagoDocente, bEstado);//cambia el estado del pago segun el valor bEstado

     //obtiene la posicion en la lista
     int iPos = this.getPagosDocenteLstBean().getLstPagosDocente().indexOf(this.getPagoDocente());
     //remueve de la lista el pago segun la posicion de esta
     //SE DEBE REMOVER EL ITEM YA QUE LUEGO DEBE VOLVER A SER AGREGADO CON EL NUEVO VALOR 'TRUE OR FALSE'
     this.getPagosDocenteLstBean().getLstPagosDocente().remove(iPos);
     //envia el mensaje segun sea true o false bEstado
     if (bEstado) {
     pagoDocente.setBorrado(Boolean.TRUE);
     sMensaje = "El dato fue eliminado, Click salir";
     } else {
     pagoDocente.setBorrado(Boolean.FALSE);
     sMensaje = "El dato fue recuperado, Click salir";
     }
            
     this.getPagosDocenteLstBean().getLstPagosDocente().add(iPos, this.getPagoDocente());
     this.getPagosDocenteLstBean().cargarPagosDocente();
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

     public void limpiar() {
     this.setPagoDocente(new PagosDocente());
     this.carreraLstBean.setLstCarrerasDocente(new ArrayList<Carrera>());
     this.docenteLstBean.setDocenteSeleccionado(new Docente());
     }//fin limpiar

     public void buscarPagoXDni() {
     FacesMessage fm;
     try {
     System.out.println("dni docente: " + this.dni);
     if (!this.dni.equals("")) {
     this.getPagosDocenteLstBean().setLstPagosDocente(PagosDocenteRNLocal.findPagosByDni(this.dni));
     System.out.println("lista : " + this.getPagosDocenteLstBean().getLstPagosDocente());
     if (this.getPagosDocenteLstBean().getLstPagosDocente().isEmpty()) {
     fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
     FacesContext fc = FacesContext.getCurrentInstance();
     fc.addMessage(null, fm);
     }//fin if

     } else {
     System.out.println("Salio por el lse");
     fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No ingreso un Dni valido", null);
     FacesContext fc = FacesContext.getCurrentInstance();
     fc.addMessage(null, fm);
                
     }
            
     } catch (Exception ex) {
     fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
     FacesContext fc = FacesContext.getCurrentInstance();
     fc.addMessage("frmPri:btnDniPD", fm);
     }//fin catch

     }*/
    public void limpiar() {
        this.setGastoGeneral(new PagosDocente());
        this.proveedorLstBean.setProveedorSelect(new Proveedor());
        this.docenteLstBean.setDocenteSeleccionado(new Docente());
        this.cuentaLstBean.setCuenta(new Cuenta());
        this.numeroCuentaBean.setNumero(0);
    }//fin limpiar

    private void modificar() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            if (this.getDocenteLstBean().getDocenteSeleccionado() != null) {
                gastoGeneral.setDocente(this.getDocenteLstBean().getDocenteSeleccionado());
            }

            if (this.getProveedorLstBean().getProveedorSelect() != null) {
                gastoGeneral.setProveedor(this.getProveedorLstBean().getProveedorSelect());
            }
            pagoGeneralRNLocal.edit(gastoGeneral);
            sMensaje = "Los Datos del Egreso fueron modificados";
            severity = FacesMessage.SEVERITY_INFO;
            //elimino y agrego  a la lista

            int iPos = this.getPagosGeneralesLstBean().getLstGastoGeneral().indexOf(this.getGastoGeneral());
            this.getPagosGeneralesLstBean().getLstGastoGeneral().remove(iPos);
            this.getPagosGeneralesLstBean().getLstGastoGeneral().add(iPos, this.getGastoGeneral());

            this.getCbAction().setValue("Editar");
            this.getCbAction().setDisabled(true);
            RequestContext.getCurrentInstance().update("frmPri:dtPagosGenerales");
            RequestContext.getCurrentInstance().execute("PF('dlgPagosGenerales').hide();");

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

}
