/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Cohorte;
import Entidades.Carreras.InscripcionAlumnos;
import Entidades.Persona.Alumno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface InscripcionAlumnosRNLocal {

    public void create(InscripcionAlumnos inscripcionAlumnos) throws Exception;

    public void edit(InscripcionAlumnos inscripcionAlumnos) throws Exception;

    public void remove(InscripcionAlumnos inscripcionAlumnos) throws Exception;

    public List<InscripcionAlumnos> findAll() throws Exception;

    public InscripcionAlumnos buscarInscripcionAlumnos(InscripcionAlumnos inscripcionAlumnos);

    public List<Cohorte> alumnoFindCohortes(Alumno alumno) throws Exception;
    
    public List<InscripcionAlumnos> findAlumnoCohorte(String dni, Long id) throws Exception;

}
