<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.prestamo.DtMaterialConPrestamo" %>
<%@ page import="cliente.prestamo.DtMaterial" %>
<%@ page import="cliente.prestamo.DtPrestamo" %>
<%@ page import="cliente.prestamo.DtLibro" %>
<%@ page import="cliente.prestamo.DtArticuloEspecial" %>
<%@ page import="cliente.modificarEstadoPrestamo.EstadoPmo" %>
<%
    List<DtMaterialConPrestamo> materiales = (List<DtMaterialConPrestamo>) request.getAttribute("materialesConPrestamo");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestión de Préstamos</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container-fluid mt-4">
    <h2 class="mb-4">Listado completo de préstamos</h2>

    <div class="card">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Préstamos y Materiales</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>ID Material</th>
                            <th>Tipo</th>
                            <th>Detalles</th>
                            <th>Lector</th>
                            <th>Fecha Solicitud</th>
                            <th>Fecha Devolución</th>
                            <th>Estado</th>
                            <th>Bibliotecario</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        for (DtMaterialConPrestamo item : materiales) {
                            DtMaterial mat = item.getMaterial();
                            DtPrestamo p = item.getPrestamo();
                    %>
                        <tr>
                            <td><%= mat.getId() %></td>
                            <td>
                                <%
                                    if (mat instanceof DtLibro) {
                                        out.print("Libro");
                                    } else if (mat instanceof DtArticuloEspecial) {
                                        out.print("Artículo Especial");
                                    } else {
                                        out.print("Material");
                                    }
                                %>
                            </td>
                            <td>
                                <%
                                    if (mat instanceof DtLibro) {
                                        DtLibro libro = (DtLibro) mat;
                                        out.print("Título: " + libro.getTitulo() + "<br/>");
                                        out.print("Páginas: " + libro.getCantidadPag());
                                    } else if (mat instanceof DtArticuloEspecial) {
                                        DtArticuloEspecial art = (DtArticuloEspecial) mat;
                                        out.print("Descripción: " + art.getDescripcion() + "<br/>");
                                        out.print("Peso: " + art.getPesoKg() + " kg<br/>");
                                        out.print("Dimensiones: " + art.getDimensiones());
                                    } else {
                                        out.print("Fecha ingreso: " + mat.getFechaIngreso());
                                    }
                                %>
                            </td>
                            <td><%= (p != null && p.getLector() != null) ? p.getLector().getNombre() : "-" %></td>
                            <td><%= (p != null && p.getFechaSolicitud() != null) ? p.getFechaSolicitud() : "-" %></td>
                            <td><%= (p != null && p.getFechaDevolucion() != null) ? p.getFechaDevolucion() : "-" %></td>
                            <td>
                                <% if (p != null) { %>
                                    <form method="post" action="ModificarEstadoPrestamoServlet">
                                        <input type="hidden" name="idPrestamo" value="<%= p.getId() %>"/>
                                        <select name="nuevoEstado" class="form-control form-control-sm d-inline w-auto">
                                            <%
                                                for (EstadoPmo estado : EstadoPmo.values()) {
                                                    String selected = estado.toString().equals(p.getEstado().toString()) ? "selected" : "";
                                                    boolean habilitado = estado == EstadoPmo.EN_CURSO || estado == EstadoPmo.DEVUELTO;
                                            %>
                                                <option value="<%= estado.toString() %>" <%= selected %> <%= habilitado ? "" : "disabled" %>>
                                                    <%= estado.toString() %>
                                                </option>
                                            <%
                                                }
                                            %>
                                        </select>
                                        <button type="submit" class="btn btn-sm btn-primary mt-1">Actualizar</button>
                                    </form>
                                <% } else { %>
                                    Sin préstamo
                                <% } %>
                            </td>
                            <td><%= (p != null && p.getBibliotecario() != null) ? p.getBibliotecario().getNombre() : "-" %></td>
                        </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <%-- Bloque de Paginación --%>
    <nav>
        <ul class="pagination justify-content-center">
            <%
                Integer currentPage = (Integer) request.getAttribute("currentPage");
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                if (currentPage != null && totalPages != null && totalPages > 1) {
                    // Botón Anterior
                    if (currentPage > 1) {
            %>
            <li class="page-item"><a class="page-link" href="GestionPrestamosServlet?page=<%= currentPage - 1 %>">Anterior</a></li>
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
            <li class="page-item"><a class="page-link" href="GestionPrestamosServlet?page=<%= i %>"><%= i %></a></li>
            <%
                        }
                    }

                    // Botón Siguiente
                    if (currentPage < totalPages) {
            %>
            <li class="page-item"><a class="page-link" href="GestionPrestamosServlet?page=<%= currentPage + 1 %>">Siguiente</a></li>
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

    <a href="menuBibliotecario.jsp" class="btn btn-secondary mt-3">Volver al menú</a>
</div>
</body>
</html>
