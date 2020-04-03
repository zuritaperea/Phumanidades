/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cohorte;
import RN.CohorteRNLocal;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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

    private int iActionBtnSelect;
//Busqueda docentes
    private String tipoBusqueda;
    private List<String> lstTipoBusqueda;
    private String busqueda;

//fin busqueda docentes
    private String busquedaCarrera;

    private Cohorte cohorte;
//Inicio Busqueda Carrera   

    private CommandButton cbAction;

    public CohorteBean() {
        cohorte = new Cohorte();

    }

    public CohorteRNLocal getCohorteRNLocal() {
        return cohorteRNLocal;
    }

    public void setCohorteRNLocal(CohorteRNLocal cohorteRNLocal) {
        this.cohorteRNLocal = cohorteRNLocal;
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

    public void actionBtn() {

        switch (this.getiActionBtnSelect()) {
            case 0:
                //System.out.println("Entro opcion 0 create()");
                this.alta();
                break;
            case 1:
                this.modificar(cohorte);
                break;
            case 2:
                //borra el campo
                this.eliminar(cohorte);
                //this.delete(Boolean.TRUE);
                break;
        }//fin switch

    }//fin actionBtn

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
            cohorteLstBean.getLstCohorte().remove(c);
            RequestContext.getCurrentInstance().update("frmPri:dtCohorte");
            severity = FacesMessage.SEVERITY_INFO;
            sMensaje = "Se eliminó la cohorte";
            this.getCbAction().setValue("Eliminar");
            this.getCbAction().setDisabled(true);
            limpiar();
        } catch (Exception ex) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al eliminar la cohorte: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin eliminar

    public void limpiar() {
        this.setCohorte(new Cohorte());
        RequestContext.getCurrentInstance().update("frmPri:itDescripcionCohorte");
        RequestContext.getCurrentInstance().update("frmPri:itCantidadCuotas");
        RequestContext.getCurrentInstance().update("frmPri:itImporteCuota");
        RequestContext.getCurrentInstance().update("frmPri:dtCohorte");
        RequestContext.getCurrentInstance().execute("PF('dtCohorte').filter();");
        RequestContext.getCurrentInstance().update("frmPri:growl");
    }

    public void cargarCarrerasAsociadas() {
        //   this.getDocenteLstBean().setLstCarrerasAsoc(new ArrayList<Carrera>(Arrays.asList(this.getDocenteLstBean().getArrayCarrerasSelect())));
        //ac referencia a DocenteLstBean, y setea las carreras seleccionadas que esta en el Array
    }

    public void setBtnSelect(ActionEvent e) {
        CommandButton btnSelect = (CommandButton) e.getSource();

        //activo el boton
        try {
            this.getCbAction().setDisabled(false);
            switch (btnSelect.getId()) {
                case "cbCreate":
                    this.setiActionBtnSelect(0);
                    this.getCbAction().setValue("Guardar");
                    break;
                case "cbDelete":
                    this.setiActionBtnSelect(2);
                    this.getCbAction().setValue("Eliminar");
                    break;
                case "cbEdit":
                    this.setiActionBtnSelect(1);
                    this.getCbAction().setValue("Modificar");
                    break;
            }
        } catch (Exception ex) {
        }

    }

    public void alta() {

        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            cohorteRNLocal.create(cohorte);
            cohorteLstBean.cargarCohorte();

            this.setCohorte(new Cohorte());

            sMensaje = "Cohorte Registrada";
            severity = FacesMessage.SEVERITY_INFO;
            this.limpiar();

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
            cohorteRNLocal.edit(cohorte);
            cohorteLstBean.cargarCohorte();

            this.setCohorte(new Cohorte());
            this.limpiar();

            sMensaje = "Cohorte Modificada";
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
