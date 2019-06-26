/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.CorreoElectronicoFacadeLocal;
import Entidades.Persona.CorreoElectronico;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class CorreoElectronicoRN implements CorreoElectronicoRNLocal {

    @EJB
    private CorreoElectronicoFacadeLocal correoElectronicoFacadeLocal;

    @Override
    public void create(CorreoElectronico correoElectronico) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void edit(CorreoElectronico correoElectronico) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(CorreoElectronico correoElectronico) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CorreoElectronico> findAll() throws Exception {
        return correoElectronicoFacadeLocal.findAll();
    }

    @Override
    public CorreoElectronico findById(Long id) throws Exception {
        return correoElectronicoFacadeLocal.find(id);
    }

}
