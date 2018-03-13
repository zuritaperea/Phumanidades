package Beans;

import DAO.TipoEgresoFacadeLocal;
import Entidades.Carreras.Cuenta;
import Entidades.Egresos.PagosDocente;
import Entidades.Egresos.RubroPresupuestario;
import Entidades.Egresos.TipoEgreso;
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
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.criteria.Predicate;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
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
    private Date feha_fin_real;
    private BigDecimal totalXGastoGeneral;

    @EJB
    private TipoEgresoFacadeLocal tipoEgresoFacadeLocal;

    private TipoEgreso tipoEgreso;
    private List<SelectItem> lstTipoEgreso;
    @Enumerated(EnumType.STRING)
    private RubroPresupuestario rubroPresupuestario;

    @PostConstruct
    private void init() {

        lstGastoGeneral = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        fechaIni = new Date();
        fechaFin = c.getTime();
        totalXGastoGeneral = new BigDecimal(0);
        cargarLstTipoEgresos();
        this.setTipoEgreso(new TipoEgreso());
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

    public TipoEgresoFacadeLocal getTipoEgresoFacadeLocal() {
        return tipoEgresoFacadeLocal;
    }

    public void setTipoEgresoFacadeLocal(TipoEgresoFacadeLocal tipoEgresoFacadeLocal) {
        this.tipoEgresoFacadeLocal = tipoEgresoFacadeLocal;
    }

    public TipoEgreso getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(TipoEgreso tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }

    public List<SelectItem> getLstTipoEgreso() {
        return lstTipoEgreso;
    }

    public void setLstTipoEgreso(List<SelectItem> lstTipoEgreso) {
        this.lstTipoEgreso = lstTipoEgreso;
    }

    public PagosDocenteRNLocal getPagoGeneralRNLocal() {
        return pagoGeneralRNLocal;
    }

    public void setPagoGeneralRNLocal(PagosDocenteRNLocal pagoGeneralRNLocal) {
        this.pagoGeneralRNLocal = pagoGeneralRNLocal;
    }

    public Date getFeha_fin_real() {
        return feha_fin_real;
    }

    public void setFeha_fin_real(Date feha_fin_real) {
        this.feha_fin_real = feha_fin_real;
    }

    public RubroPresupuestario getRubroPresupuestario() {
        return rubroPresupuestario;
    }

    public void setRubroPresupuestario(RubroPresupuestario rubroPresupuestario) {
        this.rubroPresupuestario = rubroPresupuestario;
    }

    /**
     * agrego un dia para que las busquedas sean de menor o igual
     *
     * @return fechaFinAumentada
     */
    public Date getFechaFin() {
//        if (fechaFin != null) {
//            Calendar c = Calendar.getInstance();
//            c.setTime(this.fechaFin);
//            c.add(Calendar.DATE, 1);
//            return c.getTime();
//        } else {
        return fechaFin;
//        }
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

    private void cargarLstTipoEgresos() {
        lstTipoEgreso = new ArrayList<SelectItem>();
        for (TipoEgreso t : tipoEgresoFacadeLocal.findAll()) {
            lstTipoEgreso.add(new SelectItem(t, t.getDescripcion()));
        }
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
          /*  if (fechaIni != null && fechaFin != null) {
             if(this.getCuentaLstBean().getCuenta() != null){
             this.setLstGastoGeneral(pagoGeneralRNLocal.findPagosXFechaProveedorYCuenta(this.getFechaIni(), this.getFechaFin(),this.getCuentaLstBean().getCuenta()));
             }else{
             this.setLstGastoGeneral(pagoGeneralRNLocal.findPagosXFechaProveedor(this.getFechaIni(), this.getFechaFin()));
             }
             for (PagosDocente gg : this.getLstGastoGeneral()) {
             totalXGastoGeneral = totalXGastoGeneral.add(gg.getMonto());
             }
             if (this.getLstGastoGeneral().isEmpty()) {
             fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
             FacesContext fc = FacesContext.getCurrentInstance();
             fc.addMessage(null, fm);
             }//fin if
             }*/
            this.setLstGastoGeneral(pagoGeneralRNLocal.findPagosByPredicates(fechaIni, fechaFin, this.getCuentaLstBean().getCuenta(), tipoEgreso));
            for (PagosDocente gg : this.getLstGastoGeneral()) {
                totalXGastoGeneral = totalXGastoGeneral.add(gg.getMonto());
            }

            if (this.getLstGastoGeneral().isEmpty()) {
                fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
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
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            Connection conect = dataSource.getConnection();
            System.out.println("funcionando");

            try {

                HashMap parametros = new HashMap();
                String query = "";
                if (this.getCuentaLstBean().getCuenta() != null) {
                    query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                            + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                            + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                            + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                            + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                            + "JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                            + "AND NOT ((e.PROVEEDOR_ID IS NULL))) AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' )) "
                            + "AND (e.CUENTA_ID = %d )) ORDER BY e.FECHACOMPROBANTE DESC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                            new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()), this.getCuentaLstBean().getCuenta().getId());
                    System.out.println(query);
                    parametros.put("descripcion", this.getCuentaLstBean().getCuenta().toString());
                } else {
                    query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                            + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                            + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                            + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                            + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                            + "JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                            + "AND NOT ((e.PROVEEDOR_ID IS NULL))) AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' ))"
                            + ") ORDER BY e.FECHACOMPROBANTE DESC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                            new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()));
                }

                parametros.put("escudo", escudo1);
                parametros.put("query", query);
                parametros.put("fecha_actual", new SimpleDateFormat("MMMM-yy").format(new Date()));

                System.out.println(escudo1);
//funcionando

                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator + "egresosGenerales.jasper";
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

    public void generarConsultaPagosExcel() throws SQLException {

        try {

            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            Connection conect = dataSource.getConnection();

            try {

                HashMap parametros = new HashMap();
                String query = "";
                if (this.getCuentaLstBean().getCuenta() != null) {
                    query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                            + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                            + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                            + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                            + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                            + "JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                            + "AND NOT ((e.PROVEEDOR_ID IS NULL))) AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' )) "
                            + "AND (e.CUENTA_ID = %d )) ORDER BY e.FECHACOMPROBANTE DESC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                            new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()), this.getCuentaLstBean().getCuenta().getId());
                    System.out.println(query);
                    parametros.put("descripcion", this.getCuentaLstBean().getCuenta().toString());
                } else {
                    query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                            + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                            + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                            + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                            + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                            + "JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                            + "AND NOT ((e.PROVEEDOR_ID IS NULL))) AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' ))"
                            + ") ORDER BY e.FECHACOMPROBANTE DESC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                            new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()));
                }

                parametros.put("escudo", escudo1);
                parametros.put("query", query);
                parametros.put("fecha_actual", new SimpleDateFormat("MMMM-yy").format(new Date()));

                System.out.println(escudo1);
//funcionando

                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator + "egresosGenerales.jasper";
                System.out.println(reportPath);
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parametros, conect); //new JREmptyDataSource() si le pongo eso en vez de conect me devuelve null
                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                httpServletResponse.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=egresos.xlsx");
                ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
                net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);

                exporter.exportReport();
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

    public void generarConsultaPagosBienes(String type) throws SQLException {

        try {

            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            Connection conect = dataSource.getConnection();

            try {

                HashMap parametros = new HashMap();
                String query = "";
                if (this.getCuentaLstBean().getCuenta() != null) {
                    if (this.rubroPresupuestario != null) {
                        String tipo = "";
                        if (this.rubroPresupuestario.getName().equals("Bienes de Consumo")) {
                            tipo = "BIENES_DE_CONSUMO";
                        }
                        if (this.rubroPresupuestario.getName().equals("Servicios no Personales")) {
                            tipo = "SERVICIOS_NO_PERSONALES";
                        }
                        if (this.rubroPresupuestario.getName().equals("Bienes de Capital")) {
                            tipo = "BIENES_DE_CAPITAL";
                        }
                        if (this.rubroPresupuestario.getName().equals("Transferencias")) {
                            tipo = "TRANSFERENCIAS";
                        }
                        query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                                + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                                + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                                + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                                + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                                + " LEFT JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                                + " AND (e.FECHACOMPROBANTE BETWEEN '%s' AND '%s' )) "
                                + "AND (e.CUENTA_ID = %d )) AND (e.RUBROPRESUPUESTARIO = '" + tipo + "')) ORDER BY e.FECHACOMPROBANTE ASC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                                new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()), this.getCuentaLstBean().getCuenta().getId());
                        System.out.println(query);
                        parametros.put("descripcion", this.getCuentaLstBean().getCuenta().toString());

                    } else {

                        query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                                + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                                + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                                + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                                + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                                + "LEFT JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE ((((e.BORRADO = false) AND (e.ANULADO = false)) "
                                + " AND (e.FECHACOMPROBANTE BETWEEN '%s' AND '%s' )) "
                                + "AND (e.CUENTA_ID = %d )) ORDER BY e.FECHACOMPROBANTE ASC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                                new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()), this.getCuentaLstBean().getCuenta().getId());
                        System.out.println(query);
                        parametros.put("descripcion", this.getCuentaLstBean().getCuenta().toString());

                    }

                } else {
                    if (this.rubroPresupuestario != null) {
                        String tipo = "";
                        if (this.rubroPresupuestario.getName().equals("Bienes de Consumo")) {
                            tipo = "BIENES_DE_CONSUMO";
                        }
                        if (this.rubroPresupuestario.getName().equals("Servicios no Personales")) {
                            tipo = "SERVICIOS_NO_PERSONALES";
                        }
                        if (this.rubroPresupuestario.getName().equals("Bienes de Capital")) {
                            tipo = "BIENES_DE_CAPITAL";
                        }
                        if (this.rubroPresupuestario.getName().equals("Transferencias")) {
                            tipo = "TRANSFERENCIAS";
                        }
                        query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                                + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                                + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                                + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                                + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                                + "LEFT JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                                + " AND (e.FECHACOMPROBANTE BETWEEN '%s' AND '%s' )) AND (e.RUBROPRESUPUESTARIO = '" + tipo + "'))"
                                + ") ORDER BY e.FECHACOMPROBANTE ASC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                                new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()));
                    } else {
                        query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                                + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                                + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                                + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                                + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                                + "LEFT JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE ((((e.BORRADO = false) AND (e.ANULADO = false)) "
                                + " AND (e.FECHACOMPROBANTE BETWEEN '%s' AND '%s' ))"
                                + ") ORDER BY e.FECHACOMPROBANTE ASC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                                new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()));
                    }
                    parametros.put("descripcion", "005-0025");
                }

                if (this.fechaFin != null && this.fechaIni != null) {
                    Calendar c = Calendar.getInstance();
                    this.feha_fin_real = this.fechaFin;
                    c.setTime(this.fechaFin);
                    c.add(Calendar.DATE, 1);  // number of days to add
                    this.fechaFin = c.getTime();  // fechaFin is now the new date

                    //List<Ingreso> findCobrosXFecha = ingresoCuotaRNLocal.findCobrosXFecha(this.fechaIni, this.fechaFin);
                    //this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(findCobrosXFecha);
                }
                parametros.put("fecha_inicio", this.fechaIni);
                parametros.put("fecha_fin", this.feha_fin_real);
                parametros.put("escudo", escudo1);
                parametros.put("query", query);
                parametros.put("fecha_actual", new SimpleDateFormat("MMMM-yy").format(new Date()));

                System.out.println(escudo1);
//funcionando

                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String reportPath;
                if (type.equals("pdf")) {
                    reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator + "egresosGenerales.jasper";
                } else {
                    reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator + "egresosGeneralesXLS.jasper";
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parametros, conect); //new JREmptyDataSource() si le pongo eso en vez de conect me devuelve null
                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
                if (type.equals("pdf")) {
                    httpServletResponse.addHeader("Content-disposition", "filename=reporte.pdf");
                    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                } else {
                    httpServletResponse.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    httpServletResponse.addHeader("Content-disposition", "attachment; filename=egresos.xlsx");
                    net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);

                    exporter.exportReport();
                }

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

    public void generarConsultaPagosBienesFechaRegistro(String type) throws SQLException {

        try {

            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            Connection conect = dataSource.getConnection();

            try {

                HashMap parametros = new HashMap();
                String query = "";
                if (this.getCuentaLstBean().getCuenta() != null) {
                    if (this.rubroPresupuestario != null) {
                        String tipo = "";
                        if (this.rubroPresupuestario.getName().equals("Bienes de Consumo")) {
                            tipo = "BIENES_DE_CONSUMO";
                        }
                        if (this.rubroPresupuestario.getName().equals("Servicios no Personales")) {
                            tipo = "SERVICIOS_NO_PERSONALES";
                        }
                        if (this.rubroPresupuestario.getName().equals("Bienes de Capital")) {
                            tipo = "BIENES_DE_CAPITAL";
                        }
                        if (this.rubroPresupuestario.getName().equals("Transferencias")) {
                            tipo = "TRANSFERENCIAS";
                        }
                        query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                                + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                                + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                                + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                                + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                                + " LEFT JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                                + " AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' )) "
                                + "AND (e.CUENTA_ID = %d )) AND (e.RUBROPRESUPUESTARIO = '" + tipo + "')) ORDER BY e.NUMEROORDENPAGO, e.FECHAREGISTRO ASC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                                new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()), this.getCuentaLstBean().getCuenta().getId());
                        System.out.println(query);
                        parametros.put("descripcion", this.getCuentaLstBean().getCuenta().toString());

                    } else {

                        query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                                + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                                + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                                + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                                + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                                + "LEFT JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE ((((e.BORRADO = false) AND (e.ANULADO = false)) "
                                + " AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' )) "
                                + "AND (e.CUENTA_ID = %d )) ORDER BY e.NUMEROORDENPAGO, e.FECHAREGISTRO ASC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                                new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()), this.getCuentaLstBean().getCuenta().getId());
                        System.out.println(query);
                        parametros.put("descripcion", this.getCuentaLstBean().getCuenta().toString());

                    }

                } else {
                    if (this.rubroPresupuestario != null) {
                        String tipo = "";
                        if (this.rubroPresupuestario.getName().equals("Bienes de Consumo")) {
                            tipo = "BIENES_DE_CONSUMO";
                        }
                        if (this.rubroPresupuestario.getName().equals("Servicios no Personales")) {
                            tipo = "SERVICIOS_NO_PERSONALES";
                        }
                        if (this.rubroPresupuestario.getName().equals("Bienes de Capital")) {
                            tipo = "BIENES_DE_CAPITAL";
                        }
                        if (this.rubroPresupuestario.getName().equals("Transferencias")) {
                            tipo = "TRANSFERENCIAS";
                        }
                        query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                                + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                                + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                                + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                                + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                                + "LEFT JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE (((((e.BORRADO = false) AND (e.ANULADO = false)) "
                                + " AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' )) AND (e.RUBROPRESUPUESTARIO = '" + tipo + "'))"
                                + ") ORDER BY e.NUMEROORDENPAGO, e.FECHAREGISTRO ASC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                                new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()));
                    } else {
                        query = String.format("SELECT  e.ANULADO, e.BORRADO, e.CONCEPTO, e.FECHACOMPROBANTE, "
                                + "e.FORMAPAGO, e.IMPUESTOGANANCIA, e.IVA, e.MONTO, e.MONTOCONDESCUENTOS, e.NUMEROCHEQUE, "
                                + "e.NUMEROCOMPROBANTE, e.IMPORTECOMPROBANTE, e.NUMEROORDENPAGO, e.RETENCIONIB, e.RUBROPRESUPUESTARIO, e.SUSS, "
                                + "e.TIPOCOMPROBANTE, e.CARRERA_ID, e.CUENTA_ID, e.DOCENTE_ID, e.PROVEEDOR_ID, d.apellido, "
                                + "d.nombre, p.razonsocial FROM egresos e LEFT OUTER JOIN docente d ON  e.DOCENTE_ID = d.ID "
                                + "LEFT JOIN proveedor p ON e.PROVEEDOR_ID = p.ID WHERE ((((e.BORRADO = false) AND (e.ANULADO = false)) "
                                + " AND (e.FECHAREGISTRO BETWEEN '%s' AND '%s' ))"
                                + ") ORDER BY e.NUMEROORDENPAGO, e.FECHAREGISTRO ASC", new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaIni()),
                                new SimpleDateFormat("yyyy-MM-dd").format(this.getFechaFin()));
                    }
                    parametros.put("descripcion", "005-0025");
                }

                if (this.fechaFin != null && this.fechaIni != null) {
                    Calendar c = Calendar.getInstance();
                    this.feha_fin_real = this.fechaFin;
                    c.setTime(this.fechaFin);
                    c.add(Calendar.DATE, 1);  // number of days to add
                    this.fechaFin = c.getTime();  // fechaFin is now the new date

                    //List<Ingreso> findCobrosXFecha = ingresoCuotaRNLocal.findCobrosXFecha(this.fechaIni, this.fechaFin);
                    //this.getCobroCuotasAlumnosLstBean().setLstCobroCuotas(findCobrosXFecha);
                }
                parametros.put("fecha_inicio", this.fechaIni);
                parametros.put("fecha_fin", this.feha_fin_real);
                parametros.put("escudo", escudo1);
                parametros.put("query", query);
                parametros.put("fecha_actual", new SimpleDateFormat("MMMM-yy").format(new Date()));

                System.out.println(escudo1);
//funcionando

                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String reportPath;
                if (type.equals("pdf")) {
                    reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator + "egresosGenerales.jasper";
                } else {
                    reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator + "egresosGeneralesXLS.jasper";
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parametros, conect); //new JREmptyDataSource() si le pongo eso en vez de conect me devuelve null
                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
                if (type.equals("pdf")) {
                    httpServletResponse.addHeader("Content-disposition", "filename=reporte.pdf");
                    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                } else {
                    httpServletResponse.addHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    httpServletResponse.addHeader("Content-disposition", "attachment; filename=egresos.xlsx");
                    net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter exporter = new net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);

                    exporter.exportReport();
                }

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
