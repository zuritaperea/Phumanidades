/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.TelefonoFacadeLocal;
import Entidades.Persona.Telefono;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class TelefonoRN implements TelefonoRNLocal {

    @EJB
    private TelefonoFacadeLocal telefonoFacadeLocal;

    @Override
    public void create(Telefono telefono) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void edit(Telefono telefono) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Telefono telefono) throws Exception {
        telefonoFacadeLocal.remove(telefono);
    }

    @Override
    public List<Telefono> findAll() throws Exception {
        return telefonoFacadeLocal.findAll();
    }

    @Override
    public Telefono findById(Long id) throws Exception {
        return telefonoFacadeLocal.find(id);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
