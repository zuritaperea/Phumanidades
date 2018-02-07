/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Egresos.TipoEgreso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ruben
 */
@Stateless
public class TipoEgresoFacade extends AbstractFacade<TipoEgreso> implements TipoEgresoFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    public TipoEgresoFacade() {
        super(TipoEgreso.class);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<TipoEgreso> findNoBorrados() {
        try {
            Query q = em.createNamedQuery("TipoEgreso.findNoBorrados");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
