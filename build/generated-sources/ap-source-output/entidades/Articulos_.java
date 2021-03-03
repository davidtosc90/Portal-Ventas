package entidades;

import entidades.Comentarios;
import entidades.Usuarios;
import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2017-01-27T16:57:27")
@StaticMetamodel(Articulos.class)
public class Articulos_ { 

    public static volatile SingularAttribute<Articulos, String> descripcion;
    public static volatile SingularAttribute<Articulos, Usuarios> idUser;
    public static volatile CollectionAttribute<Articulos, Comentarios> comentariosCollection;
    public static volatile SingularAttribute<Articulos, Double> precio;
    public static volatile SingularAttribute<Articulos, String> estado;
    public static volatile SingularAttribute<Articulos, String> categoria;
    public static volatile SingularAttribute<Articulos, String> anioAdq;
    public static volatile SingularAttribute<Articulos, Serializable> imagen;
    public static volatile SingularAttribute<Articulos, Integer> id;
    public static volatile SingularAttribute<Articulos, String> nombre;
    public static volatile SingularAttribute<Articulos, String> codPostal;

}