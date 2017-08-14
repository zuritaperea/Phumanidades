/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Egresos.TipoEgreso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ruben
 */
@Local
public interface TipoEgresoFacadeLocal {
    void create(TipoEgreso tipoEgreso);

    void edit(TipoEgreso tipoEgreso);

    void remove(TipoEgreso tipoEgreso);

    TipoEgreso find(Object id);

    List<TipoEgreso> findAll();

    List<TipoEgreso> findRange(int[] range);

    int count();
}
