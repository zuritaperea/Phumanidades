/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.Cohorte;
import Entidades.Ingresos.InformePagoAlumno;
import Entidades.Persona.Alumno;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hugo
 */
@Stateless
public class InformePagoAlumnoFacade extends AbstractFacade<InformePagoAlumno> {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InformePagoAlumnoFacade() {
        super(InformePagoAlumno.class);
    }

    public List<InformePagoAlumno> findPagosAlumnoCohorte(Alumno alumno, Cohorte cohorte) {
        try {
            Query q = null;
            q = em.createNamedQuery("InformePagoAlumno.findPagosAlumnoCohorte");
            q.setParameter("alumno", alumno);
            q.setParameter("cohorte", cohorte.getId());
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("exception " + e);
            return null;
        }
    }

    public List<InformePagoAlumno> findPagosOrdenadosPorFecha() {
        try {
            Query q = null;
            q = em.createNamedQuery("InformePagoAlumno.findPagosOrdenadosPorFecha");
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("exception " + e);
            return null;
        }
    }

    public int findUltimaCuota(Alumno alumno, Cohorte cohorte) {
        Query q = em.createNamedQuery("InformePagoAlumno.findUltimaCuota");
        q.setParameter("alumno", alumno);
        q.setParameter("cohorte", cohorte);
        try {
            return (int) q.getSingleResult();
        } catch (Exception e) {
            return 0;
        }

    }

    // Método para buscar por referencia
    public static InformePagoAlumno findByExternalRef(EntityManager em, String ref) {
        return em.createQuery(
            "SELECT i FROM InformePagoAlumno i WHERE i.externalReference = :ref", 
            InformePagoAlumno.class)
            .setParameter("ref", ref)
            .getSingleResult();
    }

}
