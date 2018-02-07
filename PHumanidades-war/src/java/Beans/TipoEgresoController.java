/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.TipoEgresoFacadeLocal;
import Entidades.Egresos.TipoEgreso;
import ManagedBeans.util.JsfUtil;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author ruben
 */
@ManagedBean(name = "tipoEgresoController")
@SessionScoped
public class TipoEgresoController implements Serializable {

    @EJB
    private TipoEgresoFacadeLocal ejbFacade;
    private List<TipoEgreso> items = null;
    private TipoEgreso selected;

    /**
     * Creates a new instance of TipoEgresoController
     */
    public TipoEgresoController() {
    }

    public List<TipoEgreso> getItems() {
        if (items == null) {
            items = getEjbFacade().findAll();
        }
        return items;
    }

    public void setItems(List<TipoEgreso> items) {
        this.items = items;
    }

    public TipoEgreso getSelected() {
        return selected;
    }

    public void setSelected(TipoEgreso selected) {
        this.selected = selected;
    }

    public TipoEgresoFacadeLocal getEjbFacade() {
        return ejbFacade;
    }

    public TipoEgreso prepareCreate() {
        this.setSelected(new TipoEgreso());
        System.out.println("prepare Create" + selected);
        return selected;
    }

    public List<TipoEgreso> getItemsAvailableSelectMany() {
        return getEjbFacade().findAll();
    }

    public List<TipoEgreso> getItemsAvailableSelectOne() {
        return getEjbFacade().findAll();
    }

    public List<TipoEgreso> getItemsNoBorradosSelectOne() {
        return getEjbFacade().findNoBorrados();
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoEgresoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoEgresoUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoEgresoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            System.out.println("if del selected not null" + selected);

            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getEjbFacade().edit(selected);
                } else {
                    getEjbFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                ex.printStackTrace();
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        } else {
            System.out.println("if del selected null" + selected);
        }
    }

    @FacesConverter(forClass = TipoEgreso.class)
    public static class TipoEgresoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoEgresoController controller = (TipoEgresoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoEgresoController");
            try {
                return controller.getEjbFacade().find(getKey(value));
            } catch (Exception e) {
                return null;
            }
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoEgreso) {
                TipoEgreso o = (TipoEgreso) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoEgreso.class.getName()});
                return null;
            }
        }

    }

}
