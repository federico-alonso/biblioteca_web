package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cliente.modificarEstadoPrestamo.ModificarEstadoPrestamoPublish;
import cliente.modificarEstadoPrestamo.ModificarEstadoPrestamoPublishService;
import cliente.modificarEstadoPrestamo.EstadoPmo;

import java.io.IOException;

@WebServlet("/ModificarEstadoPrestamoServlet")
public class ModificarEstadoPrestamoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ModificarEstadoPrestamoPublish servicio;

    @Override
    public void init() throws ServletException {
        ModificarEstadoPrestamoPublishService service = new ModificarEstadoPrestamoPublishService();
        servicio = service.getModificarEstadoPrestamoPublishPort();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("idPrestamo"));
            String estadoStr = request.getParameter("nuevoEstado");
            EstadoPmo nuevoEstado = EstadoPmo.valueOf(estadoStr);

            servicio.modificarEstadoPrestamo(id, nuevoEstado);
        } catch (Exception e) {
            e.printStackTrace(); // Podés loguearlo o mostrar mensaje de error
        }

        // Redirigir al servlet principal para recargar la tabla
        request.getRequestDispatcher("GestionPrestamosServlet").forward(request, response);
    }
}
