package entidades;

import entidades.Articulos;
import entidades.Comentarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2017-01-27T16:57:27")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile CollectionAttribute<Usuarios, Comentarios> comentariosCollection;
    public static volatile SingularAttribute<Usuarios, String> password;
    public static volatile SingularAttribute<Usuarios, String> twitter;
    public static volatile SingularAttribute<Usuarios, String> facebook;
    public static volatile SingularAttribute<Usuarios, String> direccion;
    public static volatile SingularAttribute<Usuarios, Integer> id;
    public static volatile SingularAttribute<Usuarios, String> telefono;
    public static volatile CollectionAttribute<Usuarios, Articulos> articulosCollection;
    public static volatile SingularAttribute<Usuarios, String> nombre;
    public static volatile SingularAttribute<Usuarios, String> codPostal;
    public static volatile SingularAttribute<Usuarios, String> email;

}