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
public class InscripcionAlumnosFacade extends AbstractFacade<InscripcionAlumnos> implements InscripcionAlumnosFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InscripcionAlumnosFacade() {
        super(InscripcionAlumnos.class);
    }

    @Override
    public List<Cohorte> alumnoFindCohorte(Alumno alumno) {
        Query q = em.createNamedQuery("InscripcionAlumnos.alumnoFindCohorte");
        q.setParameter("alumno", alumno);
        return q.getResultList();
    }

    @Override
    public List<InscripcionAlumnos> findAlumnoCohorte(String dni, Long id) {
        Query q = em.createNamedQuery("InscripcionAlumnos.findAlumnoCohorte");
        q.setParameter("dni", dni);
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public List<InscripcionAlumnos> inscripcionFindDni(String dni) {
        Query q = em.createNamedQuery("InscripcionAlumnos.inscripcionFindDni");
        q.setParameter("dni", dni);
        return q.getResultList();
    }

    @Override
    public InscripcionAlumnos find(Object id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
