/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Persona.Persona;
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
public class PersonaFacade extends AbstractFacade<Persona> implements PersonaFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }

    @Override
    public List<Persona> buscarPersonaNombre(String cadena) {
        cadena = cadena.trim().toLowerCase();
        Query q = em.createQuery("SELECT p FROM Persona p WHERE LOWER(p.apellido) LIKE '%" + cadena + "%'");
        return q.getResultList();
    }

    @Override
    public Boolean FindPersonaByDNI(String dni, Long id) {
        try {
            Query q = null;
            q = em.createNamedQuery("Persona.FindPersonaByDNI");
            q.setParameter("dni", dni);
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
