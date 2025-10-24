<%@ page import="cliente.consultarDonacionPorFecha.DtMaterial" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.consultarDonacionPorFecha.DtLibro" %>
<%@ page import="cliente.consultarDonacionPorFecha.DtArticuloEspecial" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Consultar Donaciones por Fecha</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <style>
        .date-filter-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
            padding: 25px;
            margin-bottom: 25px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .date-filter-card h3 {
            color: white;
            font-weight: 600;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .btn-search {
            background: white;
            color: #667eea;
            font-weight: 600;
            transition: all 0.3s;
        }
        .btn-search:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            background: #f8f9fa;
            color: #667eea;
        }
        .table-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .inventory-badge {
            display: inline-block;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
        }
        @media (max-width: 768px) {
            .date-filter-card {
                padding: 15px;
            }
            .table-responsive {
                font-size: 0.9rem;
            }
            .btn-search {
                margin-top: 10px;
            }
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-4">
    <div class="date-filter-card">
        <h3 class="mb-3">
            <i class="fas fa-calendar-alt"></i> Consultar Donaciones por Rango de Fechas
        </h3>
        <p class="mb-4">Filtre las donaciones por un período específico para mantener trazabilidad del inventario</p>
        
        <form method="post" action="ConsultarDonacionesPorFechaServlet" id="formConsultaFechas">
            <div class="row">
                <div class="col-md-5 mb-3">
                    <label for="fechaDesde" class="form-label font-weight-bold">
                        <i class="fas fa-calendar-check"></i> Fecha Desde:
                    </label>
                    <input type="date" 
                           class="form-control" 
                           id="fechaDesde" 
                           name="fechaDesde" 
                           value="<%= request.getParameter("fechaDesde") != null ? request.getParameter("fechaDesde") : "" %>"
                           required>
                    <small class="form-text text-white-50">Fecha de inicio del período</small>
                </div>
                <div class="col-md-5 mb-3">
                    <label for="fechaHasta" class="form-label font-weight-bold">
                        <i class="fas fa-calendar-times"></i> Fecha Hasta:
                    </label>
                    <input type="date" 
                           class="form-control" 
                           id="fechaHasta" 
                           name="fechaHasta" 
                           value="<%= request.getParameter("fechaHasta") != null ? request.getParameter("fechaHasta") : "" %>"
                           required>
                    <small class="form-text text-white-50">Fecha de fin del período</small>
                </div>
                <div class="col-md-2 mb-3 d-flex align-items-end">
                    <button type="submit" class="btn btn-search btn-block">
                        <i class="fas fa-search"></i> Buscar
                    </button>
                </div>
            </div>
            <div class="d-flex justify-content-between flex-wrap">
                <small class="text-white-50">* Ambas fechas son obligatorias</small>
                <a href="menuBibliotecario.jsp" class="btn btn-light btn-sm">
                    <i class="fas fa-arrow-left"></i> Volver al Menú
                </a>
            </div>
        </form>
    </div>

    <%
        List<DtMaterial> donaciones = (List<DtMaterial>) request.getAttribute("donaciones");
        String error = (String) request.getAttribute("error");
        String fechaDesde = (String) request.getAttribute("fechaDesde");
        String fechaHasta = (String) request.getAttribute("fechaHasta");
        
        if (error != null) {
    %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong><i class="fas fa-exclamation-triangle"></i> Error:</strong> <%= error %>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <%
        }
        if (donaciones != null) {
            if (donaciones.isEmpty()){
    %>
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong><i class="fas fa-info-circle"></i> Información:</strong> No se encontraron donaciones en el rango de fechas especificado.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
    <%
            } else {
    %>
    <div class="card shadow-sm">
        <div class="card-header table-header">
            <h5 class="mb-0">
                <i class="fas fa-list-alt"></i> Resultados de la Búsqueda - Trazabilidad de Inventario
                <% if (fechaDesde != null && fechaHasta != null) { %>
                    <br><small class="mt-2 d-block">Período: <strong><%= fechaDesde %></strong> al <strong><%= fechaHasta %></strong></small>
                <% } %>
            </h5>
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover table-striped mb-0">
                    <thead class="thead-light">
                    <tr>
                        <th style="width: 5%;">#</th>
                        <th style="width: 15%;">ID Material</th>
                        <th style="width: 20%;">Tipo</th>
                        <th style="width: 60%;">Detalles</th>
                    </tr>
                    </thead>
                    <tbody>
        <%
            int idx = 1;
            for (DtMaterial donacion : donaciones) {
        %>
        <tr>
            <td class="align-middle"><strong><%= idx++ %></strong></td>
            <td class="align-middle text-muted">
                <code><%= donacion.getId() %></code>
            </td>
            <% if (donacion instanceof DtLibro) {
                DtLibro libro = (DtLibro) donacion;
            %>
            <td class="align-middle">
                <span class="badge badge-primary badge-pill inventory-badge">
                    <i class="fas fa-book"></i> Libro
                </span>
            </td>
            <td>
                <div class="mb-1"><strong>📚 Título:</strong> <%= libro.getTitulo() %></div>
                <div><strong>📄 Páginas:</strong> <%= libro.getCantidadPag() %></div>
            </td>
            <% } else if (donacion instanceof DtArticuloEspecial) {
                DtArticuloEspecial especial = (DtArticuloEspecial) donacion;
            %>
            <td class="align-middle">
                <span class="badge badge-success badge-pill inventory-badge">
                    <i class="fas fa-box"></i> Artículo Especial
                </span>
            </td>
            <td>
                <div class="mb-1"><strong>📝 Descripción:</strong> <%= especial.getDescripcion() %></div>
                <div class="mb-1"><strong>⚖️ Peso:</strong> <%= especial.getPesoKg() %> kg</div>
                <div><strong>📐 Dimensiones:</strong> <%= especial.getDimensiones() %></div>
            </td>
            <% } else { %>
            <td class="align-middle">
                <span class="badge badge-secondary badge-pill inventory-badge">
                    <i class="fas fa-file"></i> Material
                </span>
            </td>
            <td>
                <div><strong>ℹ️ Tipo:</strong> Material general</div>
            </td>
            <% } %>
        </tr>
        <%
            }
        %>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="card-footer bg-light">
            <div class="row">
                <div class="col-md-6">
                    <small class="text-muted">
                        <i class="fas fa-box-open"></i> Total de donaciones encontradas: <strong><%= donaciones.size() %></strong>
                    </small>
                </div>
                <div class="col-md-6 text-right">
                    <small class="text-muted">
                        <i class="fas fa-calendar"></i> Reporte generado: <%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()) %>
                    </small>
                </div>
            </div>
        </div>
    </div>

    <%-- Bloque de Paginación --%>
    <nav aria-label="Navegación de donaciones" class="mt-4">
        <ul class="pagination justify-content-center">
            <%
                Integer currentPage = (Integer) request.getAttribute("currentPage");
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                Integer totalItems = (Integer) request.getAttribute("totalItems");
                
                if (currentPage != null && totalPages != null && totalPages > 1) {
                    // Construir parámetros de URL para mantener las fechas
                    String urlParams = "";
                    if (fechaDesde != null && fechaHasta != null) {
                        urlParams = "&fechaDesde=" + fechaDesde + "&fechaHasta=" + fechaHasta;
                    }
                    
                    // Botón Anterior
                    if (currentPage > 1) {
            %>
            <li class="page-item">
                <a class="page-link" href="ConsultarDonacionesPorFechaServlet?page=<%= currentPage - 1 %><%= urlParams %>">
                    <i class="fas fa-chevron-left"></i> Anterior
                </a>
            </li>
            <%
                    } else {
            %>
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">
                    <i class="fas fa-chevron-left"></i> Anterior
                </a>
            </li>
            <%
                    }

                    // Números de Página (mostrar máximo 5 páginas)
                    int startPage = Math.max(1, currentPage - 2);
                    int endPage = Math.min(totalPages, currentPage + 2);
                    
                    if (startPage > 1) {
            %>
            <li class="page-item"><a class="page-link" href="ConsultarDonacionesPorFechaServlet?page=1<%= urlParams %>">1</a></li>
            <% if (startPage > 2) { %>
            <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
            <% } %>
            <%
                    }
                    
                    for (int i = startPage; i <= endPage; i++) {
                        if (i == currentPage) {
            %>
            <li class="page-item active">
                <a class="page-link" href="#"><%= i %> <span class="sr-only">(actual)</span></a>
            </li>
            <%
                        } else {
            %>
            <li class="page-item">
                <a class="page-link" href="ConsultarDonacionesPorFechaServlet?page=<%= i %><%= urlParams %>"><%= i %></a>
            </li>
            <%
                        }
                    }
                    
                    if (endPage < totalPages) {
            %>
            <% if (endPage < totalPages - 1) { %>
            <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
            <% } %>
            <li class="page-item"><a class="page-link" href="ConsultarDonacionesPorFechaServlet?page=<%= totalPages %><%= urlParams %>"><%= totalPages %></a></li>
            <%
                    }

                    // Botón Siguiente
                    if (currentPage < totalPages) {
            %>
            <li class="page-item">
                <a class="page-link" href="ConsultarDonacionesPorFechaServlet?page=<%= currentPage + 1 %><%= urlParams %>">
                    Siguiente <i class="fas fa-chevron-right"></i>
                </a>
            </li>
            <%
                    } else {
            %>
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">
                    Siguiente <i class="fas fa-chevron-right"></i>
                </a>
            </li>
            <%
                    }
                }
                
                // Mostrar información de resultados
                if (totalItems != null && totalItems > 0) {
            %>
        </ul>
        <div class="text-center mt-2">
            <small class="text-muted">
                <i class="fas fa-info-circle"></i> Mostrando <%= ((currentPage - 1) * 6) + 1 %> - <%= Math.min(currentPage * 6, totalItems) %> de <%= totalItems %> donaciones
            </small>
        </div>
            <%
                }
            %>
    </nav>
    <%-- Fin Bloque de Paginación --%>

    <%
            }
        }
    %>
</div>

<script>
    // Validación de fechas en el cliente
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('formConsultaFechas');
        const fechaDesde = document.getElementById('fechaDesde');
        const fechaHasta = document.getElementById('fechaHasta');
        
        // Establecer fecha máxima como hoy
        const today = new Date().toISOString().split('T')[0];
        fechaDesde.setAttribute('max', today);
        fechaHasta.setAttribute('max', today);
        
        form.addEventListener('submit', function(e) {
            const desde = new Date(fechaDesde.value);
            const hasta = new Date(fechaHasta.value);
            
            // Validar que fecha desde no sea posterior a fecha hasta
            if (desde > hasta) {
                e.preventDefault();
                alert('⚠️ La fecha "Desde" no puede ser posterior a la fecha "Hasta".');
                fechaDesde.focus();
                return false;
            }
            
            // Validar que no sean fechas futuras
            const hoy = new Date();
            hoy.setHours(0, 0, 0, 0);
            
            if (desde > hoy || hasta > hoy) {
                e.preventDefault();
                alert('⚠️ No se pueden seleccionar fechas futuras.');
                return false;
            }
            
            // Mostrar indicador de carga
            const btnSubmit = form.querySelector('button[type="submit"]');
            const originalText = btnSubmit.innerHTML;
            btnSubmit.innerHTML = '<span class="spinner-border spinner-border-sm mr-2" role="status" aria-hidden="true"></span>Cargando...';
            btnSubmit.disabled = true;
        });
        
        // Actualizar fecha hasta cuando cambia fecha desde
        fechaDesde.addEventListener('change', function() {
            fechaHasta.setAttribute('min', this.value);
        });
        
        // Actualizar fecha desde cuando cambia fecha hasta
        fechaHasta.addEventListener('change', function() {
            fechaDesde.setAttribute('max', this.value);
        });
    });
</script>
</body>
</html>

