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
                <div class="col-sm-1">
                    
                </div>
                <div class="col-sm-2">
                    <br/><br/><br/><br/><br/>
                    <img class="img-responsive" src="/TFC_DAW/img/user.jpg" alt="Usuario">
                </div>
                <div class="col-sm-1">
                    
                </div>
                <div class="col-sm-6">
                    <br/>
                    <h1>Formulario de alta</h1>
                    <hr>
                    <div>${msg}</div>
                    <p class="oblg">(*) Campos obligatorios</p><br/>
                    <form action="/TFC_DAW/User/add" method="POST">
                        <table>
                            <tr>
                                <td><p class="oblg">* <label for="email">E-mail:</label></p></td>
                                <td><p><input id="email" type="email" name="email" maxlength="50" size="40" onchange="validarEmailDB();" required />
                                        <span id="emailValido"></span></p></td>
                            </tr>
                            <tr>
                                <td><p class="oblg">* <label for="clave">Clave:</label></p></td>
                                <td><p><input id="clave" type="password" name="clave" maxlength="20" required /></p></td>
                            </tr>
                            <tr>
                                <td><p class="oblg">* <label for="clave2">Repetir clave:</label></p></td>
                                <td><p><input id="clave2" type="password" name="clave2" maxlength="20" required />
                                        <span id="claveValida"></span></p></td>
                            </tr>
                            <tr>
                                <td><p class="oblg">* <label for="nombre">Nombre:</label></p></td>
                                <td><p><input id="nombre" type="text" name="nombre" maxlength="50" size="40" required /></p></td>
                            </tr>
                            <tr>
                                <td><p class="oblg">* <label for="dire">Dirección:</label></p></td>
                                <td><p><input id="dire" type="text" name="dire" maxlength="100" size="40" required /></p></td>
                            </tr>
                            <tr>
                                <td><p class="oblg">* <label for="cp">Código Postal:</label></p></td>
                                <td><p><input id="cp" type="text" name="cp" maxlength="5" size="6" pattern="((0[1-9]|5[0-2])|[1-4][0-9])[0-9]{3}" title="Formato: 01xxx - 50xxx" required /></p></td>
                            </tr>
                            <tr>
                                <td><p><label for="fb">Facebook:</label></p></td>
                                <td><p><input id="fb" type="text" name="fb" maxlength="50" size="40" pattern="^[a-z\d\.]{5,}$" title="No parece un nombre de usuario de Facebook válido" /></p></td>
                            </tr>
                            <tr>
                                <td><p><label for="twt">Twitter:</label></p></td>
                                <td><p><input id="twt" type="text" name="twt" maxlength="50" size="40" pattern="^[A-Za-z0-9_]{1,15}$" title="No parece un nombre de usuario de Twitter válido" /></p></td>
                            </tr>
                            <tr>
                                <td><p class="oblg">* <label for="tel">Teléfono:</label></p></td>
                                <td><p><input id="tel" type="tel" name="tel" maxlength="9" pattern="[0-9]{9}" title="9 números" required /></p></td>
                            </tr>
                            <tr><td><p class="oblg">(*) Campos obligatorios</p><br/></td></tr>
                            <tr>
                                <td></td>
                                <td><p><input id="bold" class="btn-primary" type="submit" value="Darse de alta" onclick="return validaFormUser();" />  
                                       <input id="bold" class="btn-link" type="reset" value="Limpiar"></p></td>
                            </tr>
                        </table>                
                    </form>
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
        <script type="text/javascript" src="/TFC_DAW/js/validaFormUsuario.js"></script>

    </body>
</html>