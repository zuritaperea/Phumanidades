/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.LocalidadFacadeLocal;
import Entidades.Localidades.Departamento;
import Entidades.Localidades.Localidad;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hugo
 */
@Stateless
public class LocalidadRN implements LocalidadRNLocal {

    @EJB
    private LocalidadFacadeLocal localidadFacadeLocal;

    @Override
    public void create(Localidad localidad) throws Exception {
        //Agregar validaciones
        localidadFacadeLocal.create(localidad);
    }

    @Override
    public void edit(Localidad localidad) throws Exception {
        localidadFacadeLocal.edit(localidad);
    }

    @Override
    public void remove(Localidad localidad) throws Exception {
        localidadFacadeLocal.remove(localidad);
    }

    @Override
    public List<Localidad> findAll() throws Exception {
        return localidadFacadeLocal.findAll();
    }

    @Override
    public List<Localidad> buscarLocalidadesDepto(Departamento depto) {
        return localidadFacadeLocal.buscarLocalidadesDepto(depto);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Localidad buscarLocalidad(Localidad localidad) {
        return localidadFacadeLocal.find(localidad.getId());
    }

}
