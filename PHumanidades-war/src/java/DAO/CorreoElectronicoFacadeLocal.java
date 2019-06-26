/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Persona.CorreoElectronico;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface CorreoElectronicoFacadeLocal {

    void create(CorreoElectronico correoElectronico);

    void edit(CorreoElectronico correoElectronico);

    void remove(CorreoElectronico correoElectronico);

    CorreoElectronico find(Object id);

    List<CorreoElectronico> findAll();

    List<CorreoElectronico> findRange(int[] range);

    int count();

}
