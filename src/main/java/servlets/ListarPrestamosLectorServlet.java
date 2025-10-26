package servlets;

import cliente.listarPrestamosLector.DtPrestamo;
import cliente.listarPrestamosLector.DtPrestamoArray;
import cliente.listarPrestamosLector.ListarPrestamosLectorPublish;
import cliente.listarPrestamosLector.ListarPrestamosLectorPublishService;
import cliente.listarPrestamosLector.StringArray;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet para listar los préstamos de un lector específico.
 * Permite ver préstamos activos o todos los préstamos históricos.
 */
@WebServlet("/ListarPrestamosLectorServlet")
public class ListarPrestamosLectorServlet extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 8;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombreLector = request.getParameter("nombreLector");
        String tipoBusqueda = request.getParameter("tipoBusqueda"); // "activos" o "todos"
        String pageStr = request.getParameter("page");

        try {
            // Validar que se haya seleccionado un lector
            if (nombreLector == null || nombreLector.trim().isEmpty()) {
                request.setAttribute("error", "Debe seleccionar un lector.");
                cargarLectores(request);
                request.getRequestDispatcher("listarPrestamosLector.jsp").forward(request, response);
                return;
            }

            // Conectar con el Web Service
            ListarPrestamosLectorPublishService service = new ListarPrestamosLectorPublishService();
            ListarPrestamosLectorPublish port = service.getListarPrestamosLectorPublishPort();

            // Llamar al método correspondiente según el tipo de búsqueda
            DtPrestamoArray resultado;
            if ("activos".equals(tipoBusqueda)) {
                resultado = port.listarPrestamosActivosLector(nombreLector);
            } else {
                resultado = port.listarTodosPrestamosLector(nombreLector);
            }

            List<DtPrestamo> allPrestamos = resultado.getItem();

            // Lógica de Paginación
            int currentPage = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
            int totalItems = allPrestamos.size();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

            // Asegurar que la página actual esté en el rango válido
            if (currentPage < 1) {
                currentPage = 1;
            } else if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }

            // Calcular el rango de elementos para la página actual
            int start = (currentPage - 1) * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, totalItems);

            List<DtPrestamo> prestamosForPage;
            if (totalItems > 0) {
                prestamosForPage = allPrestamos.subList(start, end);
            } else {
                prestamosForPage = allPrestamos;
            }

            // Establecer atributos para la vista
            request.setAttribute("prestamos", prestamosForPage);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalItems", totalItems);
            request.setAttribute("nombreLector", nombreLector);
            request.setAttribute("tipoBusqueda", tipoBusqueda);

            // Cargar la lista de lectores para el dropdown
            cargarLectores(request);

            if (totalItems == 0) {
                String mensaje = "activos".equals(tipoBusqueda) 
                    ? "No se encontraron préstamos activos para el lector seleccionado."
                    : "No se encontraron préstamos para el lector seleccionado.";
                request.setAttribute("info", mensaje);
            }

        } catch (Exception e) {
            request.setAttribute("error", "Error al consultar los préstamos: " + e.getMessage());
            e.printStackTrace();
            cargarLectores(request);
        }

        request.getRequestDispatcher("listarPrestamosLector.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nombreLector = request.getParameter("nombreLector");
        String tipoBusqueda = request.getParameter("tipoBusqueda");
        
        if (nombreLector != null && tipoBusqueda != null) {
            // Redirigir al POST para navegación de paginación
            doPost(request, response);
        } else {
            // Cargar solo la lista de lectores y mostrar el formulario
            cargarLectores(request);
            request.getRequestDispatcher("listarPrestamosLector.jsp").forward(request, response);
        }
    }

    /**
     * Carga la lista de nombres de lectores desde el Web Service
     */
    private void cargarLectores(HttpServletRequest request) {
        try {
            ListarPrestamosLectorPublishService service = new ListarPrestamosLectorPublishService();
            ListarPrestamosLectorPublish port = service.getListarPrestamosLectorPublishPort();
            
            StringArray nombresArray = port.obtenerNombresLectores();
            List<String> nombresLectores = nombresArray.getItem();
            
            request.setAttribute("lectores", nombresLectores);
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar la lista de lectores: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

