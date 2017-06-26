/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.Contador025;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vouilloz
 */
@Stateless
public class Contador025Facade extends AbstractFacade<Contador025> implements Contador025FacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Contador025Facade() {
        super(Contador025.class);
    }

    @Override
    public int findUltimoNumero() {
        Query q = em.createNamedQuery("Contador025.findUltimoNumero");
        q.setMaxResults(1);
        try {
            return (int) q.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

}
