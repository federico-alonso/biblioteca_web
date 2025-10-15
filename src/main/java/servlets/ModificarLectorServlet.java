package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cliente.modificarEstadoLector.ModificarEstadoLectorPublishService;
import cliente.modificarEstadoLector.ModificarEstadoLectorPublish;
import cliente.modificarEstadoLector.DtLector;
import cliente.modificarEstadoLector.LectorNoExisteExcepcion_Exception;
import cliente.modificarEstadoLector.EstadoLector;

import java.io.IOException;

@WebServlet("/ModificarLectorServlet")
public class ModificarLectorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correoLector");
        String nuevoEstadoStr = request.getParameter("nuevoEstado");

        ModificarEstadoLectorPublishService service = new ModificarEstadoLectorPublishService();
        ModificarEstadoLectorPublish port = service.getModificarEstadoLectorPublishPort();

        try {
            DtLector lector = port.getDtLectorPorCorreo(correo);
            String nombre = lector.getNombre(); // ← el backend espera nombre, no correo

            if (nuevoEstadoStr != null) {
                EstadoLector nuevoEstado = EstadoLector.valueOf(nuevoEstadoStr);
                port.modificarEstadoLector(nombre, nuevoEstado);
                request.setAttribute("mensajeResultado", "Estado modificado correctamente.");
            }

            // Refrescar datos del lector después de la modificación
            lector = port.getDtLectorPorCorreo(correo);
            request.setAttribute("lector", lector);
            request.getRequestDispatcher("modificarLectorPorBibliotecario.jsp").forward(request, response);

        } catch (LectorNoExisteExcepcion_Exception e) {
            request.setAttribute("mensajeResultado", "No se encontró lector con ese correo.");
            request.getRequestDispatcher("menuBibliotecario.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("mensajeResultado", "Error inesperado: " + e.getMessage());
            request.getRequestDispatcher("menuBibliotecario.jsp").forward(request, response);
        }
    }
}
