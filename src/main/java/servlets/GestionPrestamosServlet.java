package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cliente.prestamo.PrestamoPublish;
import cliente.prestamo.PrestamoPublishService;
import cliente.prestamo.DtMaterialConPrestamo;
import cliente.prestamo.DtMaterialConPrestamoArray;

import java.io.IOException;
import java.util.List;

@WebServlet("/GestionPrestamosServlet")
public class GestionPrestamosServlet extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 6;
    private static final long serialVersionUID = 1L;
    private PrestamoPublish prestamoService;

    @Override
    public void init() throws ServletException {
        PrestamoPublishService service = new PrestamoPublishService();
        prestamoService = service.getPrestamoPublishPort();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            DtMaterialConPrestamoArray respuesta = prestamoService.getMaterialesConPrestamoTodos();
            List<DtMaterialConPrestamo> todosLosMateriales = respuesta.getItem();

            // Lógica de Paginación
            String pageStr = request.getParameter("page");
            int currentPage = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);

            int totalItems = todosLosMateriales.size();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

            int start = (currentPage - 1) * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, totalItems);

            List<DtMaterialConPrestamo> materialesParaPagina = todosLosMateriales.subList(start, end);

            request.setAttribute("materialesConPrestamo", materialesParaPagina);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar los préstamos: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("gestionPrestamos.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
