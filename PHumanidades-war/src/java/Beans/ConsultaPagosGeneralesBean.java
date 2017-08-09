package Beans;

import Entidades.Carreras.Cuenta;
import Entidades.Egresos.PagosDocente;
import RN.PagosDocenteRNLocal;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 *
 */
@ManagedBean(name = "consultaPagosGeneralesBean")
@ViewScoped
public class ConsultaPagosGeneralesBean implements Serializable {
    
    private final String escudo1 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "Imagenes" + File.separator + "huma.png";

    @EJB
    private PagosDocenteRNLocal pagoGeneralRNLocal;
    
    @ManagedProperty(value = "#{cuentaLstBean}")
    private CuentaLstBean cuentaLstBean;
    
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

    public CuentaLstBean getCuentaLstBean() {
        return cuentaLstBean;
    }

    public void setCuentaLstBean(CuentaLstBean cuentaLstBean) {
        this.cuentaLstBean = cuentaLstBean;
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
                if(this.getCuentaLstBean().getCuenta() != null){
                    this.setLstGastoGeneral(pagoGeneralRNLocal.findPagosXFechaProveedorYCuenta(this.getFechaIni(), this.getFechaFin(),this.getCuentaLstBean().getCuenta()));
                }else{
                    this.setLstGastoGeneral(pagoGeneralRNLocal.findPagosXFechaProveedor(this.getFechaIni(), this.getFechaFin()));
                }
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
    
    public void generarConsultaPagos() throws SQLException {

        try {
            
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:jdbc/PHumanidades");
            Connection conect = dataSource.getConnection();
            System.out.println("funcionando");
            
            try {
                
                HashMap parametros = new HashMap();
                String query="";
                if(this.getCuentaLstBean().getCuenta() != null){
                    query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                            + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                            + "e.NUMEROCOMPROBANTE,  e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                            + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                            + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                            + "JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                            + "AND NOT ((e.PROVEEDOR_ID IS NULL))) AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' )) "
                            + "AND (e.CUENTA_ID = %d )) ORDER BY e.FECHAREGISTRO DESC",new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                            new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()),this.getCuentaLstBean().getCuenta().getId());
                    System.out.println(query);
                    parametros.put("descripcion",this.getCuentaLstBean().getCuenta().toString());
                }else{
                    query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                            + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                            + "e.NUMEROCOMPROBANTE,  e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                            + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                            + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                            + "JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                            + "AND NOT ((e.PROVEEDOR_ID IS NULL))) AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' ))"
                            + ") ORDER BY e.FECHAREGISTRO DESC",new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                            new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()));
                }
                
                
                parametros.put("escudo",escudo1 );
                parametros.put("query", query);
                parametros.put("fecha_actual",new SimpleDateFormat("MMMM-yy").format(new Date()));
                
                System.out.println(escudo1);
//funcionando
                
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator+"egresosGenerales.jasper";
                System.out.println(reportPath);
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parametros, conect); //new JREmptyDataSource() si le pongo eso en vez de conect me devuelve null
                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                httpServletResponse.addHeader("Content-disposition", "filename=reporte.pdf");
                ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                servletOutputStream.flush();
                servletOutputStream.close();
                FacesContext.getCurrentInstance().responseComplete();
                
            } catch (Exception ex) {
                System.out.println(ex + "CAUSA: " + ex.getCause());
                ex.printStackTrace();
            }
            
        }//fin generar
 catch (NamingException ex) {
            Logger.getLogger(ConsultaPagosGeneralesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
