package servlets;

import cliente.consultarDonacionPorFecha.ConsultaDonacionYFechaPublish;
import cliente.consultarDonacionPorFecha.ConsultaDonacionYFechaPublishService;
import cliente.consultarDonacionPorFecha.DtMaterial;
import cliente.consultarDonacionPorFecha.DtMaterialArray;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Servlet para consultar donaciones en un rango de fechas específico.
 * Este servlet consume el Web Service ConsultaDonacionYFechaPublish.
 * Proporciona trazabilidad del inventario de donaciones.
 */
@WebServlet("/ConsultarDonacionesPorFechaServlet")
public class ConsultarDonacionesPorFechaServlet extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 6;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String fechaDesdeStr = request.getParameter("fechaDesde");
        String fechaHastaStr = request.getParameter("fechaHasta");
        String pageStr = request.getParameter("page");

        try {
            // Validar que se hayan proporcionado ambas fechas
            if (fechaDesdeStr == null || fechaDesdeStr.isEmpty() || 
                fechaHastaStr == null || fechaHastaStr.isEmpty()) {
                request.setAttribute("error", "Debe proporcionar ambas fechas (desde y hasta).");
                request.getRequestDispatcher("consultarDonacionesPorFecha.jsp").forward(request, response);
                return;
            }

            // Convertir las fechas de String a XMLGregorianCalendar
            XMLGregorianCalendar fechaDesde = convertirStringAXMLGregorianCalendar(fechaDesdeStr);
            XMLGregorianCalendar fechaHasta = convertirStringAXMLGregorianCalendar(fechaHastaStr);

            // Validar que fecha desde no sea posterior a fecha hasta
            Date desde = DATE_FORMAT.parse(fechaDesdeStr);
            Date hasta = DATE_FORMAT.parse(fechaHastaStr);
            
            if (desde.after(hasta)) {
                request.setAttribute("error", "La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
                request.getRequestDispatcher("consultarDonacionesPorFecha.jsp").forward(request, response);
                return;
            }

            // Validar que no sean fechas futuras
            Date hoy = new Date();
            if (desde.after(hoy) || hasta.after(hoy)) {
                request.setAttribute("error", "No se pueden consultar fechas futuras.");
                request.getRequestDispatcher("consultarDonacionesPorFecha.jsp").forward(request, response);
                return;
            }

            // Consumir el Web Service
            ConsultaDonacionYFechaPublishService service = new ConsultaDonacionYFechaPublishService();
            ConsultaDonacionYFechaPublish port = service.getConsultaDonacionYFechaPublishPort();

            // Llamar al método del web service con el rango de fechas
            DtMaterialArray resultado = port.consultarDonacionPorFecha(fechaDesde, fechaHasta);
            List<DtMaterial> allDonaciones = resultado.getItem();

            // Lógica de Paginación
            int currentPage = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
            int totalItems = allDonaciones.size();
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

            List<DtMaterial> donacionesForPage;
            if (totalItems > 0) {
                donacionesForPage = allDonaciones.subList(start, end);
            } else {
                donacionesForPage = allDonaciones;
            }

            // Establecer atributos para la vista
            request.setAttribute("donaciones", donacionesForPage);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalItems", totalItems);
            request.setAttribute("fechaDesde", fechaDesdeStr);
            request.setAttribute("fechaHasta", fechaHastaStr);

        } catch (java.text.ParseException e) {
            request.setAttribute("error", "Formato de fecha inválido. Use el formato correcto (YYYY-MM-DD).");
        } catch (Exception e) {
            request.setAttribute("error", "Error al consultar las donaciones: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("consultarDonacionesPorFecha.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Permitir GET para navegación de paginación
        String fechaDesde = request.getParameter("fechaDesde");
        String fechaHasta = request.getParameter("fechaHasta");
        
        if (fechaDesde != null && fechaHasta != null) {
            doPost(request, response);
        } else {
            // Si no hay fechas, redirigir al formulario
            request.getRequestDispatcher("consultarDonacionesPorFecha.jsp").forward(request, response);
        }
    }

    /**
     * Convierte un String en formato "yyyy-MM-dd" a XMLGregorianCalendar
     * @param fechaStr Fecha en formato String (yyyy-MM-dd)
     * @return XMLGregorianCalendar
     * @throws Exception si hay error en la conversión
     */
    private XMLGregorianCalendar convertirStringAXMLGregorianCalendar(String fechaStr) throws Exception {
        Date fecha = DATE_FORMAT.parse(fechaStr);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(fecha);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }
}

