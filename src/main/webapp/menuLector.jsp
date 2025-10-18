<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String nombreLector = (String) request.getAttribute("nombreLector");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Menú Lector</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">

        <!-- Panel lateral -->
        <div class="col-md-3 bg-light vh-100">
            <h4 class="mt-4">Panel del Lector</h4>
            <ul class="nav flex-column mt-3">
                <li class="nav-item">
                    <form action="DonacionesServlet" method="get">
                        <input type="hidden" name="nombreLector" value="<%= nombreLector %>" />
                        <button type="submit" class="nav-link btn btn-outline-success mb-2">
                            Registrar Préstamo
                        </button>
                    </form>
                </li>
            </ul>
        </div>

        <!-- Área principal -->
        <div class="col-md-9">
            <div class="mt-4">
                <h2>Bienvenido al panel de lector</h2>
                <p>Desde aquí podrás registrar préstamos, consultar tus libros y acceder a funcionalidades personales.</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
