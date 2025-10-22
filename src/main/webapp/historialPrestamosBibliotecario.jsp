<%@ page import="cliente.prestamo.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Historial Prestamos Bibliotecario</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-4">
   <h3>Historial Prestamos Bibliotecario</h3>

    <form class="mb-3" method="post" action="HistorialPrestamosBibliotecarioServlet">
        <button type="submit" class="btn btn-primary">Cargar Historial</button>
        <a href="menuBibliotecario.jsp" class="btn btn-link">Volver</a>
    </form>

    <%
        List<DtPrestamo> prestamos = (List<DtPrestamo>) request.getAttribute("prestamosBibliotecario");
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="alert alert-danger"><%= error %></div>
    <%
        }
        if (prestamos != null) {
            if (prestamos.isEmpty()){
    %>
            <div class="alert alert-warning">El historial está vacío.</div>
    <%
            } else {
    %>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>ID Préstamo</th>
            <th>ID Material</th>
            <th>Bibliotecario</th>
            <th>Lector</th>
            <th>F. Inicio</th>
            <th>F. Fin</th>
            <th>Estado</th>
        </tr>
        </thead>
        <tbody>
        <%
            int idx = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            for (DtPrestamo prestamo : prestamos) {
                Date fechaSol = prestamo.getFechaSolicitud() != null
                        ? prestamo.getFechaSolicitud().toGregorianCalendar().getTime()
                        : null;

                String fechaStrSoli = (fechaSol != null)
                        ? sdf.format(fechaSol)
                        : "No disponible";

                Date fechaDev = prestamo.getFechaDevolucion() != null
                        ? prestamo.getFechaDevolucion().toGregorianCalendar().getTime()
                        : null;

                String fechaStrDev = (fechaDev != null)
                        ? sdf.format(fechaDev)
                        : "No disponible";
        %>
        <tr>
            <td><%= idx++ %></td>
            <td><%= prestamo.getId() %></td>
            <td><%= prestamo.getMaterial().getId() %></td>
            <td><%= prestamo.getBibliotecario().getNombre() %></td>
            <td><%= prestamo.getLector().getNombre() %></td>
            <td><%= fechaStrSoli %></td>
            <td><%= fechaStrDev %></td>
            <td><%= prestamo.getEstado() %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <%-- Bloque de paginación --%>
    <%
        Integer currentPage = (Integer) request.getAttribute("currentPage");
        Integer totalPages = (Integer) request.getAttribute("totalPages");
        String baseUrl = "HistorialPrestamosBibliotecarioServlet";

        if (currentPage != null && totalPages != null && totalPages > 1) {
    %>
    <nav>
        <ul class="pagination">
            <%-- Botón anterior --%>
            <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                <a class="page-link" href="<%= (currentPage > 1) ? baseUrl + "?page=" + (currentPage - 1) : "#" %>">
                    Anterior
                </a>
            </li>

            <%-- Números de página --%>
            <%
                for (int i = 1; i <= totalPages; i++) {
            %>
            <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                <a class="page-link" href="<%= (i == currentPage) ? "#" : baseUrl + "?page=" + i %>"><%= i %></a>
            </li>
            <%
                }
            %>

            <%-- Botón siguiente --%>
            <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                <a class="page-link" href="<%= (currentPage < totalPages) ? baseUrl + "?page=" + (currentPage + 1) : "#" %>">
                    Siguiente
                </a>
            </li>
        </ul>
    </nav>
    <%
        }
    %>
    <%-- Fin bloque de paginación --%>


    <%
            }
        }
    %>
</div>
</body>
</html>
