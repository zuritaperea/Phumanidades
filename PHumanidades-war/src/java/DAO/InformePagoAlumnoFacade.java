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

    public List<InformePagoAlumno> findCuotasAlumnoCohorte(Alumno alumno, Cohorte cohorte) {
        try {
            Query q = null;
            System.out.println("Entro al informepagoalumnoFACXADE");
            System.out.println(alumno);
            System.out.println(cohorte);
            q = em.createNamedQuery("InformePagoAlumno.findCuotasAlumnoCohorte");
            q.setParameter("alumno", alumno);
            q.setParameter("cohorte", cohorte);
            System.out.println("Entro consultaaaa"+q.getResultList());
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("exception " + e);
            return null;
        }
    }

}
