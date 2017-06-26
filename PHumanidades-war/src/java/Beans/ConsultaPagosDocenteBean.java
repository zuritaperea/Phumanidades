package Beans;

import Entidades.Carreras.Carrera;
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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 * Clase usada para manejar la localidad
 *
 * @author AFerSor
 */
@ManagedBean(name = "consultaPagosDocenteBean")
@ViewScoped
public class ConsultaPagosDocenteBean implements Serializable {

    @EJB
    private PagosDocenteRNLocal pagosDocenteRNLocal;

    @ManagedProperty(value = "#{carreraLstBean}")
    private CarreraLstBean carreraLstBean;

    private List<PagosDocente> lstPagosDocente;
    private Date fechaFin;
    private Date fechaIni;
    private Carrera carreraSelect;
    private BigDecimal totalXCarrera;
    private DataTable dataTable;

    @PostConstruct
    private void init() {
        carreraSelect = new Carrera();
        lstPagosDocente = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        fechaIni = new Date();
        fechaFin = c.getTime();
        totalXCarrera = new BigDecimal(0);
    }//fin init

    public PagosDocenteRNLocal getPagosDocenteRNLocal() {
        return pagosDocenteRNLocal;
    }

    public void setPagosDocenteRNLocal(PagosDocenteRNLocal pagosDocenteRNLocal) {
        this.pagosDocenteRNLocal = pagosDocenteRNLocal;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

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

    public List<PagosDocente> getLstPagosDocente() {
        return lstPagosDocente;
    }

    public void setLstPagosDocente(List<PagosDocente> lstPagosDocente) {
        this.lstPagosDocente = lstPagosDocente;
    }

    public CarreraLstBean getCarreraLstBean() {
        return carreraLstBean;
    }

    public void setCarreraLstBean(CarreraLstBean carreraLstBean) {
        this.carreraLstBean = carreraLstBean;
    }

    public Carrera getCarreraSelect() {
        return carreraSelect;
    }

    public void setCarreraSelect(Carrera carreraSelect) {
        this.carreraSelect = carreraSelect;
    }

    public BigDecimal getTotalXCarrera() {
        return totalXCarrera;
    }

    public void setTotalXCarrera(BigDecimal totalXCarrera) {
        this.totalXCarrera = totalXCarrera;
    }

    //BUSCA EL PAGO REALIZADO A DOCENTES TENIENDO EN CUENTA FECHA DE INICIO Y FIN DISCRIMINANDO POR CARRERA
    public void buscarFechaCarreras() {
        //System.out.println("entro buscarFechaCarrera");
        totalXCarrera = BigDecimal.ZERO;
        FacesMessage fm;
        try {

            if (fechaIni != null && fechaFin != null) {
                if (this.getCarreraLstBean().getCarreraSelect() != null) {
                    this.setLstPagosDocente(pagosDocenteRNLocal.findByFechaCarreraDocente(this.getFechaIni(), this.getFechaFin(), this.getCarreraLstBean().getCarreraSelect()));
                } else {
                    this.setLstPagosDocente(pagosDocenteRNLocal.findPagosXFechaDocente(this.getFechaIni(), this.getFechaFin()));
                }
//Sumamos el total de los pagos x carrera
                for (PagosDocente pd : this.getLstPagosDocente()) {
                    totalXCarrera = totalXCarrera.add(pd.getMonto());
                }
                if (this.getLstPagosDocente().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if

            }

        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:btnConsultar", fm);
        }//fin catch
    }

    //BUSCA LOS PAGOS REALIZADOS A LOS DOCENTES TENIENDO EN CUENTA FECHA DE INICIO Y FECHA DE FIN
    public void buscarFecha() {
        FacesMessage fm;
        try {
            //verifico que no sean nulas las fechas

            // System.out.println("entro if buscarFechaCarrera"+this.getCarreraLstBean().getCarreraSelect());
            //aumento un dia a la fecha fin para que la busqueda sea menor o igual
            if (fechaIni != null && fechaFin != null) {
                this.setLstPagosDocente(pagosDocenteRNLocal.findPagosXFechaDocente(this.getFechaIni(), this.getFechaFin()));
                //Sumamos el total de los pagos x carrera
                for (PagosDocente pd : this.getLstPagosDocente()) {
                    totalXCarrera = totalXCarrera.add(pd.getMonto());
                }
                if (this.getLstPagosDocente().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if

            }

        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:btnConsultar", fm);
        }//fin catch
    }

    //ESTE MEDOTO SE EJECUTA AL CAMBIAR DE TAB EN EL TAB VIEW
    public void onTabChange(TabChangeEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Seleccion: " + event.getTab().getTitle()));
        this.setLstPagosDocente(new ArrayList<PagosDocente>());
        RequestContext.getCurrentInstance().update("frmPri:dtPagosDocente");
    }

}
