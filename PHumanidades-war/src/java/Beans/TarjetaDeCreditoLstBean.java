/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import DAO.TarjetaDeCreditoFacadeLocal;
import Entidades.Ingresos.TarjetaDeCredito;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author hugo
 */
@ManagedBean(name = "tarjetaDeCreditoLstBean")
@SessionScoped
public class TarjetaDeCreditoLstBean implements Serializable {

    @EJB
    private TarjetaDeCreditoFacadeLocal ejbFacade;
    private List<TarjetaDeCredito> items = null;
    private TarjetaDeCredito tarjetaDeCredito;

    @PostConstruct
    private void init() {
        tarjetaDeCredito = new TarjetaDeCredito();
    }

    public TarjetaDeCreditoLstBean() {
    }

    private TarjetaDeCreditoFacadeLocal getFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(TarjetaDeCreditoFacadeLocal ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<TarjetaDeCredito> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;

    }

    public void setItems(List<TarjetaDeCredito> items) {
        this.items = items;
    }

    public TarjetaDeCredito getTarjetaDeCredito() {
        return tarjetaDeCredito;
    }

    public void setTarjetaDeCredito(TarjetaDeCredito tarjetaDeCredito) {
        this.tarjetaDeCredito = tarjetaDeCredito;
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
}
