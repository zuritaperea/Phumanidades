/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Ingresos;

import Entidades.Base.Base;
import Entidades.Carreras.Cohorte;
import Entidades.Carreras.Cuenta;
import Entidades.Egresos.FormaPago;
import Entidades.Persona.Alumno;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author franco
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Ingreso.findCuotasAlumno",
            query = "SELECT c FROM Ingreso c WHERE c.alumno=:alumno AND c.borrado=false AND c.anulado=false ORDER BY c.id DESC"),
    @NamedQuery(name = "Ingreso.findCuotasAlumnoGeneral",
            query = "SELECT c FROM Ingreso c WHERE c.alumno=:alumno AND c.cohorte is null AND c.borrado=false AND c.anulado=false ORDER BY c.id DESC"),
    @NamedQuery(name = "Ingreso.findCuotasAlumnoCohorte",
            query = "SELECT c FROM Ingreso c WHERE c.alumno=:alumno and c.cohorte=:cohorte  "
            + "AND c.borrado=false AND c.anulado=false ORDER BY c.id DESC"),
    @NamedQuery(name = "Ingreso.findUltimaCuotaAlumno",
            query = "SELECT MAX(c.cuota) FROM Ingreso c WHERE c.alumno=:alumno AND c.borrado=false AND c.anulado=false "),
    @NamedQuery(name = "Ingreso.findUltimaCuotaAlumnoCohorte",
            query = "SELECT MAX(c.cuota) FROM Ingreso c WHERE c.alumno=:alumno and c.cohorte=:cohorte  AND c.borrado=false AND c.anulado=false "),
    @NamedQuery(name = "Ingreso.findFechaUltimaCuotaAlumno",
            query = "SELECT MAX(c.fechaPago) FROM Ingreso c WHERE c.alumno=:alumno AND c.borrado=false AND c.anulado=false"),
    @NamedQuery(name = "Ingreso.UpdateBorrado", query = "UPDATE Ingreso c SET c.borrado = :estado WHERE c.id = :id"),
    @NamedQuery(name = "Ingreso.UpdateAnulado", query = "UPDATE Ingreso c SET c.anulado = :estado WHERE c.id = :id"),

    @NamedQuery(name = "Ingreso.findByFechaCohorte", query = "SELECT i FROM Ingreso i WHERE i.cohorte = :cohorte AND "
            + "i.borrado=false AND i.anulado=false and i.cohorte is not null AND (i.fechaPago BETWEEN :fechaIni AND :fechaFin)  "
            + "and i.importe > 0 ORDER BY i.fechaPago"),
    @NamedQuery(name = "Ingreso.findByFecha", query = "SELECT i FROM Ingreso i WHERE i.borrado=false "
            + "and i.cohorte is not null AND (i.fechaPago BETWEEN :fechaIni AND :fechaFin) "
            + "and i.importe > 0 ORDER BY i.fechaPago"),
    @NamedQuery(name = "Ingreso.findAllNoCerrados", query = "SELECT g FROM Ingreso g WHERE g.borrado=false AND g.anulado=false "
            + " and g.fechaCierre IS NULL ORDER BY g.fechaPago DESC"),
    @NamedQuery(name = "Ingreso.findNoCerradosFecha", query = "SELECT g FROM Ingreso g WHERE g.borrado=false AND g.anulado=false "
            + " and g.fechaCierre IS NULL AND g.fechaPago <= :fechaCierre ORDER BY g.fechaPago DESC"),
    @NamedQuery(name = "Ingreso.findByFechaGenerales", query = "SELECT i FROM Ingreso i WHERE i.borrado=false AND i.anulado=false "
            + "AND (i.fechaPago BETWEEN :fechaIni AND :fechaFin) and i.importe > 0 ORDER BY i.fechaPago"),
    @NamedQuery(name = "Ingreso.findCobrosByDni",
            query = "SELECT c FROM Ingreso c WHERE c.alumno.dni=:dni and c.borrado = false AND c.anulado=false "),
    @NamedQuery(name = "Ingreso.findAllDesc",
            query = "SELECT c FROM Ingreso c ORDER BY c.id DESC"),
    @NamedQuery(name = "Ingreso.findCobrosByDniOTexto",
            query = "SELECT c FROM Ingreso c WHERE c.alumno.dni=:dni or c.alumno.apellido "
            + "like :texto or c.alumno.nombre like :texto"),
    @NamedQuery(name = "Ingreso.findCobrosGeneralXFecha", query = "SELECT c FROM Ingreso c "
            + "WHERE (c.fechaPago BETWEEN :fechaIni AND :fechaFin) and c.cohorte "
            + "is null  and c.importe > 0 AND c.anulado = false AND c.borrado = false ORDER BY c.fechaPago"),
    @NamedQuery(name = "Ingreso.findCobrosGeneralXFechaTipo", query = "SELECT c FROM Ingreso c "
            + "WHERE (c.fechaPago BETWEEN :fechaIni AND :fechaFin) and c.cohorte "
            + "is null and c.tipoIngreso = :tipoIngreso and c.importe > 0 AND c.anulado = false AND c.borrado = false ORDER BY c.fechaPago"),
    @NamedQuery(name = "Ingreso.findCobrosXFecha", query = "SELECT c FROM Ingreso c "
            + "WHERE (c.fechaPago BETWEEN :fechaIni AND :fechaFin) and c.importe > 0 "
            + "ORDER BY c.fechaPago"),
    @NamedQuery(name = "Ingreso.ConsultaUltimaCuotaAlumno",
            query = "select distinct todos.alumno, todos.cohorte, todos.fechaPago, "
            + "todos.cuota from Ingreso todos where "
            + "todos.cuota = (select max(latest.cuota) "
            + "from Ingreso latest where latest.alumno = todos.alumno and latest.cuota > 0)"),
    @NamedQuery(name = "Ingreso.ConsultaUltimaCuotaAlumnoCohorte",
            query = "select distinct todos.alumno, todos.cohorte, todos.fechaPago, "
            + "todos.cuota from Ingreso todos where "
            + "todos.cuota = (select max(latest.cuota) "
            + "from Ingreso latest where latest.alumno = todos.alumno and latest.cuota > 0) AND todos.cohorte=:cohorte "
                    + "UNION select distinct a.alumno, a.cohorte, CAST(NULL AS TIMESTAMP), 0 from InscripcionAlumnos a WHERE a.cohorte=:cohorte "
                    + "and a.alumno not in (select i.alumno from Ingreso i where i.cohorte=:cohorte)"),
    @NamedQuery(name = "Ingreso.existeNumeroRecibo", query = "SELECT i FROM Ingreso i "
            + "WHERE i.numeroRecibo =:numero AND i.cuenta=:cuenta AND "
            + "i.borrado=false AND FUNC('DATE_PART','YEAR', i.fechaPago) =:anio "
            + " ORDER BY i.cuota DESC"),
    @NamedQuery(name = "Ingreso.findUltimoNumero",
            query = "SELECT MAX(i.numeroRecibo) FROM Ingreso i WHERE  i.cuenta=:cuenta AND "
            + "i.borrado=false AND i.anulado = false AND FUNC('DATE_PART','YEAR', i.fechaPago) =:anio "),
    @NamedQuery(name = "Ingreso.findAllByNumeroRecibo", query = "SELECT i FROM Ingreso i "
            + "WHERE i.numeroRecibo =:numero AND i.cuenta=:cuenta AND "
            + "i.borrado=false AND i.anulado = false AND FUNC('DATE_PART','YEAR', i.fechaPago) =:anio "
            + " ORDER BY i.cuota DESC"),
    @NamedQuery(name = "Ingreso.findAllByCuenta", query = "SELECT i FROM Ingreso i "
            + "WHERE i.cuenta=:cuenta AND "
            + "i.borrado=false AND FUNC('DATE_PART','YEAR', i.fechaPago) =:anio "
            + " ORDER BY i.id DESC")})
@Table(name = "ingresos")

public class Ingreso extends Base implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cuota;
    @Column(scale = 2, precision = 10)
    private BigDecimal importe;
    private String concepto;
    private String nombre;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPago;
    //Para el caso en que la forma de pago sea deposito, se deberá ingresa la fecha del deposito
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDeposito;

    @Column(nullable = false, columnDefinition = "boolean default 'false'")
    private Boolean borrado;
    @Column(nullable = false, columnDefinition = "boolean default 'false'")
    private Boolean anulado;

    @Column(name = "numero_recibo")
    private int numeroRecibo;
    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @OneToOne
    private Alumno alumno;

    @OneToOne
    private Cohorte cohorte;

    @OneToOne
    private Cuenta cuenta;

    @OneToOne
    private TipoIngreso tipoIngreso;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCierre;

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public TipoIngreso getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(TipoIngreso tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public Long getId() {
        return id;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    public Cohorte getCohorte() {
        return cohorte;
    }

    public void setCohorte(Cohorte cohorte) {
        this.cohorte = cohorte;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public int getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(int numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
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
        if (!(object instanceof Ingreso)) {
            return false;
        }
        Ingreso other = (Ingreso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + " " + cohorte;
    }

}
