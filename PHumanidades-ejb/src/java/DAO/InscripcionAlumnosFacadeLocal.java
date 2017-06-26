/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
public interface InscripcionAlumnosFacadeLocal {

    void create(InscripcionAlumnos inscripcionAlumnos);

    void edit(InscripcionAlumnos inscripcionAlumnos);

    void remove(InscripcionAlumnos inscripcionAlumnos);

    InscripcionAlumnos find(Object id);

    List<InscripcionAlumnos> findAll();

    List<InscripcionAlumnos> findRange(int[] range);

    int count();

    List<Cohorte> alumnoFindCohorte(Alumno alumno);
    
    List<InscripcionAlumnos> findAlumnoCohorte (String dni, Long id);

}
