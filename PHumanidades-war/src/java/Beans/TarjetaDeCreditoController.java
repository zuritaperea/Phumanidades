package Beans;

import Entidades.Ingresos.TarjetaDeCredito;
import RN.util.JsfUtil;
import RN.util.JsfUtil.PersistAction;
import DAO.TarjetaDeCreditoFacade;
import DAO.TarjetaDeCreditoFacadeLocal;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name="tarjetaDeCreditoController")
@SessionScoped
public class TarjetaDeCreditoController implements Serializable {

    @EJB
    private TarjetaDeCreditoFacadeLocal ejbFacade;
    private List<TarjetaDeCredito> items = null;
    private TarjetaDeCredito selected;

    public TarjetaDeCreditoController() {
    }

    public TarjetaDeCredito getSelected() {
        return selected;
    }

    public void setSelected(TarjetaDeCredito selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TarjetaDeCreditoFacadeLocal getFacade() {
        return ejbFacade;
    }

    public TarjetaDeCredito prepareCreate() {
        selected = new TarjetaDeCredito();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        System.err.println("Entro Al Createeee");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleTarjetaDeCredito").getString("TarjetaDeCreditoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleTarjetaDeCredito").getString("TarjetaDeCreditoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleTarjetaDeCredito").getString("TarjetaDeCreditoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TarjetaDeCredito> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            System.out.println("if del selected not null" + selected);
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleTarjetaDeCredito").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleTarjetaDeCredito").getString("PersistenceErrorOccured"));
            }
        }
    }

    public TarjetaDeCredito getTarjetaDeCredito(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TarjetaDeCredito> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TarjetaDeCredito> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TarjetaDeCredito.class)
    public static class TarjetaDeCreditoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TarjetaDeCreditoController controller = (TarjetaDeCreditoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tarjetaDeCreditoController");
            return controller.getTarjetaDeCredito(getKey(value));
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
            if (object instanceof TarjetaDeCredito) {
                TarjetaDeCredito o = (TarjetaDeCredito) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TarjetaDeCredito.class.getName()});
                return null;
            }
        }

    }

}
