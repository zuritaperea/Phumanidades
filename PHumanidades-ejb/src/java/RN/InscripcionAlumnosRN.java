/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Cohorte;
import Entidades.Carreras.InscripcionAlumnos;
import DAO.InscripcionAlumnosFacadeLocal;
import Entidades.Persona.Alumno;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class InscripcionAlumnosRN implements InscripcionAlumnosRNLocal {

    @EJB
    private InscripcionAlumnosFacadeLocal inscripcionAlumnosFacadeLocal;

    @Override
    public void create(InscripcionAlumnos inscripcionAlumnos) throws Exception {
        inscripcionAlumnosFacadeLocal.create(inscripcionAlumnos);
    }

    @Override
    public void edit(InscripcionAlumnos inscripcionAlumnos) throws Exception {
        inscripcionAlumnosFacadeLocal.edit(inscripcionAlumnos);
    }

    @Override
    public void remove(InscripcionAlumnos inscripcionAlumnos) throws Exception {
        inscripcionAlumnosFacadeLocal.remove(inscripcionAlumnos);
    }

    @Override
    public List<InscripcionAlumnos> findAll() throws Exception {
        return inscripcionAlumnosFacadeLocal.findAll();
    }

    @Override
    public InscripcionAlumnos buscarInscripcionAlumnos(InscripcionAlumnos inscripcionAlumnos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Cohorte> alumnoFindCohortes(Alumno alumno) throws Exception {
        return inscripcionAlumnosFacadeLocal.alumnoFindCohorte(alumno);
    }

    @Override
    public List<InscripcionAlumnos> findAlumnoCohorte(String dni, Long id) throws Exception {
        return inscripcionAlumnosFacadeLocal.findAlumnoCohorte(dni, id);
        
    }
}
