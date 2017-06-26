/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Persona.Persona;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface PersonaRNLocal {

    public void create(Persona persona) throws Exception;

    public void edit(Persona persona) throws Exception;

    public void remove(Persona persona) throws Exception;

    public List<Persona> findAll() throws Exception;

    public Persona buscarPersona(Persona persona);

    public List<Persona> buscarPersonaNombre(String cadena) throws Exception;

    List<Persona> findLikeNombreApellido(String cadena) throws Exception;

}
