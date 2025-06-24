/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.ProveedorFacadeLocal;
import Entidades.Egresos.PagosDocente;
import Entidades.Persona.Proveedor;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class ProveedorRN implements ProveedorRNLocal {

    @EJB
    private ProveedorFacadeLocal proveedorFacadeLocal;

    @Override
    public void create(Proveedor proveedor) throws Exception {
        proveedorFacadeLocal.create(proveedor);
    }

    @Override
    public void edit(Proveedor proveedor) throws Exception {
        proveedorFacadeLocal.edit(proveedor);
    }

    @Override
    public void remove(Proveedor proveedor) throws Exception {
        proveedorFacadeLocal.remove(proveedor);
    }

    @Override
    public List<Proveedor> findAll() throws Exception {
        return proveedorFacadeLocal.findAll();
    }

    @Override
    public Proveedor buscarProveedor(Proveedor proveedor) {
        return proveedorFacadeLocal.find(proveedor.getId());
    }
    @Override
    public Proveedor findByRazonSocial(String razonSocial) {
        return proveedorFacadeLocal.findByRazonSocial(razonSocial);
    }
    @Override
    public List<Proveedor> findLikeNombreApellido(String razonSocial) throws Exception {
        return proveedorFacadeLocal.buscarProveedorRazonSocial(razonSocial);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Proveedor> findByCuit(String cuit) {
        return proveedorFacadeLocal.findByCuit(cuit);
    }

    @Override
    public List<Proveedor> buscarProveedorRazonSocial(String cadena) throws Exception {
        return proveedorFacadeLocal.buscarProveedorRazonSocial(cadena);
    }

    @Override
    public Proveedor findById(Long id) {
        return proveedorFacadeLocal.find(id);
    }

    @Override
    public List<PagosDocente> buscarEgresosProveedor(Proveedor proveedor) throws Exception {
        return proveedorFacadeLocal.buscarEgresosProveedor(proveedor);
    }

    @Override
    public Proveedor findByCbuAlias(String cbuAlias) {
        return proveedorFacadeLocal.findByCbuAlias(cbuAlias);
    }
}
