/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Localidades.Pais;
import Entidades.Localidades.Provincia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface ProvinciaFacadeLocal {

    void create(Provincia provincia);

    void edit(Provincia provincia);

    void remove(Provincia provincia);

    Provincia find(Object id);

    List<Provincia> findAll();

    List<Provincia> findRange(int[] range);

    int count();

    public List<Provincia> buscarProvinciasPais(Pais pais);

}
