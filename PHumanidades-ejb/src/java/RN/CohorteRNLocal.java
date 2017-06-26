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
public interface CohorteRNLocal {

    public void create(Cohorte cohorte) throws Exception;

    public void edit(Cohorte cohorte) throws Exception;

    public List<Cohorte> findCohortes() throws Exception;

    public void remove(Cohorte cohorte);

    public List<Cohorte> findCohorteNombre(String nombre);

    public Cohorte findByiD(Long id) throws Exception;

    public List<InscripcionAlumnos> findAlumnoCohorte(Cohorte cohorte) throws Exception;

}
