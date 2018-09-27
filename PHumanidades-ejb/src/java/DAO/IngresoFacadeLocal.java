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
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface IngresoFacadeLocal {

    void create(Ingreso ingresoCuota);

    void edit(Ingreso ingresoCuota);

    void remove(Ingreso ingresoCuota);

    Ingreso find(Object id);

    List<Ingreso> findAll();

    List<Ingreso> findRange(int[] range);

    int count();

    List<Ingreso> findCuotasAlumno(Alumno alumno);

    List<Ingreso> findCuotasAlumnoGeneral(Alumno alumno);

    public void updateBorrado(Boolean bEstado, Long id) throws Exception;

    public List<Ingreso> findByFechaCohorte(Date ini, Date fin, Cohorte cohorte);

    public List<Ingreso> findByFecha(Date ini, Date fin);

    public List<Ingreso> findByFechaGenerales(Date ini, Date fin);

    public List<Ingreso> findCobrosByDni(String dni);

    public List<Ingreso> findCobrosByDniOTexto(String dni);

    public boolean existeNumeroRecibo(Cuenta cuenta, int numero, int anio);

    public int numeroReciboSegunCuenta(Cuenta cuenta);

    public List<Ingreso> findCobrosGeneralXFecha(Date fechaIni, Date fechaFin);

    public List<Ingreso> findCobrosXFecha(Date fechaIni, Date fechaFin);

    public List<Ingreso> findCuotasAlumnoCohorte(Alumno alumno, Cohorte cohorte);

    public int findUltimaCuotaAlumno(Alumno alumno);

    public int findUltimaCuotaAlumnoCohorte(Alumno alumno, Cohorte cohorte);

    public Date findFechaUltimaCuotaAlumno(Alumno alumno);

    public List<Ingreso> findAllDesc();

    public Ingreso getByNumeroRecibo(Cuenta cuenta, int numero, int anio);

    public List<Object[]> consultaUltimaCuotaAlumno();

    public List<Ingreso> findAllByNumeroRecibo(Cuenta cuenta, int numero, int anio);

    public List<Ingreso> findAllByCuenta(Cuenta cuenta, int anio);

    public List<Ingreso> findCobrosGeneralXFechaTipo(Date fechaIni, Date fechaFin, TipoIngreso tipoIngreso);

    public int findUltimoNumero(Cuenta cuenta, int anio);

    public List<Ingreso> findAllNoCerrados();

    public List<Ingreso> findNoCerradosFecha(Date fechaCierre);

}
