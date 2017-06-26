package Beans;

import Entidades.Carreras.TipoCarrera;
import RN.TipoCarreraRNLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Clase usada para manejar la localidad
 *
 * @author AFerSor
 */
@ManagedBean(name = "tipoCarreraBean")
@RequestScoped
public class TipoCarreraBean implements Serializable {

    @EJB
    private TipoCarreraRNLocal tipoCarreraRNLocal;

    @ManagedProperty(value = "#{tipoCarreraLstBean}")
    private TipoCarreraLstBean tipoCarreraLstBean;

    private TipoCarrera tipoCarrera;

    public TipoCarreraBean() {
    }

    public TipoCarrera getTipoCarrera() {
        return tipoCarrera;
    }

    public void setTipoCarrera(TipoCarrera tipoCarrera) {
        this.tipoCarrera = tipoCarrera;
    }

    public TipoCarreraLstBean getTipoCarreraLstBean() {
        return tipoCarreraLstBean;
    }

    public void setTipoCarreraLstBean(TipoCarreraLstBean tipoCarreraLstBean) {
        this.tipoCarreraLstBean = tipoCarreraLstBean;
    }

    @PostConstruct
    private void init() {
        tipoCarrera = new TipoCarrera();
    }

    /**
     * Busca las localidad por id
     *
     * @param id código de la localidad
     * @return Localidad localidad encontrada
     */
    public TipoCarrera findById(Long id) {
        try {
            return tipoCarreraRNLocal.findByiD(id);
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al buscar la localidad: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
        return null;
    }//fin dinfByid

    public void abrirDlgTipoCarrera() {
        this.setTipoCarrera(new TipoCarrera());
    }

    public void guardarTipoCarrera() {

        String sMensaje = "";
        FacesMessage fm;
        FacesMessage.Severity severity = null;
        try {
            tipoCarreraRNLocal.create(tipoCarrera);
            sMensaje = "Tipo Carrera Guardada";
            severity = FacesMessage.SEVERITY_INFO;
            //agregar a la lista
            // this.getUsuarioLstBean().getLstUsuario().add(this.getUsuario());
            this.getTipoCarreraLstBean().getLstTipoCarrera().add(tipoCarrera);
            //limíar campos
            this.limpiar();

        } catch (Exception ex) {

            severity = FacesMessage.SEVERITY_ERROR;
            sMensaje = "Error: " + ex.getMessage();

        } finally {
            fm = new FacesMessage(severity, sMensaje, null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

    }

    private void limpiar() {
        this.setTipoCarrera(new TipoCarrera());
    }
}
