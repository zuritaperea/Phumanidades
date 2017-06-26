package Beans;

import Entidades.Carreras.Cuenta;
import Entidades.Carreras.TipoCarrera;
import RN.CuentaRNLocal;

import RN.TipoCarreraRNLocal;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Clase usada para manejar la localidad
 *
 * @author AFerSor
 */
@ManagedBean(name = "cuentaBean")
@RequestScoped
public class CuentaBean implements Serializable {

    @EJB
    private CuentaRNLocal cuentaRNLocal;

    @ManagedProperty(value = "#{cuentaLstBean}")
    private CuentaLstBean cuentaLstBean;

    private Cuenta cuenta;

    public CuentaBean() {
        cuenta = new Cuenta();
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
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
    public Cuenta findById(Long id) {
        try {
            return cuentaRNLocal.findByiD(id);
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al tratar de recuperar las cuentas de la Base de Datos" + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
        return null;
    }//fin dinfByid

    public void abrirDlgCuenta() {
        this.setCuenta(new Cuenta());

    }

    public void guardarCuenta() {

        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            cuentaRNLocal.create(cuenta);
            sMensaje = "Cuenta Guardada";
            severity = FacesMessage.SEVERITY_INFO;
            //agregar a la lista
            // this.getUsuarioLstBean().getLstUsuario().add(this.getUsuario());
            this.getCuentaLstBean().getLstCuenta().add(cuenta);
            //limíar campos
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

    private void limpiar() {
        this.setCuenta(new Cuenta());
    }
}
