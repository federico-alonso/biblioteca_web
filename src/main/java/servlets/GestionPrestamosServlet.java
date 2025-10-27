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
import cliente.prestamo.DtPrestamo;
import cliente.autorizarPrestamo.*;
import cliente.autorizarPrestamo.DtPrestamoArray;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.xml.ws.BindingProvider;

@WebServlet("/GestionPrestamosServlet")
public class GestionPrestamosServlet extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 6;
    private static final long serialVersionUID = 1L;
    private PrestamoPublish prestamoService;
    private AutorizarPrestamoPublish autorizarService;

    @Override
    public void init() throws ServletException {
        PrestamoPublishService service = new PrestamoPublishService();
        prestamoService = service.getPrestamoPublishPort();
        
        AutorizarPrestamoPublishService authService = new AutorizarPrestamoPublishService();
        autorizarService = authService.getAutorizarPrestamoPublishPort();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        // Si es una acción de autorizar o rechazar, configurar sesión SOAP
        if ("autorizar".equals(accion) || "rechazar".equals(accion)) {
            String nombreBibliotecario = (String) request.getSession().getAttribute("nombreBibliotecario");
            
            if (nombreBibliotecario == null) {
                nombreBibliotecario = (String) request.getSession().getAttribute("nombreLector");
            }
            
            // Configurar sesión SOAP con el nombre del bibliotecario
            if (nombreBibliotecario != null) {
                BindingProvider bp = (BindingProvider) autorizarService;
                Map<String, Object> rc = bp.getRequestContext();
                rc.put("com.sun.xml.ws.security.user", nombreBibliotecario);
            }
            
            String idPrestamoStr = request.getParameter("idPrestamo");
            String comentario = request.getParameter("comentario");
            
            try {
                long idPrestamo = Long.parseLong(idPrestamoStr);
                
                if ("autorizar".equals(accion)) {
                    autorizarService.autorizarPrestamo(idPrestamo, comentario != null ? comentario : "");
                    request.setAttribute("mensaje", "Préstamo autorizado exitosamente.");
                } else if ("rechazar".equals(accion)) {
                    autorizarService.rechazarPrestamo(idPrestamo, comentario != null ? comentario : "");
                    request.setAttribute("mensaje", "Préstamo rechazado.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de préstamo inválido.");
            } catch (Exception e) {
                request.setAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
            }
        }

        try {
            // Cargar todos los préstamos
            DtMaterialConPrestamoArray respuesta = prestamoService.getMaterialesConPrestamoTodos();
            List<DtMaterialConPrestamo> materialesCompletos = respuesta.getItem();
            
            // Filtrar los préstamos rechazados - no mostrarlos en la gestión
            List<DtMaterialConPrestamo> materialesFiltrados = new ArrayList<>();
            for (DtMaterialConPrestamo item : materialesCompletos) {
                cliente.prestamo.DtPrestamo prestamo = item.getPrestamo();
                // Solo incluir si no tiene préstamo O si el préstamo NO está rechazado
                if (prestamo == null || prestamo.getEstado() == null || 
                    !prestamo.getEstado().toString().equals("RECHAZADO")) {
                    materialesFiltrados.add(item);
                }
            }

            // Lógica de Paginación
            String pageStr = request.getParameter("page");
            int currentPage = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);

            int totalItems = materialesFiltrados.size();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

            int start = (currentPage - 1) * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, totalItems);

            List<DtMaterialConPrestamo> materialesParaPagina = materialesFiltrados.subList(start, end);

            // Cargar préstamos pendientes
            DtPrestamoArray prestamosPendientes = autorizarService.listarPrestamosPendientes();
            List<cliente.autorizarPrestamo.DtPrestamo> pendientes = prestamosPendientes.getItem();

            request.setAttribute("materialesConPrestamo", materialesParaPagina);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("prestamosPendientes", pendientes);

        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar los préstamos: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("gestionPrestamos.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
