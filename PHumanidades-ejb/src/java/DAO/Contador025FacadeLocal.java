/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.Contador025;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface Contador025FacadeLocal {

    void create(Contador025 contador025);

    void edit(Contador025 contador025);

    void remove(Contador025 contador025);

    Contador025 find(Object id);

    List<Contador025> findAll();

    List<Contador025> findRange(int[] range);

    int count();

    int findUltimoNumero();

}
