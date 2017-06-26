/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.Contador005;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface Contador005FacadeLocal {

    void create(Contador005 contador005);

    void edit(Contador005 contador005);

    void remove(Contador005 contador005);

    Contador005 find(Object id);

    List<Contador005> findAll();

    List<Contador005> findRange(int[] range);

    int count();

    int findUltimoNumero();

}
