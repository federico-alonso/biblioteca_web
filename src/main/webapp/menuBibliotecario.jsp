<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Menú Bibliotecario</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Panel lateral -->
        <div class="col-md-3 bg-light vh-100">
            <h4 class="mt-4">Panel de Control</h4>
            <ul class="nav flex-column mt-3">
                <li class="nav-item">
                    <button class="nav-link btn btn-outline-primary mb-2" data-toggle="modal" data-target="#modalCorreo">
                        Gestión de Usuario
                    </button>
                </li>
            </ul>
        </div>

        <!-- Área principal -->
        <div class="col-md-9">
            <div class="mt-4">
                <h2>Bienvenido al panel de bibliotecario</h2>
                <p>Desde aquí podrás gestionar usuarios, libros y más funcionalidades administrativas.</p>
            </div>
        </div>
    </div>
</div>

<!-- Modal de ingreso de correo -->
<div class="modal fade" id="modalCorreo" tabindex="-1" role="dialog" aria-labelledby="modalCorreoLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form class="modal-content" method="post" action="ModificarLectorServlet">
            <div class="modal-header">
                <h5 class="modal-title" id="modalCorreoLabel">Buscar lector por correo</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="email" class="form-control" id="correoLector" name="correoLector" placeholder="Correo del lector" required>

                <% String mensaje = (String) request.getAttribute("mensajeResultado"); %>
                <% if (mensaje != null) { %>
                    <div id="mensajeResultado" class="mt-2 text-danger"><%= mensaje %></div>
                <% } %>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Buscar</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
