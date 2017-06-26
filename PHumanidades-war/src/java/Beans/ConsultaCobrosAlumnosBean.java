package Beans;

import Entidades.Carreras.Cohorte;
import Entidades.Carreras.InscripcionAlumnos;
import Entidades.Ingresos.Ingreso;
import Entidades.Persona.Alumno;
import RN.CohorteRNLocal;
import RN.IngresoRNLocal;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 *
 */
@ManagedBean(name = "consultaCobrosAlumnosBean")
@ViewScoped
public class ConsultaCobrosAlumnosBean implements Serializable {

    @EJB
    private IngresoRNLocal ingresoCuotaRNLocal;

    @EJB
    private CohorteRNLocal cohorteRNLocal;

    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;

    private List<Ingreso> lstIngresos;
    private List<InscripcionAlumnos> lstInscripcionAlumnos;
    private Date fechaFin;
    private Date fechaIni;
    private BigDecimal totalXCohorte;
    private DataTable tablaAlumnos;
    private List<Ingreso> listaUltimaCuota;

    @PostConstruct
    private void init() {
        listaUltimaCuota = new ArrayList<>();
        lstIngresos = new ArrayList<>();
        lstInscripcionAlumnos = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        fechaIni = new Date();
        fechaFin = c.getTime();
        totalXCohorte = new BigDecimal(0);
    }//fin init

    public IngresoRNLocal getIngresoCuotaRNLocal() {
        return ingresoCuotaRNLocal;
    }

    public void setIngresoCuotaRNLocal(IngresoRNLocal ingresoCuotaRNLocal) {
        this.ingresoCuotaRNLocal = ingresoCuotaRNLocal;
    }

    public CohorteRNLocal getCohorteRNLocal() {
        return cohorteRNLocal;
    }

    public void setCohorteRNLocal(CohorteRNLocal cohorteRNLocal) {
        this.cohorteRNLocal = cohorteRNLocal;
    }

    public List<Ingreso> getListaUltimaCuota() {
        return listaUltimaCuota;
    }

    public void setListaUltimaCuota(List<Ingreso> listaUltimaCuota) {
        this.listaUltimaCuota = listaUltimaCuota;
    }

    public DataTable getTablaAlumnos() {
        return tablaAlumnos;
    }

    public void setTablaAlumnos(DataTable tablaAlumnos) {
        this.tablaAlumnos = tablaAlumnos;
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

    public CohorteLstBean getCohorteLstBean() {
        return cohorteLstBean;
    }

    public void setCohorteLstBean(CohorteLstBean cohorteLstBean) {
        this.cohorteLstBean = cohorteLstBean;
    }

    public List<Ingreso> getLstIngresos() {
        return lstIngresos;
    }

    public void setLstIngresos(List<Ingreso> lstIngresos) {
        this.lstIngresos = lstIngresos;
    }

    public List<InscripcionAlumnos> getLstInscripcionAlumnos() {
        return lstInscripcionAlumnos;
    }

    public void setLstInscripcionAlumnos(List<InscripcionAlumnos> lstInscripcionAlumnos) {
        this.lstInscripcionAlumnos = lstInscripcionAlumnos;
    }

    public BigDecimal getTotalXCohorte() {
        return totalXCohorte;
    }

    public void setTotalXCohorte(BigDecimal totalXCohorte) {
        this.totalXCohorte = totalXCohorte;
    }

    /**
     * Buscar historial de operaciones entre dos fechas
     */
    public void prueba() {
    }

    public Date getNow() {
        return new Date();
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

    public void buscarFechaCohortes() {
        //System.out.println("entro buscarFechaCohortes");

        FacesMessage fm;
        try {
            //verifico que no sean nulas las fechas
            totalXCohorte = new BigDecimal(BigInteger.ZERO);
            //System.out.println("entro if buscarFechaCarrera" + this.getCohorteLstBean().getCohorteSelect());
            //aumento un dia a la fecha fin para que la busqueda sea menor o igual
            if (fechaIni != null && fechaFin != null) {
                if (this.getCohorteLstBean().getCohorteSelect() != null) {
                    this.setLstIngresos(ingresoCuotaRNLocal.findByFechaCohorte(this.getFechaIni(), this.getFechaFin(), this.getCohorteLstBean().getCohorteSelect()));
                } else {
                    this.setLstIngresos(ingresoCuotaRNLocal.findByFecha(fechaIni, fechaFin));
                }

//Sumamos el total de los cobros por cohorte
                for (Ingreso ic : this.getLstIngresos()) {
                    totalXCohorte = totalXCohorte.add(ic.getImporte());
                }

                if (this.getLstIngresos().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if
            }
        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:cbConsultar", fm);
        }//fin catch
    }

    public void buscarAlumnosCohorte() {
        //System.out.println("entro buscarFechaCohortes");
        FacesMessage fm;
        try {
            //verifico que no sean nulas las fechas

            //System.out.println("entro if buscarFechaCarrera" + this.getCohorteLstBean().getCohorteSelect());
            //aumento un dia a la fecha fin para que la busqueda sea menor o igual
            this.setLstInscripcionAlumnos(cohorteRNLocal.findAlumnoCohorte(this.getCohorteLstBean().getCohorteSelect()));
//Sumamos el total de los cobros por cohorte
//                for (Alumno al : this.getLstAlumnos()) {
//                    totalXCohorte = totalXCohorte.add(ic.getImporte());
//                }

            if (this.getLstInscripcionAlumnos().isEmpty()) {
                fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
            }//fin if

        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:cbConsultarAlumnosCohorte", fm);
        }//fin catch
    }

    public void buscarFecha() {
        //System.out.println("entro buscarFechaCohortes");
        FacesMessage fm;
        try {
            //verifico que no sean nulas las fechas
            totalXCohorte = new BigDecimal(BigInteger.ZERO);
            //System.out.println("entro if buscarFechaCarrera" + this.getCohorteLstBean().getCohorteSelect());
            //aumento un dia a la fecha fin para que la busqueda sea menor o igual
            if (fechaIni != null && fechaFin != null) {
                this.setLstIngresos(ingresoCuotaRNLocal.findByFecha(fechaIni, fechaFin));

//Sumamos el total de los cobros por cohorte
                for (Ingreso ic : this.getLstIngresos()) {
                    totalXCohorte = totalXCohorte.add(ic.getImporte());
                }

                if (this.getLstIngresos().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if
            }
        } catch (Exception ex) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frmPri:cbConsultar", fm);
        }//fin catch
    }

    public void onTabChange(TabChangeEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Seleccion: " + event.getTab().getTitle()));
        //     this.setLstPagosDocente(new ArrayList<PagosDocente>());
        RequestContext.getCurrentInstance().update("frmPri:dtCobrosAlumnos");
    }

    public void consultarUltimaCuotaPagada() {
        List<Object[]> consultaUltimaCuotaAlumno = ingresoCuotaRNLocal.consultaUltimaCuotaAlumno();
        listaUltimaCuota = new ArrayList<>();
        for (Object[] row : consultaUltimaCuotaAlumno) {
            Ingreso i = new Ingreso();
            i.setAlumno((Alumno) row[0]);
            i.setCohorte((Cohorte) row[1]);
            i.setFechaPago((Date) row[2]);
            i.setCuota((int) row[3]);
            listaUltimaCuota.add(i);
        }
    }
}
