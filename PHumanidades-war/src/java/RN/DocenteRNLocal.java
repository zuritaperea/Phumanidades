/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Persona.Docente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface DocenteRNLocal {

    public void create(Docente docente) throws Exception;

    public void edit(Docente docente) throws Exception;

    public void remove(Docente docente) throws Exception;

    public List<Docente> findAll() throws Exception;

    public Docente buscarDocente(Docente docente);

    public List<Docente> buscarDocenteNombre(String cadena) throws Exception;

    public List<Docente> findLikeNombreApellido(String cadena) throws Exception;

    public List<Docente> findByDocenteDni(String dni);

}
