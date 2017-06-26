/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Anio;
import RN.AnioRNLocal;
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
public class AnioLstBean implements Serializable {

    @EJB
    private AnioRNLocal anioRNLocal;//hacemos la referencia para poder utilizar el metodo findall

    private List<Anio> lstAnios; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private Anio anio;

    private String sMensaje;

    /**
     * Creates a new instance of UsuarioLstBean
     */
    public AnioLstBean() {
    }

    @PostConstruct
    private void init() {
        this.cargarAnios();
    }

    public List<Anio> getLstAnios() {
        return lstAnios;
    }

    public void setLstAnios(List<Anio> lstAnios) {
        this.lstAnios = lstAnios;
    }

    public Anio getAnio() {
        return anio;
    }

    public void setAnio(Anio anio) {
        this.anio = anio;
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public void cargarAnios() {
        try {
            this.setLstAnios(this.anioRNLocal.findAll());
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin cargarAnios

}
