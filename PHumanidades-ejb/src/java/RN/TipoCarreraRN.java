/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.TipoCarrera;
import DAO.TipoCarreraFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class TipoCarreraRN implements TipoCarreraRNLocal {

    @EJB
    private TipoCarreraFacadeLocal tipoCarreraFacadeLocal;

    @Override
    public void create(TipoCarrera tipoCarrera) throws Exception {
        tipoCarreraFacadeLocal.create(tipoCarrera);
    }

    @Override
    public void remove(TipoCarrera tipoCarrera) {
        tipoCarreraFacadeLocal.remove(tipoCarrera);
    }

    @Override
    public void edit(TipoCarrera tipoCarrera) {
        tipoCarreraFacadeLocal.edit(tipoCarrera);
    }

    @Override
    public List<TipoCarrera> findAll() {
        return tipoCarreraFacadeLocal.findAll();
    }

    @Override
    public TipoCarrera findByiD(Long id) throws Exception {
        return tipoCarreraFacadeLocal.find(id);
    }

}
