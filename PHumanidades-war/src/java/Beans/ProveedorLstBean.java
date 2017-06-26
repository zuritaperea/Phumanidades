/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Persona.Proveedor;
import RN.ProveedorRNLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@SessionScoped
public class ProveedorLstBean  implements Serializable{

    @EJB
    private ProveedorRNLocal proveedorRNLocal;//hacemos la referencia para poder utilizar el metodo findall
    private List<Proveedor> lstProveedor; //Cargamos la lista de Proveedores retornada po el metodo findAll del usuarioRNLocal
    private List<SelectItem> lstSIProveedor;//Aca se guarda el item seleccionado de la lista

    private DataTable tablaProveedor;

    public Proveedor proveedorSelect;

    /**
     * Creates a new instance of UsuarioLstBean
     */
    public ProveedorLstBean() {
    }

    @PostConstruct
    private void init() {
        this.cargarProveedor();
    }

    public ProveedorRNLocal getProveedorRNLocal() {
        return proveedorRNLocal;
    }

    public void setProveedorRNLocal(ProveedorRNLocal proveedorRNLocal) {
        this.proveedorRNLocal = proveedorRNLocal;
    }

    public List<Proveedor> getLstProveedor() {
        return lstProveedor;
    }

    public void setLstProveedor(List<Proveedor> lstProveedor) {
        this.lstProveedor = lstProveedor;
    }

    public List<SelectItem> getLstSIProveedor() {
        return lstSIProveedor;
    }

    public void setLstSIProveedor(List<SelectItem> lstSIProveedor) {
        this.lstSIProveedor = lstSIProveedor;
    }

    public DataTable getTablaProveedor() {
        return tablaProveedor;
    }

    public void setTablaProveedor(DataTable tablaProveedor) {
        this.tablaProveedor = tablaProveedor;
    }

    public Proveedor getProveedorSelect() {
        return proveedorSelect;
    }

    public void setProveedorSelect(Proveedor proveedorSelect) {
        this.proveedorSelect = proveedorSelect;
    }

    public void cargarProveedor() {
        try {
            this.setLstProveedor(this.proveedorRNLocal.findAll());
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//fin cargarUsuario

    public Proveedor getProveedorSeleccionado() {
        return (Proveedor) this.tablaProveedor.getRowData();
    }

    public void findDocenteCuit(String cuit) {
        this.setLstProveedor(this.proveedorRNLocal.findByCuit(cuit));
    }

    public void findDocenteRazonSocial(String razon) throws Exception {

        this.setLstProveedor(this.proveedorRNLocal.buscarProveedorRazonSocial(razon));
    }

    public void findAllProveedores() throws Exception {
        this.setLstProveedor(proveedorRNLocal.findAll());
    }
}
