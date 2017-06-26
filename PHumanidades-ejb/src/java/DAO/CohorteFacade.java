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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vouilloz
 */
@Stateless
public class CohorteFacade extends AbstractFacade<Cohorte> implements CohorteFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CohorteFacade() {
        super(Cohorte.class);
    }

    @Override
    public List<Cohorte> findCohorteNombre(String nombre) {
        Query q = em.createNamedQuery("Cohorte.findCohorteNombre");
        q.setParameter("cadena", "%" + nombre.toUpperCase() + "%");
        return q.getResultList();
    }

    @Override
    public List<InscripcionAlumnos> findAlumnoCohorte(Cohorte cohorte) {

        try {
            Query q = null;

            q = em.createNamedQuery("Cohorte.findAlumnoCohorte2");

            q.setParameter("cohorte", cohorte);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            return null;
        }
    }

}
