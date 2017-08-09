package Beans;

import Entidades.Ingresos.Ingreso;
import Entidades.Ingresos.TipoIngreso;
import RN.IngresoRNLocal;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 *
 */
@ManagedBean(name = "consultaCobrosGeneralesBean")
@ViewScoped
public class ConsultaCobrosGeneralesBean implements Serializable {

    private final String escudo1 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "Imagenes" + File.separator + "escudo.jpg";
    private final String escudo2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "Imagenes" + File.separator + "logo2.jpg";
    @EJB
    private IngresoRNLocal cobroGeneralRNLocal;
    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;

    private List<Ingreso> lstCobroGeneral;
    private Date fechaFin;
    private Date fechaIni;
    private BigDecimal totalXCobroGeneral;
    private TipoIngreso tipoIngreso;
    private DataTable tablaIngresos;

    @PostConstruct
    private void init() {

        lstCobroGeneral = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        fechaIni = new Date();
        fechaFin = c.getTime();
        totalXCobroGeneral = new BigDecimal(0);
    }//fin init

    public DataTable getTablaIngresos() {
        return tablaIngresos;
    }

    public void setTablaIngresos(DataTable tablaIngresos) {
        this.tablaIngresos = tablaIngresos;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public CohorteLstBean getCohorteLstBean() {
        return cohorteLstBean;
    }

    public void setCohorteLstBean(CohorteLstBean cohorteLstBean) {
        this.cohorteLstBean = cohorteLstBean;
    }

    public IngresoRNLocal getCobroGeneralRNLocal() {
        return cobroGeneralRNLocal;
    }

    public void setCobroGeneralRNLocal(IngresoRNLocal cobroGeneralRNLocal) {
        this.cobroGeneralRNLocal = cobroGeneralRNLocal;
    }

    public TipoIngreso getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(TipoIngreso tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
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

    public Date getNow() {
        return new Date();
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Ingreso> getLstCobroGeneral() {
        return lstCobroGeneral;
    }

    public void setLstCobroGeneral(List<Ingreso> lstCobroGeneral) {
        this.lstCobroGeneral = lstCobroGeneral;
    }

    public BigDecimal getTotalXCobroGeneral() {
        return totalXCobroGeneral;
    }

    public void setTotalXCobroGeneral(BigDecimal totalXCobroGeneral) {
        this.totalXCobroGeneral = totalXCobroGeneral;
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.newPage();
        pdf.setPageSize(PageSize.A4);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "Imagenes" + File.separator + "LogoFacultadHumanidades70x70.png";
        //SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        // pdf.add(new Phrase("Fecha: " + formato.format(new Date())));
        pdf.add(Image.getInstance(logo));
    }

    /**
     * Buscar historial de operaciones entre dos fechas
     */
    public void buscarCobrosGeneralesFechas() {
        //System.out.println("entro buscarFechaCohortes");
        FacesMessage fm;
        try {
            //verifico que no sean nulas las fechas
            totalXCobroGeneral = new BigDecimal(BigInteger.ZERO);
            //System.out.println("entro if buscarFechaCarrera" + this.getCohorteLstBean().getCohorteSelect());
            //aumento un dia a la fecha fin para que la busqueda sea menor o igual
            if (fechaIni != null && fechaFin != null) {
                if (tipoIngreso == null) {
                    this.setLstCobroGeneral(cobroGeneralRNLocal.findCobrosGeneralXFecha(this.getFechaIni(), this.getFechaFin()));
                } else {
                    this.setLstCobroGeneral(cobroGeneralRNLocal.findCobrosGeneralXFechaTipo(this.getFechaIni(), this.getFechaFin(), tipoIngreso));
                }

//Sumamos el total de los cobros por cohorte
                for (Ingreso cg : this.getLstCobroGeneral()) {
                    totalXCobroGeneral = totalXCobroGeneral.add(cg.getImporte());
                }

                if (this.getLstCobroGeneral().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if

            }
        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:cbConsultarCobros", fm);
        }//fin catch
    }

    public void onTabChange(TabChangeEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Seleccion: " + event.getTab().getTitle()));
        //     this.setLstPagosDocente(new ArrayList<PagosDocente>());
        RequestContext.getCurrentInstance().update("frmPri:dtCobrosGenerales");
    }

    public void preProcessPDF2(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "Imagenes" + File.separator + "LogoFacultadHumanidades70x70.png";
        //SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        // pdf.add(new Phrase("Fecha: " + formato.format(new Date())));

        PdfPTable pdfTable = new PdfPTable(6);
        PdfPCell pdfcell = new PdfPCell();
        pdfcell.setColspan(1);
        pdfcell.setImage(Image.getInstance(logo));
        pdfTable.addCell(pdfcell);
        final Phrase phrase = new Phrase("FACULTAD DE HUMANIDADES \n última cuota alumnos \n "
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        phrase.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN));
        PdfPCell pdfcell2 = new PdfPCell();
        pdfcell2.setColspan(5);
        pdfcell2.setHorizontalAlignment(1);
        pdfcell2.setVerticalAlignment(1);
        pdfcell2.setPhrase(phrase);
        pdfTable.addCell(pdfcell2);

        pdf.add(pdfTable);
    }

    public void generar() throws SQLException {

        try {

            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");

            Connection conect = dataSource.getConnection();
            String path;
            System.out.println("funcionando");

            try {

                HashMap parametros = new HashMap();

                parametros.put("escudo1", escudo1);
                parametros.put("escudo2", escudo2);
                parametros.put("cohorte", this.getCohorteLstBean().getCohorteSelect().getDescripcion());
                parametros.put("cohorteID", this.getCohorteLstBean().getCohorteSelect().getId());
                path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "reporte" + File.separator + "alumnoPorCohorte.jasper";
//funcionando

                JasperPrint jasperPrint = JasperFillManager.fillReport(path, parametros, conect); //new JREmptyDataSource() si le pongo eso en vez de conect me devuelve null
                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                httpServletResponse.addHeader("Content-disposition", "filename=reporte.pdf");
                ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                servletOutputStream.flush();
                servletOutputStream.close();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Exception ex) {
                System.out.println(ex + "CAUSA: " + ex.getCause());

            }

        }//fin generar
        catch (NamingException ex) {
            Logger.getLogger(ConsultaCobrosGeneralesBean.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void generarCobrosGenerales() throws SQLException {

        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            Connection conect = dataSource.getConnection();
            String path;
            System.out.println("funcionando");

            try {

                HashMap parametros = new HashMap();

                parametros.put("escudo1", escudo1);
                parametros.put("escudo2", escudo2);
                parametros.put("fecha_inicio", this.getFechaIni());
                parametros.put("fecha_fin", this.getFechaFin());
                Long id = null
                if (this.getTipoIngreso() != null && this.getTipoIngreso().getId() != null) {
                    id = this.getTipoIngreso().getId();
                }
                parametros.put("tipo_ingreso", id);
                path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("")
                        + File.separator + "reporte" + File.separator + "cobrosGenerales.jasper";
//funcionando


                JasperPrint jasperPrint = JasperFillManager.fillReport(path, parametros, conect); //new JREmptyDataSource() si le pongo eso en vez de conect me devuelve null
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
            Logger.getLogger(ConsultaCobrosGeneralesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
