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

import cliente.modificarZonaLector.ModificarZonaLectorPublishService;
import cliente.modificarZonaLector.ModificarZonaLectorPublish;
import cliente.modificarZonaLector.Zona;

import java.io.IOException;

@WebServlet("/ModificarLectorServlet")
public class ModificarLectorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        String correo = request.getParameter("correoLector");
        String nuevoEstadoStr = request.getParameter("nuevoEstado");
        String nuevaZonaStr = request.getParameter("nuevaZona");

        ModificarEstadoLectorPublishService estadoService = new ModificarEstadoLectorPublishService();
        ModificarEstadoLectorPublish estadoPort = estadoService.getModificarEstadoLectorPublishPort();

        ModificarZonaLectorPublishService zonaService = new ModificarZonaLectorPublishService();
        ModificarZonaLectorPublish zonaPort = zonaService.getModificarZonaLectorPublishPort();

        try {
            DtLector lector = estadoPort.getDtLectorPorCorreo(correo);
            String nombre = lector.getNombre(); // ← el backend espera nombre, no correo

            if (nuevoEstadoStr != null && !nuevoEstadoStr.isEmpty()) {
                EstadoLector nuevoEstado = EstadoLector.valueOf(nuevoEstadoStr);
                estadoPort.modificarEstadoLector(nombre, nuevoEstado);
                request.setAttribute("mensajeResultado", "Estado modificado correctamente.");
            }

            if (nuevaZonaStr != null && !nuevaZonaStr.isEmpty()) {
                Zona nuevaZona = Zona.valueOf(nuevaZonaStr);
                zonaPort.modificarZonaLector(nombre, nuevaZona);
                request.setAttribute("mensajeResultado", "Zona modificada correctamente.");
            }

            // Refrescar datos del lector después de la modificación
            lector = estadoPort.getDtLectorPorCorreo(correo);
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
