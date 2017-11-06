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
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author vouilloz
 */
@Local
public interface PagosDocenteFacadeLocal {

    void create(PagosDocente pagosDocente);

    void edit(PagosDocente pagosDocente);

    void remove(PagosDocente pagosDocente);

    PagosDocente find(Object id);

    List<PagosDocente> findAll();

    List<PagosDocente> findRange(int[] range);

    int count();

    public List<PagosDocente> findByFechaCarrera(Date ini, Date fin, Carrera carrera);

    public List<PagosDocente> findPagosGeneralXFecha(Date ini, Date fin);
    
    public List<PagosDocente> findPagosXFechaProveedorYCuenta(Date ini, Date fin, Cuenta cuenta);

    public void updateBorrado(Boolean bEstado, Long id) throws Exception;

    public List<PagosDocente> findPagosByDni(String dni);
    
    public List<PagosDocente> findPagosByTipoEgreso(TipoEgreso tipo);
    
    public List<PagosDocente> findPagosByPredicates(Date FechaInicio,Date FechaFin,Cuenta cuenta,TipoEgreso tipoEgreso);
    
    public PagosDocente buscarPagosDocenteId(Long id);

    public int findUltimoNumero();

    public List<PagosDocente> findAllDesc();

    public List<PagosDocente> findByFechaCarreraDocente(Date fechaIni, Date fechaFin, Carrera carrera);

    public List<PagosDocente> findPagosXFechaDocente(Date ini, Date fin);

    public List<PagosDocente> findPagosXFechaProveedor(Date ini, Date fin);

    public List<PagosDocente> findPagosByNumeroOrdenPago(int numeroOrdenPago);

}
