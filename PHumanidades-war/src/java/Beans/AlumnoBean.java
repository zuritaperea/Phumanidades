/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Ingresos.Ingreso;
import Entidades.Localidades.Localidad;
import Entidades.Persona.Alumno;
import Entidades.Persona.Calidad;
import Entidades.Persona.Condicion;
import Entidades.Persona.CorreoElectronico;
import Entidades.Persona.Domicilio;
import Entidades.Persona.Telefono;
import RN.AlumnoRNLocal;
import RN.InscripcionAlumnosRNLocal;
import java.io.File;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class AlumnoBean {

    private final String escudo1 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "Imagenes" + File.separator + "escudo.jpg";
    private final String escudo2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "Imagenes" + File.separator + "logo2.jpg";

    @EJB
    private AlumnoRNLocal alumnoRNLocal;

    @EJB
    private InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal;

    @ManagedProperty(value = "#{listadoEmailBean}")
    private ListadoEmailBean listadoEmailBean;

    @ManagedProperty(value = "#{listadoTelefonosBean}")
    private ListadoTelefonosBean listadoTelefonosBean;

    @ManagedProperty(value = "#{domicilioBean}")
    private DomicilioBean domicilioBean;

    @ManagedProperty(value = "#{alumnoLstBean}")
    private AlumnoLstBean alumnoLstBean;

    @ManagedProperty(value = "#{cohorteLstBean}")
    private CohorteLstBean cohorteLstBean;

    @ManagedProperty(value = "#{cobroCuotasAlumnosLstBean}")
    private CobroCuotasAlumnosLstBean cobroCuotasAlumnosLstBean;

    @ManagedProperty(value = "#{inscripcionAlumnosLstBean}")
    private InscripcionAlumnosLstBean inscripcionAlumnosLstBean;

    private Alumno alumno;
    private Domicilio domicilio;

    private CommandButton cbAction;
    private int iActionBtnSelect;
    private boolean bCamposSoloLectura;

    @Enumerated(EnumType.STRING)
    private Calidad calidad;
    private List<SelectItem> lstCalidad;

    @Enumerated(EnumType.STRING)
    private Condicion condicion;
    private List<SelectItem> lstCondicion;

    //Busqueda alumnos
    private String tipoBusqueda;
    private List<String> lstTipoBusqueda;
    private String busqueda;
    private String busquedaConsulta;

//fin busqueda alumnos
    /**
     * Creates a new instance of AlumnoBean
     */
    public AlumnoBean() {
        alumno = new Alumno();
        cargarLstCalidad();
        cargarLstCondicion();
        cargarLstTipoBusqueda();
    }

    public AlumnoRNLocal getAlumnoRNLocal() {
        return alumnoRNLocal;
    }

    public void setAlumnoRNLocal(AlumnoRNLocal alumnoRNLocal) {
        this.alumnoRNLocal = alumnoRNLocal;
    }

    public InscripcionAlumnosRNLocal getInscripcionAlumnosRNLocal() {
        return inscripcionAlumnosRNLocal;
    }

    public void setInscripcionAlumnosRNLocal(InscripcionAlumnosRNLocal inscripcionAlumnosRNLocal) {
        this.inscripcionAlumnosRNLocal = inscripcionAlumnosRNLocal;
    }

    public CobroCuotasAlumnosLstBean getCobroCuotasAlumnosLstBean() {
        return cobroCuotasAlumnosLstBean;
    }

    public void setCobroCuotasAlumnosLstBean(CobroCuotasAlumnosLstBean cobroCuotasAlumnosLstBean) {
        this.cobroCuotasAlumnosLstBean = cobroCuotasAlumnosLstBean;
    }

    public ListadoEmailBean getListadoEmailBean() {
        return listadoEmailBean;
    }

    public void setListadoEmailBean(ListadoEmailBean listadoEmailBean) {
        this.listadoEmailBean = listadoEmailBean;
    }

    public ListadoTelefonosBean getListadoTelefonosBean() {
        return listadoTelefonosBean;
    }

    public void setListadoTelefonosBean(ListadoTelefonosBean listadoTelefonosBean) {
        this.listadoTelefonosBean = listadoTelefonosBean;
    }

    public DomicilioBean getDomicilioBean() {
        return domicilioBean;
    }

    public void setDomicilioBean(DomicilioBean domicilioBean) {
        this.domicilioBean = domicilioBean;
    }

    public AlumnoLstBean getAlumnoLstBean() {
        return alumnoLstBean;
    }

    public void setAlumnoLstBean(AlumnoLstBean alumnoLstBean) {
        this.alumnoLstBean = alumnoLstBean;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public int getiActionBtnSelect() {
        return iActionBtnSelect;
    }

    public void setiActionBtnSelect(int iActionBtnSelect) {
        this.iActionBtnSelect = iActionBtnSelect;
    }

    public boolean isbCamposSoloLectura() {
        return bCamposSoloLectura;
    }

    public void setbCamposSoloLectura(boolean bCamposSoloLectura) {
        this.bCamposSoloLectura = bCamposSoloLectura;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public CommandButton getCbAction() {
        return cbAction;
    }

    public void setCbAction(CommandButton cbAction) {
        this.cbAction = cbAction;
    }

    public Calidad getCalidad() {
        return calidad;
    }

    public void setCalidad(Calidad calidad) {
        this.calidad = calidad;
    }

    public List<SelectItem> getLstCalidad() {
        return lstCalidad;
    }

    public void setLstCalidad(List<SelectItem> lstCalidad) {
        this.lstCalidad = lstCalidad;
    }

    public Condicion getCondicion() {
        return condicion;
    }

    public void setCondicion(Condicion condicion) {
        this.condicion = condicion;
    }

    public List<SelectItem> getLstCondicion() {
        return lstCondicion;
    }

    public void setLstCondicion(List<SelectItem> lstCondicion) {
        this.lstCondicion = lstCondicion;
    }

    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public List<String> getLstTipoBusqueda() {
        return lstTipoBusqueda;
    }

    public void setLstTipoBusqueda(List<String> lstTipoBusqueda) {
        this.lstTipoBusqueda = lstTipoBusqueda;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getBusquedaConsulta() {
        return busquedaConsulta;
    }

    public void setBusquedaConsulta(String busquedaConsulta) {
        this.busquedaConsulta = busquedaConsulta;
    }

    public CohorteLstBean getCohorteLstBean() {
        return cohorteLstBean;
    }

    public void setCohorteLstBean(CohorteLstBean cohorteLstBean) {
        this.cohorteLstBean = cohorteLstBean;
    }

    public InscripcionAlumnosLstBean getInscripcionAlumnosLstBean() {
        return inscripcionAlumnosLstBean;
    }

    public void setInscripcionAlumnosLstBean(InscripcionAlumnosLstBean inscripcionAlumnosLstBean) {
        this.inscripcionAlumnosLstBean = inscripcionAlumnosLstBean;
    }

    //BOTON CREAR DOCENTE
    public void setBtnSelect(ActionEvent e) {
        CommandButton btnSelect = (CommandButton) e.getSource();

        System.out.println("boton select: " + btnSelect.getId());

        //activo el boton
        System.out.println("Boton: " + btnSelect.getId().equals("cbCreate"));
        this.getCbAction().setDisabled(false);
        switch (btnSelect.getId()) {
            case "cbCreate":
                this.setiActionBtnSelect(0);
                this.getCbAction().setValue("Guardar");
                this.setAlumno(new Alumno());
                this.setDomicilio(new Domicilio());
                //Al clickear boton nuevo reseteamos las lista de telefonos y correos
                //Solo al Boton create en edit y remove necesitamos mantener los datos
                this.getDomicilioBean().setDomicilio(new Domicilio());
                this.getListadoTelefonosBean().setLstTelefonos(new ArrayList<Telefono>());
                this.getListadoEmailBean().setLstCorreoElectronico(new ArrayList<CorreoElectronico>());
                break;
            case "cbDelete":
                this.setiActionBtnSelect(2);
                this.setbCamposSoloLectura(true);
                this.getCbAction().setValue("Eliminar");
                break;
            case "cbEdit":
                this.setiActionBtnSelect(1);
                this.getCbAction().setValue("Modificar");
                //Inicio- traemos el alumno seleccionado de la tabla
                FacesContext context = FacesContext.getCurrentInstance();
                AlumnoLstBean lstAlumnos = (AlumnoLstBean) context.getApplication().evaluateExpressionGet(context, "#{alumnoLstBean}", AlumnoLstBean.class);
                this.alumno = lstAlumnos.getAlumnoSeleccionado();
                if (alumno.getDomicilio() != null) {
                    //fin traer alumno
                    //seteamos domicilio bean con el domicilio del alumno
                    this.domicilioBean.setDomicilio(alumno.getDomicilio());

                }
                if (alumno.getTelefonos() != null && !alumno.getTelefonos().isEmpty()) {
                    alumno.getTelefonos().get(0);
                    this.listadoTelefonosBean.setLstTelefonos(this.alumno.getTelefonos());
                }
                if (alumno.getCorreosElectronicos() != null && !alumno.getCorreosElectronicos().isEmpty()) {
                    alumno.getCorreosElectronicos().get(0);
                    this.listadoEmailBean.setLstCorreoElectronico(this.alumno.getCorreosElectronicos());
                }
                break;
        }

    }//FIN setBtnSelect 

    public void accion() {
        //System.out.println(this.tipoOperacion);
        //  if (this.tipoOperacion.equals("Alta")) {

        //create();
        //  }
        //  if (this.tipoOperacion.equals("Modificación")) {
        //      modificar();
        //   }
        switch (this.getiActionBtnSelect()) {
            case 0:
                this.create();
                break;
            case 1:
                this.edit();
                System.out.println("switch edit");
                break;
            case 2:
                //borra el campo
                this.delete();
                //this.delete(Boolean.TRUE);
                break;
        }//fin switch

    }

    private void create() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;

        try {
            alumno.setCalidad(calidad);
            alumno.setCondicion(condicion);
            alumno.setDomicilio(this.getDomicilioBean().getDomicilio());
            alumno.setTelefonos(this.getListadoTelefonosBean().getLstTelefonos());
            alumno.setCorreosElectronicos(this.listadoEmailBean.getLstCorreoElectronico());

            alumnoRNLocal.create(this.getAlumno());

            listadoTelefonosBean.setLstTelefonos(new ArrayList<Telefono>());
            listadoEmailBean.setLstCorreoElectronico(new ArrayList<CorreoElectronico>());
            //alumnoLstBean.cargarDocente();
            // System.out.println("Actualizo update");
            sMensaje = "El Alumno fue creado ";
            severity = FacesMessage.SEVERITY_INFO;
            alumnoLstBean.cargarAlumnos();
            limpiar();

        } catch (Exception ex) {
            // Logger.getLogger(PersonaBean.class.getName()).log(Level.SEVERE, null, ex);
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al crear el Alumno: " + ex.getMessage();
        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }

    private void edit() {

        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            this.getAlumno().setDomicilio(this.getDomicilioBean().getDomicilio());
            this.getAlumno().setTelefonos(this.getListadoTelefonosBean().getLstTelefonos());
            this.getAlumno().setCorreosElectronicos(this.getListadoEmailBean().getLstCorreoElectronico());
            this.getAlumno().setCalidad(calidad);
            this.getAlumno().setCondicion(condicion);
            alumnoRNLocal.edit(this.getAlumno());
            sMensaje = "Los Datos del Alumno han sido modificados";
            severity = FacesMessage.SEVERITY_INFO;
            //elimino y agrego  a la lista
            int iPos = this.getAlumnoLstBean().getLstAlunmo().indexOf(this.getAlumno());

            this.getAlumnoLstBean().getLstAlunmo().remove(iPos);
            this.getAlumnoLstBean().getLstAlunmo().add(iPos, this.getAlumno());

            this.getCbAction().setValue("Editar");
            this.getCbAction().setDisabled(true);
            RequestContext.getCurrentInstance().execute("PF('dlgAlumno').hide();");

        } catch (Exception ex) {

            if (ex.getMessage().trim().toLowerCase().equals("transaction aborted")) {
                sMensaje = "Error: No se puede modificar";
            } else {
                sMensaje = "Error: " + ex.getMessage();
            }

            severity = FacesMessage.SEVERITY_ERROR;

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    private void delete() {

        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            alumnoRNLocal.remove(this.getAlumno());

            sMensaje = "El Alumno fue eliminado";
            severity = FacesMessage.SEVERITY_INFO;

            //remover de la lista
            this.getAlumnoLstBean().getLstAlunmo().remove(this.getAlumno());

            this.getCbAction().setValue("Eliminar");
            this.getCbAction().setDisabled(true);

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    public void limpiar() {
        System.out.println("entro limpiar domicilio");
        this.setAlumno(new Alumno());
        this.setbCamposSoloLectura(false);

    }

    public void cargarLstCalidad() {
        lstCalidad = new ArrayList<SelectItem>();
        for (Calidad ca : Calidad.values()) {
            lstCalidad.add(new SelectItem(ca, ca.toString()));
        }
    }

    private void cargarLstCondicion() {
        lstCondicion = new ArrayList<SelectItem>();
        for (Condicion co : Condicion.values()) {
            lstCondicion.add(new SelectItem(co, co.toString()));
        }
    }

    //metodo cargar selectOneMenu DocenteFindDlg (opciones DNI, Apellido)  
    public void cargarLstTipoBusqueda() {
        lstTipoBusqueda = new ArrayList();
        lstTipoBusqueda.add("Apellido");
        lstTipoBusqueda.add("dni");
    }

    public void buscarDniApellido() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            if (tipoBusqueda.equals("dni")) {
                if (busqueda.equals("")) {
                    this.getAlumnoLstBean().findAllAlumnos();
                } else {
                    this.getAlumnoLstBean().findAlumnoDni(busqueda);
                }
            } else {
                if (busqueda.equals("")) {
                    this.getAlumnoLstBean().findAllAlumnos();
                } else {

                    this.getAlumnoLstBean().findDocenteApellido(busqueda);
                }
            }
        } catch (Exception e) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + e.getMessage();
            //this.getMensajeBean().setMensaje("Error: " + e.getMessage());
            // RequestContext.getCurrentInstance().update("dMensaje");
            // RequestContext.getCurrentInstance().execute("dlgMensaje.show()");
        }

    }

    public void buscarDniConsulta() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            if (busquedaConsulta.equals("")) {
                //System.out.println("cadena vacia"+busquedaConsulta );
                sMensaje = "Ingrese un numero de Documento ";
                severity = FacesMessage.SEVERITY_INFO;
            } else {
                //System.out.println("cpor el else "+busquedaConsulta );
                this.getAlumnoLstBean().findAlumnoDniConsulta(busquedaConsulta);
            }

        } catch (Exception e) {
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + e.getMessage();
            //this.getMensajeBean().setMensaje("Error: " + e.getMessage());
            // RequestContext.getCurrentInstance().update("dMensaje");
            // RequestContext.getCurrentInstance().execute("dlgMensaje.show()");
        }

    }

    public void devolverAlumno() {
        try {
            this.cobroCuotasAlumnosLstBean.getIngreso().setAlumno(this.alumnoLstBean.getAlumnoSelect());
        } catch (Exception e) {
        }

        obtenerCohortesAlumnos();
        RequestContext.getCurrentInstance().update("frmPri:otAlumno1");//outPutText inscripcion Almunos
        RequestContext.getCurrentInstance().update("frmPri:otAlumno2");//outPutText cobros Almunos
        RequestContext.getCurrentInstance().update("frmPri:otAlumno3");//outPutText cobros generales
        RequestContext.getCurrentInstance().update("frmPri:otAlumno4");//outPutText consulta alumnos iingresos
        RequestContext.getCurrentInstance().update("frmPri:otAlumno2Inicial");//outPutText inscripcion Almunos
        RequestContext.getCurrentInstance().update("frmPri:otAlumnoModificarInscripcion");//Actualiza el outputText en InscripcionAlumnosEdit.xhtml
    }

    public void devolverAlumnoModificarInscripcion() {
        try {
            this.inscripcionAlumnosLstBean.getInscripcion().setAlumno(this.alumnoLstBean.getAlumnoSelect());
        } catch (Exception e) {
        }

        RequestContext.getCurrentInstance().update("frmPri:otAlumnoModificarInscripcion");//Actualiza el outputText en InscripcionAlumnosEdit.xhtml
    }

    public void devolverAlumnoConsulta() {
        obtenerCohortesAlumnosConsulta();
        RequestContext.getCurrentInstance().update("frmPri:pnAlumno");//outPutText Consulta Publica Alumnos

    }

    //Inicio Metodos Buscar Docentes
    public void abrirDlgFindAlumno() {
        // btnSelect = (CommandButton) e.getSource();
        this.cohorteLstBean.setLstCuotasAlumnoConsulta(new ArrayList<Ingreso>());
        this.cohorteLstBean.setLstCuotasAlumnoGeneral(new ArrayList<Ingreso>());
        //RequestContext.getCurrentInstance().update("dFindTurnoExamen");
        this.getAlumnoLstBean().setLstAlunmo(new ArrayList<Alumno>());
        RequestContext.getCurrentInstance().execute("PF('dlgFindAlumno').show();");
    }

    public void abrirDlgFindAlumnoModificarInscripcion() {
        // btnSelect = (CommandButton) e.getSource();

        //RequestContext.getCurrentInstance().update("dFindTurnoExamen");
        this.getAlumnoLstBean().setLstAlunmo(new ArrayList<Alumno>());
        RequestContext.getCurrentInstance().execute("PF('dlgFindAlumnoMI').show();");
    }

    public void abrirDlgFindAlumnoConsulta() {
        // btnSelect = (CommandButton) e.getSource();
        this.cohorteLstBean.setLstCuotasAlumnoConsulta(new ArrayList<Ingreso>());
        this.cohorteLstBean.setLstCuotasAlumnoGeneral(new ArrayList<Ingreso>());
        //RequestContext.getCurrentInstance().update("dFindTurnoExamen");
        this.getAlumnoLstBean().setLstAlumnoConsulta(new ArrayList<Alumno>());
        RequestContext.getCurrentInstance().execute("PF('dlgFindAlumnoConsulta').show();");
    }

    public void obtenerCohortesAlumnos() {
        try {
            //para obtener las cohorte del alumno seleccionado primero tengo que buscar las cohortes en las que se inncribi
//el alumno y en las que esta activo solamente
            //System.out.println(this.inscripcionAlumnosRNLocal.alumnoFindCohortes(this.alumnoLstBean.getAlumnoSelect()));
            this.cohorteLstBean.setLstCohortesAlumnos(this.inscripcionAlumnosRNLocal.alumnoFindCohortes(this.alumnoLstBean.getAlumnoSelect()));
            RequestContext.getCurrentInstance().update("frmPri:pnCohortes");
            RequestContext.getCurrentInstance().update("frmPri:dtCortesCobrosInicial");
            RequestContext.getCurrentInstance().update("frmPri:dtCortesCobros");
            /*    if (this.docenteLstBean.getDocenteSeleccionado().getCarreras() != null && !this.docenteLstBean.getDocenteSeleccionado().getCarreras().isEmpty()) {
             this.docenteLstBean.getDocenteSeleccionado().getCarreras().get(0);
             this.carreraLstBean.setLstCarrerasDocente(this.docenteLstBean.getDocenteSeleccionado().getCarreras());
             }
             if(this.alumnoLstBean.getAlumnoSelect().get){}
             RequestContext.getCurrentInstance().update("frmPri:otDocente");
             RequestContext.getCurrentInstance().update("frmPri:dtCarrerasPagos");*/
        } catch (Exception ex) {
            Logger.getLogger(AlumnoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerCohortesAlumnosConsulta() {
        try {
            //para obtener las cohorte del alumno seleccionado primero tengo que buscar las cohortes en las que se inncribi
//el alumno y en las que esta activo solamente
            //System.out.println(this.inscripcionAlumnosRNLocal.alumnoFindCohortes(this.alumnoLstBean.getAlumnoSelect()));
            this.cohorteLstBean.setLstCohortesAlumnosConsulta(this.inscripcionAlumnosRNLocal.alumnoFindCohortes(this.alumnoLstBean.getAlumnoSelectConsulta()));
            RequestContext.getCurrentInstance().update("frmPri:pnCohortesConsulta");

        } catch (Exception ex) {
            Logger.getLogger(AlumnoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCodigoRapipago(Alumno a) {
        try {
            String dni = a.getDni();
            if (dni.length() != 8) {
                dni = String.format("%8d", Integer.parseInt(a.getDni()));
            }
            String codigo = "47799" + dni;
            char[] cArrayCodigo = codigo.toCharArray();
            char[] cArrayCodigoRP = "1357935793579".toCharArray();
            BigInteger resultado = BigInteger.ZERO;
            for (int i = 0; i < cArrayCodigo.length; i++) {
                resultado = resultado.add(new BigInteger(Character.toString(cArrayCodigo[i])).
                        multiply(new BigInteger(Character.toString(cArrayCodigoRP[i]))));

            }

            BigInteger salidaInt = resultado.
                    divide(new BigInteger("2")).mod(new BigInteger("10"));
            return codigo + salidaInt.toString();
        } catch (Exception exception) {
            return "";
        }

    }

    public void generar() throws SQLException {
        Connection conect = null;

        try {

            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            conect = dataSource.getConnection();
            String path;
            System.out.println("funcionando" + conect);

            try {

                HashMap parametros = new HashMap();

                parametros.put("escudo1", escudo1);
                parametros.put("escudo2", escudo2);
                parametros.put("apellido", this.alumnoLstBean.getAlumnoSelect().getApellido());
                parametros.put("nombre", this.alumnoLstBean.getAlumnoSelect().getNombre());
                parametros.put("dni", this.alumnoLstBean.getAlumnoSelect().getDni());

                parametros.put("codigo", this.getCodigoRapipago(alumnoLstBean.getAlumnoSelect()));
                parametros.put("alumno_id", this.alumnoLstBean.getAlumnoSelect().getId());
                parametros.put("cohorte_id", this.cohorteLstBean.getCohorteSeleccionada().getId());

//funcionando
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator + "ingresosPorPagodeCuotas.jasper";
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

            }

        }//fin generar
        catch (NamingException ex) {
            Logger.getLogger(AlumnoBean.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            if (conect != null) {
                conect.close();
            }
        }
    }

    public void generarCuotasGeneral() throws SQLException {
        Connection conect = null;

        try {

            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            conect = dataSource.getConnection();
            String path;
            System.out.println("funcionando");

            try {

                HashMap parametros = new HashMap();

                parametros.put("escudo1", escudo1);
                parametros.put("escudo2", escudo2);
                parametros.put("apellido", this.alumnoLstBean.getAlumnoSelect().getApellido());
                parametros.put("nombre", this.alumnoLstBean.getAlumnoSelect().getNombre());
                parametros.put("dni", this.alumnoLstBean.getAlumnoSelect().getDni());

                parametros.put("codigo", this.getCodigoRapipago(alumnoLstBean.getAlumnoSelect()));
                parametros.put("alumno_id", this.alumnoLstBean.getAlumnoSelect().getId());

                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                String reportPath = context.getRealPath("") + File.separator + "reporte" + File.separator + "ingresosPorPagosGenerales.jasper";
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

            } finally {
                if (conect != null) {
                    conect.close();
                }
            }

        }//fin generar
        catch (NamingException ex) {
            Logger.getLogger(AlumnoBean.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}
