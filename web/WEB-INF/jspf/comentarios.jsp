<%@page import="java.util.List"%>
<%@page import="entidades.Comentarios"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
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