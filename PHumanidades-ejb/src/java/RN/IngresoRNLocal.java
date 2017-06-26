/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Cohorte;
import Entidades.Carreras.Cuenta;
import Entidades.Ingresos.Ingreso;
import Entidades.Ingresos.TipoIngreso;
import Entidades.Persona.Alumno;
import Entidades.Usuarios.Usuarios;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface IngresoRNLocal {

    public void create(Ingreso ingresoCuota) throws Exception;

    public void edit(Ingreso ingresoCuota) throws Exception;

    public void remove(Ingreso ingresoCuota, Boolean bEstado) throws Exception;

    public List<Ingreso> findAll() throws Exception;

    public List<Ingreso> findCuotasAlumno(Alumno alumno) throws Exception;

    public List<Ingreso> findCuotasAlumnoGeneral(Alumno alumno) throws Exception;

    public List<Ingreso> findByFechaCohorte(Date ini, Date fin, Cohorte cohorte) throws Exception;

    public List<Ingreso> findByFecha(Date ini, Date fin);

    public List<Ingreso> findByFechaGenerales(Date ini, Date fin);

    public List<Ingreso> findCobrosByDni(String dni) throws Exception;

    public List<Ingreso> findCobrosByDniOTexto(String dni) throws Exception;

    public int numeroReciboSegunCuenta(String cuenta) throws Exception;

    public List<Ingreso> findCobrosGeneralXFecha(Date fechaIni, Date fechaFin);

    public List<Ingreso> findCobrosXFecha(Date fechaIni, Date fechaFin);

    public List<Ingreso> findCuotasAlumnoCohorte(Alumno alumno, Cohorte cohorte);

    public int findUltimaCuotaAlumno(Alumno alumno);

    public int findUltimaCuotaAlumnoCohorte(Alumno alumno, Cohorte cohorte);

    public List<Ingreso> findAllDesc() throws Exception;

    public Ingreso find(Long id);

    public Ingreso getByNumeroRecibo(Cuenta cuenta, int numero, int anio);

    public List<Object[]> consultaUltimaCuotaAlumno();

    public void create(Ingreso ingresoCuota, boolean multiCuota) throws Exception;

    public List<Ingreso> findAllByNumeroRecibo(Cuenta cuenta, int numero, int anio);

    public List<Ingreso> findAllByCuenta(Cuenta cuenta, int anio);

    public void anular(Ingreso ingreso, Usuarios usuario);

    public List<Ingreso> findCobrosGeneralXFechaTipo(Date fechaIni, Date fechaFin, TipoIngreso tipoIngreso);

}
