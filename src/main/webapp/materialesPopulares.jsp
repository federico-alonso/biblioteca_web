<%@ page import="servlets.MaterialesPopularesServlet.MaterialInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.prestamo.DtLibro" %>
<%@ page import="cliente.prestamo.DtArticuloEspecial" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Materiales con Más Préstamos Pendientes</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <style>
        .priority-high { background-color: #ffebee; border-left: 4px solid #f44336; }
        .priority-medium { background-color: #fff3e0; border-left: 4px solid #ff9800; }
        .priority-low { background-color: #e8f5e8; border-left: 4px solid #4caf50; }
        .stats-card { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }
    </style>
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">Materiales con Más Préstamos Pendientes</h2>
    <p class="text-muted">Identifica materiales que requieren atención prioritaria para devolución o reposición.</p>

    <!-- Botón Volver -->
    <div class="mb-4">
        <%
            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            String menuDestino = "lector".equals(tipoUsuario) ? "menuLector.jsp" : "menuBibliotecario.jsp";
        %>
        <a href="<%= menuDestino %>" class="btn btn-secondary">Volver al menú</a>
    </div>

    <!-- Formulario para actualizar datos -->
    <form class="mb-3" method="post" action="MaterialesPopularesServlet">
        <button type="submit" class="btn btn-primary">Actualizar Lista</button>
    </form>

    <%
        List<MaterialInfo> materialesPopulares = (List<MaterialInfo>) request.getAttribute("materialesPopulares");
        String error = (String) request.getAttribute("error");
        
        if (error != null) {
    %>
        <div class="alert alert-danger"><%= error %></div>
    <%
        } else if (materialesPopulares != null) {
            if (materialesPopulares.isEmpty()) {
    %>
                <div class="alert alert-info">
                    <h5>¡Excelente!</h5>
                    <p>No hay materiales con préstamos pendientes en este momento.</p>
                </div>
    <%
            } else {
                // Calcular estadísticas
                int totalMateriales = materialesPopulares.size();
                int totalPrestamosPendientes = materialesPopulares.stream()
                    .mapToInt(MaterialInfo::getPrestamosPendientes)
                    .sum();
                int promedioPrestamos = totalPrestamosPendientes / totalMateriales;
    %>
                <!-- Estadísticas -->
                <div class="row mb-4">
                    <div class="col-md-4">
                        <div class="card stats-card">
                            <div class="card-body text-center">
                                <h3><%= totalMateriales %></h3>
                                <p class="mb-0">Materiales con Pendientes</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card stats-card">
                            <div class="card-body text-center">
                                <h3><%= totalPrestamosPendientes %></h3>
                                <p class="mb-0">Total Préstamos Pendientes</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card stats-card">
                            <div class="card-body text-center">
                                <h3><%= promedioPrestamos %></h3>
                                <p class="mb-0">Promedio por Material</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Tabla de materiales -->
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th>#</th>
                                <th>Tipo</th>
                                <th>Detalles del Material</th>
                                <th class="text-center">Préstamos Pendientes</th>
                                <th class="text-center">Total Préstamos</th>
                                <th class="text-center">Prioridad</th>
                                <th>Acciones Sugeridas</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            int idx = 1;
                            for (MaterialInfo materialInfo : materialesPopulares) {
                                int pendientes = materialInfo.getPrestamosPendientes();
                                int total = materialInfo.getPrestamosTotales();
                                
                                // Determinar prioridad
                                String priorityClass;
                                String priorityText;
                                String suggestedAction;
                                
                                if (pendientes >= 5) {
                                    priorityClass = "priority-high";
                                    priorityText = "ALTA";
                                    suggestedAction = "Contactar lectores inmediatamente";
                                } else if (pendientes >= 3) {
                                    priorityClass = "priority-medium";
                                    priorityText = "MEDIA";
                                    suggestedAction = "Seguimiento activo recomendado";
                                } else {
                                    priorityClass = "priority-low";
                                    priorityText = "BAJA";
                                    suggestedAction = "Monitoreo regular";
                                }
                        %>
                            <tr class="<%= priorityClass %>">
                                <td><%= idx++ %></td>
                                <td>
                                    <%
                                        if (materialInfo.getMaterial() instanceof DtLibro) {
                                            out.print("Libro");
                                        } else if (materialInfo.getMaterial() instanceof DtArticuloEspecial) {
                                            out.print("Artículo Especial");
                                        } else {
                                            out.print("Material");
                                        }
                                    %>
                                </td>
                                <td>
                                    <%
                                        if (materialInfo.getMaterial() instanceof DtLibro) {
                                            DtLibro libro = (DtLibro) materialInfo.getMaterial();
                                            out.print("<strong>Título:</strong> " + libro.getTitulo() + "<br/>");
                                            out.print("<strong>Páginas:</strong> " + libro.getCantidadPag());
                                        } else if (materialInfo.getMaterial() instanceof DtArticuloEspecial) {
                                            DtArticuloEspecial art = (DtArticuloEspecial) materialInfo.getMaterial();
                                            out.print("<strong>Descripción:</strong> " + art.getDescripcion() + "<br/>");
                                            out.print("<strong>Peso:</strong> " + art.getPesoKg() + " kg<br/>");
                                            out.print("<strong>Dimensiones:</strong> " + art.getDimensiones());
                                        } else {
                                            out.print("<strong>ID:</strong> " + materialInfo.getMaterial().getId());
                                        }
                                    %>
                                </td>
                                <td class="text-center">
                                    <span class="badge badge-danger badge-pill"><%= pendientes %></span>
                                </td>
                                <td class="text-center">
                                    <span class="badge badge-info badge-pill"><%= total %></span>
                                </td>
                                <td class="text-center">
                                    <strong><%= priorityText %></strong>
                                </td>
                                <td>
                                    <small class="text-muted"><%= suggestedAction %></small>
                                </td>
                            </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>

                <!-- Tipos de Prioridades -->
                <div class="mt-4">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="alert alert-danger">
                                <strong>ALTA (5+ pendientes):</strong> Requiere acción inmediata
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="alert alert-warning">
                                <strong>MEDIA (3-4 pendientes):</strong> Seguimiento activo recomendado
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="alert alert-success">
                                <strong>BAJA (1-2 pendientes):</strong> Monitoreo regular
                            </div>
                        </div>
                    </div>
                </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
