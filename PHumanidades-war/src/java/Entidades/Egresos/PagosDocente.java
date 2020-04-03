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
    @NamedQuery(name = "PagosDocente.findPagosByNumeroOrdenPago", query = "SELECT p FROM PagosDocente p WHERE p.numeroOrdenPago=:numeroOrdenPago "
            + "AND p.borrado=false AND p.anulado=false ORDER BY p.id ASC"),
    @NamedQuery(name = "PagosDocente.findPagosByNumeroOrdenPagoAnio", query = "SELECT p FROM PagosDocente p WHERE p.numeroOrdenPago=:numeroOrdenPago "
            + "AND p.borrado=false AND p.anulado=false AND FUNC('DATE_PART','YEAR', p.fechaRegistro) =:anio  ORDER BY p.id ASC"),
    @NamedQuery(name = "PagosDocente.findPagosByNumeroOrdenPagoAnioAnulado", query = "SELECT p FROM PagosDocente p WHERE p.numeroOrdenPago=:numeroOrdenPago "
            + "AND p.borrado=false AND p.anulado=true AND FUNC('DATE_PART','YEAR', p.fechaRegistro) =:anio  ORDER BY p.id ASC"),
    @NamedQuery(name = "PagosDocente.findPagosByNumeroOrdenPagoAnioBorrado", query = "SELECT p FROM PagosDocente p WHERE p.numeroOrdenPago=:numeroOrdenPago "
            + "AND p.borrado=true AND p.anulado=false AND FUNC('DATE_PART','YEAR', p.fechaRegistro) =:anio  ORDER BY p.id ASC"),
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
    @NamedQuery(name = "PagosDocente.findPagosXFechaProveedorYCuenta", query = "SELECT g FROM PagosDocente g WHERE g.borrado=false AND g.anulado=false "
            + "and g.proveedor IS NOT NULL and g.fechaRegistro BETWEEN :fechaIni AND :fechaFin AND g.cuenta=:cuenta ORDER BY g.fechaRegistro DESC"),
    @NamedQuery(name = "PagosDocente.findAllNoCerrados", query = "SELECT g FROM PagosDocente g WHERE g.borrado=false AND g.anulado=false "
            + " and g.fechaCierre IS NULL ORDER BY g.fechaRegistro DESC"),
    @NamedQuery(name = "PagosDocente.findNoCerradosFecha", query = "SELECT g FROM PagosDocente g WHERE g.borrado=false AND g.anulado=false "
            + " and g.fechaCierre IS NULL AND g.fechaRegistro <= :fechaCierre ORDER BY g.fechaRegistro DESC"),
    @NamedQuery(name = "PagosDocente.findAllDesc",
            query = "SELECT c FROM PagosDocente c ORDER BY c.id DESC"),
    @NamedQuery(name = "PagosDocente.findPagosByDni", query = "SELECT p FROM PagosDocente p WHERE p.docente.dni =:dni  "
            + "AND p.borrado=false and p.anulado=false ORDER BY p.fechaRegistro DESC"),
    @NamedQuery(name = "PagosDocente.findPagosByComprobante", query = "SELECT p FROM PagosDocente p WHERE p.numeroComprobante = :numeroComprobante AND "
            + "p.fechaComprobante=:fechaComprobante AND (p.docente=:docente or p.proveedor=:proveedor)  "
            + "AND p.borrado=false and p.anulado=false ORDER BY p.fechaRegistro DESC"),
    @NamedQuery(name = "PagosDocente.findPagosByTipoEgreso", query = "SELECT p FROM PagosDocente p WHERE p.tipoEgreso=:tipo "
            + "AND p.borrado=false and p.anulado=false ORDER BY p.fechaRegistro DESC"),
    @NamedQuery(name = "PagosDocente.findPagosDocentesId", query = "SELECT p FROM PagosDocente p WHERE p.id =:id  AND p.borrado=false"),
    @NamedQuery(name = "PagosDocente.findProveedorTodos", query = "SELECT p FROM PagosDocente p WHERE p.proveedor =:proveedor")})
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

    private String numeroCheque;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRegistro;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaComprobante;
    @OneToOne
    private Docente docente;
    @OneToOne
    private Carrera carrera;
    @OneToOne
    private Proveedor proveedor;

    private int numeroOrdenPago;

    @OneToOne
    private Cuenta cuenta;
    @OneToOne
    private TipoEgreso tipoEgreso;
    @Column(scale = 2, precision = 12)
    private BigDecimal importeComprobante;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCierre;

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public TipoEgreso getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(TipoEgreso tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }

    public RubroPresupuestario getRubroPresupuestario() {
        return rubroPresupuestario;
    }

    public void setRubroPresupuestario(RubroPresupuestario rubroPresupuestario) {
        this.rubroPresupuestario = rubroPresupuestario;
    }

    public Date getFechaComprobante() {
        return fechaComprobante;
    }

    public void setFechaComprobante(Date fechaComprobante) {
        this.fechaComprobante = fechaComprobante;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getImporteComprobante() {
        return importeComprobante;
    }

    public void setImporteComprobante(BigDecimal importeComprobante) {
        this.importeComprobante = importeComprobante;
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
