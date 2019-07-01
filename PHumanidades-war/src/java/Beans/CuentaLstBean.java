/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cuenta;
import RN.CuentaRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
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
public class CuentaLstBean implements Serializable {

    @EJB
    private CuentaRNLocal cuentaRNLocal;//hacemos la referencia para poder utilizar el metodo findall

    private List<Cuenta> lstCuenta; //Cargamos la lista de Usuarios retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSICuenta;//Aca se guarda el item seleccionado de la lista
    private Cuenta cuenta;
    private Integer cuenta_id;
    public List<Cuenta> getLstCuenta() {
        return lstCuenta;
    }

    public void setLstCuenta(List<Cuenta> lstCuenta) {
        this.lstCuenta = lstCuenta;
    }

    public CuentaRNLocal getCuentaRNLocal() {
        return cuentaRNLocal;
    }

    public void setCuentaRNLocal(CuentaRNLocal cuentaRNLocal) {
        this.cuentaRNLocal = cuentaRNLocal;
    }

    public Integer getCuenta_id() {
        return cuenta_id;
    }

    public void setCuenta_id(Integer cuenta_id) {
        this.cuenta_id = cuenta_id;
    }

    public List<SelectItem> getLstSICuenta() {
        return lstSICuenta;
    }

    public void setLstSICuenta(List<SelectItem> lstSICuenta) {
        this.lstSICuenta = lstSICuenta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * Creates a new instance of UsuarioLstBean
     */
    public CuentaLstBean() {
    }

    @PostConstruct
    private void init() {
        this.cargarCuentas();
        cuenta = new Cuenta();
    }

    public void cargarCuentas() {
        try {
            this.setLstCuenta(this.cuentaRNLocal.findAll());
            cargar_SI_Cuenta();
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin cargarCarreras
     public void cargar_SI_Cuenta() {
        this.setLstSICuenta(new ArrayList<SelectItem>());

        for (Cuenta a : this.getLstCuenta()) {
            SelectItem si = new SelectItem(a, a.getDescripcion());
            this.getLstSICuenta().add(si);
        }
    }

    /*  public void findCarreraNombre(String nombre){
    
     this.setLstCarrera(carrerasRNLocal.findCarreraNombre(nombre));
     }
     */
}
