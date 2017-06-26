/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.Carrera;
import Entidades.Egresos.PagosDocente;
import java.util.Date;
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
public class PagosDocenteFacade extends AbstractFacade<PagosDocente> implements PagosDocenteFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagosDocenteFacade() {
        super(PagosDocente.class);
    }

    @Override
    public List<PagosDocente> findByFechaCarrera(Date ini, Date fin, Carrera carrera) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findByFechaCarrera");
        q.setParameter("fechaIni", ini);
        q.setParameter("fechaFin", fin);
        q.setParameter("carrera", carrera);
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findAllDesc() {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findAllDesc");
        return q.getResultList();
    }

    @Override
    public void updateBorrado(Boolean bEstado, Long id) throws Exception {
        Query q = em.createNamedQuery("PagosDocente.UpdateBorrado");
        q.setParameter("estado", bEstado);
        q.setParameter("id", id);
        q.executeUpdate();
    }

    @Override
    public List<PagosDocente> findPagosByDni(String dni) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosByDni");
        q.setParameter("dni", dni);
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findPagosGeneralXFecha(Date fechaIni, Date fechaFin) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosGeneralXFecha");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findByFechaCarreraDocente(Date fechaIni, Date fechaFin, Carrera carrera) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findByFechaCarreraDocente");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("carrera", carrera);

        return q.getResultList();
    }

    @Override
    public int findUltimoNumero() {
        Query q = em.createNamedQuery("PagosDocente.findUltimoNumero");
        try {
            return (Integer) q.getSingleResult();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public PagosDocente buscarPagosDocenteId(Long id) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosDocentesId");
        q.setParameter("id", id);
        return (PagosDocente) q.getSingleResult();
    }

    @Override
    public List<PagosDocente> findPagosXFechaDocente(Date ini, Date fin) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosXFechaDocente");
        q.setParameter("fechaIni", ini);
        q.setParameter("fechaFin", fin);
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findPagosXFechaProveedor(Date ini, Date fin) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosXFechaProveedor");
        q.setParameter("fechaIni", ini);
        q.setParameter("fechaFin", fin);
        return q.getResultList();
    }

}
