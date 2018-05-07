/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Carrera;
import Entidades.Carreras.Cuenta;
import Entidades.Egresos.PagosDocente;
import Entidades.Egresos.TipoEgreso;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface PagosDocenteRNLocal {

    public void create(PagosDocente pagosDocente) throws Exception;

    public void edit(PagosDocente pagosDocente) throws Exception;

    public void remove(PagosDocente pagosDocente, Boolean bEstado) throws Exception;

    public List<PagosDocente> findAll() throws Exception;

    public PagosDocente buscarPagosDocente(PagosDocente pagosDocente);

    //para resolver el modificar de pagos docente
    public PagosDocente buscarPagosDocenteId(Long id);

    public List<PagosDocente> findByFechaCarrera(Date ini, Date fin, Carrera carrera) throws Exception;

    public List<PagosDocente> findPagosByDni(String dni) throws Exception;

    public List<PagosDocente> findPagosByTipoEgreso(TipoEgreso tipo) throws Exception;

    public List<PagosDocente> findPagosByPredicates(Date ini, Date fin, Cuenta cuenta, TipoEgreso tipoEgreso, Carrera carrera) throws Exception;

    public List<PagosDocente> findPagosGeneralXFecha(Date ini, Date fin);

    public List<PagosDocente> findPagosXFechaProveedorYCuenta(Date ini, Date fin, Cuenta cuenta);

    public int findUltimoNumero();

    public List<PagosDocente> findAllDesc() throws Exception;

    public List<PagosDocente> findByFechaCarreraDocente(Date fechaIni, Date fechaFin, Carrera carrera);

    public List<PagosDocente> findPagosXFechaProveedor(Date ini, Date fin);

    public List<PagosDocente> findPagosXFechaDocente(Date ini, Date fin);

    public List<PagosDocente> findPagosByNumeroOrdenPago(int numeroOrdenPago) throws Exception;

}
