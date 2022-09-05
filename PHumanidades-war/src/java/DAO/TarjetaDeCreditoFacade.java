/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Ingresos.TarjetaDeCredito;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hugo
 */
@Stateless
public class TarjetaDeCreditoFacade extends AbstractFacade<TarjetaDeCredito> implements TarjetaDeCreditoFacadeLocal {
    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarjetaDeCreditoFacade() {
        super(TarjetaDeCredito.class);
    }
    
}
