package entidades;

import entidades.Articulos;
import entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2017-01-27T16:57:27")
@StaticMetamodel(Comentarios.class)
public class Comentarios_ { 

    public static volatile SingularAttribute<Comentarios, String> texto;
    public static volatile SingularAttribute<Comentarios, Usuarios> idUser;
    public static volatile SingularAttribute<Comentarios, Integer> id;
    public static volatile SingularAttribute<Comentarios, Articulos> idArt;
    public static volatile SingularAttribute<Comentarios, String> privacidad;

}