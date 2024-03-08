/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class SessionControlerBean {

    private int iTimeSession;
    @ManagedProperty(value = "#{usuarioLogerBean}")
    private UsuarioLogerBean usuarioLogerBean;

    public UsuarioLogerBean getUsuarioLogerBean() {
        return usuarioLogerBean;
    }

    public void setUsuarioLogerBean(UsuarioLogerBean usuarioLogerBean) {
        this.usuarioLogerBean = usuarioLogerBean;
    }

    /**
     * Creates a new instance of SessionControlerBean
     */
    public SessionControlerBean() {
    }

    public int getiTimeSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        return session.getMaxInactiveInterval();
    }

    public void setiTimeSession(int iTimeSession) {
        this.iTimeSession = iTimeSession;
    }

    /**
     * Cierra la session del usuario
     *
     * @return url de la pagina
     */
    public String cerrarSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String url = request.getRequestURL().toString();
            String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
            String loginURL = baseURL + "login.xhtml?faces-redirect=true";
            return loginURL;
        }//fin if

        return "";
    }//fin cerrarSession

    /**
     * Crea una session nueva
     *
     * @return url de la pagina
     */
    public String createSession() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        /*session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
         if(session != null){
         session.invalidate();
         }//fin */

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        String loginURL = baseURL + "login.xhtml?faces-redirect=true";
        return loginURL;
    }//fin abrirSession

}
