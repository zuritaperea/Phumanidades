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
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

    @EJB
    private IngresoRNLocal cobroGeneralRNLocal;

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

}
