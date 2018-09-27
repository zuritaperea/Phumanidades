package Beans;

import DAO.IngresoFacadeLocal;
import DAO.PagosDocenteFacadeLocal;
import Entidades.Egresos.PagosDocente;
import Entidades.Ingresos.Ingreso;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@Named("cierreBean")
@SessionScoped
public class CierreBean implements Serializable {

    @EJB
    private PagosDocenteFacadeLocal pagosDocenteFacadeLocal;
    @EJB
    private IngresoFacadeLocal ingresoFacadeLocal;
    private String sMensaje = "";
    BigDecimal saldo = new BigDecimal("0.00");
    BigDecimal totalIngresos = new BigDecimal("0.00");
    BigDecimal totalEgresos = new BigDecimal("0.00");
    Date fechaCierre = new Date();

    public CierreBean() {
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public PagosDocenteFacadeLocal getPagosDocenteFacadeLocal() {
        return pagosDocenteFacadeLocal;
    }

    public void setPagosDocenteFacadeLocal(PagosDocenteFacadeLocal pagosDocenteFacadeLocal) {
        this.pagosDocenteFacadeLocal = pagosDocenteFacadeLocal;
    }

    public IngresoFacadeLocal getIngresoFacadeLocal() {
        return ingresoFacadeLocal;
    }

    public void setIngresoFacadeLocal(IngresoFacadeLocal ingresoFacadeLocal) {
        this.ingresoFacadeLocal = ingresoFacadeLocal;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public BigDecimal getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(BigDecimal totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void calcularSaldo() {

        //selected.setMovimientosCaja(movimientoCajaRNLocal.getMovimientosdesdeFecha(selected.getFechaInicio()));
        for (PagosDocente egreso : pagosDocenteFacadeLocal.findAllNoCerrados()) {
            try {
                totalEgresos = totalEgresos.add(egreso.getMonto());
            } catch (Exception e) {
            }
        }
        for (Ingreso ingreso : ingresoFacadeLocal.findAllNoCerrados()) {
            try {
                totalIngresos = totalIngresos.add(ingreso.getImporte());

            } catch (Exception e) {
            }
        }
        //calculamos el saldo
        saldo = totalIngresos.subtract(totalEgresos);
        RequestContext.getCurrentInstance().execute("PF('CajaCierreDlg').show();");
    }

    public void cerrarCaja() {
        for (PagosDocente egreso : pagosDocenteFacadeLocal.findNoCerradosFecha(fechaCierre)) {
            egreso.setFechaCierre(fechaCierre);
            pagosDocenteFacadeLocal.edit(egreso);
        }
        for (Ingreso ingreso : ingresoFacadeLocal.findAllNoCerrados()) {
            ingreso.setFechaCierre(fechaCierre);
            ingresoFacadeLocal.edit(ingreso);
        }
        //selected.setCajaFinal(cajaArqueo);
        RequestContext.getCurrentInstance().execute("PF('CajaCierreDlg').hide();");
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cierre Realizado", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

}
