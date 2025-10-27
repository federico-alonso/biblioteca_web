<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="servlets.ReportePrestamosZonaServlet.ZonaInfo" %>
<%@ page import="cliente.listarPrestamosZona.Zona" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reporte de Préstamos por Zona</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@3.9.1/dist/chart.min.js"></script>
    <style>
        .zone-card {
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .zone-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }
        .stat-value {
            font-size: 2rem;
            font-weight: bold;
        }
        .stat-label {
            font-size: 0.9rem;
            color: #6c757d;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        .chart-container {
            position: relative;
            height: 350px;
            margin-bottom: 30px;
        }
        .total-summary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Panel lateral -->
        <div class="col-md-2 bg-light vh-100">
            <h5 class="mt-4">Menú</h5>
            <ul class="nav flex-column mt-3">
                <li class="nav-item">
                    <a class="nav-link btn btn-outline-primary mb-2" href="menuBibliotecario.jsp">
                        ← Volver al Menú
                    </a>
                </li>
            </ul>
        </div>

        <!-- Área principal -->
        <div class="col-md-10">
            <div class="mt-4">
                <h2 class="mb-4">
                    <i class="bi bi-geo-alt"></i> Reporte de Préstamos por Zona
                </h2>
                <p class="text-muted">Análisis del uso del servicio de biblioteca en diferentes barrios y zonas.</p>

                <% 
                    String error = (String) request.getAttribute("error");
                    Map<Zona, ZonaInfo> zonasInfo = (Map<Zona, ZonaInfo>) request.getAttribute("zonasInfo");
                    Integer totalGeneral = (Integer) request.getAttribute("totalGeneral");
                    Zona[] zonas = (Zona[]) request.getAttribute("zonas");
                %>

                <% if (error != null) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error:</strong> <%= error %>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                <% } %>

                <% if (zonasInfo != null && !zonasInfo.isEmpty()) { %>
                    
                    <!-- Resumen Total -->
                    <div class="total-summary">
                        <div class="row text-center">
                            <div class="col-md-2">
                                <h3 class="mb-0"><%= totalGeneral != null ? totalGeneral : 0 %></h3>
                                <small>Total de Préstamos</small>
                            </div>
                            <div class="col-md-2">
                                <h3 class="mb-0"><%= zonasInfo.size() %></h3>
                                <small>Zonas Activas</small>
                            </div>
                            <div class="col-md-2">
                                <% 
                                    int totalPendientes = 0;
                                    for (ZonaInfo info : zonasInfo.values()) {
                                        totalPendientes += info.getPendientes();
                                    }
                                %>
                                <h3 class="mb-0"><%= totalPendientes %></h3>
                                <small>Préstamos Pendientes</small>
                            </div>
                            <div class="col-md-2">
                                <% 
                                    int totalActivos = 0;
                                    for (ZonaInfo info : zonasInfo.values()) {
                                        totalActivos += info.getActivos();
                                    }
                                %>
                                <h3 class="mb-0"><%= totalActivos %></h3>
                                <small>Préstamos Activos</small>
                            </div>
                            <div class="col-md-2">
                                <% 
                                    int totalDevueltos = 0;
                                    for (ZonaInfo info : zonasInfo.values()) {
                                        totalDevueltos += info.getDevueltos();
                                    }
                                %>
                                <h3 class="mb-0"><%= totalDevueltos %></h3>
                                <small>Préstamos Devueltos</small>
                            </div>
                        </div>
                    </div>

                    <!-- Gráficos -->
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Distribución de Préstamos por Zona</h5>
                                    <div class="chart-container">
                                        <canvas id="zonasChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Estados de Préstamos por Zona</h5>
                                    <div class="chart-container">
                                        <canvas id="estadosChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Tarjetas por Zona -->
                    <h4 class="mb-3">Detalle por Zona</h4>
                    <div class="row">
                        <% for (Zona zona : zonas) { 
                            ZonaInfo info = zonasInfo.get(zona);
                            if (info != null && info.getTotal() > 0) {
                        %>
                            <div class="col-md-4 mb-4">
                                <div class="card zone-card h-100">
                                    <div class="card-header bg-primary text-white">
                                        <h5 class="mb-0"><%= info.getNombreZona() %></h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="row text-center">
                                            <div class="col-6 mb-3">
                                                <div class="stat-value text-primary"><%= info.getTotal() %></div>
                                                <div class="stat-label">Total</div>
                                            </div>
                                            <div class="col-6 mb-3">
                                                <div class="stat-value text-warning"><%= info.getPendientes() %></div>
                                                <div class="stat-label">Pendientes</div>
                                            </div>
                                            <div class="col-6">
                                                <div class="stat-value text-success"><%= info.getActivos() %></div>
                                                <div class="stat-label">Activos</div>
                                            </div>
                                            <div class="col-6">
                                                <div class="stat-value text-info"><%= info.getDevueltos() %></div>
                                                <div class="stat-label">Devueltos</div>
                                            </div>
                                        </div>
                                        
                                        <!-- Barra de progreso -->
                                        <div class="mt-3">
                                            <% 
                                                double porcentajeActivos = info.getTotal() > 0 ? 
                                                    (info.getActivos() * 100.0 / info.getTotal()) : 0;
                                            %>
                                            <small class="text-muted">Tasa de préstamos activos</small>
                                            <div class="progress" style="height: 20px;">
                                                <div class="progress-bar bg-success" role="progressbar" 
                                                     style="width: <%= porcentajeActivos %>%;" 
                                                     aria-valuenow="<%= porcentajeActivos %>" 
                                                     aria-valuemin="0" aria-valuemax="100">
                                                    <%= String.format("%.1f", porcentajeActivos) %>%
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <% 
                            }
                        } 
                        %>
                    </div>

                    <!-- Tabla Resumen -->
                    <div class="card mt-4">
                        <div class="card-header bg-secondary text-white">
                            <h5 class="mb-0">Tabla Resumen</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead class="thead-dark">
                                        <tr>
                                            <th>Zona</th>
                                            <th class="text-center">Pendientes</th>
                                            <th class="text-center">Activos</th>
                                            <th class="text-center">Devueltos</th>
                                            <th class="text-center">Total</th>
                                            <th class="text-center">% del Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Zona zona : zonas) { 
                                            ZonaInfo info = zonasInfo.get(zona);
                                            if (info != null && info.getTotal() > 0) {
                                                double porcentaje = totalGeneral > 0 ? 
                                                    (info.getTotal() * 100.0 / totalGeneral) : 0;
                                        %>
                                            <tr>
                                                <td><strong><%= info.getNombreZona() %></strong></td>
                                                <td class="text-center"><span class="badge badge-warning"><%= info.getPendientes() %></span></td>
                                                <td class="text-center"><span class="badge badge-success"><%= info.getActivos() %></span></td>
                                                <td class="text-center"><span class="badge badge-info"><%= info.getDevueltos() %></span></td>
                                                <td class="text-center"><strong><%= info.getTotal() %></strong></td>
                                                <td class="text-center"><%= String.format("%.1f", porcentaje) %>%</td>
                                            </tr>
                                        <% 
                                            }
                                        } 
                                        %>
                                        <tr class="table-primary font-weight-bold">
                                            <td><strong>TOTAL GENERAL</strong></td>
                                            <td class="text-center">
                                                <strong><%= totalPendientes %></strong>
                                            </td>
                                            <td class="text-center"><strong><%= totalActivos %></strong></td>
                                            <td class="text-center"><strong><%= totalDevueltos %></strong></td>
                                            <td class="text-center"><strong><%= totalGeneral %></strong></td>
                                            <td class="text-center"><strong>100%</strong></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                <% } else if (error == null) { %>
                    <div class="alert alert-info mt-3">
                        <h5>No hay datos disponibles</h5>
                        <p>No se encontraron préstamos registrados en ninguna zona.</p>
                    </div>
                <% } %>

            </div>
        </div>
    </div>
</div>

<% if (zonasInfo != null && !zonasInfo.isEmpty()) { %>
<script>
    // Preparar datos para los gráficos
    const zonas = [
        <% for (Zona zona : zonas) { 
            ZonaInfo info = zonasInfo.get(zona);
            if (info != null && info.getTotal() > 0) {
        %>
        {
            nombre: '<%= info.getNombreZona() %>',
            total: <%= info.getTotal() %>,
            pendientes: <%= info.getPendientes() %>,
            activos: <%= info.getActivos() %>,
            devueltos: <%= info.getDevueltos() %>
        },
        <% 
            }
        } 
        %>
    ];

    // Gráfico de distribución por zona (Pie Chart)
    const ctxZonas = document.getElementById('zonasChart').getContext('2d');
    new Chart(ctxZonas, {
        type: 'pie',
        data: {
            labels: zonas.map(z => z.nombre),
            datasets: [{
                data: zonas.map(z => z.total),
                backgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56',
                    '#4BC0C0',
                    '#9966FF'
                ]
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });

    // Gráfico de estados por zona (Stacked Bar Chart)
    const ctxEstados = document.getElementById('estadosChart').getContext('2d');
    new Chart(ctxEstados, {
        type: 'bar',
        data: {
            labels: zonas.map(z => z.nombre),
            datasets: [
                {
                    label: 'Pendientes',
                    data: zonas.map(z => z.pendientes),
                    backgroundColor: '#FFC107'
                },
                {
                    label: 'Activos',
                    data: zonas.map(z => z.activos),
                    backgroundColor: '#28A745'
                },
                {
                    label: 'Devueltos',
                    data: zonas.map(z => z.devueltos),
                    backgroundColor: '#17A2B8'
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    stacked: true
                },
                y: {
                    stacked: true,
                    beginAtZero: true
                }
            },
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
</script>
<% } %>

</body>
</html>

