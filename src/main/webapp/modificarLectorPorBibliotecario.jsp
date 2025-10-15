<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cliente.modificarEstadoLector.DtLector" %>
<%@ page import="cliente.modificarEstadoLector.EstadoLector" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Modificar lector</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body class="bg-light">
<div class="container mt-4 mb-5">
    <%
        DtLector lector = (DtLector) request.getAttribute("lector");
        String mensaje = (String) request.getAttribute("mensajeResultado");

        if (mensaje != null) {
    %>
        <div class="alert alert-info text-center"><%= mensaje %></div>
    <%
        }

        if (lector == null) {
    %>
        <div class="alert alert-danger text-center">No se recibió ningún lector.</div>
        <div class="text-center mt-3">
            <a href="menuBibliotecario.jsp" class="btn btn-secondary">Volver al menú</a>
        </div>
    <%
        } else {
            Date fecha = lector.getFechaRegistro() != null
                ? lector.getFechaRegistro().toGregorianCalendar().getTime()
                : null;
            String fechaStr = (fecha != null)
                ? new SimpleDateFormat("dd/MM/yyyy").format(fecha)
                : "No disponible";
    %>
        <div class="card shadow-sm mx-auto" style="max-width: 500px;">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">Modificar estado del lector</h5>
            </div>
            <div class="card-body">
                <form method="post" action="ModificarLectorServlet">
                    <div class="mb-3">
                        <label class="form-label">Nombre</label>
                        <input type="text" class="form-control" value="<%= lector.getNombre() %>" disabled>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="text" class="form-control" value="<%= lector.getEmail() %>" disabled>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Dirección</label>
                        <input type="text" class="form-control" value="<%= lector.getDireccion() %>" disabled>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Fecha de registro</label>
                        <input type="text" class="form-control" value="<%= fechaStr %>" disabled>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Estado actual</label>
                        <input type="text" class="form-control" value="<%= lector.getEstado() %>" disabled>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Nuevo estado</label>
                        <select name="nuevoEstado" class="form-select" required>
                            <%
                                for (EstadoLector estado : EstadoLector.values()) {
                            %>
                                <option value="<%= estado.name() %>"><%= estado.name() %></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <input type="hidden" name="correoLector" value="<%= lector.getEmail() %>">
                    <div class="d-flex justify-content-between">
                        <a href="menuBibliotecario.jsp" class="btn btn-outline-secondary">Volver</a>
                        <button type="submit" class="btn btn-success">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    <%
        }
    %>
</div>
</body>
</html>
