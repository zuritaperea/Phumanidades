/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.DomicilioFacadeLocal;
import Entidades.Persona.Domicilio;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hugo
 */
@Stateless
public class DomicilioRN implements DomicilioRNLocal {

    @EJB
    private DomicilioFacadeLocal domicilioFacadeLocal;

    @Override
    public void create(Domicilio domicilio) throws Exception {
        //Agregar validaciones
        domicilioFacadeLocal.create(domicilio);
    }

    @Override
    public void edit(Domicilio domicilio) throws Exception {
        domicilioFacadeLocal.edit(domicilio);
    }

    @Override
    public void remove(Domicilio domicilio) throws Exception {
        domicilioFacadeLocal.remove(domicilio);
    }

    @Override
    public List<Domicilio> findAll() throws Exception {
        return domicilioFacadeLocal.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Domicilio buscarDomicilio(Domicilio domicilio) {
        return domicilioFacadeLocal.find(domicilio.getId());
    }

}
