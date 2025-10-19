package servlets;

import cliente.consultarDonacion.ConsultarDonacionPublish;
import cliente.consultarDonacion.ConsultarDonacionPublishService;
import cliente.consultarDonacion.DtMaterial;
import cliente.consultarDonacion.DtMaterialArray;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ConsultarDonacionesServlet")
public class ConsultarDonacionesServlet extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 6;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ConsultarDonacionPublishService service = new ConsultarDonacionPublishService();
            ConsultarDonacionPublish port = service.getConsultarDonacionPublishPort();

            DtMaterialArray resultado = port.consultarDonacion();
            List<DtMaterial> allDonaciones = resultado.getItem();

            // Lógica de Paginación
            String pageStr = request.getParameter("page");
            int currentPage = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);

            int totalItems = allDonaciones.size();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

            int start = (currentPage - 1) * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, totalItems);

            List<DtMaterial> donacionesForPage = allDonaciones.subList(start, end);

            request.setAttribute("donaciones", donacionesForPage);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

        } catch (Exception e) {
            request.setAttribute("error", "Error al consultar las donaciones: " + e.getMessage());
        }

        request.getRequestDispatcher("consultarDonaciones.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
