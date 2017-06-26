/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Carrera;
import Entidades.Carreras.Cuenta;
import Entidades.Carreras.Materia;
import RN.CarrerasRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AFerSor
 */
@ManagedBean
@RequestScoped
public class CarreraBean implements Serializable {

    @EJB
    private CarrerasRNLocal carrerasRNLocal;

    @ManagedProperty("#{carreraLstBean}")
    private CarreraLstBean carreraLstBean;

    @ManagedProperty(value = "#{listadoMateriasBean}")
    private ListadoMateriasBean listadoMateriasBean;

    @ManagedProperty(value = "#{tipoCarreraLstBean}")
    private TipoCarreraLstBean tipoCarreraLstBean;

    @ManagedProperty(value = "#{cuentaLstBean}")
    private CuentaLstBean cuentaLstBean;

    private Carrera carrera;
    private int iActionBtnSelect;
    private CommandButton cbAction;

    private String sMensaje;

    public CarreraBean() {
        carrera = new Carrera();
    }

    /*   public UsuarioLstBean getUsuarioLstBean() {
     return usuarioLstBean;
     }*/
    /*   public void setUsuarioLstBean(UsuarioLstBean usuarioLstBean) {
     this.usuarioLstBean = usuarioLstBean;
     }*/
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
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

    public CarreraLstBean getCarreraLstBean() {
        return carreraLstBean;
    }

    public void setCarreraLstBean(CarreraLstBean carreraLstBean) {
        this.carreraLstBean = carreraLstBean;
    }

    public ListadoMateriasBean getListadoMateriasBean() {
        return listadoMateriasBean;
    }

    public void setListadoMateriasBean(ListadoMateriasBean listadoMateriasBean) {
        this.listadoMateriasBean = listadoMateriasBean;
    }

    public TipoCarreraLstBean getTipoCarreraLstBean() {
        return tipoCarreraLstBean;
    }

    public void setTipoCarreraLstBean(TipoCarreraLstBean tipoCarreraLstBean) {
        this.tipoCarreraLstBean = tipoCarreraLstBean;
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public CuentaLstBean getCuentaLstBean() {
        return cuentaLstBean;
    }

    public void setCuentaLstBean(CuentaLstBean cuentaLstBean) {
        this.cuentaLstBean = cuentaLstBean;
    }

    public void actionBtn() {

        switch (this.getiActionBtnSelect()) {
            case 0:
                //System.out.println("Entro opcion 0 create()");
                this.create();
                break;
            case 1:
                this.edit();
                break;
            case 2:
                //borra el campo
                System.out.println("case delete");
                this.delete();
                //this.delete(Boolean.TRUE);
                break;
        }//fin switch

    }//fin actionBtn

    public void setBtnSelect(ActionEvent e) {
        CommandButton btnSelect = (CommandButton) e.getSource();

        System.out.println("boton select: " + btnSelect.getId());

        //activo el boton
        this.getCbAction().setDisabled(false);
        switch (btnSelect.getId()) {
            case "cbCreate":
                this.setiActionBtnSelect(0);
                this.getCbAction().setValue("Guardar");
                this.getListadoMateriasBean().setLstMateria(new ArrayList<Materia>());
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

    }//fin setBtnSelect

    public void create() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            this.getCarrera().setMaterias(this.getListadoMateriasBean().getLstMateria());
            this.getCarrera().setCuenta(this.getCuentaLstBean().getCuenta());
            this.getCarrera().setTipoCarrera(this.getTipoCarreraLstBean().getTipoCarrera());
            carrerasRNLocal.create(this.getCarrera());
            sMensaje = "La Carrera fue Creada";
            severity = FacesMessage.SEVERITY_INFO;
            //agregar a la lista
            this.getCarreraLstBean().getLstCarrera().add(this.getCarrera());
            //limpíar campos
            this.limpiar();

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin create

    public void delete() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            carrerasRNLocal.remove(this.getCarrera());

            sMensaje = "La Carrera fue Borrada";
            severity = FacesMessage.SEVERITY_INFO;

            //remover de la lista
            this.getCarreraLstBean().getLstCarrera().remove(this.getCarrera());

            this.getCbAction().setValue("Eliminar");
            this.getCbAction().setDisabled(true);

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin delete

    public void limpiar() {
        this.setCarrera(new Carrera());
        RequestContext.getCurrentInstance().update("frmPri:dtCarreras");

    }//fin limpiar

    public Carrera findById(Long id) {
        try {
            return carrerasRNLocal.findByiD(id);
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al buscar la Carrera: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
        return null;
    }//fin dinfByid

    private void edit() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            this.getCarrera().setMaterias(this.getListadoMateriasBean().getLstMateria());
            this.getCarrera().setCuenta(this.getCuentaLstBean().getCuenta());
            this.getCarrera().setTipoCarrera(this.getTipoCarreraLstBean().getTipoCarrera());
            carrerasRNLocal.edit(this.getCarrera());
            sMensaje = "La Carrera fue Modificada";
            severity = FacesMessage.SEVERITY_INFO;
            //agregar a la lista
            this.getCarreraLstBean().cargarCarrera();
            //limpíar campos
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

}
