/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Egresos.PagosDocente;
import Entidades.Persona.Proveedor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface ProveedorFacadeLocal {

    void create(Proveedor proveedor);

    void edit(Proveedor proveedor);

    void remove(Proveedor proveedor);

    Proveedor find(Object id);

    List<Proveedor> findAll();

    List<Proveedor> findRange(int[] range);

    int count();

    public List<Proveedor> findByCuit(String cuit);

    public List<Proveedor> buscarProveedorRazonSocial(String razon);

    public Proveedor findByRazonSocial(String razonSocial);
    
    public Proveedor findByCbuAlias(String cbuAlias);

    public List<PagosDocente> buscarEgresosProveedor(Proveedor proveedor);

}
