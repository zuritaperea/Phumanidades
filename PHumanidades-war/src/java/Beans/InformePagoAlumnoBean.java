/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author hugo
 */
@ManagedBean(name = "informePagoAlumnoBean")
@SessionScoped
public class InformePagoAlumnoBean implements Serializable {

    /**
     * Creates a new instance of InformePagoAlumnoBean
     */
    public InformePagoAlumnoBean() {
    }
    
}
