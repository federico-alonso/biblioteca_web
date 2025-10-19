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
    <title>Gestión de Préstamos</title>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Listado completo de préstamos</h2>

    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead class="thead-light">
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

    <a href="menuBibliotecario.jsp" class="btn btn-secondary mt-3">Volver al menú</a>
</div>
</body>
</html>
