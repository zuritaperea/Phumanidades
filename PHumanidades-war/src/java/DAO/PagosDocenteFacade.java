/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.Carrera;
import Entidades.Carreras.Cuenta;
import Entidades.Egresos.PagosDocente;
import Entidades.Egresos.TipoEgreso;
import Entidades.Persona.Docente;
import Entidades.Persona.Proveedor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    public List<PagosDocente> findAllDescAnio(int year) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findAllDescAnio");
        q.setParameter("anio", year);
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

    @Override
    public List<PagosDocente> findPagosXFechaProveedorYCuenta(Date ini, Date fin, Cuenta cuenta) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosXFechaProveedorYCuenta");
        q.setParameter("fechaIni", ini);
        q.setParameter("fechaFin", fin);
        q.setParameter("cuenta", cuenta);
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findPagosByTipoEgreso(TipoEgreso tipo) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosByTipoEgreso");
        q.setParameter("tipoEgreso", tipo);
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findPagosByPredicates(Date FechaInicio, Date FechaFin, Cuenta cuenta, TipoEgreso tipoEgreso, Carrera carrera) {

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery cq = qb.createQuery();
        Root<PagosDocente> pagos = cq.from(PagosDocente.class);
        List<Predicate> predicates = new ArrayList<>();
        if (FechaInicio != null) {
            predicates.add(
                    qb.greaterThanOrEqualTo(pagos.<Date>get("fechaRegistro"), FechaInicio));
        }
        if (FechaFin != null) {
            predicates.add(
                    qb.lessThanOrEqualTo(pagos.<Date>get("fechaRegistro"), FechaFin));
        }
        if (cuenta != null && cuenta.getId() != null) {
            predicates.add(
                    qb.equal(pagos.get("cuenta"), cuenta));
        }
        if (tipoEgreso != null && tipoEgreso.getId() != null) {
            predicates.add(
                    qb.equal(pagos.get("tipoEgreso"), tipoEgreso));
        }
        if (carrera != null && carrera.getId() != null) {
            predicates.add(
                    qb.equal(pagos.get("carrera"), carrera));
        }
        predicates.add(
                qb.equal(pagos.get("borrado"), false));

        predicates.add(
                qb.equal(pagos.get("anulado"), false));

        Predicate[] predicatesarr = predicates.toArray(new Predicate[predicates.size()]);
        cq.select(pagos)
                .where(predicatesarr);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<PagosDocente> findPagosByNumeroOrdenPago(int numeroOrdenPago) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosByNumeroOrdenPago");
        q.setParameter("numeroOrdenPago", numeroOrdenPago);
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findPagosByNumeroOrdenPagoAnio(int numeroOrdenPago, int year) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosByNumeroOrdenPagoAnio");
        q.setParameter("numeroOrdenPago", numeroOrdenPago);
        q.setParameter("anio", year);

        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findPagosByNumeroOrdenPagoAnioAnulado(int numeroOrdenPago, int year) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosByNumeroOrdenPagoAnioAnulado");
        q.setParameter("numeroOrdenPago", numeroOrdenPago);
        q.setParameter("anio", year);

        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findPagosByNumeroOrdenPagoAnioBorrado(int numeroOrdenPago, int year) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosByNumeroOrdenPagoAnioBorrado");
        q.setParameter("numeroOrdenPago", numeroOrdenPago);
        q.setParameter("anio", year);

        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findAllNoCerrados() {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findAllNoCerrados");
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findNoCerradosFecha(Date fechaCierre) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findNoCerradosFecha");
        q.setParameter("fechaCierre", fechaCierre);
        return q.getResultList();
    }

    @Override
    public List<PagosDocente> findPagosByComprobante(Docente docente, Proveedor proveedor, String numeroComprobante, Date fechaComprobante) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosByComprobante");
        q.setParameter("docente", docente);
        q.setParameter("proveedor", proveedor);
        q.setParameter("numeroComprobante", numeroComprobante);
        q.setParameter("fechaComprobante", fechaComprobante);
        return q.getResultList();
    }

    @Override
    public boolean comprobanteDuplicado(Docente docente, Proveedor proveedor, String numeroComprobante, Date fechaComprobante) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.findPagosByComprobante");
        q.setParameter("docente", docente);
        q.setParameter("proveedor", proveedor);
        q.setParameter("numeroComprobante", numeroComprobante);
        q.setParameter("fechaComprobante", fechaComprobante);
        return !q.getResultList().isEmpty();
    }

    @Override
    public boolean existeNumeroComprobante(Proveedor proveedor, String numeroComprobante) {

        Query q = null;
        q = em.createNamedQuery("PagosDocente.existeNumeroComprobante");
        q.setParameter("numeroComprobante", numeroComprobante);
        q.setParameter("proveedor", proveedor);
        return !q.getResultList().isEmpty();
    }

    @Override
    public boolean existeNumeroComprobanteDocente(Docente docente, String numeroComprobante) {
        Query q = null;
        q = em.createNamedQuery("PagosDocente.existeNumeroComprobanteDocente");
        q.setParameter("numeroComprobante", numeroComprobante);
        q.setParameter("docente", docente);
        return !q.getResultList().isEmpty();

    }

}
