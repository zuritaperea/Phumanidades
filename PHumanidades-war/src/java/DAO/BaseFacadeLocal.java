/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Base.Base;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franco
 */
@Local
public interface BaseFacadeLocal {

    void create(Base base);

    void edit(Base base);

    void remove(Base base);

    Base find(Object id);

    List<Base> findAll();
    
    List<Base> findAllDesc();

    List<Base> findRange(int[] range);

    int count();
    
}
