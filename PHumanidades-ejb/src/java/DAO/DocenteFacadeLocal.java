/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Persona.Docente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface DocenteFacadeLocal {

    void create(Docente docente);

    void edit(Docente docente);

    void remove(Docente docente);

    Docente find(Object id);

    List<Docente> findAll();

    List<Docente> findRange(int[] range);

    int count();

    public boolean FindPersonaByDNI(String dni, Long id);

    public List<Docente> findByDocenteDni(String dni);

    public List<Docente> findLikeNombreApellido(String cadena);

    public Docente findDocenteDni(String dni) throws Exception;

}
