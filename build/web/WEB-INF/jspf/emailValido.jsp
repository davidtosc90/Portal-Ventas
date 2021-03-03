<% if ("No".equals(request.getAttribute("emailValido"))) { %>
    <span class="error">Email no válido, ya existe</span>
<% } else { %>
    <span class="ok">OK</span>
<% } %>