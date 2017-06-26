/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Materia;
import DAO.MateriaFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class MateriaRN implements MateriaRNLocal {

    @EJB
    private MateriaFacadeLocal materiaFacadeLocal;

    @Override
    public void create(Materia m) throws Exception {
        materiaFacadeLocal.create(m);
    }

    @Override
    public void edit(Materia m) throws Exception {
        materiaFacadeLocal.edit(m);
    }

    @Override
    public void remove(Materia m) {
        materiaFacadeLocal.remove(m);

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Materia> findAll() {
        return materiaFacadeLocal.findAll();
    }
}
