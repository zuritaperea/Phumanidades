/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Persona.Alumno;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vouilloz
 */
@Stateless
public class AlumnoFacade extends AbstractFacade<Alumno> implements AlumnoFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlumnoFacade() {
        super(Alumno.class);
    }

    @Override
    public List<Alumno> findLikeNombreApellido(String cadena) {
        Query q = em.createNamedQuery("Alumno.findLikeNombreApellido");
        q.setParameter("cadena", "%" + cadena.toUpperCase() + "%");
        return q.getResultList();
    }

    @Override
    public Alumno findAlumnoDni(String dni) throws Exception {
        Alumno al = null;
        try {
            Query q = em.createNamedQuery("Alumno.findByAlumnoDni");
            q.setParameter("dni", dni);
            al = (Alumno) q.getSingleResult();
        } catch (NoResultException e) {
            //throw new Exception("No se encontraron resultados");

        }
        return al;

    }

}
