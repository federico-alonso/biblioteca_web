<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Login de Usuario</title>
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        crossorigin="anonymous">
</head>
<body>
  <div class="container mt-5">
    <h2 class="text-center">Ingreso de Usuario</h2>
    <form action="loginLector" method="post" class="mt-4">
      <div class="form-group">
        <label for="email">Correo electrónico:</label>
        <input type="email" class="form-control" id="email" name="email" required>
      </div>

      <div class="form-group">
        <label for="contrasena">Contraseña:</label>
        <input type="password" class="form-control" id="contrasena" name="contrasena" required>
      </div>

      <button type="submit" class="btn btn-primary">Ingresar</button>
    </form>

    <div class="text-center mt-3">
      <p>¿No tenés cuenta?</p>
      <a href="registroUsuario.jsp" class="btn btn-secondary">Registrarse</a>
    </div>

    <%
      String mensaje = (String) request.getAttribute("mensaje");
      if (mensaje != null) {
    %>
      <div class="alert alert-info mt-3"><%= mensaje %></div>
    <%
      }
    %>
  </div>
</body>
</html>
