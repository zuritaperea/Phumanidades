/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Carrera;
import DAO.PagosDocenteFacadeLocal;
import Entidades.Carreras.Cuenta;
import Entidades.Egresos.PagosDocente;
import Entidades.Egresos.TipoEgreso;
import Entidades.Persona.Docente;
import Entidades.Persona.Proveedor;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class PagosDocenteRN implements PagosDocenteRNLocal {

    @EJB
    private PagosDocenteFacadeLocal pagosDocenteFacadeLocal;

    @Override
    public void create(PagosDocente pagosDocente) throws Exception {
        pagosDocente.setBorrado(Boolean.FALSE);
        pagosDocente.setAnulado(Boolean.FALSE);
        pagosDocenteFacadeLocal.create(pagosDocente);
    }

    @Override
    public void edit(PagosDocente pagosDocente) throws Exception {
        pagosDocenteFacadeLocal.edit(pagosDocente);
    }

    @Override
    public List<PagosDocente> findAll() throws Exception {
        return pagosDocenteFacadeLocal.findAll();
    }

    @Override
    public List<PagosDocente> findAllDesc() throws Exception {
        return pagosDocenteFacadeLocal.findAllDesc();
    }

    @Override
    public List<PagosDocente> findAllDescAnio(int year) throws Exception {
        return pagosDocenteFacadeLocal.findAllDescAnio(year);
    }

    @Override
    public PagosDocente buscarPagosDocente(PagosDocente pagosDocente) {
        return pagosDocenteFacadeLocal.find(pagosDocente.getId());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<PagosDocente> findByFechaCarrera(Date ini, Date fin, Carrera carrera) throws Exception {
        return pagosDocenteFacadeLocal.findByFechaCarrera(ini, fin, carrera);
    }

    @Override
    public List<PagosDocente> findPagosGeneralXFecha(Date ini, Date fin) {
        return pagosDocenteFacadeLocal.findPagosGeneralXFecha(ini, fin);
    }

    @Override
    public List<PagosDocente> findByFechaCarreraDocente(Date ini, Date fin, Carrera carrera) {
        return pagosDocenteFacadeLocal.findByFechaCarreraDocente(ini, fin, carrera);
    }

    @Override
    public List<PagosDocente> findPagosXFechaDocente(Date ini, Date fin) {
        return pagosDocenteFacadeLocal.findPagosXFechaDocente(ini, fin);
    }

    @Override
    public List<PagosDocente> findPagosXFechaProveedor(Date ini, Date fin) {
        return pagosDocenteFacadeLocal.findPagosXFechaProveedor(ini, fin);
    }

    @Override
    public void remove(PagosDocente pagosDocente, Boolean bEstado) throws Exception {
        //System.out.println("remove pagoDocente: " + bEstado + " " + pagosDocente.getId());
        pagosDocenteFacadeLocal.updateBorrado(bEstado, pagosDocente.getId());
    }

    @Override
    public List<PagosDocente> findPagosByDni(String dni) throws Exception {
        return pagosDocenteFacadeLocal.findPagosByDni(dni);
    }

    @Override
    public int findUltimoNumero() {
        return pagosDocenteFacadeLocal.findUltimoNumero();
    }

    @Override
    public PagosDocente buscarPagosDocenteId(Long id) {
        return pagosDocenteFacadeLocal.buscarPagosDocenteId(id);
    }

    @Override
    public List<PagosDocente> findPagosXFechaProveedorYCuenta(Date ini, Date fin, Cuenta cuenta) {
        return pagosDocenteFacadeLocal.findPagosXFechaProveedorYCuenta(ini, fin, cuenta);
    }

    @Override
    public List<PagosDocente> findPagosByTipoEgreso(TipoEgreso tipo) throws Exception {
        return pagosDocenteFacadeLocal.findPagosByTipoEgreso(tipo);
    }

    @Override
    public List<PagosDocente> findPagosByPredicates(Date ini, Date fin, Cuenta cuenta, TipoEgreso tipoEgreso, Carrera carrera) throws Exception {
        return pagosDocenteFacadeLocal.findPagosByPredicates(ini, fin, cuenta, tipoEgreso, carrera);
    }

    @Override
    public List<PagosDocente> findPagosByNumeroOrdenPago(int numeroOrdenPago) throws Exception {
        return pagosDocenteFacadeLocal.findPagosByNumeroOrdenPago(numeroOrdenPago);
    }

    @Override
    public List<PagosDocente> findPagosByNumeroOrdenPagoAnio(int numeroOrdenPago, int year) throws Exception {
        return pagosDocenteFacadeLocal.findPagosByNumeroOrdenPagoAnio(numeroOrdenPago, year);
    }

    @Override
    public List<PagosDocente> findPagosByNumeroOrdenPagoAnioAnulado(int numeroOrdenPago, int year) throws Exception {
        return pagosDocenteFacadeLocal.findPagosByNumeroOrdenPagoAnioAnulado(numeroOrdenPago, year);
    }

    @Override
    public List<PagosDocente> findPagosByNumeroOrdenPagoAnioBorrado(int numeroOrdenPago, int year) throws Exception {
        return pagosDocenteFacadeLocal.findPagosByNumeroOrdenPagoAnioBorrado(numeroOrdenPago, year);
    }

    @Override
    public void removeTotal(PagosDocente pagosDocente) throws Exception {
        pagosDocenteFacadeLocal.remove(pagosDocente);
    }

    @Override
    public boolean comprobanteDuplicado(Docente docente, Proveedor proveedor, String numeroComprobante, Date fechaComprobante) {
        return pagosDocenteFacadeLocal.comprobanteDuplicado(docente, proveedor, numeroComprobante, fechaComprobante);
    }

    @Override
    public boolean existeNumeroComprobante(Proveedor proveedor, String numeroComprobante) {
        return pagosDocenteFacadeLocal.existeNumeroComprobante(proveedor, numeroComprobante);

    }

    @Override
    public boolean existeNumeroComprobanteDocente(Docente docente, String numeroComprobante) {
       return pagosDocenteFacadeLocal.existeNumeroComprobanteDocente(docente, numeroComprobante); 
    }

}
