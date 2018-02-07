/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Ingresos.TipoIngreso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

/**
 *
 * @author franco
 */
@Stateless
public class TipoIngresoFacade extends AbstractFacade<TipoIngreso> implements TipoIngresoFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoIngresoFacade() {
        super(TipoIngreso.class);
    }

    @Override
    public List<TipoIngreso> findNoBorrados() {
       try {
            Query q = em.createNamedQuery("TipoIngreso.findNoBorrados");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
