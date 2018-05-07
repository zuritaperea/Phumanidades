/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author franco
 */
@ManagedBean
@RequestScoped
public class CantidadCargaBean implements Serializable{
     List<Object[]> cargas = new ArrayList<>();

    public List<Object[]> getCargas() {
        return cargas;
    }

    public void setCargas(List<Object[]> cargas) {
        this.cargas = cargas;
    }

    public void getCantidadCarga() {
        try {
            cargas = new ArrayList<>();
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            Connection conect = dataSource.getConnection();
            Statement stmt = conect.createStatement();
            String query = "SELECT creadopor as usuario, count(creadopor) as cantidad, 'Ingresos' as movimiento       "
                    + "  FROM ingresos where borrado is false  group by creadopor having count(creadopor) > 0  UNION "
                    + " SELECT creadopor as usuario, count(creadopor) as cantidad, 'Egresos' as movimiento       "
                    + "  FROM egresos where borrado is false  group by creadopor having count(creadopor) > 0 ;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                    Object[] resultArray = new Object[3];
                    resultArray[0] =  rs.getString(1);
                    resultArray[1] = rs.getInt(2);
                    resultArray[2] = rs.getString(3);
                    cargas.add(resultArray);
                         }
        } catch (NamingException ex) {
            Logger.getLogger(CantidadCargaBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CantidadCargaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}