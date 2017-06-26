/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.TipoCarrera;
import RN.TipoCarreraRNLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@SessionScoped
public class TipoCarreraLstBean implements Serializable {

    @EJB
    private TipoCarreraRNLocal tipoCarreraRNLocal;//hacemos la referencia para poder utilizar el metodo findall

    private List<TipoCarrera> lstTipoCarrera; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSITipoCarrera;//Aca se guarda el item seleccionado de la lista
    private TipoCarrera tipoCarrera;

    public List<TipoCarrera> getLstTipoCarrera() {
        return lstTipoCarrera;
    }

    public void setLstTipoCarrera(List<TipoCarrera> lstTipoCarrera) {
        this.lstTipoCarrera = lstTipoCarrera;
    }

    public List<SelectItem> getLstSITipoCarrera() {
        return lstSITipoCarrera;
    }

    public void setLstSITipoCarrera(List<SelectItem> lstSITipoCarrera) {
        this.lstSITipoCarrera = lstSITipoCarrera;
    }

    public TipoCarrera getTipoCarrera() {
        return tipoCarrera;
    }

    public void setTipoCarrera(TipoCarrera tipoCarrera) {
        this.tipoCarrera = tipoCarrera;
    }

    /**
     * Creates a new instance of UsuarioLstBean
     */
    public TipoCarreraLstBean() {
    }

    @PostConstruct
    private void init() {
        this.cargarTipoCarrera();
        tipoCarrera = new TipoCarrera();
    }

    public void cargarTipoCarrera() {
        try {
            this.setLstTipoCarrera(this.tipoCarreraRNLocal.findAll());
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin cargarCarreras

    /*  public void findCarreraNombre(String nombre){
    
     this.setLstCarrera(carrerasRNLocal.findCarreraNombre(nombre));
     }
     */
}
