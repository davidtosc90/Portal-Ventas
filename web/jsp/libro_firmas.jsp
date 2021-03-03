<%@page import="entidades.Visitas"%>
<%@page import="java.util.List"%>
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
                <div class="col-sm-4">
                    
                </div>
                <div class="col-sm-6">
                    <h1>Libro de Visitas</h1>
                    <hr>
                    <div id="ver_form_visita">
                        <% if(session.getAttribute("valorado") == "Si") { %>
                            <p>Ya has valorado en esta sesión</p>
                        <% } else { %>
                            <p class="oblg">(*) Campos obligatorios</p><br/>
                            <form id="form_visita" action="/TFC_DAW/addVisita" method="POST">
                                <table>
                                    <tr>
                                        <td><p class="oblg">* <label for="nombre">Nombre:</label></p></td>
                                        <td><p><input id="nombre" type="text" name="nombre" maxlength="50" size="40" required /></p></td>
                                    </tr>
                                    <tr>
                                        <td><p class="oblg">* <label for="correo">Correo:</label></p></td>
                                        <td><p><input id="correo" type="email" name="correo" maxlength="50" size="40" required /></p></td>
                                    </tr>
                                    <% if(session.getAttribute("user") != null) { %>
                                        <tr>
                                            <td><p><label for="val">Valoración:</label></p></td>
                                            <td><p>
                                                <select id="val" name="val">
                                                    <option value="uno">1</option>
                                                    <option value="dos">2</option>
                                                    <option value="tres">3</option>
                                                    <option value="cuatro">4</option>
                                                    <option value="cinco">5</option>
                                                </select>
                                            </p></td>
                                        </tr>
                                    <% } %>
                                    <tr>
                                        <td><p><label for="mensaje">Mensaje:</label></p></td>
                                        <td><p><textarea id="mensaje" name="mensaje" rows="4" cols="50" maxlength="200"></textarea></p></td>
                                    </tr>
                                    <tr><td><p class="oblg">(*) Campos obligatorios</p><br/></td></tr>
                                    <tr>
                                        <td></td>
                                        <td><p><input id="bold" class="btn-primary" type="submit" value="Enviar Comentario" onclick="return validaFormVisita();" />  
                                               <input id="bold" class="btn-link" type="reset" value="Limpiar"></p></td>
                                    </tr>
                                </table>                
                            </form>
                        <% } %>
                    </div>
                    <hr><hr>
                    <div id="lista_visitas">
                        <h1>Comentarios y Valoraciones</h1>
                        <hr>
                        <div>${msg}</div>
                        <c:choose>
                            <c:when test="${!empty requestScope.visitas}">
                                <% int i = ((List<Visitas>)request.getAttribute("visitas")).size(); %>
                                <table>
                                    <c:forEach var="v" items="${requestScope.visitas}">
                                        <tr>
                                            <td>
                                                <p><span>#<%= i %></span><span id="n"> ${v.nombre}</span><span> (${v.correo})</span></p>
                                                <p id="sms">${v.mensaje}</p>
                                                <p>
                                                    <c:choose>
                                                        <c:when test="${v.valoracion == 'uno'}">
                                                            <span class="glyphicon glyphicon-star"></span>
                                                        </c:when>
                                                        <c:when test="${v.valoracion == 'dos'}">
                                                            <span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>
                                                        </c:when>
                                                        <c:when test="${v.valoracion == 'tres'}">
                                                            <span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>
                                                        </c:when>
                                                        <c:when test="${v.valoracion == 'cuatro'}">
                                                            <span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>
                                                        </c:when>
                                                        <c:when test="${v.valoracion == 'cinco'}">
                                                            <span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            (Usuario no registrado)
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </td>
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
        <script type="text/javascript" src="/TFC_DAW/js/validaFormVisita.js"></script>

    </body>
</html>
