/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Entidades.Egresos.RubroPresupuestario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author cris
 */
@ManagedBean
@SessionScoped
public class ListaRubroPresupuestario implements Serializable {

    private List<SelectItem> lstSIRubroType;

    /**
     * Creates a new instance of ListIdentificationTypeBean1
     */
    public ListaRubroPresupuestario() {
    }

    public List<SelectItem> getLstSIRubroType() {
        return lstSIRubroType;
    }

    public void setLstSIRubroType(List<SelectItem> lstSIRubroType) {
        this.lstSIRubroType = lstSIRubroType;
    }

    public void cargarIdentificationTypes() {
        lstSIRubroType = new ArrayList<>();
        for (RubroPresupuestario it : RubroPresupuestario.values()) {
            lstSIRubroType.add(new SelectItem(it, it.getName()));
        }
    }

}
