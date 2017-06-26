/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Carreras.Cohorte;
import Entidades.Carreras.Cuenta;
import Entidades.Ingresos.Ingreso;
import Entidades.Ingresos.TipoIngreso;
import Entidades.Persona.Alumno;
import java.util.ArrayList;
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
public class IngresoFacade extends AbstractFacade<Ingreso> implements IngresoFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IngresoFacade() {
        super(Ingreso.class);
    }

    @Override
    public List<Ingreso> findCuotasAlumno(Alumno alumno) {
        Query q = em.createNamedQuery("Ingreso.findCuotasAlumno");
        q.setParameter("alumno", alumno);
        return q.getResultList();
    }

    @Override
    public List<Ingreso> findCuotasAlumnoGeneral(Alumno alumno) {
        try {
            Query q;
            q = em.createNamedQuery("Ingreso.findCuotasAlumnoGeneral");
            q.setParameter("alumno", alumno);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("exception: " + e);
            return null;
        }
    }

    @Override
    public List<Ingreso> findAllDesc() {
        Query q = em.createNamedQuery("Ingreso.findAllDesc");
        return q.getResultList();
    }

    @Override
    public void updateBorrado(Boolean bEstado, Long id) throws Exception {
        Query q = em.createNamedQuery("Ingreso.UpdateBorrado");
        q.setParameter("estado", bEstado);
        q.setParameter("id", id);
        q.executeUpdate();
    }

    @Override
    public List<Ingreso> findByFechaCohorte(Date ini, Date fin, Cohorte cohorte) {
        Query q = null;
        q = em.createNamedQuery("Ingreso.findByFechaCohorte");
        q.setParameter("fechaIni", ini);
        q.setParameter("fechaFin", fin);
        q.setParameter("cohorte", cohorte);
        return q.getResultList();
    }

    @Override
    public List<Ingreso> findByFecha(Date ini, Date fin) {
        System.out.println("entro find by fecha");
        Query q = null;
        q = em.createNamedQuery("Ingreso.findByFecha");
        q.setParameter("fechaIni", ini);
        q.setParameter("fechaFin", fin);
        return q.getResultList();
    }

    @Override
    public List<Ingreso> findByFechaGenerales(Date ini, Date fin) {
        Query q = null;
        q = em.createNamedQuery("Ingreso.findByFechaGenerales");
        q.setParameter("fechaIni", ini);
        q.setParameter("fechaFin", fin);
        return q.getResultList();
    }

    @Override
    public List<Ingreso> findCobrosByDni(String dni) {
        Query q = null;
        q = em.createNamedQuery("Ingreso.findCobrosByDni");
        q.setParameter("dni", dni);
        return q.getResultList();
    }

    @Override
    public List<Ingreso> findCobrosByDniOTexto(String dni) {
        Query q = null;
        q = em.createNamedQuery("Ingreso.findCobrosByDniOTexto");
        q.setParameter("dni", dni);
        try {
            dni = dni.toUpperCase();
        } catch (Exception e) {
        }
        q.setParameter("texto", "%" + dni + "%");
        return q.getResultList();
    }

    @Override
    public boolean existeNumeroRecibo(Cuenta cuenta, int numero, int anio) {
        try {
            Query q = em.createNamedQuery("Ingreso.existeNumeroRecibo");
            q.setParameter("numero", numero);
            q.setParameter("cuenta", cuenta);
            q.setParameter("anio", anio);
            q.setMaxResults(1);
            return q.getSingleResult() != null;

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Ingreso getByNumeroRecibo(Cuenta cuenta, int numero, int anio) {
        try {
            Query q = em.createNamedQuery("Ingreso.existeNumeroRecibo");
            q.setParameter("numero", numero);
            q.setParameter("cuenta", cuenta);
            q.setParameter("anio", anio);

            q.setMaxResults(1);

            return (Ingreso) q.getSingleResult();

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }

    }

    @Override
    public List<Ingreso> findAllByNumeroRecibo(Cuenta cuenta, int numero, int anio) {
        try {
            Query q = em.createNamedQuery("Ingreso.findAllByNumeroRecibo");
            q.setParameter("numero", numero);
            q.setParameter("cuenta", cuenta);
            q.setParameter("anio", anio);

            return q.getResultList();

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }

    }

    @Override
    public List<Ingreso> findAllByCuenta(Cuenta cuenta, int anio) {
        try {
            Query q = em.createNamedQuery("Ingreso.findAllByCuenta");
            q.setParameter("cuenta", cuenta);
            q.setParameter("anio", anio);

            return q.getResultList();

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }

    }

    @Override
    public int numeroReciboSegunCuenta(String cuenta) {
        //System.out.println("existeNumeroRecibo, IMGRESO CUOTA FACADE"+numero+""+cuenta.getDescripcion());
        try {
            if (cuenta.equals("025")) {
                Query q = em.createNamedQuery("Contador025.findUltimoNumero");
                q.setMaxResults(1);
                return (int) q.getSingleResult();
            } else {
                Query q = em.createNamedQuery("Contador005.findUltimoNumero");
                q.setMaxResults(1);
                return (int) q.getSingleResult();
            }

        } catch (Exception e) {
            System.out.println("Error numeroReciboSegunCuenta: " + e);
            return 0;
        }
    }

    @Override
    public List<Ingreso> findCobrosGeneralXFecha(Date fechaIni, Date fechaFin) {
        Query q = null;
        q = em.createNamedQuery("Ingreso.findCobrosGeneralXFecha");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        return q.getResultList();
    }

    @Override
    public List<Ingreso> findCobrosGeneralXFechaTipo(Date fechaIni, Date fechaFin, TipoIngreso tipoIngreso) {
        Query q = null;
        q = em.createNamedQuery("Ingreso.findCobrosGeneralXFechaTipo");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("tipoIngreso", tipoIngreso);
        return q.getResultList();
    }

    @Override
    public List<Ingreso> findCobrosXFecha(Date fechaIni, Date fechaFin) {
        Query q = null;
        q = em.createNamedQuery("Ingreso.findCobrosXFecha");
        q.setParameter("fechaIni", fechaIni);
        q.setParameter("fechaFin", fechaFin);
        return q.getResultList();
    }

    @Override
    public List<Ingreso> findCuotasAlumnoCohorte(Alumno alumno, Cohorte cohorte) {
        try {
            Query q = em.createNamedQuery("Ingreso.findCuotasAlumnoCohorte");
            q.setParameter("alumno", alumno);
            q.setParameter("cohorte", cohorte);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("exception " + e);
            return null;
        }
    }

    @Override
    public List<Object[]> consultaUltimaCuotaAlumno() {
        Query q = em.createNamedQuery("Ingreso.ConsultaUltimaCuotaAlumno");
        try {
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<Object[]>();
        }
    }

    @Override
    public int findUltimaCuotaAlumno(Alumno alumno) {
        Query q = em.createNamedQuery("Ingreso.findUltimaCuotaAlumno");
        q.setParameter("alumno", alumno);
        try {
            return (int) q.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int findUltimaCuotaAlumnoCohorte(Alumno alumno, Cohorte cohorte) {
        Query q = em.createNamedQuery("Ingreso.findUltimaCuotaAlumnoCohorte");
        q.setParameter("alumno", alumno);
        q.setParameter("cohorte", cohorte);
        try {
            return (int) q.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

}
