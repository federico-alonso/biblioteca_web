<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Alta de Lector</title>
</head>
<body>
  <h2>Alta de Lector</h2>
  <form action="altaLector" method="post">
    <label for="nombre">Nombre (ID):</label>
    <input type="text" id="nombre" name="nombre" required><br><br>

    <label for="email">Correo electrónico:</label>
    <input type="email" id="email" name="email" required><br><br>

    <label for="contrasena">Contraseña:</label>
    <input type="password" id="contrasena" name="contrasena" required><br><br>

    <label for="direccion">Dirección:</label>
    <input type="text" id="direccion" name="direccion" required><br><br>

    <label for="fechaRegistro">Fecha de registro:</label>
    <input type="date" id="fechaRegistro" name="fechaRegistro" required><br><br>

    <label for="zona">Zona:</label>
    <select id="zona" name="zona" required>
      <option value="BIBLIOTECA_CENTRAL">Centro</option>
      <option value="ARCHIVO_GENERAL">Sur</option>
      <option value="SUCURSAL_ESTE">Este</option>
      <option value="SUCURSAL_OESTE">Oeste</option>
      <option value="BIBLIOTECA_INFANTIL">Infantil</option>
    </select><br><br>

    <button type="submit">Registrar lector</button>
  </form>

  <%
    String mensaje = (String) request.getAttribute("mensaje");
    if (mensaje != null) {
  %>
    <p><%= mensaje %></p>
  <%
    }
  %>
</body>
</html>
