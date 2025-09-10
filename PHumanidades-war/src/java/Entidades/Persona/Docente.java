/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Persona;

import Entidades.Carreras.Carrera;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author vouilloz
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Docente.findByDocenteDni",
            query = "SELECT d FROM Docente d where d.dni=:dni"),

    @NamedQuery(name = "Docente.findLikeNombreApellido", query = "SELECT d FROM Docente d WHERE upper(d.nombre) LIKE :cadena "
            + "OR upper(d.apellido) LIKE :cadena ORDER BY d.apellido, d.nombre")})

public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;
    private String cbuAlias;

    @OneToOne(cascade = CascadeType.ALL)
    private Domicilio domicilio;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Telefono> telefonos;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CorreoElectronico> correosElectronicos;

    @OneToMany
    private List<Carrera> carreras;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public List<CorreoElectronico> getCorreosElectronicos() {
        return correosElectronicos;
    }

    public void setCorreosElectronicos(List<CorreoElectronico> correosElectronicos) {
        this.correosElectronicos = correosElectronicos;
    }

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    public String getCbuAlias() {
        return cbuAlias;
    }

    public void setCbuAlias(String cbuAlias) {
        this.cbuAlias = cbuAlias;
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
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return apellido +" "+nombre;
    }

}
