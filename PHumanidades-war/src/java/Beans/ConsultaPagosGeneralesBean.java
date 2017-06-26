package Beans;

import Entidades.Egresos.PagosDocente;
import RN.PagosDocenteRNLocal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 *
 */
@ManagedBean(name = "consultaPagosGeneralesBean")
@ViewScoped
public class ConsultaPagosGeneralesBean implements Serializable {

    @EJB
    private PagosDocenteRNLocal pagoGeneralRNLocal;

    private List<PagosDocente> lstGastoGeneral;
    private Date fechaFin;
    private Date fechaIni;
    private BigDecimal totalXGastoGeneral;

    @PostConstruct
    private void init() {

        lstGastoGeneral = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        fechaIni = new Date();
        fechaFin = c.getTime();
        totalXGastoGeneral = new BigDecimal(0);
    }//fin init

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    /**
     * agrego un dia para que las busquedas sean de menor o igual
     *
     * @return fechaFinAumentada
     */
    public Date getFechaFin() {
        if (fechaFin != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(this.fechaFin);
            c.add(Calendar.DATE, 1);
            return c.getTime();
        } else {
            return fechaFin;
        }
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<PagosDocente> getLstGastoGeneral() {
        return lstGastoGeneral;
    }

    public void setLstGastoGeneral(List<PagosDocente> lstGastoGeneral) {
        this.lstGastoGeneral = lstGastoGeneral;
    }

    public BigDecimal getTotalXGastoGeneral() {
        return totalXGastoGeneral;
    }

    public void setTotalXGastoGeneral(BigDecimal totalXGastoGeneral) {
        this.totalXGastoGeneral = totalXGastoGeneral;
    }

    /**
     * Buscar historial de operaciones entre dos fechas
     */
    public void buscarGastosGeneralesFechas() {
        //System.out.println("entro buscarFechaCohortes");
        FacesMessage fm;
        try {
            //verifico que no sean nulas las fechas
            totalXGastoGeneral = BigDecimal.ZERO;
            //System.out.println("entro if buscarFechaCarrera" + this.getCohorteLstBean().getCohorteSelect());
            //aumento un dia a la fecha fin para que la busqueda sea menor o igual
            if (fechaIni != null && fechaFin != null) {
                this.setLstGastoGeneral(pagoGeneralRNLocal.findPagosXFechaProveedor(this.getFechaIni(), this.getFechaFin()));

//Sumamos el total de los cobros por cohorte
                for (PagosDocente gg : this.getLstGastoGeneral()) {
                    totalXGastoGeneral = totalXGastoGeneral.add(gg.getMonto());
                }

                if (this.getLstGastoGeneral().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if
            }
        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:cbConsultarGastos", fm);
        }//fin catch
    }

    public void onTabChange(TabChangeEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Seleccion: " + event.getTab().getTitle()));
        //     this.setLstPagosDocente(new ArrayList<PagosDocente>());
        RequestContext.getCurrentInstance().update("frmPri:dtGastosGenerales");
    }

}
