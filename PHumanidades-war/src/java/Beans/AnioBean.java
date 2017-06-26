package Beans;

import Entidades.Carreras.Anio;
import RN.AnioRNLocal;
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
@ManagedBean(name = "anioBean")
@RequestScoped
public class AnioBean implements Serializable {

    @EJB
    private AnioRNLocal anioRNLocal;

    @ManagedProperty(value = "#{anioLstBean}")
    private AnioLstBean anioLstBean;

    private Anio anio;

    public AnioBean() {
    }

    public AnioLstBean getAnioLstBean() {
        return anioLstBean;
    }

    public void setAnioLstBean(AnioLstBean anioLstBean) {
        this.anioLstBean = anioLstBean;
    }

    public Anio getAnio() {
        return anio;
    }

    public void setAnio(Anio anio) {
        this.anio = anio;
    }

    @PostConstruct
    private void init() {
        anio = new Anio();
    }

    /**
     * Busca las localidad por id
     *
     * @param id código de la localidad
     * @return Localidad localidad encontrada
     */
    public Anio findById(Long id) {
        try {
            return anioRNLocal.findByiD(id);
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al buscar el Anio: " + ex,
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
        return null;
    }//fin dinfByid

}
