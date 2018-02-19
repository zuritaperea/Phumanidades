/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Persona.Docente;
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
public class DocenteFacade extends AbstractFacade<Docente> implements DocenteFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocenteFacade() {
        super(Docente.class);
    }

    @Override
    public boolean FindPersonaByDNI(String dni, Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Docente> findByDocenteDni(String dni) {
        Query q = em.createNamedQuery("Docente.findByDocenteDni");
        q.setParameter("dni", dni);
        return q.getResultList();
    }

    @Override
    public List<Docente> findLikeNombreApellido(String cadena) {
        Query q = em.createNamedQuery("Docente.findLikeNombreApellido");
        q.setParameter("cadena", "%" + cadena + "%");
        return q.getResultList();
    }

    @Override
    public Docente findDocenteDni(String dni) throws Exception {
        Docente doc = null;
        try {
            Query q = em.createNamedQuery("Docente.findByDocenteDni");
            q.setParameter("dni", dni);
            doc = (Docente) q.getSingleResult();
        } catch (NoResultException e) {
            //throw new Exception("No se encontraron resultados");

        }
        return doc;
    }

}
