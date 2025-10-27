<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="cliente.autorizarPrestamo.DtPrestamo" %>
<%@ page import="cliente.autorizarPrestamo.DtMaterial" %>
<%@ page import="cliente.autorizarPrestamo.DtLibro" %>
<%@ page import="cliente.autorizarPrestamo.DtArticuloEspecial" %>
<%@ page import="cliente.autorizarPrestamo.EstadoPmo" %>
<%
    List<DtPrestamo> prestamos = (List<DtPrestamo>) request.getAttribute("prestamos");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Autorizar Prestamos</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container-fluid mt-4">
    <h2 class="mb-4">Prestamos Pendientes de Autorizacion</h2>

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

    <% if (prestamos != null && !prestamos.isEmpty()) { %>
    <div class="row">
        <%
            for (DtPrestamo prestamo : prestamos) {
                DtMaterial material = prestamo.getMaterial();
        %>
        <div class="col-12 col-md-6 col-lg-4 mb-4">
            <div class="card shadow-sm">
                <div class="card-header bg-warning text-white">
                    <h5 class="mb-0">Solicitud #<%= prestamo.getId() %></h5>
                </div>
                <div class="card-body">
                    <div class="mb-3">
                        <strong>Lector:</strong> <%= prestamo.getLector() != null ? prestamo.getLector().getNombre() : "N/A" %><br>
                        <strong>Email:</strong> <%= prestamo.getLector() != null && prestamo.getLector().getEmail() != null ? prestamo.getLector().getEmail() : "N/A" %><br>
                        <strong>Fecha Solicitud:</strong> <%= prestamo.getFechaSolicitud() != null ? prestamo.getFechaSolicitud() : "N/A" %><br>
                        <strong>Bibliotecario:</strong> <%= prestamo.getBibliotecario() != null ? prestamo.getBibliotecario().getNombre() : "N/A" %>
                    </div>

                    <hr>

                    <div class="mb-3">
                        <strong>Material:</strong><br>
                        <% if (material instanceof DtLibro) {
                                DtLibro libro = (DtLibro) material;
                        %>
                            <div class="ml-3">
                                <strong>Tipo:</strong> Libro<br>
                                <strong>Titulo:</strong> <%= libro.getTitulo() %><br>
                                <strong>Paginas:</strong> <%= libro.getCantidadPag() %><br>
                                <strong>Fecha Ingreso:</strong> <%= libro.getFechaIngreso() != null ? libro.getFechaIngreso() : "N/A" %>
                            </div>
                        <% } else if (material instanceof DtArticuloEspecial) {
                                DtArticuloEspecial articulo = (DtArticuloEspecial) material;
                        %>
                            <div class="ml-3">
                                <strong>Tipo:</strong> Articulo Especial<br>
                                <strong>Descripcion:</strong> <%= articulo.getDescripcion() %><br>
                                <strong>Peso:</strong> <%= articulo.getPesoKg() %> kg<br>
                                <strong>Dimensiones:</strong> <%= articulo.getDimensiones() %><br>
                                <strong>Fecha Ingreso:</strong> <%= articulo.getFechaIngreso() != null ? articulo.getFechaIngreso() : "N/A" %>
                            </div>
                        <% } else { %>
                            <div class="ml-3">
                                <strong>Tipo:</strong> Material<br>
                                <strong>Fecha Ingreso:</strong> <%= material.getFechaIngreso() != null ? material.getFechaIngreso() : "N/A" %>
                            </div>
                        <% } %>
                    </div>

                    <hr>

                    <form method="post" action="AutorizarPrestamoServlet">
                        <input type="hidden" name="idPrestamo" value="<%= prestamo.getId() %>"/>
                        <input type="hidden" name="accion" id="accion_<%= prestamo.getId() %>" value=""/>
                        
                        <div class="btn-group w-100" role="group">
                            <button type="submit" class="btn btn-success" onclick="document.getElementById('accion_<%= prestamo.getId() %>').value='autorizar'">
                                <i class="fas fa-check"></i> Autorizar
                            </button>
                            <button type="submit" class="btn btn-danger" onclick="document.getElementById('accion_<%= prestamo.getId() %>').value='rechazar'">
                                <i class="fas fa-times"></i> Rechazar
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
    <% } else { %>
        <div class="alert alert-info">
            No hay prestamos pendientes de autorizacion en este momento.
        </div>
    <% } %>

    <a href="menuBibliotecario.jsp" class="btn btn-secondary mt-3">Volver al menu</a>
</div>
</body>
</html>

