/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Cohorte;
import DAO.IngresoFacadeLocal;
import Entidades.Carreras.Cuenta;
import Entidades.Ingresos.Ingreso;
import Entidades.Ingresos.TipoIngreso;
import Entidades.Persona.Alumno;
import Entidades.Usuarios.Usuarios;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author vouilloz
 */
@Stateless
public class IngresoRN implements IngresoRNLocal {

    @EJB
    private IngresoFacadeLocal ingresoCuotaFacadeLocal;

    @Override
    public void create(Ingreso ingresoCuota) throws Exception {
        this.validar(ingresoCuota);
        ingresoCuotaFacadeLocal.create(ingresoCuota);
    }

    @Override
    public void create(Ingreso ingresoCuota, boolean multiCuota) throws Exception {
        this.validar(ingresoCuota, true);
        ingresoCuotaFacadeLocal.create(ingresoCuota);
    }

    @Override
    public void edit(Ingreso ingresoCuota) throws Exception {
        this.validar(ingresoCuota, true);
        ingresoCuotaFacadeLocal.edit(ingresoCuota);
    }

    @Override
    public List<Ingreso> findAll() throws Exception {
        return ingresoCuotaFacadeLocal.findAll();
    }

    @Override
    public List<Ingreso> findAllDesc() throws Exception {
        return ingresoCuotaFacadeLocal.findAllDesc();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Ingreso> findCuotasAlumno(Alumno alumno) throws Exception {
        return ingresoCuotaFacadeLocal.findCuotasAlumno(alumno);
    }

    @Override
    public void remove(Ingreso ingresoCuota, Boolean bEstado) throws Exception {
        this.validar(ingresoCuota, true);
        ingresoCuotaFacadeLocal.updateBorrado(bEstado, ingresoCuota.getId());
    }

    @Override
    public List<Ingreso> findByFechaCohorte(Date ini, Date fin, Cohorte cohorte) throws Exception {
        return ingresoCuotaFacadeLocal.findByFechaCohorte(ini, fin, cohorte);
    }

    @Override
    public List<Ingreso> findByFecha(Date ini, Date fin) {
        return ingresoCuotaFacadeLocal.findByFecha(ini, fin);
    }

    @Override
    public List<Ingreso> findByFechaGenerales(Date ini, Date fin) {
        return ingresoCuotaFacadeLocal.findByFechaGenerales(ini, fin);
    }

    @Override
    public Ingreso getByNumeroRecibo(Cuenta cuenta, int numero, int anio) {
        return ingresoCuotaFacadeLocal.getByNumeroRecibo(cuenta, numero, anio);
    }

    @Override
    public void anular(Ingreso ingreso, Usuarios usuario) {
        if (ingreso.getNumeroRecibo() != 0) {
            Calendar c = Calendar.getInstance();
            c.setTime(ingreso.getFechaPago());
            int anio = c.get(Calendar.YEAR);
            List<Ingreso> findAllByNumeroRecibo = ingresoCuotaFacadeLocal.findAllByNumeroRecibo(ingreso.getCuenta(), ingreso.getNumeroRecibo(), anio);
            for (Ingreso i : findAllByNumeroRecibo) {
                String sMensaje = "";
                FacesMessage fm;
                FacesMessage.Severity severity = FacesMessage.SEVERITY_INFO;
                try {
                    i.setFechaModificado(new Date());
                    i.setModificadoPor(usuario.getUsuario());
                    i.setAnulado(true);
                    this.edit(i);
                    sMensaje = "El ingreso fue anulado";

                } catch (Exception ex) {
                    severity = FacesMessage.SEVERITY_ERROR;
                    sMensaje = "Error: " + ex.getMessage();
                    System.out.println(sMensaje);

                } finally {
                    fm = new FacesMessage(severity, sMensaje, null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);

                }
            }
        }
    }

    private void validar(Ingreso ingresoCuota) throws Exception {
        if (ingresoCuota.getFechaPago() == null) {
            throw new Exception("No selecciono Fecha de Pago");
        }//fin if
        Date ultimoPago = ingresoCuotaFacadeLocal.findFechaUltimaCuotaAlumno(ingresoCuota.getAlumno());
        if (ingresoCuota.getFechaPago() != null && ultimoPago != null) {
            if (ingresoCuota.getFechaPago().compareTo(ultimoPago) < 0) {
                Calendar c = Calendar.getInstance();
                c.setTime(ultimoPago);
                String format = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                throw new Exception("La Fecha de Pago debe ser mayor o igual que el ultimo pago: "
                        + format);
            }
        }
        if (ingresoCuota.getCuenta() == null) {
            throw new Exception("Debe seleccionar una cuenta");
        } else if (ingresoCuota.getCuenta().getCodigo() == null) {
            throw new Exception("Cuenta Incorrecta");
        } else if (ingresoCuota.getId() == null) { //si es nulo entonces es un nuevo ingreso
            int numeroRecibo = ingresoCuota.getNumeroRecibo();
            if (numeroRecibo != 0) {
                Calendar c = Calendar.getInstance();
                c.setTime(ingresoCuota.getFechaPago());
                int anio = c.get(Calendar.YEAR);
                if (ingresoCuotaFacadeLocal.existeNumeroRecibo(ingresoCuota.getCuenta(), numeroRecibo, anio)) {
                    throw new Exception("Ya existe el numero de recibo, seleccione otro");
                }
            }
        } else {
            int numeroRecibo = ingresoCuota.getNumeroRecibo();
            Calendar c = Calendar.getInstance();
            c.setTime(ingresoCuota.getFechaPago());
            int anio = c.get(Calendar.YEAR);
            Ingreso byNumeroRecibo = ingresoCuotaFacadeLocal.getByNumeroRecibo(ingresoCuota.getCuenta(), numeroRecibo, anio);
            ingresoCuotaFacadeLocal.findAllByNumeroRecibo(ingresoCuota.getCuenta(), numeroRecibo, anio);

            if (byNumeroRecibo != null) {
                if (!Objects.equals(byNumeroRecibo.getId(), ingresoCuota.getId())) {
                    if (numeroRecibo != 0) {
                        List<Ingreso> findAllByNumeroRecibo = ingresoCuotaFacadeLocal.findAllByNumeroRecibo(ingresoCuota.getCuenta(), numeroRecibo, anio);
                        if (findAllByNumeroRecibo.size() < 2) {
                            if (ingresoCuotaFacadeLocal.existeNumeroRecibo(ingresoCuota.getCuenta(), numeroRecibo, anio)) {
                                throw new Exception("Ya existe el numero de recibo, seleccione otro");
                            }
                        }
                    }
                }
            }
        }

    }

    private void validar(Ingreso ingresoCuota, boolean multiCuotas) throws Exception {
        if (ingresoCuota.getFechaPago() == null) {
            throw new Exception("No selecciono Fecha de Pago");
        }//fin if
        if (ingresoCuota.getCuenta() == null) {
            throw new Exception("Debe seleccionar una cuenta");
        } else if (ingresoCuota.getCuenta().getCodigo() == null) {
            throw new Exception("Cuenta Incorrecta");
        } else if (ingresoCuota.getId() == null) { //si es nulo entonces es un nuevo ingreso
            int numeroRecibo = ingresoCuota.getNumeroRecibo();
            if (numeroRecibo != 0) {
                Calendar c = Calendar.getInstance();
                c.setTime(ingresoCuota.getFechaPago());
                int anio = c.get(Calendar.YEAR);
                if (ingresoCuotaFacadeLocal.existeNumeroRecibo(ingresoCuota.getCuenta(), numeroRecibo, anio)) {
                    throw new Exception("Ya existe el numero de recibo, seleccione otro");
                }
            }
        }

    }

    @Override
    public List<Ingreso> findCobrosByDni(String dni) throws Exception {
        return ingresoCuotaFacadeLocal.findCobrosByDni(dni);
    }

    @Override
    public List<Ingreso> findCobrosByDniOTexto(String dni) throws Exception {
        return ingresoCuotaFacadeLocal.findCobrosByDniOTexto(dni);
    }

    @Override
    public int numeroReciboSegunCuenta(Cuenta cuenta) throws Exception {
        return ingresoCuotaFacadeLocal.numeroReciboSegunCuenta(cuenta);
    }

    @Override
    public List<Ingreso> findCobrosGeneralXFecha(Date fechaIni, Date fechaFin) {
        return ingresoCuotaFacadeLocal.findCobrosGeneralXFecha(fechaIni, fechaFin);
    }

    @Override
    public List<Ingreso> findCobrosGeneralXFechaTipo(Date fechaIni, Date fechaFin, TipoIngreso tipoIngreso) {
        return ingresoCuotaFacadeLocal.findCobrosGeneralXFechaTipo(fechaIni, fechaFin, tipoIngreso);
    }

    @Override
    public List<Ingreso> findCobrosXFecha(Date fechaIni, Date fechaFin) {
        return ingresoCuotaFacadeLocal.findCobrosXFecha(fechaIni, fechaFin);
    }

    @Override
    public List<Ingreso> findCuotasAlumnoCohorte(Alumno alumno, Cohorte cohorte) {
        return ingresoCuotaFacadeLocal.findCuotasAlumnoCohorte(alumno, cohorte);
    }

    @Override
    public int findUltimaCuotaAlumno(Alumno alumno) {
        return ingresoCuotaFacadeLocal.findUltimaCuotaAlumno(alumno);
    }

    @Override
    public int findUltimaCuotaAlumnoCohorte(Alumno alumno, Cohorte cohorte) {
        return ingresoCuotaFacadeLocal.findUltimaCuotaAlumnoCohorte(alumno, cohorte);
    }

    @Override
    public Ingreso find(Long id) {
        return ingresoCuotaFacadeLocal.find(id);
    }

    @Override
    public List<Ingreso> findCuotasAlumnoGeneral(Alumno alumno) throws Exception {
        return ingresoCuotaFacadeLocal.findCuotasAlumnoGeneral(alumno);
    }

    @Override
    public List<Object[]> consultaUltimaCuotaAlumno() {
        return ingresoCuotaFacadeLocal.consultaUltimaCuotaAlumno();
    }
    
      @Override
    public List<Object[]> consultaUltimaCuotaAlumno(Cohorte cohorte) {
        return ingresoCuotaFacadeLocal.consultaUltimaCuotaAlumno(cohorte);
    }

    @Override
    public List<Ingreso> findAllByNumeroRecibo(Cuenta cuenta, int numero, int anio) {
        return ingresoCuotaFacadeLocal.findAllByNumeroRecibo(cuenta, numero, anio);
    }

    @Override
    public List<Ingreso> findAllByCuenta(Cuenta cuenta, int anio) {
        return ingresoCuotaFacadeLocal.findAllByCuenta(cuenta, anio);
    }

    @Override
    public void edit(Ingreso ingresoCuota, boolean validar) throws Exception {
        if (validar) {
            this.validar(ingresoCuota);
        }
        ingresoCuotaFacadeLocal.edit(ingresoCuota);
    }

    @Override
    public int findUltimoNumero(Cuenta cuenta, int anio) {
        return ingresoCuotaFacadeLocal.findUltimoNumero(cuenta, anio);
    }

    @Override
    public List<Ingreso> findAllNoCerrados() {
        return ingresoCuotaFacadeLocal.findAllNoCerrados();
    }

}
