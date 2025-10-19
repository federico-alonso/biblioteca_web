package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import cliente.altaDonacionEspecial.AltaDonacionEspecialPublish;
import cliente.altaDonacionEspecial.AltaDonacionEspecialPublishService;
import cliente.altaDonacionEspecial.DtArticuloEspecial;

@WebServlet("/AltaDonacionEspecialServlet")
public class AltaDonacionEspecialServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String descripcion = request.getParameter("descripcion");
        String pesoKgStr = request.getParameter("pesoKg");
        String dimensiones = request.getParameter("dimensiones");

        try {
            float pesoKg = Float.parseFloat(pesoKgStr);

            AltaDonacionEspecialPublishService service = new AltaDonacionEspecialPublishService();
            AltaDonacionEspecialPublish port = service.getAltaDonacionEspecialPublishPort();

            DtArticuloEspecial dto = new DtArticuloEspecial();
            dto.setDescripcion(descripcion);
            dto.setPesoKg(pesoKg);
            dto.setDimensiones(dimensiones);

            port.altaDonacionEspecial(dto);

            request.setAttribute("mensaje", "Donación especial registrada correctamente.");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El peso (kg) debe ser numérico válido.");
        } catch (Exception e) {
            request.setAttribute("error", "Error inesperado: " + e.getMessage());
        }

        request.getRequestDispatcher("altaDonacionEspecial.jsp").forward(request, response);
    }
}


