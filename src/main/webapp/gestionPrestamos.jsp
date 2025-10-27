<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.prestamo.DtMaterialConPrestamo" %>
<%@ page import="cliente.prestamo.DtMaterial" %>
<%@ page import="cliente.prestamo.DtPrestamo" %>
<%@ page import="cliente.prestamo.DtLibro" %>
<%@ page import="cliente.prestamo.DtArticuloEspecial" %>
<%@ page import="cliente.modificarEstadoPrestamo.EstadoPmo" %>
<%@ page import="cliente.autorizarPrestamo.*" %>
<%
    List<cliente.prestamo.DtMaterialConPrestamo> materiales = (List<cliente.prestamo.DtMaterialConPrestamo>) request.getAttribute("materialesConPrestamo");
    List<cliente.autorizarPrestamo.DtPrestamo> prestamosPendientes = (List<cliente.autorizarPrestamo.DtPrestamo>) request.getAttribute("prestamosPendientes");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Préstamos</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Gestión de Préstamos</h2>
    
    <% if (mensaje != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <%= mensaje %>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    <% } %>
    
    <% if (error != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= error %>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    <% } %>
    
    <% if (prestamosPendientes != null && !prestamosPendientes.isEmpty()) { %>
    <div class="alert alert-warning mb-4">
        <h4 class="alert-heading">Préstamos Pendientes de Autorización</h4>
        <p>Tienes <%= prestamosPendientes.size() %> préstamo(s) pendiente(s) de autorización.</p>
    </div>
    
    <!-- Sección de préstamos pendientes -->
    <div class="mb-5">
        <h3>Préstamos Pendientes</h3>
        <div class="row">
            <%
                for (cliente.autorizarPrestamo.DtPrestamo prestamo : prestamosPendientes) {
                    cliente.autorizarPrestamo.DtMaterial material = prestamo.getMaterial();
            %>
            <div class="col-md-6 mb-3">
                <div class="card shadow-sm">
                    <div class="card-header bg-warning text-white">
                        <h5 class="mb-0">Solicitud #<%= prestamo.getId() %></h5>
                    </div>
                    <div class="card-body">
                        <p><strong>Lector:</strong> <%= prestamo.getLector() != null ? prestamo.getLector().getNombre() : "N/A" %></p>
                        <p><strong>Fecha Solicitud:</strong> <%= prestamo.getFechaSolicitud() != null ? prestamo.getFechaSolicitud() : "N/A" %></p>
                        <p><strong>Material:</strong>
                        <% if (material instanceof cliente.autorizarPrestamo.DtLibro) {
                            cliente.autorizarPrestamo.DtLibro libro = (cliente.autorizarPrestamo.DtLibro) material;
                        %>
                            Libro - <%= libro.getTitulo() %> (<%= libro.getCantidadPag() %> páginas)
                        <% } else if (material instanceof cliente.autorizarPrestamo.DtArticuloEspecial) {
                            cliente.autorizarPrestamo.DtArticuloEspecial art = (cliente.autorizarPrestamo.DtArticuloEspecial) material;
                        %>
                            Artículo Especial - <%= art.getDescripcion() %>
                        <% } else { %>
                            Material
                        <% } %>
                        </p>
                        
                        <form method="post" action="GestionPrestamosServlet">
                            <input type="hidden" name="idPrestamo" value="<%= prestamo.getId() %>"/>
                            <input type="hidden" name="accion" id="accion_<%= prestamo.getId() %>" value=""/>
                            
                            <div class="form-group">
                                <label for="comentario_<%= prestamo.getId() %>">Comentario (opcional):</label>
                                <textarea class="form-control" id="comentario_<%= prestamo.getId() %>" name="comentario" rows="2"></textarea>
                            </div>
                            
                            <div class="btn-group w-100" role="group">
                                <button type="submit" class="btn btn-success" onclick="document.getElementById('accion_<%= prestamo.getId() %>').value='autorizar'">
                                    Autorizar
                                </button>
                                <button type="submit" class="btn btn-danger" onclick="document.getElementById('accion_<%= prestamo.getId() %>').value='rechazar'">
                                    Rechazar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
    
    <hr class="my-5">
    <% } %>
    
    <!-- Sección de todos los préstamos -->
    <h3 class="mb-3">Todos los Préstamos</h3>
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
                                        // Solo mostrar EN_CURSO y DEVUELTO
                                        EstadoPmo[] estadosPermitidos = {EstadoPmo.EN_CURSO, EstadoPmo.DEVUELTO};
                                        for (EstadoPmo estado : estadosPermitidos) {
                                            String selected = estado.toString().equals(p.getEstado().toString()) ? "selected" : "";
                                    %>
                                        <option value="<%= estado.toString() %>" <%= selected %>>
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
