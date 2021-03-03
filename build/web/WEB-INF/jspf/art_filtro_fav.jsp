<%@taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<c:choose>
    <c:when test="${!empty requestScope.articulos}">
        <table class="table table-responsive table-hover">
            <thead>
                <tr>
                    <th>C�DIGO POSTAL</th>
                    <th>CATEGOR�A</th>
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
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>NO HAY ART�CULOS</p>
    </c:otherwise>
</c:choose>
