<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Menú Bibliotecario</title>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Panel lateral -->
        <div class="col-md-3 bg-light vh-100">
            <h4 class="mt-4">Panel de Control</h4>
            <ul class="nav flex-column mt-3">
                <li class="nav-item">
                    <a class="nav-link btn btn-outline-primary mb-2" href="#">Gestión de Usuario</a>
                    <!-- En el futuro: href="gestionLector.jsp" -->
                </li>
                <!-- Otros botones futuros -->
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
</body>
</html>
