/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Persona.Alumno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface AlumnoRNLocal {

    public void create(Alumno alumno) throws Exception;

    public void edit(Alumno alumno) throws Exception;

    public void remove(Alumno alumno) throws Exception;

    public List<Alumno> findAll() throws Exception;

    public Alumno buscarAlumno(Alumno alumno);

    public List<Alumno> buscarAlumnoNombre(String cadena) throws Exception;

    List<Alumno> findLikeNombreApellido(String cadena) throws Exception;

    public Alumno findByAlumnoDni(String dni) throws Exception;

}
