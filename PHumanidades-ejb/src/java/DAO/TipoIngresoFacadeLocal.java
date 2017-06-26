/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Ingresos.TipoIngreso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franco
 */
@Local
public interface TipoIngresoFacadeLocal {

    void create(TipoIngreso tipoIngreso);

    void edit(TipoIngreso tipoIngreso);

    void remove(TipoIngreso tipoIngreso);

    TipoIngreso find(Object id);

    List<TipoIngreso> findAll();

    List<TipoIngreso> findRange(int[] range);

    int count();
    
}
