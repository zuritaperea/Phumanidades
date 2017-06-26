/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Persona.CorreoElectronico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vouilloz
 */
@Stateless
public class CorreoElectronicoFacade extends AbstractFacade<CorreoElectronico> implements CorreoElectronicoFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CorreoElectronicoFacade() {
        super(CorreoElectronico.class);
    }

}
