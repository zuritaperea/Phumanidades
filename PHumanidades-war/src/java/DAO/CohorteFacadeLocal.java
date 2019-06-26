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
public interface CohorteFacadeLocal {

    void create(Cohorte cohorte);

    void edit(Cohorte cohorte);

    void remove(Cohorte cohorte);

    Cohorte find(Object id);

    List<Cohorte> findAll();

    List<Cohorte> findRange(int[] range);

    int count();

    public List<Cohorte> findCohorteNombre(String cadena);

    public List<InscripcionAlumnos> findAlumnoCohorte(Cohorte cohorte);

}
