/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.InformePagoAlumnoFacade;
import Entidades.Carreras.Cohorte;
import Entidades.Ingresos.EstadoComprobanteAlumno;
import Entidades.Ingresos.InformePagoAlumno;
import Entidades.Ingresos.Ingreso;
import Entidades.Persona.Alumno;
import RN.AlumnoRNLocal;
import RN.InscripcionAlumnosRNLocal;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author hugo
 */
@ManagedBean
@SessionScoped
public class InformePagoAlumnoBean implements Serializable {

    /**
     * Creates a new instance of InformePagoAlumnoBean
     */
    @EJB
    private InformePagoAlumnoFacade informePagoAlumnoFacade;
    private List<InformePagoAlumno> items = null;
    private InformePagoAlumno selected;
    private List<Alumno> lstAlumno;
    private Alumno alumno;
    private String textoBusqueda;
    private String documento;
    @EJB
    private AlumnoRNLocal alumnoRNLocal;
    @EJB
    private InscripcionAlumnosRNLocal inscripcionAlumnoRNLocal;

    private List<Cohorte> lstCohorteAlumnoPago;
    @Enumerated(EnumType.STRING)
    private EstadoComprobanteAlumno estadoComprobanteAlumno;
    private List<SelectItem> listEstadoComprobanteAlumno;
    //los de abajo son mara manejar los archivos del comprobante
    private UploadedFile archivo;
    private StreamedContent file;
    private StreamedContent vistaPrevia;

    public InformePagoAlumnoBean() {
        cargarLstEstadosComprobante();
    }

    public InformePagoAlumnoFacade getInformePagoAlumnoFacade() {
        return informePagoAlumnoFacade;
    }

    public void setInformePagoAlumnoFacade(InformePagoAlumnoFacade informePagoAlumnoFacade) {
        this.informePagoAlumnoFacade = informePagoAlumnoFacade;
    }

    public List<InformePagoAlumno> getItems() {
        return items;
    }

    public void setItems(List<InformePagoAlumno> items) {
        this.items = items;
    }

    public InformePagoAlumno getSelected() {
        return selected;
    }

    public void setSelected(InformePagoAlumno selected) {
        this.selected = selected;
    }

    public List<Alumno> getLstAlumno() {
        return lstAlumno;
    }

    public void setLstAlumno(List<Alumno> lstAlumno) {
        this.lstAlumno = lstAlumno;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public AlumnoRNLocal getAlumnoRNLocal() {
        return alumnoRNLocal;
    }

    public void setAlumnoRNLocal(AlumnoRNLocal alumnoRNLocal) {
        this.alumnoRNLocal = alumnoRNLocal;
    }

    public InscripcionAlumnosRNLocal getInscripcionAlumnoRNLocal() {
        return inscripcionAlumnoRNLocal;
    }

    public void setInscripcionAlumnoRNLocal(InscripcionAlumnosRNLocal inscripcionAlumnoRNLocal) {
        this.inscripcionAlumnoRNLocal = inscripcionAlumnoRNLocal;
    }

    public List<Cohorte> getLstCohorteAlumnoPago() {
        return lstCohorteAlumnoPago;
    }

    public void setLstCohorteAlumnoPago(List<Cohorte> lstCohorteAlumnoPago) {
        this.lstCohorteAlumnoPago = lstCohorteAlumnoPago;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public EstadoComprobanteAlumno getEstadoComprobanteAlumno() {
        return estadoComprobanteAlumno;
    }

    public void setEstadoComprobanteAlumno(EstadoComprobanteAlumno estadoComprobanteAlumno) {
        this.estadoComprobanteAlumno = estadoComprobanteAlumno;
    }

    public List<SelectItem> getListEstadoComprobanteAlumno() {
        return listEstadoComprobanteAlumno;
    }

    public void setListEstadoComprobanteAlumno(List<SelectItem> listEstadoComprobanteAlumno) {
        this.listEstadoComprobanteAlumno = listEstadoComprobanteAlumno;
    }

    public StreamedContent getVistaPrevia() {
        return vistaPrevia;
    }

    public void setVistaPrevia(StreamedContent vistaPrevia) {
        this.vistaPrevia = vistaPrevia;
    }

    public void abrirDlgFindAlumnoConsulta() {
        // btnSelect = (CommandButton) e.getSource();
        this.setItems(new ArrayList<InformePagoAlumno>());
        this.setLstAlumno(new ArrayList<Alumno>());
        //agregados para probar
        this.setAlumno(new Alumno());
        //this.setTextoBusqueda("");
        //RequestContext.getCurrentInstance().execute("PF('dlgFindAlumnoPago').show();");
        RequestContext.getCurrentInstance().execute("PF('dialogBuscarAlumno').show();");
    }

    public void buscarAlumnoDni() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        System.out.println("entroo buscar dni consulta: " + this.getTextoBusqueda());
        try {
            if (this.getTextoBusqueda() == null) {
                System.out.println("cadena vacia" + this.getTextoBusqueda());
                sMensaje = "Ingrese un numero de Documento ";
                severity = FacesMessage.SEVERITY_INFO;
            } else {
                System.out.println("cpor el else " + textoBusqueda);
                this.findAlumnoDniPago(this.getTextoBusqueda());
            }

        } catch (Exception e) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + e.getMessage();
            //this.getMensajeBean().setMensaje("Error: " + e.getMessage());
            // RequestContext.getCurrentInstance().update("dMensaje");
            // RequestContext.getCurrentInstance().execute("dlgMensaje.show()");
        }
    }

    public void findAlumnoDniPago(String dni) throws Exception {
        System.out.println("entroo FIND ALUMNO DNI PAGO");
        this.setLstAlumno(new ArrayList<Alumno>());
        this.lstAlumno.add(alumnoRNLocal.findByAlumnoDni(dni));

    }

    public void devolverAlumnoDialog() {
        obtenerCohortesAlumnosPago();
        RequestContext.getCurrentInstance().update("frmPri:pnAlumnoPago");//outPutText Consulta Publica Alumnos

    }

    public void obtenerCohortesAlumnosPago() {
        try {
            //para obtener las cohorte del alumno seleccionado primero tengo que buscar las cohortes en las que se inncribi
            //el alumno y en las que esta activo solamente
            //System.out.println(this.inscripcionAlumnosRNLocal.alumnoFindCohortes(this.alumnoLstBean.getAlumnoSelect()));
            System.out.println("Imprimo alumno seleccionado: " + this.getAlumno());
            this.setLstCohorteAlumnoPago(this.inscripcionAlumnoRNLocal.alumnoFindCohortes(this.getAlumno()));
            System.out.println("Imprimo lista de cohortes: " + this.getLstCohorteAlumnoPago());
            RequestContext.getCurrentInstance().update("frmPri:pnCohortesPagoAlumno");
            //RequestContext.getCurrentInstance().update("frmPri:dtCohortesAlumno");
        } catch (Exception ex) {
            Logger.getLogger(AlumnoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getComprobantesAlumno(Alumno alumno, Cohorte cohorte) throws Exception {
        FacesMessage fm;
        if (cohorte != null) {
            System.out.println("alumno cohorte: " + alumno);

            try {
                System.out.println("entro a setearlistade comprobantes");
                this.setItems(informePagoAlumnoFacade.findPagosAlumnoCohorte(alumno, cohorte));
                System.out.println(this.getItems());
                if (this.getItems().isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron registros", null);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }//fin if

            } catch (Exception ex) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + ex.getMessage(), null);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Error", fm);
            }//fin catch
            //RequestContext.getCurrentInstance().update("dtListaCoprobantesAlumnos");
        }
    }

    public void cargarLstEstadosComprobante() {
        listEstadoComprobanteAlumno = new ArrayList<SelectItem>();
        for (EstadoComprobanteAlumno estadoComprobanteAlumno : EstadoComprobanteAlumno.values()) {
            listEstadoComprobanteAlumno.add(new SelectItem(estadoComprobanteAlumno, estadoComprobanteAlumno.toString()));
        }
    }

    public void modificar() {
        System.out.println("entro modificar coso: " + selected);
        System.out.println("entro modificar coso ESTADo:  " + selected.getEstadoComprobanteAlumno().name());
        try {
            System.out.println("entro modificar coso: " + selected);
            System.out.println("entro modificar coso ESTADo:  " + selected.getEstadoComprobanteAlumno().name());
            informePagoAlumnoFacade.edit(selected);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacion Realizada", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al realizar operacion", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }

    }

    //PARA MANEJAR ARCHIVO ADJUNTO
    public StreamedContent descargarArchivo(InformePagoAlumno informePagoAlumno) {
        InputStream stream = new ByteArrayInputStream(informePagoAlumno.getComprobantePago());
        StreamedContent file = new DefaultStreamedContent(stream, "application/octet-stream", informePagoAlumno.getNombreComprobantePago());
        System.out.println("fileeee para descargar: ");
        System.out.println(file);
        return file;
    }

    public void FileDownloadView(InformePagoAlumno informePagoAlumno) {
        System.out.println("entro descargar Archivo: ");
        System.out.println("archivo a exportar" + informePagoAlumno.getComprobantePago());
        InputStream stream = new ByteArrayInputStream(informePagoAlumno.getComprobantePago());
        file = new DefaultStreamedContent(stream, "application/octet-stream", informePagoAlumno.getNombreComprobantePago());

    }

    public void verPdf(InformePagoAlumno informePagoAlumno) {
        vistaPrevia=null;
        System.out.println("Entrooo ver pdf");
        System.out.println("nombre archivo: "+informePagoAlumno.getNombreComprobantePago());
        System.out.println("contenido archivo: "+informePagoAlumno.getComprobantePago());
        FacesMessage fm;
        InputStream stream = new ByteArrayInputStream(informePagoAlumno.getComprobantePago());

        if (informePagoAlumno.getNombreComprobantePago().contains(".pdf")) {
            vistaPrevia = new DefaultStreamedContent(stream, "application/pdf", informePagoAlumno.getNombreComprobantePago());
            //RequestContext.getCurrentInstance().update("informePagoAlumnoListInterno:dialogPDF");
            RequestContext.getCurrentInstance().execute("PF('pdfDialog').show();");
        }
        if (informePagoAlumno.getNombreComprobantePago().contains(".jpeg") || informePagoAlumno.getNombreComprobantePago().contains(".jpg") || informePagoAlumno.getNombreComprobantePago().contains(".png")) {
            vistaPrevia = new DefaultStreamedContent(stream, "image/jpeg", informePagoAlumno.getNombreComprobantePago());
            //RequestContext.getCurrentInstance().update("informePagoAlumnoListInterno:dialogJPG");
            RequestContext.getCurrentInstance().execute("PF('jpgDialog').show();");
        } else {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato no admitido", null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("Error", fm);
            RequestContext.getCurrentInstance().update("frmPri:mensajes");
        }

    }

    public void cargarTodos() {
        this.setItems(informePagoAlumnoFacade.findPagosOrdenadosPorFecha());
    }
}
