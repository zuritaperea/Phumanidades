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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author franco
 */
@ManagedBean
@RequestScoped
public class GraficosBean implements Serializable {

    private List<Object[]> cargas = new ArrayList<>();
    private Date fechaInicio;
    private Date fechaFin;
    private HorizontalBarChartModel barModeIngresos;
    private HorizontalBarChartModel barModelEgresos;

    @PostConstruct
    public void init() {
        createBarModels();
    }

    public List<Object[]> getCargas() {
        return cargas;
    }

    public void setCargas(List<Object[]> cargas) {
        this.cargas = cargas;
    }

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

    public HorizontalBarChartModel getBarModeIngresos() {
        return barModeIngresos;
    }

    public void setBarModeIngresos(HorizontalBarChartModel barModeIngresos) {
        this.barModeIngresos = barModeIngresos;
    }

    public HorizontalBarChartModel getBarModelEgresos() {
        return barModelEgresos;
    }

    public void setBarModelEgresos(HorizontalBarChartModel barModelEgresos) {
        this.barModelEgresos = barModelEgresos;
    }

    private void createBarModels() {
        barModeIngresos = initBarModelIngresos();
        barModelEgresos = initBarModelEgresos();

//        Axis yAxis = barModel.getAxis(AxisType.Y);
//        yAxis.setMin(0);
        //yAxis.setMax(20000000000);
//        barModel.setTitle("Ingresos y Egresos Carreras");
//        barModel.setLegendPosition("e");
//        barModel.setShowPointLabels(true);
//        barModel.getAxes().put(AxisType.X, new CategoryAxis("Importe"));
//        yAxis = barModel.getAxis(AxisType.Y);
//        yAxis.setLabel("Carreras");
//        yAxis.setMin(0);
        //  yAxis.setMax(200);
    }

    public List<Object[]> getIngresos() {
        Connection conect = null;
        try {
            cargas = new ArrayList<>();
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            conect = dataSource.getConnection();

            String query = "Select sum(importe), CASE "
                    + "      WHEN carrera.descripcion is null  THEN 'Sin carrera'"
                    + "           ELSE carrera.descripcion END from ingresos left join cohorte on ingresos.cohorte_id = cohorte.id "
                    + "left join carrera on cohorte.carrera_id = carrera.id "
                    + "where borrado is not true and anulado is not true and importe > 0 and EXTRACT(YEAR FROM fechapago) = ? "
                    + "group by carrera.descripcion order by carrera.descripcion;";

            PreparedStatement stmt = conect.prepareStatement(query);

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int anio = c.get(Calendar.YEAR);
            stmt.setInt(1, anio);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] resultArray = new Object[2];
                resultArray[0] = rs.getFloat(1);
                resultArray[1] = rs.getString(2);
                cargas.add(resultArray);
            }
            conect.close();

            return cargas;
        } catch (Exception ex) {

        }
        return new ArrayList<>();
    }

    public List<Object[]> getEgresos() {
        Connection conect = null;
        try {
            cargas = new ArrayList<>();
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/Phumanidades");
            conect = dataSource.getConnection();

            String query = "Select sum(importecomprobante), CASE "
                    + "      WHEN carrera.descripcion is null  THEN 'Sin carrera' "
                    + "           ELSE carrera.descripcion END from egresos left join carrera on egresos.carrera_id = carrera.id "
                    + "where borrado is not true and anulado is not true and importecomprobante > 0 and EXTRACT(YEAR FROM fechacomprobante) = ? "
                    + "group by carrera.descripcion order by carrera.descripcion;";

            PreparedStatement stmt = conect.prepareStatement(query);

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int anio = c.get(Calendar.YEAR);
            stmt.setInt(1, anio);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] resultArray = new Object[2];
                resultArray[0] = rs.getFloat(1);
                resultArray[1] = rs.getString(2);
                cargas.add(resultArray);
            }
            conect.close();
            return cargas;
        } catch (Exception ex) {

        }
        return new ArrayList<>();
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

    private HorizontalBarChartModel initBarModelEgresos() {
        HorizontalBarChartModel model = new HorizontalBarChartModel();

        List<Object[]> egresosList = getEgresos();

        ChartSeries egresos = new ChartSeries();
        egresos.setLabel("Egresos");

        for (Object[] o : egresosList) {
            egresos.set(o[1].toString(), Float.parseFloat(o[0].toString()));
        }

        model.addSeries(egresos);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int anio = c.get(Calendar.YEAR);

        model.setTitle("Egresos Carreras " + anio);
        model.setLegendPosition("e");
        //model.setStacked(true);
        model.setSeriesColors("58BA27");
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Importe");
        xAxis.setMin(0);
        //xAxis.setTickAngle(45);
        xAxis.setTickInterval("100000");

        // xAxis.setMax(200);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Carrera");

        return model;
    }

    private HorizontalBarChartModel initBarModelIngresos() {
        HorizontalBarChartModel model = new HorizontalBarChartModel();

        ChartSeries ingresos = new ChartSeries();
        ingresos.setLabel("Ingresos");
        List<Object[]> ingresosList = getIngresos();

        for (Object[] o : ingresosList) {
            ingresos.set(o[1].toString(), Float.parseFloat(o[0].toString()));
        }

        model.addSeries(ingresos);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int anio = c.get(Calendar.YEAR);

        model.setTitle("Ingresos Carreras " + anio);
        model.setLegendPosition("e");
        //model.setStacked(true);

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Importe");
        xAxis.setMin(0);
        //xAxis.setTickAngle(45);
        xAxis.setTickInterval("50000");

        // xAxis.setMax(200);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Carrera");

        return model;
    }
}
