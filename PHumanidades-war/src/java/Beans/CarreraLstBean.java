/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Carrera;
import Entidades.Carreras.TipoCarrera;
import RN.CarrerasRNLocal;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@SessionScoped
public class CarreraLstBean implements Serializable {

    @EJB
    private CarrerasRNLocal carrerasRNLocal;//hacemos la referencia para poder utilizar el metodo findall
    private List<Carrera> lstCarrera; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSICarrera;//Aca se guarda el item seleccionado de la lista

    private List<Carrera> lstCarrerasDocente;
    private Carrera carreraSeleccionada;

    private List<TipoCarrera> lstTipoCarrerasAsoc;//se usa en tipo de carreras

    private List<Carrera> lstCarrerasAsoc;
    private Carrera[] arrayCarrerasSelect;
    private Carrera carreraSelect;

    private String sMensaje;

    CommandButton btnSelect;

    /**
     * Creates a new instance of UsuarioLstBean
     */
    public CarreraLstBean() {
    }

    @PostConstruct
    private void init() {
        this.cargarCarrera();
    }

    public CarrerasRNLocal getCarrerasRNLocal() {
        return carrerasRNLocal;
    }

    public void setCarrerasRNLocal(CarrerasRNLocal carrerasRNLocal) {
        this.carrerasRNLocal = carrerasRNLocal;
    }

    public List<Carrera> getLstCarrera() {
        return lstCarrera;
    }

    public void setLstCarrera(List<Carrera> lstCarrera) {
        this.lstCarrera = lstCarrera;
    }

    public List<SelectItem> getLstSICarrera() {
        return lstSICarrera;
    }

    public void setLstSICarrera(List<SelectItem> lstSICarrera) {
        this.lstSICarrera = lstSICarrera;
    }

    public List<Carrera> getLstCarrerasDocente() {
        return lstCarrerasDocente;
    }

    public void setLstCarrerasDocente(List<Carrera> lstCarrerasDocente) {
        this.lstCarrerasDocente = lstCarrerasDocente;
    }

    public Carrera getCarreraSeleccionada() {
        return carreraSeleccionada;
    }

    public void setCarreraSeleccionada(Carrera carreraSeleccionada) {
        this.carreraSeleccionada = carreraSeleccionada;
    }

    public List<TipoCarrera> getLstTipoCarrerasAsoc() {
        return lstTipoCarrerasAsoc;
    }

    public void setLstTipoCarrerasAsoc(List<TipoCarrera> lstTipoCarrerasAsoc) {
        this.lstTipoCarrerasAsoc = lstTipoCarrerasAsoc;
    }

    public List<Carrera> getLstCarrerasAsoc() {
        return lstCarrerasAsoc;
    }

    public void setLstCarrerasAsoc(List<Carrera> lstCarrerasAsoc) {
        this.lstCarrerasAsoc = lstCarrerasAsoc;
    }

    public Carrera[] getArrayCarrerasSelect() {
        return arrayCarrerasSelect;
    }

    public void setArrayCarrerasSelect(Carrera[] arrayCarrerasSelect) {
        this.arrayCarrerasSelect = arrayCarrerasSelect;
    }

    public Carrera getCarreraSelect() {
        return carreraSelect;
    }

    public void setCarreraSelect(Carrera carreraSelect) {
        this.carreraSelect = carreraSelect;
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public void cargarCarrera() {
        try {
            this.setLstCarrera(this.carrerasRNLocal.findCarreras());
            this.setLstSICarrera(new ArrayList<SelectItem>());
            for (Carrera car : this.getLstCarrera()) {
                this.getLstSICarrera().add(new SelectItem(car, car.getDescripcion()));
            }
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin cargarCarreras

    public void findCarreraNombre(String nombre) {

        this.setLstCarrera(carrerasRNLocal.findCarreraNombre(nombre));
    }

    public void cargarDatosListCarrera() {

        this.setLstCarrerasAsoc(new ArrayList<Carrera>(Arrays.asList(this.getArrayCarrerasSelect())));
        //ac referencia a DocenteLstBean, y setea las carreras seleccionadas que esta en el Array
        if (btnSelect.getId().equals("btnCarrerasDoc")) {
            //Entra al if si es llamado por Docente.xhtml

            RequestContext.getCurrentInstance().update("frmPri:dtCarrerasAso");
            RequestContext.getCurrentInstance().update("frmPri:otMensajeDocente");
        }
        if (btnSelect.getId().equals("btnCarrerasCohorte")) {
            //Entra al if si es llamado por Docente.xhtml

            RequestContext.getCurrentInstance().update("frmPri:dtCarrerasCohorte");
            RequestContext.getCurrentInstance().update("frmPri:otMensajeCohorte");
        }
    }

    public void quitarCarrera() {
        this.getLstCarrerasAsoc().remove(this.getCarreraSelect());
        this.setsMensaje("La Carrera se elimino con exito");

    }

    public void definirActionBoton(ActionEvent e) {

        btnSelect = (CommandButton) e.getSource();

    }

}
