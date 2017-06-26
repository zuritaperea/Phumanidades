/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Carrera;

import Entidades.Persona.CorreoElectronico;
import Entidades.Persona.Docente;
import Entidades.Persona.Domicilio;
import Entidades.Persona.Telefono;
import Entidades.Persona.TipoTelefono;
import RN.DocenteRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

/**
 *
 * @author vouilloz
 */
@ManagedBean
@RequestScoped
public class DocenteBean implements Serializable {

    @EJB
    private DocenteRNLocal docenteRnLocal;

    @ManagedProperty(value = "#{listadoTelefonosBean}")
    private ListadoTelefonosBean listadoTelefonosBean;

    @ManagedProperty(value = "#{listadoEmailBean}")
    private ListadoEmailBean listadoEmailBean;

    @ManagedProperty(value = "#{docenteLstBean}")
    private DocenteLstBean docenteLstBean;

    @ManagedProperty(value = "#{domicilioBean}")
    private DomicilioBean domicilioBean;

    @ManagedProperty(value = "#{carreraLstBean}")
    private CarreraLstBean carreraLstBean;
    
    @ManagedProperty(value = "#{pagosDocenteLstBean}")
    private PagosDocenteLstBean pagosDocenteLstBean;
    
    @ManagedProperty(value = "#{proveedorLstBean}")
    private ProveedorLstBean proveedorLstBean;

    private Docente docente;

    private Telefono telefono;
    private List<Telefono> lstTelefono;
    private TipoTelefono tipotelefono;
    private DataTable dtTelefono;
    private CorreoElectronico correoElectronico;
    private List<CorreoElectronico> lstcorreosElectronicos;

    private CommandButton cbAction;
    private int iActionBtnSelect;
    private CommandButton cbActionTelefonos;
    private CommandButton cbActionEmail;
    private String tipoOperacion;
    private boolean bCamposSoloLectura;

    //Busqueda docentes
    private String tipoBusqueda;
    private List<String> lstTipoBusqueda;
    private String busqueda;

//fin busqueda docentes
    private CommandButton btnSelect;

    /**
     * Creates a new instance of DocenteBean
     */
    @PostConstruct
    private void init() {
        docente = new Docente();
        cargarLstTipoBusqueda();
    }

    public DocenteBean() {
    }

    public ListadoTelefonosBean getListadoTelefonosBean() {
        return listadoTelefonosBean;
    }

    public void setListadoTelefonosBean(ListadoTelefonosBean listadoTelefonosBean) {
        this.listadoTelefonosBean = listadoTelefonosBean;
    }

    public ListadoEmailBean getListadoEmailBean() {
        return listadoEmailBean;
    }

    public void setListadoEmailBean(ListadoEmailBean listadoEmailBean) {
        this.listadoEmailBean = listadoEmailBean;
    }

    public DocenteLstBean getDocenteLstBean() {
        return docenteLstBean;
    }

    public void setDocenteLstBean(DocenteLstBean docenteLstBean) {
        this.docenteLstBean = docenteLstBean;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Telefono setTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    public List<Telefono> getLstTelefono() {
        return lstTelefono;
    }

    public void setLstTelefono(List<Telefono> lstTelefono) {
        this.lstTelefono = lstTelefono;
    }

    public TipoTelefono getTipotelefono() {
        return tipotelefono;
    }

    public void setTipotelefono(TipoTelefono tipotelefono) {
        this.tipotelefono = tipotelefono;
    }

    public DataTable getDtTelefono() {
        return dtTelefono;
    }

    public void setDtTelefono(DataTable dtTelefono) {
        this.dtTelefono = dtTelefono;
    }

    public CorreoElectronico getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(CorreoElectronico correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public List<CorreoElectronico> getLstcorreosElectronicos() {
        return lstcorreosElectronicos;
    }

    public void setLstcorreosElectronicos(List<CorreoElectronico> lstcorreosElectronicos) {
        this.lstcorreosElectronicos = lstcorreosElectronicos;
    }

    public DomicilioBean getDomicilioBean() {
        return domicilioBean;
    }

    public void setDomicilioBean(DomicilioBean domicilioBean) {
        this.domicilioBean = domicilioBean;
    }

    public CommandButton getCbAction() {
        return cbAction;
    }

    public void setCbAction(CommandButton cbAction) {
        this.cbAction = cbAction;
    }

    public int getiActionBtnSelect() {
        return iActionBtnSelect;
    }

    public void setiActionBtnSelect(int iActionBtnSelect) {
        this.iActionBtnSelect = iActionBtnSelect;
    }

    public CommandButton getCbActionTelefonos() {
        return cbActionTelefonos;
    }

    public void setCbActionTelefonos(CommandButton cbActionTelefonos) {
        this.cbActionTelefonos = cbActionTelefonos;
    }

    public CommandButton getCbActionEmail() {
        return cbActionEmail;
    }

    public void setCbActionEmail(CommandButton cbActionEmail) {
        this.cbActionEmail = cbActionEmail;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public boolean isbCamposSoloLectura() {
        return bCamposSoloLectura;
    }

    public void setbCamposSoloLectura(boolean bCamposSoloLectura) {
        this.bCamposSoloLectura = bCamposSoloLectura;
    }

    public CarreraLstBean getCarreraLstBean() {
        return carreraLstBean;
    }

    public void setCarreraLstBean(CarreraLstBean carreraLstBean) {
        this.carreraLstBean = carreraLstBean;
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

    public CommandButton getBtnSelect() {
        return btnSelect;
    }

    public void setBtnSelect(CommandButton btnSelect) {
        this.btnSelect = btnSelect;
    }

    public PagosDocenteLstBean getPagosDocenteLstBean() {
        return pagosDocenteLstBean;
    }

    public void setPagosDocenteLstBean(PagosDocenteLstBean pagosDocenteLstBean) {
        this.pagosDocenteLstBean = pagosDocenteLstBean;
    }

    public ProveedorLstBean getProveedorLstBean() {
        return proveedorLstBean;
    }

    public void setProveedorLstBean(ProveedorLstBean proveedorLstBean) {
        this.proveedorLstBean = proveedorLstBean;
    }
    
    

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
                break;
            case 2:
                this.delete();
                break;
        }//fin switch
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
                this.docente = new Docente();
                this.getDomicilioBean().setDomicilio(new Domicilio());
                //Al clickear boton nuevo reseteamos las lista de telefonos y correos
                //Solo al Boton create en edit y remove necesitamos mantener los datos
                this.getCarreraLstBean().setLstCarrerasAsoc(new ArrayList<Carrera>());
                this.getListadoTelefonosBean().setLstTelefonos(new ArrayList<Telefono>());
                this.getListadoEmailBean().setLstCorreoElectronico(new ArrayList<CorreoElectronico>());
                break;
            case "cbDeleteDocente":
                this.setiActionBtnSelect(2);
                this.setbCamposSoloLectura(true);
                this.getCbAction().setValue("Eliminar");
                break;
            case "cbEditarDocente":

                this.setiActionBtnSelect(1);
                this.getCbAction().setValue("Modificar");

                //Inicio- traemos el docente seleccionado de la tabla
                FacesContext context = FacesContext.getCurrentInstance();
                DocenteLstBean lstDocentes = (DocenteLstBean) context.getApplication().evaluateExpressionGet(context, "#{docenteLstBean}", DocenteLstBean.class);
                this.docente = lstDocentes.getDocenteSeleccionadoDeTabla();

                //fin traer docente
                if (docente.getDomicilio() != null) {
                    this.domicilioBean.setDomicilio(docente.getDomicilio());
                }
                if (docente.getTelefonos() != null && !docente.getTelefonos().isEmpty()) {
                    docente.getTelefonos().get(0);
                    this.listadoTelefonosBean.setLstTelefonos(this.docente.getTelefonos());
                }
                if (docente.getCorreosElectronicos() != null && !docente.getCorreosElectronicos().isEmpty()) {
                    docente.getCorreosElectronicos().get(0);
                    this.listadoEmailBean.setLstCorreoElectronico(this.docente.getCorreosElectronicos());
                }

                if (docente.getCarreras() != null && !docente.getCarreras().isEmpty()) {
                    docente.getCarreras().get(0);
                    this.carreraLstBean.setLstCarrerasAsoc(this.docente.getCarreras());
                } else {
                    this.carreraLstBean.setLstCarrerasAsoc(null);
                }
                break;
        }

    }//fin setBtnSelect

    public void create() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;

        try {
            /* Alumno docente = new Alumno();
             docente.setApellido("Caceres");
             docente.setNombre("Jorge");
             docente.setDni("27256189");
             docente.setMatricula("1258");
             docente.setCalidad("Activo");
             docente.setTelefonos(this.getListadoTelefonosBean().getLstTelefonos());
             docente.setCorreosElectronicos(this.listadoEmailBean.getLstCorreoElectronico());
             docente.setDomicilio(domicilio);
             docente.setFechaNacimiento(docente.getFechaNacimiento());
             docenteRnLocal.create(docente);*/

            docente.setDomicilio(this.getDomicilioBean().getDomicilio());
            docente.setTelefonos(this.getListadoTelefonosBean().getLstTelefonos());
            docente.setCorreosElectronicos(this.listadoEmailBean.getLstCorreoElectronico());
            docente.setCarreras(this.carreraLstBean.getLstCarrerasAsoc());
            docenteRnLocal.create(this.getDocente());

            sMensaje = "El Docente fue creado ";
            severity = FacesMessage.SEVERITY_INFO;

            listadoTelefonosBean.setLstTelefonos(new ArrayList<Telefono>());
            listadoEmailBean.setLstCorreoElectronico(new ArrayList<CorreoElectronico>());
            docenteLstBean.cargarDocente();
            // System.out.println("Actualizo update");

            limpiar();

        } catch (Exception ex) {
            //Logger.getLogger(PersonaBean.class.getName()).log(Level.SEVERE, null, ex);
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al crear el Docente: " + ex.getMessage();
        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }

    public void cargarTablaTelefonos() {

        if (lstTelefono == null) {
            lstTelefono = new ArrayList<>();
        }

        lstTelefono.add(telefono);
        this.setTelefono(new Telefono());
        //System.out.println("Entro al cargar telefonos: " + telefono.getNumero());
        // this.getListadoTelefonosBean().getLstTelefonos().add(telefono);
    }

    public void quitarTablaTelefonos() {
        Telefono tel = (Telefono) this.getDtTelefono().getRowData();
        lstTelefono.remove(tel);

    }//fin quitarTablaTelefonos

    public void modificar() {
        try {
            docenteRnLocal.edit(docente);
            //System.out.println("pasa por aqui");
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al modificar los docentes", null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    //FIN AREA E MAIL
    public void limpiar() {
        this.setDocente(new Docente());
        this.setbCamposSoloLectura(false);

    }//fin limpiar

    private void edit() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            this.getDocente().setDomicilio(this.getDomicilioBean().getDomicilio());
            this.getDocente().setTelefonos(this.getListadoTelefonosBean().getLstTelefonos());
            this.getDocente().setCorreosElectronicos(this.getListadoEmailBean().getLstCorreoElectronico());
            this.getDocente().setCarreras(this.getCarreraLstBean().getLstCarrerasAsoc());
            docenteRnLocal.edit(this.getDocente());
            sMensaje = "Los Datos del Docente han sido modificados";
            severity = FacesMessage.SEVERITY_INFO;
            //elimino y agrego  a la lista
            int iPos = this.getDocenteLstBean().getLstDocente().indexOf(this.getDocente());

            this.getDocenteLstBean().getLstDocente().remove(iPos);
            this.getDocenteLstBean().getLstDocente().add(iPos, this.getDocente());

            this.getCbAction().setValue("Editar");
            this.getCbAction().setDisabled(true);
            RequestContext.getCurrentInstance().execute("PF('dlgDocente').hide();");

        } catch (Exception ex) {

            if (ex.getMessage().trim().toLowerCase().equals("transaction aborted")) {
                sMensaje = "Error: No se puede eliminar";
            } else {
                sMensaje = "Error: " + ex.getMessage();
            }

            severity = FacesMessage.SEVERITY_ERROR;

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }//Fin Editar

    private void delete() {

        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {

            docenteRnLocal.remove(this.getDocente());

            sMensaje = "El docente fue eliminado";
            severity = FacesMessage.SEVERITY_INFO;

            //remover de la lista
            this.getDocenteLstBean().getLstDocente().remove(this.getDocente());

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

    public void buscarDniApellido() {

        try {
            if (tipoBusqueda.equals("dni")) {
                if (busqueda.equals("")) {
                    this.getDocenteLstBean().findAllDocentes();
                } else {
                    this.getDocenteLstBean().findDocenteDni(busqueda);
                }
            } else {
                if (busqueda.equals("")) {
                    this.getDocenteLstBean().findAllDocentes();
                } else {
                    this.getDocenteLstBean().findDocenteApellido(busqueda);
                }
            }
        } catch (Exception e) {
            //this.getMensajeBean().setMensaje("Error: " + e.getMessage());
            // RequestContext.getCurrentInstance().update("dMensaje");
            // RequestContext.getCurrentInstance().execute("dlgMensaje.show()");
        }

    }

    //metodo cargar selectOneMenu DocenteFindDlg (opciones DNI, Apellido)  
    public void cargarLstTipoBusqueda() {
        lstTipoBusqueda = new ArrayList();
        lstTipoBusqueda.add("dni");
        lstTipoBusqueda.add("Apellido");
    }

    //Inicio Metodos Buscar Docentes
    public void abrirDlgFindDocente(ActionEvent e) {
        btnSelect = (CommandButton) e.getSource();

        //RequestContext.getCurrentInstance().update("dFindTurnoExamen");
        this.getDocenteLstBean().setLstDocente(new ArrayList<Docente>());
        RequestContext.getCurrentInstance().execute("PF('dlgFindDocente').show();");
    }

    public void obtenerCarreras() {

        try {
            if (this.docenteLstBean.getDocenteSeleccionado().getCarreras() != null && !this.docenteLstBean.getDocenteSeleccionado().getCarreras().isEmpty()) {
                this.docenteLstBean.getDocenteSeleccionado().getCarreras().get(0);
                this.carreraLstBean.setLstCarrerasDocente(this.docenteLstBean.getDocenteSeleccionado().getCarreras());
                this.proveedorLstBean.setProveedorSelect(null);
                this.pagosDocenteLstBean.setDocProv(1);
            }
        } catch (Exception e) {
        }
        RequestContext.getCurrentInstance().update("frmPri:otDocente");
        RequestContext.getCurrentInstance().update("frmPri:otDocente4");
        RequestContext.getCurrentInstance().update("frmPri:dtCarrerasPagos");
        RequestContext.getCurrentInstance().update("frmPri:otProveedor");
    }

}
