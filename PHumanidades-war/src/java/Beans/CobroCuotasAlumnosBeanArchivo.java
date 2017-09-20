/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Cuenta;
import Entidades.Carreras.InscripcionAlumnos;
import Entidades.Ingresos.Ingreso;
import Entidades.Ingresos.TipoIngreso;
import RN.IngresoRNLocal;
import RN.InscripcionAlumnosRNLocal;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ruben
 */
@ManagedBean
@SessionScoped
public class CobroCuotasAlumnosBeanArchivo {
    
    private UploadedFile file;
    private String fileContent;
    private List<DataFile> dataList;
    private DataTable tablaDatos;
    @EJB
    private InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal;
    private List<SelectItem> lstSIIA;
    @EJB
    private IngresoRNLocal ingresoRNLocal;
    
    /**
     * Creates a new instance of CobroCuotasAlumnosBeanArchivo
     */
    public CobroCuotasAlumnosBeanArchivo() {
    }
    
    @PostConstruct
    private void init(){
        dataList = new ArrayList<DataFile>();
        this.setLstSIIA(new ArrayList<SelectItem>());
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public IngresoRNLocal getIngresoRNLocal() {
        return ingresoRNLocal;
    }

    public void setIngresoRNLocal(IngresoRNLocal ingresoRNLocal) {
        this.ingresoRNLocal = ingresoRNLocal;
    }

    public InscripcionAlumnosRNLocal getInscripcionAlumnosRNLocal() {
        return inscripcionAlumnosRNLocal;
    }

    public void setInscripcionAlumnosRNLocal(InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal) {
        this.inscripcionAlumnosRNLocal = inscripcionAlumnosRNLocal;
    }

    public List<SelectItem> getLstSIIA() {
        return lstSIIA;
    }

    public void setLstSIIA(List<SelectItem> lstSIIA) {
        this.lstSIIA = lstSIIA;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public List<DataFile> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataFile> dataList) {
        this.dataList = dataList;
    }

    public DataTable getTablaDatos() {
        return tablaDatos;
    }

    public void setTablaDatos(DataTable tablaDatos) {
        this.tablaDatos = tablaDatos;
    }

    
    public void validateFile(FacesContext ctx,
                         UIComponent comp,
                         Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        UploadedFile localfile = (UploadedFile)value;
        System.out.println(localfile.getContentType());
        if (localfile.getSize() > 1024) {
            msgs.add(new FacesMessage("Archivo muy grande"));
        }
        if (!"application/octet-stream".equals(localfile.getContentType())) {
            msgs.add(new FacesMessage("Tipo de Archivo no soportado"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }
    
    public String upload() {
        try {
            dataList = new ArrayList<DataFile>();
            Scanner sc = new Scanner(file.getInputstream());
            while(sc.hasNextLine()){
                fileContent = sc.nextLine().trim();
                System.out.println(fileContent);
                System.out.println(fileContent.length());
                if(fileContent.length()==37){
                    System.out.println(fileContent);
                    DataFile df = new DataFile();
                    df.setDni(fileContent.substring(28, 36));
                    df.setMonto(new BigDecimal(fileContent.substring(17, 21)));
                    df.setCodigo(fileContent.substring(23, 37));
                    dataList.add(df);
                }
            }
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
    }
    
    public List<SelectItem> obtenerDatos(String dni){
        List<InscripcionAlumnos> lista = new ArrayList<>();
        this.setLstSIIA(new ArrayList<SelectItem>());
        try {
            lista = inscripcionAlumnosRNLocal.inscripcionFindDni(dni);
        } catch (Exception ex) {
            Logger.getLogger(CobroCuotasAlumnosBeanArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(InscripcionAlumnos ia: lista){
            this.getLstSIIA().add(new SelectItem(ia,ia.toString()));
        }
        return this.getLstSIIA();
    }
    
    public String guardarRegistro(DataFile d){
        try {
            Ingreso i = new Ingreso();
            i.setAlumno(d.getIa().getAlumno());
            i.setCohorte(d.getIa().getCohorte());
            i.setCuota(Integer.valueOf(d.getCuota()));
            i.setImporte(d.getMonto());
            i.setTipoIngreso(d.getTipoIngreso());
            i.setNumeroRecibo(Integer.valueOf(d.getNumRecibo()));
            i.setFechaPago(new Date()); 
            i.setCuenta(d.getIa().getCohorte().getCarrera().getCuenta());
            i.setAnulado(Boolean.FALSE);
            i.setBorrado(Boolean.FALSE);
            this.getIngresoRNLocal().create(i);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println(ex.getMessage());
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(ex.getLocalizedMessage()));
            return null;
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Guardado correctamente"));
        dataList.remove(d);
        RequestContext.getCurrentInstance().update("frmPri:form:dtDatosArchivo");
        return null;
    }
    
    public String generarCobros(){
        System.out.println("funcionando....asd");
        System.out.println(dataList.isEmpty());
        if(!dataList.isEmpty()){
            for(DataFile d: dataList){
                System.out.println(d);
                Ingreso i = new Ingreso();
                i.setAlumno(d.getIa().getAlumno());
                i.setCohorte(d.getIa().getCohorte());
                i.setCuota(Integer.valueOf(d.getCuota()));
                i.setNumeroRecibo(Integer.valueOf(d.getNumRecibo()));
                i.setFechaPago(new Date()); 
                i.setCuenta(d.getIa().getCohorte().getCarrera().getCuenta());
                i.setAnulado(Boolean.FALSE);
                i.setBorrado(Boolean.FALSE);
                try {
                    this.getIngresoRNLocal().create(i);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR.toString(), ex.getMessage()));
                    return null;
                }
            }
        }else{
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage( "primero debe cargar el archivo"));
            return null;
        }
        return "CobrosAlumnos.xhtml";//retornar a la vista listado de ingresos
    }
    
   
    public class DataFile{
        
        String dni;
        String cuota;
        String codigo;
        String numRecibo;
        InscripcionAlumnos ia;
        Cuenta cuenta;
        BigDecimal monto;
        TipoIngreso tipoIngreso;

        public DataFile(String dni, String cuota, String codigo) {
            this.dni = dni;
            this.cuota = cuota;
            this.codigo = codigo;
        }

        public DataFile() {
        }

        public InscripcionAlumnos getIa() {
            return ia;
        }

        public void setIa(InscripcionAlumnos ia) {
            this.ia = ia;
        }

        public Cuenta getCuenta() {
            return cuenta;
        }

        public void setCuenta(Cuenta cuenta) {
            this.cuenta = cuenta;
        }

        public String getDni() {
            return dni;
        }

        public void setDni(String dni) {
            this.dni = dni;
        }

        public String getCuota() {
            return cuota;
        }

        public void setCuota(String cuota) {
            this.cuota = cuota;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getNumRecibo() {
            return numRecibo;
        }

        public void setNumRecibo(String numRecibo) {
            this.numRecibo = numRecibo;
        }

        public BigDecimal getMonto() {
            return monto;
        }

        public void setMonto(BigDecimal monto) {
            this.monto = monto;
        }

        public TipoIngreso getTipoIngreso() {
            return tipoIngreso;
        }

        public void setTipoIngreso(TipoIngreso tipoIngreso) {
            this.tipoIngreso = tipoIngreso;
        }
        
        

        @Override
        public String toString() {
            return "DataFile{" + "dni=" + dni + ", cuota=" + cuota + ", codigo=" + codigo + ", numRecibo=" + numRecibo +", ia=" + ia+  '}';
        }
        
        
    }
}
