/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Ingresos;

import Entidades.Carreras.Cohorte;
import Entidades.Persona.Alumno;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author hugo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "InformePagoAlumno.findPagosAlumnoCohorte", query = "SELECT c FROM InformePagoAlumno c WHERE c.alumno=:alumno AND c.cohorte.id=:cohorte"),
    @NamedQuery(name = "InformePagoAlumno.findUltimaCuota",
            query = "SELECT MAX(c.nroCuota) FROM InformePagoAlumno c WHERE c.alumno=:alumno and c.cohorte=:cohorte"),
    @NamedQuery(name = "InformePagoAlumno.findPagosOrdenadosPorFecha", query = "SELECT c FROM InformePagoAlumno c ORDER BY c.fecha DESC ")})
@Table(name = "informe_pago_alumno")
public class InformePagoAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Alumno alumno;
    @OneToOne
    private Cohorte cohorte;
    private Integer nroCuota;
    private Integer cantidadCuotas;
    private String descripcion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha;
    private String nombreComprobantePago;
    @Lob
    private byte[] comprobantePago;
    @Enumerated(EnumType.STRING)
    private EstadoComprobanteAlumno estadoComprobanteAlumno;
    @OneToOne
    private TipoIngreso tipoIngreso;
    @Lob
    private String mensajeAlumno;
    @Lob
    private String respuestaSistema;
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Cohorte getCohorte() {
        return cohorte;
    }

    public void setCohorte(Cohorte cohorte) {
        this.cohorte = cohorte;
    }

    public Integer getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(Integer nroCuota) {
        this.nroCuota = nroCuota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public byte[] getComprobantePago() {
        return comprobantePago;
    }

    public void setComprobantePago(byte[] comprobantePago) {
        this.comprobantePago = comprobantePago;
    }

    public EstadoComprobanteAlumno getEstadoComprobanteAlumno() {
        return estadoComprobanteAlumno;
    }

    public void setEstadoComprobanteAlumno(EstadoComprobanteAlumno estadoComprobanteAlumno) {
        this.estadoComprobanteAlumno = estadoComprobanteAlumno;
    }

    public String getNombreComprobantePago() {
        return nombreComprobantePago;
    }

    public void setNombreComprobantePago(String nombreComprobantePago) {
        this.nombreComprobantePago = nombreComprobantePago;
    }

    public TipoIngreso getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(TipoIngreso tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public String getMensajeAlumno() {
        return mensajeAlumno;
    }

    public void setMensajeAlumno(String mensajeAlumno) {
        this.mensajeAlumno = mensajeAlumno;
    }

    public String getRespuestaSistema() {
        return respuestaSistema;
    }

    public void setRespuestaSistema(String respuestaSistema) {
        this.respuestaSistema = respuestaSistema;
    }

    public Integer getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(Integer cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformePagoAlumno)) {
            return false;
        }
        InformePagoAlumno other = (InformePagoAlumno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Ingresos.InformePagoAlumno[ id=" + id + " ]";
    }

}
