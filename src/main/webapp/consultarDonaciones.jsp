<%@ page import="cliente.consultarDonacion.DtMaterial" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.consultarDonacion.DtLibro" %>
<%@ page import="cliente.consultarDonacion.DtArticuloEspecial" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Consultar Donaciones</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-4">
    <h3>Donaciones registradas</h3>

    <form class="mb-3" method="post" action="ConsultarDonacionesServlet">
        <button type="submit" class="btn btn-primary">Cargar donaciones</button>
        <a href="menuBibliotecario.jsp" class="btn btn-link">Volver</a>
    </form>

    <%
        List<DtMaterial> donaciones = (List<DtMaterial>) request.getAttribute("donaciones");
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="alert alert-danger"><%= error %></div>
    <%
        }
        if (donaciones != null) {
            if (donaciones.isEmpty()){
    %>
            <div class="alert alert-warning">No se encontraron donaciones.</div>
    <%
            } else {
    %>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>Tipo</th>
            <th>Detalles</th>
        </tr>
        </thead>
        <tbody>
        <%
            int idx = 1;
            for (DtMaterial donacion : donaciones) {
        %>
        <tr>
            <td><%= idx++ %></td>
            <% if (donacion instanceof DtLibro) {
                DtLibro libro = (DtLibro) donacion;
            %>
            <td>Libro</td>
            <td><strong>Título:</strong> <%= libro.getTitulo() %><br>
                <strong>Páginas:</strong> <%= libro.getCantidadPag() %>
            </td>
            <% } else if (donacion instanceof DtArticuloEspecial) {
                DtArticuloEspecial especial = (DtArticuloEspecial) donacion;
            %>
            <td>Artículo Especial</td>
            <td><strong>Descripción:</strong> <%= especial.getDescripcion() %><br>
                <strong>Peso:</strong> <%= especial.getPesoKg() %> kg<br>
                <strong>Dimensiones:</strong> <%= especial.getDimensiones() %>
            </td>
            <% } else { %>
            <td>Material</td>
            <td>ID: <%= donacion.getId() %>
            </td>
            <% } %>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <%-- Bloque de Paginación --%>
    <nav>
        <ul class="pagination">
            <%
                Integer currentPage = (Integer) request.getAttribute("currentPage");
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                if (currentPage != null && totalPages != null && totalPages > 1) {
                    // Botón Anterior
                    if (currentPage > 1) {
            %>
            <li class="page-item"><a class="page-link" href="ConsultarDonacionesServlet?page=<%= currentPage - 1 %>">Anterior</a></li>
            <%
                    } else {
            %>
            <li class="page-item disabled"><a class="page-link" href="#">Anterior</a></li>
            <%
                    }

                    // Números de Página
                    for (int i = 1; i <= totalPages; i++) {
                        if (i == currentPage) {
            %>
            <li class="page-item active"><a class="page-link" href="#"><%= i %></a></li>
            <%
                        } else {
            %>
            <li class="page-item"><a class="page-link" href="ConsultarDonacionesServlet?page=<%= i %>"><%= i %></a></li>
            <%
                        }
                    }

                    // Botón Siguiente
                    if (currentPage < totalPages) {
            %>
            <li class="page-item"><a class="page-link" href="ConsultarDonacionesServlet?page=<%= currentPage + 1 %>">Siguiente</a></li>
            <%
                    } else {
            %>
            <li class="page-item disabled"><a class="page-link" href="#">Siguiente</a></li>
            <%
                    }
                }
            %>
        </ul>
    </nav>
    <%-- Fin Bloque de Paginación --%>

    <%
            }
        }
    %>
</div>
</body>
</html>


