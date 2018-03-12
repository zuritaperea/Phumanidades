/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Usuarios.Usuarios;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vouilloz
 */
@ManagedBean(name = "usuarioLogerBean")
@SessionScoped
public class UsuarioLogerBean implements Serializable {

    private Usuarios usuario;

    /**
     * Creates a new instance of UsuarioLogerBean
     */
    public UsuarioLogerBean() {
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public void navigationStatus() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        if (usuario == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String url = request.getRequestURL().toString();
            String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
            String loginURL = baseURL + "login.xhtml?faces-redirect=true";
            response.sendRedirect(loginURL);
        }

    }

    public boolean isGrupo(String descripcion) {
        try {
            return this.usuario.getGrupo().getDescripcion().equals(descripcion);
        } catch (Exception e) {
            return false;
        }
    }

}
