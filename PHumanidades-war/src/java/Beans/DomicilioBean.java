/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Persona.Domicilio;
import Entidades.Localidades.Pais;
import Entidades.Localidades.Departamento;
import Entidades.Localidades.Provincia;
import Entidades.Localidades.Localidad;
import RN.DepartamentoRNLocal;
import RN.DomicilioRNLocal;
import RN.LocalidadRNLocal;
import RN.PaisRNLocal;
import RN.ProvinciaRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.model.SelectItem;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

/**
 *
 * @author hugo
 */
@ManagedBean
@SessionScoped
public class DomicilioBean implements Serializable {

    @EJB
    private DomicilioRNLocal domicilioRNLocal;
    @EJB
    private PaisRNLocal paisRNLocal;
    @EJB
    private ProvinciaRNLocal provinciaRNLocal;
    @EJB
    private DepartamentoRNLocal departamentoRNLocal;
    @EJB
    private LocalidadRNLocal localidadRNLocal;

    private List<SelectItem> listaPais;
    private List<SelectItem> listaProvincias;
    private List<SelectItem> listaDepartamentos;
    private List<SelectItem> listaLocalidades;

    private Domicilio domicilio;
    private Pais pais;
    private Provincia provincia;
    private Departamento departamento;
    private Localidad localidad;
    private String actualizar;
    CommandButton btnSelect;
    int iTipoBoton;

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public List<SelectItem> getListaPais() {
        return listaPais;
    }

    public void setListaPais(List<SelectItem> listaPais) {
        this.listaPais = listaPais;
    }

    public List<SelectItem> getListaProvincias() {
        return listaProvincias;
    }

    public void setListaProvincias(List<SelectItem> listaProvincias) {
        this.listaProvincias = listaProvincias;
    }

    public List<SelectItem> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<SelectItem> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<SelectItem> getListaLocalidades() {
        return listaLocalidades;
    }

    public void setListaLocalidades(List<SelectItem> listaLocalidades) {
        this.listaLocalidades = listaLocalidades;
    }

    public int getiTipoBoton() {
        return iTipoBoton;
    }

    public void setiTipoBoton(int iTipoBoton) {
        this.iTipoBoton = iTipoBoton;
    }

    /**
     * Creates a new instance of domicilioBean
     */
    public DomicilioBean() {
    }

    @PostConstruct
    private void inicializarComponentes() {
        domicilio = new Domicilio();
        cargarPaises();
        cargarProvincias();
    }

    public void cargarProvincias() {
        try {

            listaProvincias = new ArrayList<SelectItem>();
            List<Provincia> provincias = provinciaRNLocal.findAll();
            for (Provincia prov : provincias) {
                listaProvincias.add(new SelectItem(prov, prov.toString()));
            }
        } catch (Exception ex) {

            Logger.getLogger(DomicilioBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarDepartamentos() {
        System.out.println("paso por departamentos");

        try {
            listaDepartamentos = new ArrayList<SelectItem>();

            for (Departamento depto : departamentoRNLocal.buscarDptoProvincia(provincia)) {
                listaDepartamentos.add(new SelectItem(depto, depto.toString()));

            }

        } catch (Exception ex) {
            Logger.getLogger(DomicilioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarLocalidades() {

        try {
            listaLocalidades = new ArrayList<SelectItem>();
            for (Localidad loc : localidadRNLocal.buscarLocalidadesDepto(departamento)) {
                listaLocalidades.add(new SelectItem(loc, loc.toString()));

            }
        } catch (Exception ex) {
            Logger.getLogger(DomicilioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarPaises() {

        try {
            listaPais = new ArrayList<SelectItem>();
            List<Pais> paises = paisRNLocal.findAll();
            for (Pais pa : paises) {
                listaPais.add(new SelectItem(pa, pa.toString()));
            }
        } catch (Exception ex) {
            Logger.getLogger(DomicilioBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarProvPais() {
        listaProvincias = new ArrayList<SelectItem>();

        for (Provincia prov : provinciaRNLocal.buscarProvinciasPais(pais)) {
            listaProvincias.add(new SelectItem(prov, prov.toString()));

        }
        //para anular los Departamentos y localidades
        if (listaProvincias.isEmpty()) {
            listaDepartamentos = null;
            listaLocalidades = null;
        }
    }

    public void definirActionBoton(ActionEvent e) {

        //System.out.println("Entro al evento definirActionBotonEmail");
        //System.out.println("boton : " + e);
        //System.out.println("boton 2 : " + e.getSource());
        btnSelect = (CommandButton) e.getSource();

        //System.out.println("boton id: " + btnSelect.getId());
        //this.getCbAction().setDisabled(false);
       /* if (btnSelect.getId().equals("btnEdit")) {
            
         this.setiTipoBoton(1);
         }
         if (btnSelect.getId().equals("btnDelete")) {
            
         this.setiTipoBoton(2);
         }

         if (btnSelect.getId().equals("btnDomicilioDocentes")||(btnSelect.getId().equals("btnDomicilioAlumnos"))) {
         //System.out.println("Entro al if : " + this.getPersona());
         //   this.getCbAction().setValue("Guardar");
            
         this.setiTipoBoton(0);

         }//fin if
        
      

         //System.out.println("termino el definir: " + this.getPersona());*/
    }

    public void validarLocalidad(ActionEvent e) {
        // Para validar que haya seleccionado la localidad en
        // cualquiera de los dialogos (LugarNacimiento y DOmicilio)

        //   if (this.getPersona().getLugarNacimiento() != null) {
        //this.setsDireccion(this.getPersona().getLugarNacimiento().getDepartamento().getDescripcion() + ", " + this.getPersona().getLugarNacimiento().getDescripcion());
        //System.out.println(actualizarPanel);
        if (btnSelect.getId().equals("btnDomicilioDocentes")) {
            RequestContext.getCurrentInstance().update("frmPri:pnDomicilio");
            RequestContext.getCurrentInstance().execute("PF('dgDomicilio').hide();");
        }
        if (btnSelect.getId().equals("btnDomicilioAlumnos")) {
            RequestContext.getCurrentInstance().update("frmPri:pnDomicilioAlumno");
            RequestContext.getCurrentInstance().execute("PF('dgDomicilio').hide();");

        }

        if (btnSelect.getId().equals("btnDomicilioProveedor")) {
            RequestContext.getCurrentInstance().update("frmPri:pnDomicilioProveedor");
            RequestContext.getCurrentInstance().execute("PF('dgDomicilio').hide();");

        }

    }

    public void afterPhase(PhaseEvent PhaseEvent) {
        UIComponent component = findComponentInRoot("pnDomicilio");
        if (component != null) {
            System.out.println("found the component = " + component.getId() + " class: " + component);
        }
    }

    public static UIComponent findComponentInRoot(String id) {
        UIComponent component = null;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }

        return component;
    }

    public static UIComponent findComponent(UIComponent base, String id) {
        if (id.equals(base.getId())) {
            return base;
        }

        UIComponent kid = null;
        UIComponent result = null;
        Iterator kids = base.getFacetsAndChildren();
        while (kids.hasNext() && (result == null)) {
            kid = (UIComponent) kids.next();
            if (id.equals(kid.getId())) {
                result = kid;
                break;
            }
            result = findComponent(kid, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

}
