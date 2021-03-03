/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david
 */
@Entity
@Table(name = "VISITAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visitas.findAll", query = "SELECT v FROM Visitas v")
    , @NamedQuery(name = "Visitas.findById", query = "SELECT v FROM Visitas v WHERE v.id = :id")
    , @NamedQuery(name = "Visitas.findByNombre", query = "SELECT v FROM Visitas v WHERE v.nombre = :nombre")
    , @NamedQuery(name = "Visitas.findByCorreo", query = "SELECT v FROM Visitas v WHERE v.correo = :correo")
    , @NamedQuery(name = "Visitas.findByValoracion", query = "SELECT v FROM Visitas v WHERE v.valoracion = :valoracion")
    , @NamedQuery(name = "Visitas.findByMensaje", query = "SELECT v FROM Visitas v WHERE v.mensaje = :mensaje")})
public class Visitas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CORREO")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "VALORACION")
    private String valoracion;
    @Size(max = 200)
    @Column(name = "MENSAJE")
    private String mensaje;

    public Visitas() {
    }

    public Visitas(Integer id) {
        this.id = id;
    }

    public Visitas(Integer id, String nombre, String correo, String valoracion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.valoracion = valoracion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
        if (!(object instanceof Visitas)) {
            return false;
        }
        Visitas other = (Visitas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Visitas[ id=" + id + " ]";
    }
    
}
