/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.PaisFacadeLocal;
import Entidades.Localidades.Pais;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hugo
 */
@Stateless
public class PaisRN implements PaisRNLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private PaisFacadeLocal paisFacadeLocal;

    @Override
    public void create(Pais pais) throws Exception {

        paisFacadeLocal.create(pais);
    }

    @Override
    public void edit(Pais pais) throws Exception {
        paisFacadeLocal.edit(pais);
    }

    @Override
    public void remove(Pais pais) throws Exception {
        paisFacadeLocal.remove(pais);
    }

    @Override
    public List<Pais> findAll() throws Exception {
        return paisFacadeLocal.findAll();
    }

}
