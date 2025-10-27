<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.modificarTodoPrestamo.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestion Completa de Prestamos</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <style>
        .badge-custom {
            padding: 0.5em 0.75em;
            font-size: 0.875rem;
        }
        .table-actions {
            white-space: nowrap;
        }
        .modal-lg {
            max-width: 900px;
        }
        .form-section {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 15px;
        }
        .form-section h6 {
            color: #495057;
            font-weight: bold;
            margin-bottom: 10px;
            border-bottom: 2px solid #007bff;
            padding-bottom: 5px;
        }
    </style>
</head>
<body>
<div class="container-fluid mt-4">
    
    <% 
        String mensaje = (String) request.getAttribute("mensaje");
        String error = (String) request.getAttribute("error");
        List<DtPrestamo> prestamos = (List<DtPrestamo>) request.getAttribute("prestamos");
        List<DtLector> lectores = (List<DtLector>) request.getAttribute("lectores");
        List<DtBibliotecario> bibliotecarios = (List<DtBibliotecario>) request.getAttribute("bibliotecarios");
        List<DtMaterial> materiales = (List<DtMaterial>) request.getAttribute("materiales");
        EstadoPmo[] estados = (EstadoPmo[]) request.getAttribute("estados");
    %>

    <!-- Header -->
    <div class="row mb-4">
        <div class="col-md-12">
            <h2>
                <i class="bi bi-pencil-square"></i> Gestion Completa de Prestamos
            </h2>
            <p class="text-muted">Modifica cualquier informacion de los prestamos: material, lector, bibliotecario, fechas y estado.</p>
        </div>
    </div>

    <!-- Mensajes -->
    <% if (mensaje != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>¡Exito!</strong> <%= mensaje %>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    <% } %>

    <% if (error != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error:</strong> <%= error %>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    <% } %>

    <!-- Tabla de Préstamos -->
    <% if (prestamos != null && !prestamos.isEmpty()) { %>
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">Listado de Prestamos (<%= prestamos.size() %>)</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th>ID</th>
                                <th>Material</th>
                                <th>Lector</th>
                                <th>Bibliotecario</th>
                                <th>Fecha Solicitud</th>
                                <th>Fecha Devolucion</th>
                                <th>Estado</th>
                                <th class="table-actions">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (DtPrestamo prestamo : prestamos) { %>
                                <tr>
                                    <td><%= prestamo.getId() %></td>
                                    <td>
                                        <% if (prestamo.getMaterial() != null) { %>
                                            <strong>ID:</strong> <%= prestamo.getMaterial().getId() %><br>
                                            <% if (prestamo.getMaterial() instanceof DtLibro) { 
                                                DtLibro libro = (DtLibro) prestamo.getMaterial();
                                            %>
                                                <small class="text-muted">Libro: <%= libro.getTitulo() %></small>
                                            <% } else if (prestamo.getMaterial() instanceof DtArticuloEspecial) { 
                                                DtArticuloEspecial art = (DtArticuloEspecial) prestamo.getMaterial();
                                            %>
                                                <small class="text-muted">Art. Especial: <%= art.getDescripcion() %></small>
                                            <% } %>
                                        <% } else { %>
                                            <span class="text-muted">Sin material</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <%= prestamo.getLector() != null ? prestamo.getLector().getNombre() : "-" %>
                                        <% if (prestamo.getLector() != null) { %>
                                            <br><small class="text-muted"><%= prestamo.getLector().getEmail() %></small>
                                        <% } %>
                                    </td>
                                    <td>
                                        <%= prestamo.getBibliotecario() != null ? prestamo.getBibliotecario().getNombre() : "-" %>
                                    </td>
                                    <td>
                                        <% if (prestamo.getFechaSolicitud() != null) { %>
                                            <%= prestamo.getFechaSolicitud().getDay() %>/<%= prestamo.getFechaSolicitud().getMonth() %>/<%= prestamo.getFechaSolicitud().getYear() %>
                                        <% } else { %>
                                            -
                                        <% } %>
                                    </td>
                                    <td>
                                        <% if (prestamo.getFechaDevolucion() != null) { %>
                                            <%= prestamo.getFechaDevolucion().getDay() %>/<%= prestamo.getFechaDevolucion().getMonth() %>/<%= prestamo.getFechaDevolucion().getYear() %>
                                        <% } else { %>
                                            -
                                        <% } %>
                                    </td>
                                    <td>
                                        <% if (prestamo.getEstado() != null) { 
                                            String badgeClass = "";
                                            switch (prestamo.getEstado()) {
                                                case PENDIENTE:
                                                    badgeClass = "badge-warning";
                                                    break;
                                                case EN_CURSO:
                                                    badgeClass = "badge-success";
                                                    break;
                                                case DEVUELTO:
                                                    badgeClass = "badge-info";
                                                    break;
                                                default:
                                                    badgeClass = "badge-secondary";
                                            }
                                        %>
                                            <span class="badge <%= badgeClass %> badge-custom">
                                                <%= prestamo.getEstado().toString() %>
                                            </span>
                                        <% } else { %>
                                            <span class="badge badge-secondary badge-custom">Sin estado</span>
                                        <% } %>
                                    </td>
                                    <td class="table-actions">
                                        <button type="button" class="btn btn-sm btn-primary btn-editar" 
                                                data-id="<%= prestamo.getId() %>"
                                                data-material="<%= prestamo.getMaterial() != null ? prestamo.getMaterial().getId() : "" %>"
                                                data-lector="<%= prestamo.getLector() != null ? prestamo.getLector().getEmail() : "" %>"
                                                data-bibliotecario="<%= prestamo.getBibliotecario() != null ? prestamo.getBibliotecario().getEmail() : "" %>"
                                                data-fecha-solicitud="<%= prestamo.getFechaSolicitud() != null ? formatDateForInput(prestamo.getFechaSolicitud()) : "" %>"
                                                data-fecha-devolucion="<%= prestamo.getFechaDevolucion() != null ? formatDateForInput(prestamo.getFechaDevolucion()) : "" %>"
                                                data-estado="<%= prestamo.getEstado() != null ? prestamo.getEstado().toString() : "" %>">
                                            <i class="bi bi-pencil"></i> Editar
                                        </button>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <!-- Paginacion -->
        <%
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");
            if (currentPage != null && totalPages != null && totalPages > 1) {
        %>
        <nav aria-label="Paginacion">
            <ul class="pagination justify-content-center">
                <%
                    // Boton Anterior
                    if (currentPage > 1) {
                %>
                <li class="page-item"><a class="page-link" href="ModificarTodoPrestamoServlet?page=<%= currentPage - 1 %>">Anterior</a></li>
                <%
                    } else {
                %>
                <li class="page-item disabled"><a class="page-link" href="#">Anterior</a></li>
                <%
                    }

                    // Numeros de Pagina
                    for (int i = 1; i <= totalPages; i++) {
                        if (i == currentPage) {
                %>
                <li class="page-item active"><a class="page-link" href="#"><%= i %></a></li>
                <%
                        } else {
                %>
                <li class="page-item"><a class="page-link" href="ModificarTodoPrestamoServlet?page=<%= i %>"><%= i %></a></li>
                <%
                        }
                    }

                    // Boton Siguiente
                    if (currentPage < totalPages) {
                %>
                <li class="page-item"><a class="page-link" href="ModificarTodoPrestamoServlet?page=<%= currentPage + 1 %>">Siguiente</a></li>
                <%
                    } else {
                %>
                <li class="page-item disabled"><a class="page-link" href="#">Siguiente</a></li>
                <%
                    }
                %>
            </ul>
        </nav>
        <%
            }
        %>
        
    <% } else { %>
        <div class="alert alert-info">
            <h5>No hay prestamos registrados</h5>
            <p>No se encontraron prestamos en el sistema.</p>
        </div>
    <% } %>

    <!-- Boton volver -->
    <div class="mt-3">
        <a href="menuBibliotecario.jsp" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Volver al Menu
        </a>
    </div>
</div>

<!-- Modal de Edición -->
<div class="modal fade" id="modalEditarPrestamo" tabindex="-1" role="dialog" aria-labelledby="modalEditarPrestamoLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <form method="post" action="ModificarTodoPrestamoServlet">
                <input type="hidden" name="action" value="modificar">
                <input type="hidden" name="idPrestamo" id="editIdPrestamo">
                
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="modalEditarPrestamoLabel">
                        <i class="bi bi-pencil-square"></i> Editar Préstamo
                    </h5>
                    <button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                
                <div class="modal-body">
                    
                    <!-- Sección: Material -->
                    <div class="form-section">
                        <h6><i class="bi bi-book"></i> Material</h6>
                        <div class="form-group">
                            <label for="editIdMaterial">Seleccionar Material *</label>
                            <select class="form-control" id="editIdMaterial" name="idMaterial" required>
                                <option value="">-- Seleccione un material --</option>
                                <% if (materiales != null) { 
                                    for (DtMaterial material : materiales) { %>
                                        <option value="<%= material.getId() %>">
                                            [ID: <%= material.getId() %>] 
                                            <% if (material instanceof DtLibro) { 
                                                DtLibro libro = (DtLibro) material;
                                            %>
                                                Libro: <%= libro.getTitulo() %>
                                            <% } else if (material instanceof DtArticuloEspecial) { 
                                                DtArticuloEspecial art = (DtArticuloEspecial) material;
                                            %>
                                                Artículo Especial: <%= art.getDescripcion() %>
                                            <% } else { %>
                                                Material genérico
                                            <% } %>
                                        </option>
                                    <% } 
                                } %>
                            </select>
                        </div>
                    </div>

                    <!-- Sección: Lector -->
                    <div class="form-section">
                        <h6><i class="bi bi-person"></i> Lector</h6>
                        <div class="form-group">
                            <label for="editCorreoLector">Seleccionar Lector *</label>
                            <select class="form-control" id="editCorreoLector" name="correoLector" required>
                                <option value="">-- Seleccione un lector --</option>
                                <% if (lectores != null) { 
                                    for (DtLector lector : lectores) { %>
                                        <option value="<%= lector.getEmail() %>">
                                            <%= lector.getNombre() %> (<%= lector.getEmail() %>)
                                            <% if (lector.getZona() != null) { %>
                                                - <%= lector.getZona().toString() %>
                                            <% } %>
                                        </option>
                                    <% } 
                                } %>
                            </select>
                        </div>
                    </div>

                    <!-- Sección: Bibliotecario -->
                    <div class="form-section">
                        <h6><i class="bi bi-person-badge"></i> Bibliotecario</h6>
                        <div class="form-group">
                            <label for="editCorreoBibliotecario">Seleccionar Bibliotecario *</label>
                            <select class="form-control" id="editCorreoBibliotecario" name="correoBibliotecario" required>
                                <option value="">-- Seleccione un bibliotecario --</option>
                                <% if (bibliotecarios != null) { 
                                    for (DtBibliotecario biblio : bibliotecarios) { %>
                                        <option value="<%= biblio.getEmail() %>">
                                            <%= biblio.getNombre() %> (<%= biblio.getEmail() %>)
                                        </option>
                                    <% } 
                                } %>
                            </select>
                        </div>
                    </div>

                    <!-- Sección: Fechas -->
                    <div class="form-section">
                        <h6><i class="bi bi-calendar"></i> Fechas</h6>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="editFechaSolicitud">Fecha de Solicitud *</label>
                                    <input type="datetime-local" class="form-control" id="editFechaSolicitud" name="fechaSolicitud" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="editFechaDevolucion">Fecha de Devolución</label>
                                    <input type="datetime-local" class="form-control" id="editFechaDevolucion" name="fechaDevolucion">
                                    <small class="form-text text-muted">Dejar vacío si no se ha devuelto</small>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Sección: Estado -->
                    <div class="form-section">
                        <h6><i class="bi bi-check-circle"></i> Estado</h6>
                        <div class="form-group">
                            <label for="editEstado">Estado del Préstamo *</label>
                            <select class="form-control" id="editEstado" name="estado" required>
                                <% if (estados != null) { 
                                    for (EstadoPmo estado : estados) { %>
                                        <option value="<%= estado.toString() %>">
                                            <%= estado.toString() %>
                                        </option>
                                    <% } 
                                } %>
                            </select>
                        </div>
                    </div>

                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Guardar Cambios
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<%!
    // Método para formatear fecha para input datetime-local
    private String formatDateForInput(javax.xml.datatype.XMLGregorianCalendar xmlCal) {
        if (xmlCal == null) return "";
        try {
            return String.format("%04d-%02d-%02dT%02d:%02d",
                xmlCal.getYear(),
                xmlCal.getMonth(),
                xmlCal.getDay(),
                xmlCal.getHour(),
                xmlCal.getMinute()
            );
        } catch (Exception e) {
            return "";
        }
    }
%>

<script>
$(document).ready(function() {
    // Manejar clic en botones de editar
    $('.btn-editar').on('click', function() {
        const btn = $(this);
        
        // Obtener datos del botón
        const id = btn.data('id');
        const idMaterial = btn.data('material');
        const correoLector = btn.data('lector');
        const correoBibliotecario = btn.data('bibliotecario');
        const fechaSolicitud = btn.data('fecha-solicitud');
        const fechaDevolucion = btn.data('fecha-devolucion');
        const estado = btn.data('estado');
        
        // Establecer valores en el formulario del modal
        $('#editIdPrestamo').val(id);
        $('#editIdMaterial').val(idMaterial);
        $('#editCorreoLector').val(correoLector);
        $('#editCorreoBibliotecario').val(correoBibliotecario);
        $('#editFechaSolicitud').val(fechaSolicitud);
        $('#editFechaDevolucion').val(fechaDevolucion);
        $('#editEstado').val(estado);
        
        // Mostrar el modal
        $('#modalEditarPrestamo').modal('show');
    });
});
</script>

</body>
</html>

