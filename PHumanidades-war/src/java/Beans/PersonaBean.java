/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Persona.CorreoElectronico;
import Entidades.Persona.Domicilio;
import Entidades.Persona.Persona;
import Entidades.Persona.Telefono;
import Entidades.Persona.TipoTelefono;
import RN.PersonaRNLocal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
@SessionScoped
public class PersonaBean {

    @EJB
    private PersonaRNLocal personaRnLocal;
    @ManagedProperty(value = "#{listadoTelefonosBean}")
    private ListadoTelefonosBean listadoTelefonosBean;
    @ManagedProperty(value = "#{listadoEmailBean}")
    private ListadoEmailBean listadoEmailBean;
    private Persona persona;
    private Domicilio domicilio;
    private Telefono telefono;
    private List<Telefono> lstTelefono;
    private TipoTelefono tipotelefono;
    private DataTable dtTelefono;
    private CorreoElectronico correoElectronico;
    private List<CorreoElectronico> lstcorreosElectronicos;
    private DataTable dtEmail;
    private CommandButton cbActionTelefonos;
    private CommandButton cbActionEmail;
    private String tipoOperacion;

    /**
     * Creates a new instance of PersonaBean
     */
    public PersonaBean() {
        domicilio = new Domicilio();
        telefono = new Telefono();
        correoElectronico = new CorreoElectronico();
        persona = new Persona();
        this.tipoOperacion = "Alta";

    }

    /**
     * Getters and Setters
     *
     *
     */
    public Persona getPersona() {
        return persona;

    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
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

    public List<Telefono> getLstTelefono() {
        return lstTelefono;
    }

    public void setLstTelefono(List<Telefono> lstTelefono) {
        this.lstTelefono = lstTelefono;
    }

    public TipoTelefono getTipotelefono() {
        return tipotelefono;
    }

    public DataTable getDtEmail() {
        return dtEmail;
    }

    public void setDtEmail(DataTable dtEmail) {
        this.dtEmail = dtEmail;
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

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public void accion() {
        System.out.println(this.tipoOperacion);
        if (this.tipoOperacion.equals("Alta")) {
            create();
        }
        if (this.tipoOperacion.equals("Modificación")) {
            modificar();
        }

    }

    public void create() {
        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            System.out.println("Persona: " + persona.getApellido());
            persona.setDomicilio(domicilio);

            personaRnLocal.create(persona);
            //Se agrega la persona a la lista para que muestre en la consulta
            //this.listaPersonas.add(persona);

            sMensaje = "La persona fue creada ";
            severity = FacesMessage.SEVERITY_INFO;
            persona = new Persona();

        } catch (Exception ex) {
            Logger.getLogger(PersonaBean.class.getName()).log(Level.SEVERE, null, ex);
            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error al crear la persona: " + ex.getMessage();
        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }

    public void modificar() {
        try {
            personaRnLocal.edit(persona);
            //System.out.println("pasa por aqui");
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al modificar los docentes", null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    public void definirActionBoton(ActionEvent e) {

        //System.out.println("Entro al evento definirActionBoton");
        //System.out.println("boton : " + e);
        //System.out.println("boton 2 : " + e.getSource());
        CommandButton btnSelect = (CommandButton) e.getSource();

        //System.out.println("boton id: " + btnSelect.getId());
        //this.getCbAction().setDisabled(false);
        if (btnSelect.getId().equals("btnEdit")) {
            this.getCbActionTelefonos().setValue("Editar");
            this.getListadoTelefonosBean().setiTipoBoton(1);
            //System.out.println("Entro al Edit");
        }
        if (btnSelect.getId().equals("btnDelete")) {
            this.getCbActionTelefonos().setValue("Eliminar");
            this.getListadoTelefonosBean().setiTipoBoton(2);
            //System.out.println("Entro al Delete");
        }

        if (btnSelect.getId().equals("btnCreateTelefonos")) {
            //System.out.println("Entro al if : " + this.getPersona());
            //   this.getCbAction().setValue("Guardar");
            //System.out.println("Despues del if");
            this.getListadoTelefonosBean().setiTipoBoton(0);
            System.out.println("Get Tipo boton" + this.getListadoEmailBean().getiTipoBoton());
        }//fin if

        //System.out.println("termino el definir: " + this.getPersona());
    }

    public void validarLocalidad(ActionEvent e) {
        // Para validar que haya seleccionado la localidad en
        // cualquiera de los dialogos (LugarNacimiento y DOmicilio)
        CommandButton botonSelecionado = (CommandButton) e.getSource();

        //   if (this.getPersona().getLugarNacimiento() != null) {
        //this.setsDireccion(this.getPersona().getLugarNacimiento().getDepartamento().getDescripcion() + ", " + this.getPersona().getLugarNacimiento().getDescripcion());
        RequestContext context = RequestContext.getCurrentInstance();

        if (botonSelecionado.getId().equals("cbDomicilio")) {
            context.execute("PF('dgDomicilio').hide();");
            context.update(":frmPri:pnDomicilio");
        }

//        } else {
//            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "No seleccionó localidad!!\n "
//                    + "si no existe, comuniquese con el administrador", null);
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage(null, fm);
//
//
//        }
    }

    public void definirActionBotonEmail(ActionEvent e) {

        //System.out.println("Entro al evento definirActionBotonEmail");
        //System.out.println("boton : " + e);
        //System.out.println("boton 2 : " + e.getSource());
        CommandButton btnSelect = (CommandButton) e.getSource();

        //System.out.println("boton id: " + btnSelect.getId());
        //this.getCbAction().setDisabled(false);
        if (btnSelect.getId().equals("btnEdit")) {
            this.getCbActionEmail().setValue("Editar");
            this.getListadoEmailBean().setiTipoBoton(1);
        }
        if (btnSelect.getId().equals("btnDelete")) {
            this.getCbActionEmail().setValue("Eliminar");
            this.getListadoEmailBean().setiTipoBoton(2);
        }

        if (btnSelect.getId().equals("btnCreateTelefonos")) {
            //System.out.println("Entro al if : " + this.getPersona());
            //   this.getCbAction().setValue("Guardar");

            this.getListadoEmailBean().setiTipoBoton(0);

        }//fin if

        //System.out.println("termino el definir: " + this.getPersona());
    }

    public void actionButton() {

        //System.out.println("Entro al actionButton");
        Integer iBoton = this.getListadoTelefonosBean().getiTipoBoton();
        //System.out.println("boton select: " + iBoton);
        //0 guardar
        //1 modificar

        //2 eliminar
        switch (iBoton) {
            case 0:
                cargarTablaTelefonos();
                break;
            case 1:
                modificar();
                break;
            case 2:
                //eliminar();
                break;
        }//fin switch

    }//fin actionButton

    public void actionButtonEmail() {

        Integer iBoton = this.getListadoEmailBean().getiTipoBoton();
        //0 guardar
        //1 modificar

        //2 eliminar
        switch (iBoton) {
            case 0:
                cargarTablaEmail();
                break;
            case 1:
                modificar();
                break;
            case 2:
                //eliminar();
                break;
        }//fin switch

    }//fin actionButton

    public void cargarTablaTelefonos() {

        if (lstTelefono == null) {
            lstTelefono = new ArrayList<>();
        }

        lstTelefono.add(telefono);
        this.setTelefono(new Telefono());
        //System.out.println("Entro al cargar telefonos: " + telefono.getNumero());
        //  this.getListadoTelefonosBean().getLstTelefonos().add(telefono);
    }

    public void quitarTablaTelefonos() {
        Telefono tel = (Telefono) this.getDtTelefono().getRowData();
        lstTelefono.remove(tel);

    }//fin quitarTablaTelefonos

    public void cargarTablaEmail() {
        if (lstcorreosElectronicos == null) {
            lstcorreosElectronicos = new ArrayList<>();
        }
        lstcorreosElectronicos.add(correoElectronico);
        this.setCorreoElectronico(new CorreoElectronico());
    }

    public void quitarTablaEmail() {
        CorreoElectronico correo = (CorreoElectronico) this.getDtEmail().getRowData();
        lstcorreosElectronicos.remove(correo);
    }
}
