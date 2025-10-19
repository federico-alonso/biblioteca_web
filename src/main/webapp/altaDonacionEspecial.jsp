<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Alta Donación Especial</title>
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
    <h3>Alta de Donación Especial</h3>
    <form action="AltaDonacionEspecialServlet" method="post" class="mt-3">

        <div class="form-group">
            <label for="descripcion">Descripción</label>
            <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required></textarea>
        </div>

        <div class="form-group">
            <label for="pesoKg">Peso (kg)</label>
            <input type="number" step="0.01" class="form-control" id="pesoKg" name="pesoKg" min="0" required>
        </div>

        <div class="form-group">
            <label for="dimensiones">Dimensiones</label>
            <input type="text" class="form-control" id="dimensiones" name="dimensiones" placeholder="Largo x Ancho x Alto" required>
        </div>

        <button type="submit" class="btn btn-primary">Registrar donación especial</button>
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


