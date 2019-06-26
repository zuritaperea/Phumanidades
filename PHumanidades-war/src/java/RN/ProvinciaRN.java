/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.ProvinciaFacadeLocal;
import Entidades.Localidades.Pais;
import Entidades.Localidades.Provincia;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hugo
 */
@Stateless
public class ProvinciaRN implements ProvinciaRNLocal {

    @EJB
    private ProvinciaFacadeLocal provinciaFacadeLocal;

    @Override
    public void create(Provincia provincia) throws Exception {
        //Agregar validaciones
        provinciaFacadeLocal.create(provincia);
    }

    @Override
    public void edit(Provincia provincia) throws Exception {
        provinciaFacadeLocal.edit(provincia);
    }

    @Override
    public void remove(Provincia provincia) throws Exception {
        provinciaFacadeLocal.remove(provincia);
    }

    @Override
    public List<Provincia> findAll() throws Exception {
        return provinciaFacadeLocal.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Provincia buscarProvincia(Provincia provincia) {
        return provinciaFacadeLocal.find(provincia.getId());
    }

    @Override
    public List<Provincia> buscarProvinciasPais(Pais pais) {
        return provinciaFacadeLocal.buscarProvinciasPais(pais);
    }

}
