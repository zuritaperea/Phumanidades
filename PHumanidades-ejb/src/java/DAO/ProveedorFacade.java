/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Persona.Proveedor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vouilloz
 */
@Stateless
public class ProveedorFacade extends AbstractFacade<Proveedor> implements ProveedorFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProveedorFacade() {
        super(Proveedor.class);
    }

    @Override
    public List<Proveedor> findByCuit(String cuit) {
        Query q = em.createNamedQuery("Proveedor.findByDocenteDni");
        q.setParameter("cuit", cuit);
        return q.getResultList();
    }
    
    @Override
    public Proveedor findByRazonSocial(String razonSocial) {
        Query q = em.createNamedQuery("Proveedor.findByRazonSocial");
        q.setParameter("razonSocial", razonSocial);
        q.setMaxResults(1);
        return (Proveedor) q.getSingleResult();
    }

    @Override
    public List<Proveedor> buscarProveedorRazonSocial(String razon) {
        Query q = em.createNamedQuery("Proveedor.buscarProveedorRazonSocial");
        q.setParameter("razon", "%" + razon + "%");
        return q.getResultList();

    }

}
