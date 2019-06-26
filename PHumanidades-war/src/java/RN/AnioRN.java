/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.AnioFacadeLocal;
import Entidades.Carreras.Anio;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class AnioRN implements AnioRNLocal {

    @EJB
    private AnioFacadeLocal anioFacadeLocal;

    @Override
    public void create(Anio anio) throws Exception {
        anioFacadeLocal.create(anio);
    }

    @Override
    public void remove(Anio anio) {
        anioFacadeLocal.remove(anio);
    }

    @Override
    public void edit(Anio anio) {
        anioFacadeLocal.edit(anio);
    }

    @Override
    public List<Anio> findAll() {
        return anioFacadeLocal.findAll();
    }

    @Override
    public Anio findByiD(Long id) throws Exception {
        return anioFacadeLocal.find(id);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
