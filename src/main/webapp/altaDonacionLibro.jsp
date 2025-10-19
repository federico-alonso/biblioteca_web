<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Alta Donación de Libro</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <style>
        .container-narrow { max-width: 760px; }
    </style>
</head>
<body>
<div class="container container-narrow mt-4">
    <h3>Alta de Donación de Libro</h3>
    <form action="AltaDonacionLibroServlet" method="post" class="mt-3">

        <div class="form-group">
            <label for="titulo">Título</label>
            <input type="text" class="form-control" id="titulo" name="titulo" required>
        </div>

        <div class="form-group">
            <label for="cantidadPag">Cantidad de páginas</label>
            <input type="number" class="form-control" id="cantidadPag" name="cantidadPag" min="1" max="20000" required>
        </div>

        <button type="submit" class="btn btn-primary">Registrar donación</button>
        <a href="menuBibliotecario.jsp" class="btn btn-link">Volver</a>
    </form>

    <%
        String mensaje = (String) request.getAttribute("mensaje");
        String error = (String) request.getAttribute("error");
        if (mensaje != null) {
    %>
        <div class="alert alert-success mt-3"><%= mensaje %></div>
    <%
        } else if (error != null) {
    %>
        <div class="alert alert-danger mt-3"><%= error %></div>
    <%
        }
    %>
</div>
</body>
</html>

