<%@page import="java.util.List"%>
<%@page import="entidades.Comentarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Artículos de Segunda Mano</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/TFC_DAW/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="/TFC_DAW/css/estilos.css" />
        <%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    </head>

    <body>

        <div id="contenedor">
            <header class="jumbotron" id="cabecera">
                <h1>El Trastero</h1>
                <h2>Portal de venta de artículos de segunda mano</h2>
            </header>

            <%@include file="../WEB-INF/jspf/menu.jspf" %>

            <article id="principal">
                <div class="col-sm-2">
                    
                </div>
                <div class="col-sm-8">
                    <br/>
                    <div id="datos_art">
                        <table class="espaciada">
                            <tr>
                                <td><h1>Detalles del artículo</h1></td>
                                <td>
                                    <div id='btn_fav'>
                                    <% if(session.getAttribute("user") != null) { 
                                            if(request.getAttribute("favorito").equals("Si")) { %>
                                                <button id="fav" type="button" class="btn btn-primary" disabled>Añadido a 'Mis artículos de interés'</button>
                                            <% } else { %>
                                                <button id="fav" type="button" class="btn btn-primary" onclick="botonFav();">Añadir a 'Mis artículos de interés'</button>
                                            <% }
                                        } 
                                    %>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <hr>
                        <c:choose>
                            <c:when test="${!empty requestScope.articulo}">
                                <c:set var="art" value="${requestScope.articulo}"/>
                                <table class="espaciada">
                                    <tr class="oculto">
                                        <td><label>Id:</label></td>
                                        <td><p><input id="id_art" type="text" disabled value="${art.id}" /></p></td>
                                    </tr>
                                    <tr>
                                        <td><label>Nombre:</label></td>
                                        <td><p><input type="text" size="40" disabled value="${art.nombre}" /></p></td>
                                        <td><label>Estado:</label></td>
                                        <td><p><input type="text" size="15" disabled value="${art.estado}" /></p></td>
                                    </tr>
                                    <tr>
                                        <td><label>Precio:</label></td>
                                        <td><p><input type="text" size="10" disabled value="${art.precio}" /> €</p></td>
                                        <td><label>Código postal:</label></td>
                                        <td><p><input type="text" size="10" disabled value="${art.codPostal}" /></p></td>
                                    </tr>
                                    <tr>
                                        <td><label>Categoría:</label></td>
                                        <td><p><input type="text" size="10" disabled value="${art.categoria}" /></p></td>
                                        <td><label>Año adquisición:</label></td>
                                        <td><p><input type="text" size="10" disabled value="${art.anioAdq}" /></p></td>
                                    </tr>
                                    <tr>
                                        <td><label>Descripción:</label></td>
                                        <td><p><textarea rows="4" cols="50" disabled>${art.descripcion}</textarea></p></td>
                                    </tr>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p class="error">ERROR AL ENVIAR EL ARTÍCULO</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div id="datos_user">
                        <h1>Detalles del vendedor</h1>
                        <hr>
                        <c:choose>
                            <c:when test="${!empty requestScope.vendedor}">
                                <c:set var="user" value="${requestScope.vendedor}"/>
                                <table class="espaciada">
                                    <tr class="oculto">
                                        <td><label>Id:</label></td>
                                        <td><p><input id="id_user" type="text" disabled value="${user.id}" /></p></td>
                                    </tr>
                                    <tr>
                                        <td><label>Nombre:</label></td>
                                        <td><p><input type="text" size="40" disabled value="${user.nombre}" /></p></td>
                                        <td><label>Facebook:</label></td>
                                        <td><p><input type="text" size="40" disabled value="${user.facebook}" /></p></td>
                                    </tr>
                                    <tr>
                                        <td><label>E-mail:</label></td>
                                        <td><p><input type="text" size="40" disabled value="${user.email}" /></p></td>
                                        <td><label>Twitter:</label></td>
                                        <td><p><input type="text" size="40" disabled value="${user.twitter}" /></p></td>
                                    </tr>
                                    <tr>
                                        <td><label>Dirección:</label></td>
                                        <td><p><input type="text" size="40" disabled value="${user.direccion}" /></p></td>
                                        <td><label>Teléfono:</label></td>
                                        <td><p><input type="text" size="10" disabled value="${user.telefono}" /></p></td>
                                    </tr>
                                    <tr>
                                        <td><label>Código postal:</label></td>
                                        <td><p><input type="text" size="10" disabled value="${user.codPostal}" /></p></td>
                                    </tr>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p class="error">ERROR AL ENVIAR DATOS DEL VENDEDOR</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    </br></br>
                    <div id="form_coment">
                        <form>
                            <fieldset class="form-group">
                                <legend>Añade tu comentario sobre este artículo:</legend>
                                <c:choose>
                                    <c:when test="${!empty sessionScope.user}">
                                        <c:set var="user_log" value="${sessionScope.user}"/>
                                        <c:set var="art" value="${requestScope.articulo}"/>
                                        <table class="espaciada2">
                                            <tr>
                                                <td><p><textarea id="texto" name="texto" rows="4" cols="50" maxlength="200" placeholder="Inserte aquí su comentario..." ></textarea></p></td>
                                                <td><p><label for="priv">Privacidad:</label>
                                                    <select id="priv" name="priv">
                                                        <option value="publico">Público</option>
                                                        <option value="vendedor">Para vendedor</option>
                                                        <option value="privado">Privado</option>
                                                    </select>
                                                    <span class="glyphicon glyphicon-info-sign" title="Público --> visible para todos&#10;Para vendedor --> visible para quien publica el artículo e inserta el comentario&#10;Privado --> visible solo para quien inserta el comentario"></span></p>
                                                    <p class="oculto"><input type="text" id="id_user_c" name="id_user_c" value="${user_log.id}" /></p>
                                                    <p class="oculto"><input type="text" id="id_art_c" name="id_art_c" value="${art.id}" /></p>
                                                    <p><input id="bold" class="btn-primary" type="button" value="Publicar comentario" onclick="insertarComentario();" />  
                                                        <input id="bold" class="btn-link" type="reset" value="Limpiar">
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                    </c:when>
                                    <c:otherwise>
                                        <p>REGÍSTRATE O INICIA SESIÓN PARA COMENTAR ESTE ARTÍCULO</p>
                                    </c:otherwise>
                                </c:choose>
                            </fieldset>
                        </form>
                    </div>
                    <div id="lista_coment">
                        <br/>
                        <h1>Comentarios sobre este artículo</h1>
                        <hr>
                        <div>${msg}</div>
                        <c:choose>
                            <c:when test="${!empty requestScope.comentarios}">
                                <% int i = ((List<Comentarios>)request.getAttribute("comentarios")).size(); %>
                                <table>
                                    <c:forEach var="c" items="${requestScope.comentarios}">
                                        <tr>
                                            <td><p>#<%= i %> ${c.texto}</p></td>
                                        </tr>
                                        <% i--; %>
                                    </c:forEach>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p>NO HAY COMENTARIOS</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <br/><br/><br/><br/>
                </div>
                <div class="col-sm-2">
                    
                </div>
            </article>

            <footer id="pie">
                <p>&copy; David Toscano Rodríguez, 2017 | Todos los derechos reservados</p>
            </footer>
            
        </div>
            
        <script type="text/javascript" src="/TFC_DAW/js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="/TFC_DAW/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/TFC_DAW/js/funciones.js"></script>

    </body>
</html>