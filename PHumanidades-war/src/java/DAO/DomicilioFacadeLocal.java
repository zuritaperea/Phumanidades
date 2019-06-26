/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Persona.Domicilio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface DomicilioFacadeLocal {

    void create(Domicilio domicilio);

    void edit(Domicilio domicilio);

    void remove(Domicilio domicilio);

    Domicilio find(Object id);

    List<Domicilio> findAll();

    List<Domicilio> findRange(int[] range);

    int count();

}
