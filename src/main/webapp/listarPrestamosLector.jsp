<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.listarPrestamosLector.DtPrestamo" %>
<%@ page import="cliente.listarPrestamosLector.DtMaterial" %>
<%@ page import="cliente.listarPrestamosLector.DtLibro" %>
<%@ page import="cliente.listarPrestamosLector.DtArticuloEspecial" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Préstamos por Lector - Biblioteca</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px 0;
        }
        .container {
            max-width: 1200px;
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            margin-bottom: 30px;
        }
        .card-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px 15px 0 0 !important;
            padding: 20px;
        }
        .card-header h3 {
            margin: 0;
            font-weight: 600;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 8px;
            padding: 10px 25px;
            font-weight: 500;
            transition: all 0.3s;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-success {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            border: none;
            border-radius: 8px;
            padding: 10px 25px;
            font-weight: 500;
        }
        .btn-info {
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            border: none;
            border-radius: 8px;
            padding: 10px 25px;
            font-weight: 500;
        }
        .btn-back {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            border: none;
            color: white;
            border-radius: 8px;
            padding: 10px 25px;
            font-weight: 500;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
        }
        .btn-back:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(245, 87, 108, 0.4);
            color: white;
            text-decoration: none;
        }
        .alert {
            border-radius: 10px;
            border: none;
        }
        .table-responsive {
            border-radius: 10px;
            overflow: hidden;
        }
        table {
            margin-bottom: 0;
        }
        thead {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        tbody tr {
            transition: all 0.2s;
        }
        tbody tr:hover {
            background-color: #f8f9fa;
            transform: scale(1.01);
        }
        .badge {
            padding: 8px 12px;
            border-radius: 6px;
            font-weight: 500;
            font-size: 0.85rem;
        }
        .badge-activo {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
        }
        .badge-finalizado {
            background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
            color: white;
        }
        .badge-vencido {
            background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%);
            color: white;
        }
        .badge-libro {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }
        .badge-articulo {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
            color: white;
        }
        .form-control, .form-select {
            border-radius: 8px;
            border: 2px solid #e0e0e0;
            padding: 10px 15px;
        }
        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .pagination {
            margin-top: 20px;
        }
        .page-link {
            border-radius: 8px;
            margin: 0 3px;
            border: none;
            color: #667eea;
        }
        .page-item.active .page-link {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
        }
        @media (max-width: 768px) {
            .table-responsive {
                font-size: 0.85rem;
            }
            .btn {
                width: 100%;
                margin-bottom: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Encabezado -->
        <div class="text-center mb-4">
            <h1 class="text-white mb-3">📚 Préstamos por Lector</h1>
            <p class="text-white">Consulta el historial de préstamos de cada lector</p>
        </div>

        <!-- Botón Volver -->
        <div class="mb-3">
            <a href="menuBibliotecario.jsp" class="btn-back">← Volver al Menú</a>
        </div>

        <!-- Mensajes de Error/Éxito -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>❌ Error:</strong> <%= request.getAttribute("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <% if (request.getAttribute("info") != null) { %>
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                <strong>ℹ️ Info:</strong> <%= request.getAttribute("info") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <!-- Formulario de Búsqueda -->
        <div class="card">
            <div class="card-header">
                <h3>🔍 Seleccionar Lector</h3>
            </div>
            <div class="card-body">
                <form method="post" action="ListarPrestamosLectorServlet" id="formBusqueda">
                    <div class="row align-items-end">
                        <div class="col-md-6 mb-3">
                            <label for="nombreLector" class="form-label fw-bold">Lector:</label>
                            <select class="form-select" id="nombreLector" name="nombreLector" required>
                                <option value="">-- Seleccione un lector --</option>
                                <% 
                                    List<String> lectores = (List<String>) request.getAttribute("lectores");
                                    String nombreLectorSeleccionado = (String) request.getAttribute("nombreLector");
                                    if (lectores != null) {
                                        for (String nombreLector : lectores) {
                                            String selected = (nombreLector.equals(nombreLectorSeleccionado)) ? "selected" : "";
                                %>
                                            <option value="<%= nombreLector %>" <%= selected %>><%= nombreLector %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>

                        <div class="col-md-6 mb-3">
                            <div class="d-grid gap-2 d-md-flex">
                                <button type="submit" name="tipoBusqueda" value="activos" class="btn btn-success flex-fill">
                                    📋 Préstamos Activos
                                </button>
                                <button type="submit" name="tipoBusqueda" value="todos" class="btn btn-info flex-fill">
                                    📚 Todos los Préstamos
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Resultados -->
        <% 
            List<DtPrestamo> prestamos = (List<DtPrestamo>) request.getAttribute("prestamos");
            if (prestamos != null && !prestamos.isEmpty()) {
                Integer totalItems = (Integer) request.getAttribute("totalItems");
                String tipoBusqueda = (String) request.getAttribute("tipoBusqueda");
                String tituloTabla = "activos".equals(tipoBusqueda) ? "Préstamos Activos" : "Historial Completo de Préstamos";
        %>
            <div class="card">
                <div class="card-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <h3><%= tituloTabla %> de <%= nombreLectorSeleccionado %></h3>
                        <span class="badge bg-light text-dark"><%= totalItems %> préstamo(s)</span>
                    </div>
                </div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Material</th>
                                    <th>Tipo</th>
                                    <th>Estado</th>
                                    <th>Fecha Solicitud</th>
                                    <th>Fecha Devolución</th>
                                    <th>Duración</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                    for (DtPrestamo prestamo : prestamos) {
                                        DtMaterial material = prestamo.getMaterial();
                                        String tipoMaterial = "";
                                        String nombreMaterial = "";
                                        String badgeTipo = "";
                                        
                                        if (material instanceof DtLibro) {
                                            tipoMaterial = "Libro";
                                            nombreMaterial = ((DtLibro) material).getTitulo();
                                            badgeTipo = "badge-libro";
                                        } else if (material instanceof DtArticuloEspecial) {
                                            tipoMaterial = "Artículo Especial";
                                            nombreMaterial = ((DtArticuloEspecial) material).getDescripcion();
                                            badgeTipo = "badge-articulo";
                                        }
                                        
                                        String estadoStr = prestamo.getEstado().toString();
                                        String badgeEstado = "";
                                        String iconoEstado = "";
                                        
                                        switch (estadoStr) {
                                            case "ACTIVO":
                                                badgeEstado = "badge-activo";
                                                iconoEstado = "✅";
                                                break;
                                            case "FINALIZADO":
                                                badgeEstado = "badge-finalizado";
                                                iconoEstado = "🏁";
                                                break;
                                            case "VENCIDO":
                                                badgeEstado = "badge-vencido";
                                                iconoEstado = "⚠️";
                                                break;
                                        }
                                %>
                                    <tr>
                                        <td><strong>#<%= prestamo.getId() %></strong></td>
                                        <td><%= nombreMaterial %></td>
                                        <td><span class="badge <%= badgeTipo %>"><%= tipoMaterial %></span></td>
                                        <td><span class="badge <%= badgeEstado %>"><%= iconoEstado %> <%= estadoStr %></span></td>
                                        <td><%= prestamo.getFechaSolicitud() != null ? prestamo.getFechaSolicitud().toString().substring(0, 10) : "N/A" %></td>
                                        <td><%= prestamo.getFechaDevolucion() != null ? prestamo.getFechaDevolucion().toString().substring(0, 10) : "Pendiente" %></td>
                                        <td>
                                            <% 
                                                // Calcular días entre fechas
                                                if (prestamo.getFechaSolicitud() != null) {
                                                    long dias = 0;
                                                    if (prestamo.getFechaDevolucion() != null) {
                                                        long diff = prestamo.getFechaDevolucion().toGregorianCalendar().getTimeInMillis() 
                                                                  - prestamo.getFechaSolicitud().toGregorianCalendar().getTimeInMillis();
                                                        dias = diff / (24 * 60 * 60 * 1000);
                                                    } else {
                                                        long diff = System.currentTimeMillis() 
                                                                  - prestamo.getFechaSolicitud().toGregorianCalendar().getTimeInMillis();
                                                        dias = diff / (24 * 60 * 60 * 1000);
                                                    }
                                                    out.print(dias + " días");
                                                } else {
                                                    out.print("N/A");
                                                }
                                            %>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card-footer bg-light">
                    <small class="text-muted">
                        📅 Consultado el <%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()) %>
                    </small>
                </div>
            </div>

            <!-- Paginación -->
            <% 
                Integer currentPage = (Integer) request.getAttribute("currentPage");
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                
                if (totalPages > 1) {
            %>
                <nav aria-label="Paginación">
                    <ul class="pagination justify-content-center">
                        <!-- Botón Anterior -->
                        <li class="page-item <%= (currentPage <= 1) ? "disabled" : "" %>">
                            <a class="page-link" 
                               href="ListarPrestamosLectorServlet?nombreLector=<%= nombreLectorSeleccionado %>&tipoBusqueda=<%= tipoBusqueda %>&page=<%= currentPage - 1 %>">
                                ← Anterior
                            </a>
                        </li>

                        <!-- Números de página -->
                        <% 
                            int startPage = Math.max(1, currentPage - 2);
                            int endPage = Math.min(totalPages, currentPage + 2);
                            
                            for (int i = startPage; i <= endPage; i++) {
                        %>
                            <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                <a class="page-link" 
                                   href="ListarPrestamosLectorServlet?nombreLector=<%= nombreLectorSeleccionado %>&tipoBusqueda=<%= tipoBusqueda %>&page=<%= i %>">
                                    <%= i %>
                                </a>
                            </li>
                        <% } %>

                        <!-- Botón Siguiente -->
                        <li class="page-item <%= (currentPage >= totalPages) ? "disabled" : "" %>">
                            <a class="page-link" 
                               href="ListarPrestamosLectorServlet?nombreLector=<%= nombreLectorSeleccionado %>&tipoBusqueda=<%= tipoBusqueda %>&page=<%= currentPage + 1 %>">
                                Siguiente →
                            </a>
                        </li>
                    </ul>
                </nav>
            <% } %>
        <% } %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Auto-cerrar alerts después de 5 segundos
        setTimeout(function() {
            var alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                var bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>

