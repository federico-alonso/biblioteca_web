package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import cliente.altaDonacionLibro.AltaDonacionLibroPublish;
import cliente.altaDonacionLibro.AltaDonacionLibroPublishService;
import cliente.altaDonacionLibro.DtLibro;

@WebServlet("/AltaDonacionLibroServlet")
public class AltaDonacionLibroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String titulo = request.getParameter("titulo");
        String cantidadPagStr = request.getParameter("cantidadPag");

        try {
            int cantidadPag = Integer.parseInt(cantidadPagStr);

            AltaDonacionLibroPublishService service = new AltaDonacionLibroPublishService();
            AltaDonacionLibroPublish port = service.getAltaDonacionLibroPublishPort();

            DtLibro dto = new DtLibro();
            dto.setTitulo(titulo);
            dto.setCantidadPag(cantidadPag);

            port.altaDonacionLibro(dto);

            request.setAttribute("mensaje", "Donación registrada correctamente.");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "La cantidad de páginas debe ser numérica válida.");
        } catch (Exception e) {
            request.setAttribute("error", "Error inesperado: " + e.getMessage());
        }

        request.getRequestDispatcher("altaDonacionLibro.jsp").forward(request, response);
    }
}


