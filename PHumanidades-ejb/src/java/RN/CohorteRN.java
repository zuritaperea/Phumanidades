/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.CohorteFacadeLocal;
import Entidades.Carreras.Cohorte;
import Entidades.Carreras.InscripcionAlumnos;
import Entidades.Persona.Alumno;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class CohorteRN implements CohorteRNLocal {

    @EJB
    private CohorteFacadeLocal cohorteFacadeLocal;

    @Override
    public void create(Cohorte cohorte) throws Exception {
        if (validar(cohorte)) {
            cohorteFacadeLocal.create(cohorte);
        } else {
            throw new Exception("Debe ingresar una carrera");
        }
    }

    @Override
    public void edit(Cohorte cohorte) throws Exception {
        if (validar(cohorte)) {
            cohorteFacadeLocal.edit(cohorte);
        } else {
            throw new Exception("Debe ingresar una descripción");
        }
    }

    @Override
    public List<Cohorte> findCohortes() throws Exception {
        return cohorteFacadeLocal.findAll();
    }

    @Override
    public void remove(Cohorte cohorte) {
        cohorteFacadeLocal.remove(cohorte);
    }

    @Override
    public List<Cohorte> findCohorteNombre(String nombre) {
        return cohorteFacadeLocal.findCohorteNombre(nombre);
    }

    @Override
    public Cohorte findByiD(Long id) throws Exception {
        return cohorteFacadeLocal.find(id);
    }

    @Override
    public List<InscripcionAlumnos> findAlumnoCohorte(Cohorte cohorte) throws Exception {
        return cohorteFacadeLocal.findAlumnoCohorte(cohorte);
    }

    private boolean validar(Cohorte cohorte) {
        return cohorte.getCarrera() != null;
    }
}
