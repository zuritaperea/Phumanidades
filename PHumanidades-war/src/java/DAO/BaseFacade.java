/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Base.Base;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author franco
 */
@Stateless
public class BaseFacade extends AbstractFacade<Base> implements BaseFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BaseFacade() {
        super(Base.class);
    }

    @Override
    public List<Base> findAllDesc() {
        try {
            Query q = em.createNamedQuery("Base.findAllDesc");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
