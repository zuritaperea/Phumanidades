package Beans;

import Entidades.Carreras.Contador005;
import Entidades.Carreras.Contador025;
import Entidades.Carreras.Cuenta;
import RN.Contador005RNLocal;
import RN.Contador025RNLocal;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 * Clase usada para manejar la localidad
 *
 * @author vouilloz
 */
@ManagedBean(name = "numeroCuentaBean")
@RequestScoped
public class NumeroCuentaBean implements Serializable {

    @EJB
    private Contador005RNLocal contador005RNLocal;

    @EJB
    private Contador025RNLocal contador025RNLocal;

    @ManagedProperty(value = "#{cuentaLstBean}")
    private CuentaLstBean cuentaLstBean;

    private int numero;

    @PostConstruct
    private void init() {
        try {
            //cargarNumero();
        } catch (Exception ex) {
            Logger.getLogger(NumeroCuentaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public NumeroCuentaBean() {

    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public CuentaLstBean getCuentaLstBean() {
        return cuentaLstBean;
    }

    public void setCuentaLstBean(CuentaLstBean cuentaLstBean) {
        this.cuentaLstBean = cuentaLstBean;
    }

    /**
     * Busca las localidad por id
     *
     * @param id código de la localidad
     * @return Localidad localidad encontrada
     */
//    public Cuenta findById(Long id) {
//        try {
//            return cuentaRNLocal.findByiD(id);
//        } catch (Exception ex) {
//            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                    "Error al tratar de recuperar las cuentas de la Base de Datos" + ex,
//                    null);
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage(null, fm);
//        }
//        return null;
//    }//fin dinfByid
    public void guardarCuenta() {
//
//        String sMensaje = "";
//        FacesMessage fm;
//        FacesMessage.Severity severity = null;
//        try {
//
//            cuentaRNLocal.create(cuenta);
//            sMensaje = "Cuenta Guardada";
//            severity = FacesMessage.SEVERITY_INFO;
//            //agregar a la lista
//            // this.getUsuarioLstBean().getLstUsuario().add(this.getUsuario());
//            this.getCuentaLstBean().getLstCuenta().add(cuenta);
//            //limíar campos
//            this.limpiar();
//
//        } catch (Exception ex) {
//
//            severity = FacesMessage.SEVERITY_ERROR;
//            sMensaje = "Error: " + ex.getMessage();
//
//        } finally {
//            fm = new FacesMessage(severity, sMensaje, null);
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage(null, fm);
//        }

    }

    private void limpiar() {

    }

    public void cargarNumero() throws Exception {
        try {
            if (this.cuentaLstBean.getCuenta().getCodigo().equals("005")) {
                try {
                    numero = this.contador005RNLocal.findUltimoNumero() + 1;
                } catch (Exception exception) {
                    numero = 1;
                }
            }
        } catch (Exception e) {
        }
        try {
            if (this.cuentaLstBean.getCuenta().getCodigo().equals("025")) {
                try {
                    numero = this.contador025RNLocal.findUltimoNumero() + 1;
                } catch (Exception exception) {
                    numero = 1;
                }
            }
        } catch (Exception e) {
        }

    }
}
