/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Carrera;
import Entidades.Carreras.Cohorte;
import Entidades.Egresos.FormaPago;

import Entidades.Egresos.TipoComprobante;
import RN.CohorteRNLocal;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;

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
public class CohorteBean implements Serializable {

    @EJB
    private CohorteRNLocal cohorteRNLocal;

    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;

    @ManagedProperty(value = "#{navegarBean}")
    private NavegarBean navegarBean;

    @ManagedProperty(value = "#{carreraLstBean}")
    private CarreraLstBean carreraLstBean;

    @ManagedProperty(value = "#{anioLstBean}")
    private AnioLstBean anioLstBean;

//Busqueda docentes
    private String tipoBusqueda;
    private List<String> lstTipoBusqueda;
    private String busqueda;

//fin busqueda docentes
    private String busquedaCarrera;

    private Cohorte cohorte;
//Inicio Busqueda Carrera   

//Fin Busqueda Carrera    
    @Enumerated(EnumType.STRING)
    private TipoComprobante tipocomprobante;
    private List<SelectItem> lstTipoComprobante;

    @Enumerated(EnumType.STRING)
    private FormaPago formapago;
    private List<SelectItem> lstFormaPago;
    private CommandButton cbAction;

    /**
     * Creates a new instance of DocenteBean
     */
    @PostConstruct
    private void init() {
        //  cargarLstTipoBusqueda();
        cohorte = new Cohorte();
        cohorteLstBean.getLstCohorte();
        //   cargarLstFormaPago();
        //   cargarLstTipoComprobante();
    }

    public CohorteBean() {
    }

    public CommandButton getCbAction() {
        return cbAction;
    }

    public void setCbAction(CommandButton cbAction) {
        this.cbAction = cbAction;
    }

    public NavegarBean getNavegarBean() {
        return navegarBean;
    }

    public void setNavegarBean(NavegarBean navegarBean) {
        this.navegarBean = navegarBean;
    }

    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public List<String> getLstTipoBusqueda() {
        return lstTipoBusqueda;
    }

    public void setLstTipoBusqueda(List<String> lstTipoBusqueda) {
        this.lstTipoBusqueda = lstTipoBusqueda;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getBusquedaCarrera() {
        return busquedaCarrera;
    }

    public void setBusquedaCarrera(String busquedaCarrera) {
        this.busquedaCarrera = busquedaCarrera;
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

    public TipoComprobante getTipocomprobante() {
        return tipocomprobante;
    }

    public void setTipocomprobante(TipoComprobante tipocomprobante) {
        this.tipocomprobante = tipocomprobante;
    }

    public FormaPago getFormapago() {
        return formapago;
    }

    public void setFormapago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public Cohorte getCohorte() {
        return cohorte;
    }

    public void setCohorte(Cohorte cohorte) {
        this.cohorte = cohorte;
    }

    public CohorteLstBean getCohorteLstBean() {
        return cohorteLstBean;
    }

    public void setCohorteLstBean(CohorteLstBean cohorteLstBean) {
        this.cohorteLstBean = cohorteLstBean;
    }

    public CarreraLstBean getCarreraLstBean() {
        return carreraLstBean;
    }

    public void setCarreraLstBean(CarreraLstBean carreraLstBean) {
        this.carreraLstBean = carreraLstBean;
    }

    public AnioLstBean getAnioLstBean() {
        return anioLstBean;
    }

    public void setAnioLstBean(AnioLstBean anioLstBean) {
        this.anioLstBean = anioLstBean;
    }

    /**
     * elimina una cohorte
     */
    public void eliminar(Cohorte c) {
        //System.out.println("entro Eliminar");
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            cohorteRNLocal.remove(c);//cambia el estado del pago segun el valor bEstado
            this.carreraLstBean.setLstCarrerasAsoc(new ArrayList<Carrera>());
            cohorteLstBean.getLstCohorte().remove(c);
            RequestContext.getCurrentInstance().update("frmPri:dtCohorte");
            severity = FacesMessage.SEVERITY_INFO;
            sMensaje = "Se eliminó la cohorte";

        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al eliminar la cohorte: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin eliminar

    public void cargarCarrerasAsociadas() {
        //   this.getDocenteLstBean().setLstCarrerasAsoc(new ArrayList<Carrera>(Arrays.asList(this.getDocenteLstBean().getArrayCarrerasSelect())));
        //ac referencia a DocenteLstBean, y setea las carreras seleccionadas que esta en el Array
    }

    public void alta() {

        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            cohorte.setAnio(this.getAnioLstBean().getAnio());
            cohorteRNLocal.create(cohorte);
            cohorteLstBean.cargarCohorte();

            this.setCohorte(new Cohorte());

            RequestContext.getCurrentInstance().update("frmPri:itDescripcionCohorte");
            RequestContext.getCurrentInstance().update("frmPri:itCantidadCuotas");
            RequestContext.getCurrentInstance().update("frmPri:itImporteCuota");
            RequestContext.getCurrentInstance().update("frmPri:dtCohorte");

            sMensaje = "Cohorte Registrada";
            severity = FacesMessage.SEVERITY_INFO;

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    public void salir() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String path = context.getRequestContextPath() + "/Cohorte.xhtml?faces-redirect=true";
            context.redirect(path);
        } catch (IOException ex) {
            Logger.getLogger(CobroCuotasAlumnosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * define el action
     *
     * @param nuevo si es nuevo o modificar*
     */
    public void definirActionBoton(boolean nuevo) {
        if (nuevo) {
            this.getCohorteLstBean().setiTipoBoton(0);
        } else {
            try {
                this.getCohorteLstBean().setiTipoBoton(1);
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String path = context.getRequestContextPath() + "/CohorteEdit.xhtml?faces-redirect=true";
                context.redirect(path);
            } catch (IOException ex) {
                Logger.getLogger(CohorteBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }// fin definirActionBoton

    public void modificar(Cohorte c) {
        this.cohorte = c;
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            cohorte.setAnio(this.getAnioLstBean().getAnio());
            cohorteRNLocal.edit(cohorte);
            cohorteLstBean.cargarCohorte();

            this.setCohorte(new Cohorte());

            RequestContext.getCurrentInstance().update("frmPri:itDescripcionCohorte");
            RequestContext.getCurrentInstance().update("frmPri:itCantidadCuotas");
            RequestContext.getCurrentInstance().update("frmPri:itImporteCuota");
            RequestContext.getCurrentInstance().update("frmPri:dtCohorte");

            sMensaje = "Cohorte Modificada, Click en Salir";
            severity = FacesMessage.SEVERITY_INFO;

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    //Inicio Metodos Buscar Cohorte
    public void abrirDlgFindCohorte() {
       // btnSelect = (CommandButton) e.getSource();

        //RequestContext.getCurrentInstance().update("dFindTurnoExamen");
        this.getCohorteLstBean().setLstCohorte(new ArrayList<Cohorte>());
        RequestContext.getCurrentInstance().execute("PF('dlgFindCohorte').show();");
    }

    public void buscarCohorte() throws Exception {

        this.getCohorteLstBean().findCohorte(busqueda);
    }

    public void devolverCohorte() {

        RequestContext.getCurrentInstance().update("frmPri:otCohorte");

    }

    public Cohorte findById(Long id) {
        try {
            return cohorteRNLocal.findByiD(id);
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al buscar la Cohorte: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
        return null;
    }//fin dinfByid

}
