<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, cliente.prestamo.*" %>
<%
    String nombreLector = (String) request.getSession().getAttribute("nombreLector");
    List<DtMaterialConPrestamo> materiales = (List<DtMaterialConPrestamo>) request.getSession().getAttribute("materialesConPrestamo");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Préstamos Activos</title>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<div class="container mt-4">
    <h2>Préstamos activos de <strong><%= nombreLector %></strong></h2>

    <div class="table-responsive">
        <table class="table table-bordered table-hover mt-3">
            <thead class="thead-light">
                <tr>
                    <th>ID Material</th>
                    <th>Tipo</th>
                    <th>Detalles</th>
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
                    <td><%= (p != null && p.getFechaSolicitud() != null) ? p.getFechaSolicitud() : "-" %></td>
                    <td><%= (p != null && p.getFechaDevolucion() != null) ? p.getFechaDevolucion() : "-" %></td>
                    <td><%= (p != null) ? p.getEstado() : "Sin préstamo" %></td>
                    <td><%= (p != null && p.getBibliotecario() != null) ? p.getBibliotecario().getNombre() : "-" %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <form action="menuLector.jsp" method="get">
        <input type="hidden" name="nombreLector" value="<%= nombreLector %>" />
        <button type="submit" class="btn btn-secondary btn-block mt-3">Volver al menú</button>
    </form>
</div>
</body>
</html>
