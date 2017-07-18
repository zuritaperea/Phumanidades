/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Egresos;

import Entidades.Base.Base;
import Entidades.Carreras.Carrera;
import Entidades.Carreras.Cuenta;
import Entidades.Persona.Docente;
import Entidades.Persona.Proveedor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
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
 * @author vouilloz
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PagosDocente.findUltimoNumero",
            query = "SELECT MAX(c.numeroOrdenPago) FROM PagosDocente c WHERE c.borrado = false"),
    @NamedQuery(name = "PagosDocente.existeNumero", query = "SELECT c FROM PagosDocente c WHERE c.numeroOrdenPago =:numeroOrdenPago "
            + "AND c.borrado = false"),
    @NamedQuery(name = "PagosDocente.findByFechaCarrera", query = "SELECT p FROM PagosDocente p WHERE p.carrera=:carrera "
            + "AND p.borrado=false AND p.fechaRegistro BETWEEN :fechaIni AND :fechaFin ORDER BY p.fechaRegistro"),
    @NamedQuery(name = "PagosDocente.findByFechaCarreraDocente", query = "SELECT p FROM PagosDocente p WHERE p.carrera=:carrera "
            + "AND p.docente IS NOT NULL AND p.borrado=false AND p.fechaRegistro BETWEEN :fechaIni AND :fechaFin ORDER BY p.fechaRegistro"),
    @NamedQuery(name = "PagosDocente.UpdateBorrado", query = "UPDATE PagosDocente p SET p.borrado = :estado WHERE p.id = :id"),
    @NamedQuery(name = "PagosDocente.UpdateAnulado", query = "UPDATE PagosDocente p SET p.anulado = :estado WHERE p.id = :id"),
    @NamedQuery(name = "PagosDocente.findPagosGeneralXFecha", query = "SELECT g FROM PagosDocente g WHERE g.borrado=false AND g.anulado=false "
            + "and g.fechaRegistro BETWEEN :fechaIni AND :fechaFin ORDER BY g.fechaRegistro DESC"),
    @NamedQuery(name = "PagosDocente.findPagosXFechaDocente", query = "SELECT g FROM PagosDocente g WHERE g.borrado=false AND g.anulado=false "
            + "and g.docente IS NOT NULL AND g.fechaRegistro  BETWEEN :fechaIni AND :fechaFin ORDER BY g.fechaRegistro"),
    @NamedQuery(name = "PagosDocente.findPagosXFechaProveedor", query = "SELECT g FROM PagosDocente g WHERE g.borrado=false AND g.anulado=false "
            + "and g.proveedor IS NOT NULL AND g.fechaRegistro BETWEEN :fechaIni AND :fechaFin ORDER BY g.fechaRegistro"),
    @NamedQuery(name = "PagosDocente.findAllDesc",
            query = "SELECT c FROM PagosDocente c ORDER BY c.id DESC"),
    @NamedQuery(name = "PagosDocente.findPagosByDni", query = "SELECT p FROM PagosDocente p WHERE p.docente.dni =:dni  "
            + "AND p.borrado=false and p.anulado=false ORDER BY p.fechaRegistro DESC"),

    @NamedQuery(name = "PagosDocente.findPagosDocentesId", query = "SELECT p FROM PagosDocente p WHERE p.id =:id  AND p.borrado=false")})
@Table(name = "egresos")
public class PagosDocente extends Base implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(scale = 2, precision = 12)
    private BigDecimal monto;
    @Column(scale = 2, precision = 12)
    private BigDecimal retencionIB;
    @Column(scale = 2, precision = 12)
    private BigDecimal iva;
    @Column(scale = 2, precision = 12)
    private BigDecimal impuestoGanancia;
    @Column(scale = 2, precision = 12)
    private BigDecimal suss;
    @Column(scale = 2, precision = 12)
    private BigDecimal montoConDescuentos;
    @Lob
    private String concepto;

    @Column(nullable = false, columnDefinition = "boolean default 'false'")
    private Boolean borrado;

    @Column(nullable = false, columnDefinition = "boolean default 'false'")
    private Boolean anulado;

    @Enumerated(EnumType.STRING)
    private FormaPago formapago;
    
    @Enumerated(EnumType.STRING)
    private RubroPresupuestario rubroPresupuestario;

    @Enumerated(EnumType.STRING)
    private TipoComprobante tipocomprobante;
    private String numeroComprobante;
    @Enumerated(EnumType.STRING)
    private TipoComprobante tipocomprobante2;
    private String numeroComprobante2;
    @Enumerated(EnumType.STRING)
    private TipoComprobante tipocomprobante3;
    private String numeroComprobante3;
    @Enumerated(EnumType.STRING)
    private TipoComprobante tipocomprobante4;
    private String numeroComprobante4;
    @Enumerated(EnumType.STRING)
    private TipoComprobante tipocomprobante5;
    private String numeroComprobante5;
    @Enumerated(EnumType.STRING)
    private TipoComprobante tipocomprobante6;
    private String numeroComprobante6;

    private String numeroCheque;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRegistro;
    @OneToOne
    private Docente docente;
    @OneToOne
    private Carrera carrera;
    @OneToOne
    private Proveedor proveedor;
    @OneToOne
    private Proveedor proveedor2;
    @OneToOne
    private Proveedor proveedor3;
    @OneToOne
    private Proveedor proveedor4;
    @OneToOne
    private Proveedor proveedor5;
    @OneToOne
    private Proveedor proveedor6;
    private int numeroOrdenPago;

    @OneToOne
    private Cuenta cuenta;

    public RubroPresupuestario getRubroPresupuestario() {
        return rubroPresupuestario;
    }

    public void setRubroPresupuestario(RubroPresupuestario rubroPresupuestario) {
        this.rubroPresupuestario = rubroPresupuestario;
    }
    
    

    public int getNumeroOrdenPago() {
        return numeroOrdenPago;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    public void setNumeroOrdenPago(int numeroOrdenPago) {
        this.numeroOrdenPago = numeroOrdenPago;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public TipoComprobante getTipocomprobante2() {
        return tipocomprobante2;
    }

    public void setTipocomprobante2(TipoComprobante tipocomprobante2) {
        this.tipocomprobante2 = tipocomprobante2;
    }

    public String getNumeroComprobante2() {
        return numeroComprobante2;
    }

    public void setNumeroComprobante2(String numeroComprobante2) {
        this.numeroComprobante2 = numeroComprobante2;
    }

    public TipoComprobante getTipocomprobante3() {
        return tipocomprobante3;
    }

    public void setTipocomprobante3(TipoComprobante tipocomprobante3) {
        this.tipocomprobante3 = tipocomprobante3;
    }

    public String getNumeroComprobante3() {
        return numeroComprobante3;
    }

    public void setNumeroComprobante3(String numeroComprobante3) {
        this.numeroComprobante3 = numeroComprobante3;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Long getId() {
        return id;
    }

    public TipoComprobante getTipocomprobante4() {
        return tipocomprobante4;
    }

    public void setTipocomprobante4(TipoComprobante tipocomprobante4) {
        this.tipocomprobante4 = tipocomprobante4;
    }

    public String getNumeroComprobante4() {
        return numeroComprobante4;
    }

    public void setNumeroComprobante4(String numeroComprobante4) {
        this.numeroComprobante4 = numeroComprobante4;
    }

    public TipoComprobante getTipocomprobante5() {
        return tipocomprobante5;
    }

    public void setTipocomprobante5(TipoComprobante tipocomprobante5) {
        this.tipocomprobante5 = tipocomprobante5;
    }

    public String getNumeroComprobante5() {
        return numeroComprobante5;
    }

    public void setNumeroComprobante5(String numeroComprobante5) {
        this.numeroComprobante5 = numeroComprobante5;
    }

    public TipoComprobante getTipocomprobante6() {
        return tipocomprobante6;
    }

    public void setTipocomprobante6(TipoComprobante tipocomprobante6) {
        this.tipocomprobante6 = tipocomprobante6;
    }

    public String getNumeroComprobante6() {
        return numeroComprobante6;
    }

    public void setNumeroComprobante6(String numeroComprobante6) {
        this.numeroComprobante6 = numeroComprobante6;
    }

    public Proveedor getProveedor2() {
        return proveedor2;
    }

    public void setProveedor2(Proveedor proveedor2) {
        this.proveedor2 = proveedor2;
    }

    public Proveedor getProveedor3() {
        return proveedor3;
    }

    public void setProveedor3(Proveedor proveedor3) {
        this.proveedor3 = proveedor3;
    }

    public Proveedor getProveedor4() {
        return proveedor4;
    }

    public void setProveedor4(Proveedor proveedor4) {
        this.proveedor4 = proveedor4;
    }

    public Proveedor getProveedor5() {
        return proveedor5;
    }

    public void setProveedor5(Proveedor proveedor5) {
        this.proveedor5 = proveedor5;
    }

    public Proveedor getProveedor6() {
        return proveedor6;
    }

    public void setProveedor6(Proveedor proveedor6) {
        this.proveedor6 = proveedor6;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getRetencionIB() {
        return retencionIB;
    }

    public void setRetencionIB(BigDecimal retencionIB) {
        this.retencionIB = retencionIB;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getImpuestoGanancia() {
        return impuestoGanancia;
    }

    public void setImpuestoGanancia(BigDecimal impuestoGanancia) {
        this.impuestoGanancia = impuestoGanancia;
    }

    public BigDecimal getSuss() {
        return suss;
    }

    public void setSuss(BigDecimal suss) {
        this.suss = suss;
    }

    public BigDecimal getMontoConDescuentos() {
        return montoConDescuentos;
    }

    public void setMontoConDescuentos(BigDecimal montoConDescuentos) {
        this.montoConDescuentos = montoConDescuentos;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public FormaPago getFormapago() {
        return formapago;
    }

    public void setFormapago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public TipoComprobante getTipocomprobante() {
        return tipocomprobante;
    }

    public void setTipocomprobante(TipoComprobante tipocomprobante) {
        this.tipocomprobante = tipocomprobante;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    public Boolean getBorrado() {
        return borrado;
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
        if (!(object instanceof PagosDocente)) {
            return false;
        }
        PagosDocente other = (PagosDocente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Egresos.PagosDocente[ id=" + id + " ]";
    }

}
