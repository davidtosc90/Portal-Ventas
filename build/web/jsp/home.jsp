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
                    <br/><br/><br/><br/>
                    <img class="img-responsive" src="/TFC_DAW/img/logo.jpg" alt="Logo">
                </div>
                <div class="col-sm-8">
                    <h1>Bienvenid@</h1>
                    <p>Te presentamos 'El Trastero', la web donde podrás publicar esos artículos que ya 
                    no utilizas y solo ocupan espacio en tu casa, y además así recuperar parte del dinero 
                    que te gastaste en él. O bien, donde podrás comprar ese objeto que tanto deseas por un 
                    muy buen precio y aún siendo de segunda mano, en una muy buena calidad. Sin necesidad de 
                    registrarte, podrás ver todos los artículos publicados. Para poder publicar tus objetos y que 
                    otros usuarios los vean, deberás registrarte en la web (Tranquilo, solo serán unos segundos). 
                    Una vez registrado, también podrás comentar sobre los artículos, pudiendo decidir quien quieres 
                    que vea tus comentarios, si todos los usuarios, el vendedor de ese artículo o solo tú mismo. 
                    Además podrás seleccionar tus objetos favoritos, para así luego acceder a ellos de una forma 
                    más rápida. ¿A qué esperas? Corre y echa un vistazo a los últimos artículos publicados.</p>
                    <h2>Últimos artículos publicados</h2>
                    <div>${msg}</div>
                    <div>
                        <c:choose>
                            <c:when test="${!empty requestScope.ult_art}">
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
                                        <c:forEach var="art" items="${requestScope.ult_art}">
                                            <tr>
                                                <td>${art.codPostal}</td>
                                                <td>${art.categoria}</td>
                                                <td>${art.nombre}</td>
                                                <td>${art.precio}</td>
                                                <td><a href="/TFC_DAW/Art/detalle?id=${art.id}">Detalle</a></td>
                                                <% if(session.getAttribute("user") != null) { %>
                                                    <td><a href="/TFC_DAW/Art/enlaceFav?id=${art.id}&pag=home.jsp">+Favorito</a></td>
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

    </body>
</html>
