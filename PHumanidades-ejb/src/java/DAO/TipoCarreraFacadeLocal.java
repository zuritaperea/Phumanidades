/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.TipoCarrera;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface TipoCarreraFacadeLocal {

    void create(TipoCarrera tipoCarrera);

    void edit(TipoCarrera tipoCarrera);

    void remove(TipoCarrera tipoCarrera);

    TipoCarrera find(Object id);

    List<TipoCarrera> findAll();

    List<TipoCarrera> findRange(int[] range);

    int count();

}
