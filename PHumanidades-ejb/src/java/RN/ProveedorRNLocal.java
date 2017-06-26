/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Persona.Proveedor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface ProveedorRNLocal {

    public void create(Proveedor proveedor) throws Exception;

    public void edit(Proveedor proveedor) throws Exception;

    public void remove(Proveedor proveedor) throws Exception;

    public List<Proveedor> findAll() throws Exception;

    public Proveedor buscarProveedor(Proveedor proveedor);

    public Proveedor findById(Long id);

    //public List<Proveedor> buscarProveedor(String razonSocial) throws Exception;
    List<Proveedor> findLikeNombreApellido(String razonSocial) throws Exception;

    public List<Proveedor> findByCuit(String dni);

    public List<Proveedor> buscarProveedorRazonSocial(String cadena) throws Exception;

    public Proveedor findByRazonSocial(String razonSocial);

}
