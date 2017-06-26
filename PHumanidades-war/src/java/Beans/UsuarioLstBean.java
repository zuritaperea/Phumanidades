/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Usuarios.Usuarios;
import RN.UsuariosRNLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class UsuarioLstBean {

    @EJB
    private UsuariosRNLocal usuarioRNLocal;//hacemos la referencia para poder utilizar el metodo findall
    private List<Usuarios> lstUsuario; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSIUsuario;//Aca se guarda el item seleccionado de la lista

    /**
     * Creates a new instance of UsuarioLstBean
     */
    public UsuarioLstBean() {
    }

    @PostConstruct
    private void init() {
        this.cargarUsuario();
    }

    public UsuariosRNLocal getUsuarioRNLocal() {
        return usuarioRNLocal;
    }

    public void setUsuarioRNLocal(UsuariosRNLocal usuarioRNLocal) {
        this.usuarioRNLocal = usuarioRNLocal;
    }

    public List<Usuarios> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(List<Usuarios> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public List<SelectItem> getLstSIUsuario() {
        return lstSIUsuario;
    }

    public void setLstSIUsuario(List<SelectItem> lstSIUsuario) {
        this.lstSIUsuario = lstSIUsuario;
    }

    public void cargarUsuario() {
        try {
            this.setLstUsuario(this.usuarioRNLocal.findUsuarios(null));
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin cargarUsuario

}
