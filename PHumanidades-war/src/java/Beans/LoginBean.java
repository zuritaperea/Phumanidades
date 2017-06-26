/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Usuarios.Usuarios;
import RN.UsuariosRNLocal;
import Recursos.Encrypter;
import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class LoginBean {

    private Usuarios usuario;
    private String sConfirmarContrasena;
    private String user;
    private String password;
    private String contrasena;

    @ManagedProperty(value = "#{usuarioLogerBean}")
    private UsuarioLogerBean usuarioLogerBean;
    @ManagedProperty(value = "#{sessionControlerBean}")
    private SessionControlerBean sessionControlerBean;
    @EJB
    private UsuariosRNLocal usuariosRNLocal;

    //Inicio getters and setters
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getsConfirmarContrasena() {
        return sConfirmarContrasena;
    }

    public void setsConfirmarContrasena(String sConfirmarContrasena) {
        this.sConfirmarContrasena = sConfirmarContrasena;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioLogerBean getUsuarioLogerBean() {
        return usuarioLogerBean;
    }

    public void setUsuarioLogerBean(UsuarioLogerBean usuarioLogerBean) {
        this.usuarioLogerBean = usuarioLogerBean;
    }

    public SessionControlerBean getSessionControlerBean() {
        return sessionControlerBean;
    }

    public void setSessionControlerBean(SessionControlerBean sessionControlerBean) {
        this.sessionControlerBean = sessionControlerBean;
    }

    //Fin getters and setters
    /**
     * Creates a new instance of LoginBean
     */
    @PostConstruct
    private void inicializarComponentes() {
        /*  try {
         this.setUsuario(new Usuario());
         usuario.setNombre("Franco");
         usuario.setApellido("Aguirre");
         usuario.setDni("27256186");
         usuario.setCuil("23272561869");
         usuario.setUsuario("vouilloz");
         usuario.setContrasena("123");
         usuario.setBorrado(Boolean.FALSE);
         this.getUsuario().setContrasena(Encrypter.encriptar(this.getUsuario().getContrasena()));
         System.out.println("Post construct" + usuario.getContrasena());
         this.usuarioRNLocal.create(usuario, this.getContrasena());
         } catch (Exception e) {
         }*/
    }

    public LoginBean() {
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Verifica el tipo de usuario y acceso a los datos
     *
     * @return url de la pagina dependiendo el acceso
     */
    public String login() {

        try {
            Usuarios usuAux = usuariosRNLocal.findUserByNombreContrasena(user, Encrypter.encriptar(password));
            //Encrypter.encriptar(contrasena)
            if (usuAux != null) {

                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("userLogged", 1);
                //PARA MOSTRAR USUARIO LOGUEADO 
                usuarioLogerBean.setUsuario(usuAux);//Se guarda el usuario para poder ser mostrado como usuario logueado


                return "index.xhtml?faces-redirect=true";
                // }//fin if
            } else {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor introduzca un nombre de usuario y una contraseña correctos. Tenga en cuenta que ambos campos son sensibles a mayúsculas/minúsculas.", null);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
            }//fin else


        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Por favor introduzca un nombre de usuario y una contraseña correctos. Tenga en cuenta que ambos campos son sensibles a mayúsculas/minúsculas.", null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

        return "";

    }//fin login

    /**
     * Finaliza la session
     */
    public void logout() throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().invalidate();
        //request.logout(); comentado
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    } //fin logout

    /**
     * Busca el usuario logeado cuando se usa roles
     */
    public String getLoggedUser() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.getUserPrincipal() != null) {
            return request.getUserPrincipal().getName();
        }
        return "";
    }//fin getLoggedUser()
}
