/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author franco
 */
@ManagedBean
@RequestScoped
public class CantidadCargaBean implements Serializable {

    List<Object[]> cargas = new ArrayList<>();
    Date fechaInicio;
    Date fechaFin;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Object[]> getCargas() {
        return cargas;
    }

    public void setCargas(List<Object[]> cargas) {
        this.cargas = cargas;
    }

    public void getCantidadCarga() {
        Connection conect = null;
        try {
            cargas = new ArrayList<>();
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            conect = dataSource.getConnection();
            if (fechaInicio == null) {
                throw new Exception("Debe ingresar una fecha de inicio");
            }
            if (fechaFin == null) {
                throw new Exception("Debe ingresar una fecha de fin");
            }
            java.sql.Timestamp dateInicio = new java.sql.Timestamp(fechaInicio.getTime());
            java.sql.Timestamp dateFin = new java.sql.Timestamp(fechaFin.getTime());

            String query = "SELECT creadopor as usuario, count(creadopor) as cantidad, 'Ingresos' as movimiento       "
                    + "  FROM ingresos where borrado is false  and fechacreado BETWEEN ? AND ? group by creadopor having count(creadopor) > 0  UNION "
                    + " SELECT creadopor as usuario, count(creadopor) as cantidad, 'Egresos' as movimiento       "
                    + "  FROM egresos where borrado is false  and fechacreado BETWEEN ? AND ? group by creadopor having count(creadopor) > 0 ;";

            PreparedStatement stmt = conect.prepareStatement(query);

            stmt.setTimestamp(1, dateInicio);
            stmt.setTimestamp(2, dateFin);
            stmt.setTimestamp(3, dateInicio);
            stmt.setTimestamp(4, dateFin);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] resultArray = new Object[3];
                resultArray[0] = rs.getString(1);
                resultArray[1] = rs.getInt(2);
                resultArray[2] = rs.getString(3);
                cargas.add(resultArray);
            }
            conect.close();
        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error: " + ex.getMessage(),
                    null);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }
    }
}
