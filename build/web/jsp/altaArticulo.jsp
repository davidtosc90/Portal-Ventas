<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Artículos de Segunda Mano</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/TFC_DAW/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="/TFC_DAW/css/estilos.css" />
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
                    <img class="img-responsive" src="/TFC_DAW/img/publicar.jpg" alt="Hoja">
                </div>
                <div class="col-sm-1">
                    
                </div>
                <div class="col-sm-6">
                    <br/>
                    <h1>Publicar artículo</h1>
                    <hr>
                    <p class="oblg">(*) Campos obligatorios</p><br/>
                    <form action="/TFC_DAW/Art/add" method="POST" enctype="multipart/form-data">
                        <table>
                            <tr>
                                <td><p class="oblg">* <label for="nombre">Nombre:</label></p></td>
                                <td><p><input id="nombre" type="text" name="nombre" maxlength="50" size="40" required /></p></td>
                            </tr>
                            <tr>
                                <td><p><label for="desc">Descripción:</label></p></td>
                                <td><p><textarea id="desc" name="desc" rows="4" cols="50" maxlength="200"></textarea></p></td>
                            </tr>
                            <tr>
                                <td><p><label for="cat">Categoría:</label></p></td>
                                <td><p>
                                    <select id="cat" name="cat">
                                        <option value="Electronica">Electrónica</option>
                                        <option value="Lectura">Lectura</option>
                                        <option value="Juegos">Juegos</option>
                                        <option value="Otros">Otros</option>
                                    </select>
                                </p></td>
                            </tr>
                            <tr>
                                <td><p><label for="est">Estado:</label></p></td>
                                <td><p>
                                    <select id="est" name="est">
                                        <option value="Nuevo">Nuevo</option>
                                        <option value="Seminuevo">Seminuevo</option>
                                        <option value="Deteriorado">Deteriorado</option>
                                        <option value="Antiguo">Antiguo</option>
                                    </select>
                                </p></td>
                            </tr>
                            <tr>
                                <td><p><label for="anio">Año adquisición:</label></p></td>
                                <td><p><input id="anio" type="number" name="anio" min="0" max="9999" /></p></td>
                            </tr>
                            <tr>
                                <td><p class="oblg">* <label for="cp">Código Postal:</label></p></td>
                                <td><p><input id="cp" type="text" name="cp" maxlength="5" size="6" pattern="((0[1-9]|5[0-2])|[1-4][0-9])[0-9]{3}" title="Formato: 01xxx - 50xxx" required /></p></td>
                            </tr>
                            <tr>
                                <td><p class="oblg">* <label for="precio">Precio:</label></p></td>
                                <td><p><input id="precio" type="text" name="precio" size="6" pattern="[0-9]{1,10}.[0-9]{0,2}" title="parte entera(1-10 numeros).parte decimal(0-2 numeros)" required /> €</p></td>
                            </tr>
                            <tr>
                                <td><p><label for="file">Imagen: </label></p></td>
                                <td><p><input id="file" type="file" name="file" value="" /></p></td>
                            </tr>
                            <tr><td><p class="oblg">(*) Campos obligatorios</p><br/></td></tr>
                            <tr>
                                <td></td>
                                <td><p><input id="bold" class="btn-primary" type="submit" value="Publicar artículo" />  
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

    </body>
</html>