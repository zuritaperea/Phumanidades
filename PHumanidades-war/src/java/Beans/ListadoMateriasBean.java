/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Carreras.Materia;
import Entidades.Persona.CorreoElectronico;
import RN.CorreoElectronicoRNLocal;
import RN.MateriaRNLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
@ManagedBean(name = "listadoMateriasBean")
@SessionScoped
public class ListadoMateriasBean implements Serializable {

    @EJB
    private MateriaRNLocal listadoMateriaRNLocal;
    private List<Materia> lstMateria;
    private int iTipoBoton;
    private CommandButton CbActionMateria;
    private Materia materia;
    private DataTable dtMateria;

    CommandButton btnSelect;

    public MateriaRNLocal getListadoMateriaRNLocal() {
        return listadoMateriaRNLocal;
    }

    public void setListadoMateriaRNLocal(MateriaRNLocal listadoMateriaRNLocal) {
        this.listadoMateriaRNLocal = listadoMateriaRNLocal;
    }

    public List<Materia> getLstMateria() {
        return lstMateria;
    }

    public void setLstMateria(List<Materia> lstMateria) {
        this.lstMateria = lstMateria;
    }

    public int getiTipoBoton() {
        return iTipoBoton;
    }

    public void setiTipoBoton(int iTipoBoton) {
        this.iTipoBoton = iTipoBoton;
    }

    public CommandButton getCbActionMateria() {
        return CbActionMateria;
    }

    public void setCbActionMateria(CommandButton CbActionMateria) {
        this.CbActionMateria = CbActionMateria;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public DataTable getDtMateria() {
        return dtMateria;
    }

    public void setDtMateria(DataTable dtMateria) {
        this.dtMateria = dtMateria;
    }

    public CommandButton getBtnSelect() {
        return btnSelect;
    }

    public void setBtnSelect(CommandButton btnSelect) {
        this.btnSelect = btnSelect;
    }

    /**
     * Creates a new instance of ListadoEmailBean
     */
    public ListadoMateriasBean() {
    }

    @PostConstruct
    private void init() {
        this.setLstMateria(new ArrayList<Materia>());
        cargarMaterias();
        materia = new Materia();
        lstMateria.clear();
    }

    private void cargarMaterias() {
        try {
            this.setLstMateria(listadoMateriaRNLocal.findAll());

        } catch (Exception ex) {

            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Error al cargar las Materias: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }//fin cargar Correos

    public void definirActionBotonMaterias(ActionEvent e) {

        //System.out.println("Entro al evento definirActionBotonEmail");
        //System.out.println("boton : " + e);
        //System.out.println("boton 2 : " + e.getSource());
        btnSelect = (CommandButton) e.getSource();

        //System.out.println("boton id: " + btnSelect.getId());
        //this.getCbAction().setDisabled(false);
        if (btnSelect.getId().equals("btnEdit")) {
            this.getCbActionMateria().setValue("Editar");
            this.setiTipoBoton(1);
        }
        if (btnSelect.getId().equals("btnDelete")) {
            this.getCbActionMateria().setValue("Eliminar");
            this.setiTipoBoton(2);
        }

        if (btnSelect.getId().equals("btnCreateMateria") || (btnSelect.getId().equals("btnCreateMateria"))) {
            //System.out.println("Entro al if : " + this.getPersona());
            //   this.getCbAction().setValue("Guardar");
            this.setMateria(new Materia());
            this.setiTipoBoton(0);

        }//fin if

        //System.out.println("termino el definir: " + this.getPersona());
    }

    //INICIO AREA E MAIL
    public void actionButtonMateria() {

        //System.out.println("Entro al actionButtonEmail" + this.getListadoEmailBean().getiTipoBoton());
        //Integer iBoton = this.getListadoEmailBean().getiTipoBoton();
        //System.out.println("boton select: " + iBoton);
        //0 guardar
        //1 modificar
        //2 eliminar
        System.out.println("tipo boton" + iTipoBoton);
        switch (this.iTipoBoton) {
            case 0:
                cargarTablaMateria();
                break;
            case 1:
                // modificar();
                break;
            case 2:
                //eliminar();
                break;
        }//fin switch

    }//fin actionButton

    public void cargarTablaMateria() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (lstMateria == null) {
            lstMateria = new ArrayList<>();
        }
        lstMateria.add(materia);

        context.update("frmPri:dtMaterias");
        context.execute("PF('dlgMaterias').hide();");

    }

    public void quitarMateria() {
        Materia mat = (Materia) this.getDtMateria().getRowData();
        lstMateria.remove(mat);
    }
}
