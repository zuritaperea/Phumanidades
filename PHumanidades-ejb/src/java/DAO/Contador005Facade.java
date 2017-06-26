/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.Contador005;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vouilloz
 */
@Stateless
public class Contador005Facade extends AbstractFacade<Contador005> implements Contador005FacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Contador005Facade() {
        super(Contador005.class);
    }

    @Override
    public int findUltimoNumero() {
        Query q = em.createNamedQuery("Contador005.findUltimoNumero");
        q.setMaxResults(1);
        try {
            return (int) q.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

}
