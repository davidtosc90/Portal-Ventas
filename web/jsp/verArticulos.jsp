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
                    <h1>Listado de artículos publicados</h1>
                    <hr>
                    <div>${msg}</div>
                    <div>
                        <form>
                            <fieldset class="form-group">
                                <legend>Filtros</legend>
                                <label for="cat">Categoría: </label>
                                <select id="cat" name="cat">
                                    <option value="todas">Todas</option>
                                    <option value="Electronica">Electrónica</option>
                                    <option value="Lectura">Lectura</option>
                                    <option value="Juegos">Juegos</option>
                                    <option value="Otros">Otros</option>
                                </select> | 
                                <label for="cod">Código Postal: </label>
                                <input id="cod" type="text" name="cod" size="5" maxlength="5" /> |
                                <label for="pmenor">Precio: </label>
                                <input id="pmenor" type="text" name="menor" size="4" title="Formato: 99.99" /> -
                                <input id="pmayor" type="text" name="mayor" size="4" title="Formato: 99.99" /> |
                                <input type="button" id="bold" class="btn-primary" value="Filtrar" onclick="filtrar();" />
                                <input type="reset" id="bold" class="btn-link" value="Limpiar" />
                            </fieldset>
                        </form>
                    </div>
                    <div id="respuesta">
                        <c:choose>
                            <c:when test="${!empty requestScope.articulos}">
                                <table class="table table-responsive table-hover">
                                    <thead>
                                        <tr>
                                            <th>CÓDIGO POSTAL</th>
                                            <th>CATEGORÍA</th>
                                            <th>NOMBRE</th>
                                            <th>PRECIO</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="art" items="${requestScope.articulos}">
                                            <tr>
                                                <td>${art.codPostal}</td>
                                                <td>${art.categoria}</td>
                                                <td>${art.nombre}</td>
                                                <td>${art.precio}</td>
                                                <td><a href="/TFC_DAW/Art/detalle?id=${art.id}">Detalle</a></td>
                                                <% if(session.getAttribute("user") != null) { %>
                                                    <td><a href="/TFC_DAW/Art/enlaceFav?id=${art.id}&pag=verArticulos.jsp">+Favorito</a></td>
                                                <% } %>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p>NO HAY ARTÍCULOS</p>
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
