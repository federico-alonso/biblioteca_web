<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.prestamo.DtMaterial" %>
<%@ page import="cliente.prestamo.DtLibro" %>
<%@ page import="cliente.prestamo.DtArticuloEspecial" %>
<%
    String nombreLector = (String) request.getAttribute("nombreLector");
    List<DtMaterial> donaciones = (List<DtMaterial>) request.getAttribute("donaciones");
    String mensaje = (String) request.getAttribute("mensaje");
%>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Catálogo de la Biblioteca</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        body { font-family: Arial, sans-serif; background-color: #f8f9fa; }
        h1, h4 { color: #333; }
        .card-title { font-size: 1.1rem; }
        .card-text { font-size: 0.95rem; }
    </style>
</head>
<body>
<div class="container mt-4 mb-5">
    <h1 class="text-center mb-4">Catálogo de la Biblioteca</h1>

    <!-- Botón Volver -->
    <div class="text-center mb-4">
        <%
            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            String menuDestino = "lector".equals(tipoUsuario) ? "menuLector.jsp" : "menuBibliotecario.jsp";
        %>
        <a href="<%= menuDestino %>" class="btn btn-secondary">Volver al menú</a>
    </div>

    <% if (mensaje != null) { %>
        <div class="alert alert-info text-center"><%= mensaje %></div>
    <% } %>

    <% if (donaciones != null && !donaciones.isEmpty()) { %>
    <div class="row">
        <!-- Libros -->
        <div class="col-md-6 mb-4">
            <h4 class="text-primary">Libros</h4>
            <% for (DtMaterial mat : donaciones) {
                   if (mat instanceof DtLibro) {
                       DtLibro libro = (DtLibro) mat;
            %>
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title text-success">Libro: <%= libro.getTitulo() %></h5>
                    <p class="card-text">
                        <strong>Páginas:</strong> <%= libro.getCantidadPag() %>
                    </p>
                    <form action="RegistrarPrestamoServlet" method="post">
                        <input type="hidden" name="idMaterial" value="<%= libro.getId() %>" />
                        <input type="hidden" name="nombreLector" value="<%= nombreLector %>" />
                        <button type="submit" class="btn btn-sm btn-outline-primary">Solicitar préstamo</button>
                    </form>
                </div>
            </div>
            <%     }
               }
            %>
        </div>

        <!-- Artículos Especiales -->
        <div class="col-md-6 mb-4">
            <h4 class="text-primary">Artículos Especiales</h4>
            <% for (DtMaterial mat : donaciones) {
                   if (mat instanceof DtArticuloEspecial) {
                       DtArticuloEspecial art = (DtArticuloEspecial) mat;
            %>
            <div class="card mb-3 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title text-warning">Artículo Especial</h5>
                    <p class="card-text">
                        <strong>Descripción:</strong> <%= art.getDescripcion() %><br>
                        <strong>Peso:</strong> <%= art.getPesoKg() %> kg<br>
                        <strong>Dimensiones:</strong> <%= art.getDimensiones() %>
                    </p>
                    <form action="RegistrarPrestamoServlet" method="post">
                        <input type="hidden" name="idMaterial" value="<%= art.getId() %>" />
                        <input type="hidden" name="nombreLector" value="<%= nombreLector %>" />
                        <button type="submit" class="btn btn-sm btn-outline-primary">Solicitar préstamo</button>
                    </form>
                </div>
            </div>
            <%     }
               }
            %>
        </div>
    </div>
    <% } else { %>
        <div class="alert alert-warning text-center">No hay materiales disponibles en este momento.</div>
    <% } %>
</div>
</body>
</html>
