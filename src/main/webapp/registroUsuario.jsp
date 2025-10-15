<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Registro de Usuario</title>
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        crossorigin="anonymous">
</head>
<body>
  <div class="container mt-5">
    <h2 class="text-center">Registro de Lector</h2>
    <form action="AltaLectorServlet" method="post" class="mt-4">

      <div class="form-group">
        <label for="nombre">Nombre (ID):</label>
        <input type="text" class="form-control" id="nombre" name="nombre" required>
      </div>

      <div class="form-group">
        <label for="email">Correo electrónico:</label>
        <input type="email" class="form-control" id="email" name="email" required>
      </div>

      <div class="form-group">
        <label for="contrasena">Contraseña:</label>
        <input type="password" class="form-control" id="contrasena" name="contrasena" required>
      </div>

      <div class="form-group">
        <label for="direccion">Dirección:</label>
        <input type="text" class="form-control" id="direccion" name="direccion" required>
      </div>

      <div class="form-group">
        <label for="fechaRegistro">Fecha de registro:</label>
        <input type="date" class="form-control" id="fechaRegistro" name="fechaRegistro" required>
      </div>

      <div class="form-group">
        <label for="zona">Zona:</label>
        <select class="form-control" id="zona" name="zona" required>
          <option value="BIBLIOTECA_CENTRAL">Centro</option>
          <option value="ARCHIVO_GENERAL">Sur</option>
          <option value="SUCURSAL_ESTE">Este</option>
          <option value="SUCURSAL_OESTE">Oeste</option>
          <option value="BIBLIOTECA_INFANTIL">Infantil</option>
        </select>
      </div>

      <button type="submit" class="btn btn-success btn-block">Registrar lector</button>
    </form>

    <%
      String mensaje = (String) request.getAttribute("mensaje");
      if (mensaje != null) {
    %>
      <div class="alert alert-info mt-3"><%= mensaje %></div>
    <%
      }
    %>

    <div class="text-center mt-4">
      <a href="loginUsuario.jsp" class="btn btn-link">¿Ya tenés cuenta? Iniciar sesión</a>
    </div>
  </div>
</body>
</html>
