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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "COMENTARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentarios.findAll", query = "SELECT c FROM Comentarios c")
    , @NamedQuery(name = "Comentarios.findById", query = "SELECT c FROM Comentarios c WHERE c.id = :id")
    , @NamedQuery(name = "Comentarios.findByTexto", query = "SELECT c FROM Comentarios c WHERE c.texto = :texto")
    , @NamedQuery(name = "Comentarios.findByPrivacidad", query = "SELECT c FROM Comentarios c WHERE c.privacidad = :privacidad")})
public class Comentarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "TEXTO")
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PRIVACIDAD")
    private String privacidad;
    @JoinColumn(name = "ID_ART", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Articulos idArt;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuarios idUser;

    public Comentarios() {
    }

    public Comentarios(Integer id) {
        this.id = id;
    }

    public Comentarios(Integer id, String texto, String privacidad) {
        this.id = id;
        this.texto = texto;
        this.privacidad = privacidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }

    public Articulos getIdArt() {
        return idArt;
    }

    public void setIdArt(Articulos idArt) {
        this.idArt = idArt;
    }

    public Usuarios getIdUser() {
        return idUser;
    }

    public void setIdUser(Usuarios idUser) {
        this.idUser = idUser;
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
        if (!(object instanceof Comentarios)) {
            return false;
        }
        Comentarios other = (Comentarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Comentarios[ id=" + id + " ]";
    }
    
}
