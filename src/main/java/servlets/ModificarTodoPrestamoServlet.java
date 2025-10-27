package servlets;

import cliente.modificarTodoPrestamo.*;

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
 * Servlet para gestionar y modificar completamente los préstamos.
 * Permite editar cualquier información de un préstamo: material, lector, bibliotecario, fechas y estado.
 */
@WebServlet("/ModificarTodoPrestamoServlet")
public class ModificarTodoPrestamoServlet extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 6;
    private static final long serialVersionUID = 1L;
    private ModificarTodoPrestamoPublish prestamoService;

    @Override
    public void init() throws ServletException {
        ModificarTodoPrestamoPublishService service = new ModificarTodoPrestamoPublishService();
        prestamoService = service.getModificarTodoPrestamoPublishPort();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("edit".equals(action)) {
            // Cargar datos para editar un préstamo específico
            String idPrestamo = request.getParameter("idPrestamo");
            cargarDatosParaEdicion(request, idPrestamo);
        } else {
            // Listar todos los préstamos
            cargarListadoPrestamos(request);
        }
        
        request.getRequestDispatcher("gestionPrestamoCompleto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("modificar".equals(action)) {
            // Modificar préstamo
            try {
                modificarPrestamo(request);
                request.setAttribute("mensaje", "Préstamo actualizado exitosamente.");
            } catch (Exception e) {
                request.setAttribute("error", "Error al modificar el préstamo: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Recargar listado
        cargarListadoPrestamos(request);
        request.getRequestDispatcher("gestionPrestamoCompleto.jsp").forward(request, response);
    }

    /**
     * Carga el listado completo de préstamos y los datos necesarios para los dropdowns
     */
    private void cargarListadoPrestamos(HttpServletRequest request) {
        try {
            // Obtener todos los préstamos
            DtPrestamoArray prestamosArray = prestamoService.listarPrestamos();
            List<DtPrestamo> todosLosPrestamos = prestamosArray.getItem();
            
            // Lógica de Paginación
            String pageStr = request.getParameter("page");
            int currentPage = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);

            int totalItems = todosLosPrestamos.size();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

            int start = (currentPage - 1) * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, totalItems);

            List<DtPrestamo> prestamosParaPagina = todosLosPrestamos.subList(start, end);
            
            // Obtener listas para los dropdowns
            DtLectorArray lectoresArray = prestamoService.getListadoLectores();
            List<DtLector> lectores = lectoresArray.getItem();
            
            DtBibliotecarioArray bibliotecariosArray = prestamoService.getListadoBibliotecarios();
            List<DtBibliotecario> bibliotecarios = bibliotecariosArray.getItem();
            
            DtMaterialArray materialesArray = prestamoService.getListadoMateriales();
            List<DtMaterial> materiales = materialesArray.getItem();
            
            // Estados disponibles
            EstadoPmo[] estados = EstadoPmo.values();
            
            // Establecer atributos
            request.setAttribute("prestamos", prestamosParaPagina);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("lectores", lectores);
            request.setAttribute("bibliotecarios", bibliotecarios);
            request.setAttribute("materiales", materiales);
            request.setAttribute("estados", estados);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga los datos necesarios para editar un préstamo específico
     */
    private void cargarDatosParaEdicion(HttpServletRequest request, String idPrestamoStr) {
        try {
            long idPrestamo = Long.parseLong(idPrestamoStr);
            
            // Obtener todos los préstamos y buscar el específico
            DtPrestamoArray prestamosArray = prestamoService.listarPrestamos();
            List<DtPrestamo> prestamos = prestamosArray.getItem();
            
            DtPrestamo prestamoAEditar = null;
            for (DtPrestamo p : prestamos) {
                if (p.getId() == idPrestamo) {
                    prestamoAEditar = p;
                    break;
                }
            }
            
            if (prestamoAEditar != null) {
                request.setAttribute("prestamoEditar", prestamoAEditar);
            } else {
                request.setAttribute("error", "Préstamo no encontrado.");
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar el préstamo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Modifica un préstamo con los datos del formulario
     */
    private void modificarPrestamo(HttpServletRequest request) throws Exception {
        // Obtener datos del formulario
        long idPrestamo = Long.parseLong(request.getParameter("idPrestamo"));
        long idMaterial = Long.parseLong(request.getParameter("idMaterial"));
        String correoLector = request.getParameter("correoLector");
        String correoBibliotecario = request.getParameter("correoBibliotecario");
        String fechaSolicitudStr = request.getParameter("fechaSolicitud");
        String fechaDevolucionStr = request.getParameter("fechaDevolucion");
        String estadoStr = request.getParameter("estado");
        
        // Buscar el préstamo original
        DtPrestamoArray prestamosArray = prestamoService.listarPrestamos();
        List<DtPrestamo> prestamos = prestamosArray.getItem();
        
        DtPrestamo prestamoOriginal = null;
        for (DtPrestamo p : prestamos) {
            if (p.getId() == idPrestamo) {
                prestamoOriginal = p;
                break;
            }
        }
        
        if (prestamoOriginal == null) {
            throw new Exception("Préstamo no encontrado");
        }
        
        // Crear objeto DtPrestamo actualizado
        DtPrestamo prestamoModificado = new DtPrestamo();
        prestamoModificado.setId(idPrestamo);
        
        // Buscar y asignar el material
        DtMaterialArray materialesArray = prestamoService.getListadoMateriales();
        List<DtMaterial> materiales = materialesArray.getItem();
        for (DtMaterial m : materiales) {
            if (m.getId() == idMaterial) {
                prestamoModificado.setMaterial(m);
                break;
            }
        }
        
        // Buscar y asignar el lector
        DtLectorArray lectoresArray = prestamoService.getListadoLectores();
        List<DtLector> lectores = lectoresArray.getItem();
        for (DtLector l : lectores) {
            if (l.getEmail().equals(correoLector)) {
                prestamoModificado.setLector(l);
                break;
            }
        }
        
        // Buscar y asignar el bibliotecario
        DtBibliotecarioArray bibliotecariosArray = prestamoService.getListadoBibliotecarios();
        List<DtBibliotecario> bibliotecarios = bibliotecariosArray.getItem();
        for (DtBibliotecario b : bibliotecarios) {
            if (b.getEmail().equals(correoBibliotecario)) {
                prestamoModificado.setBibliotecario(b);
                break;
            }
        }
        
        // Convertir y asignar fechas
        if (fechaSolicitudStr != null && !fechaSolicitudStr.isEmpty()) {
            prestamoModificado.setFechaSolicitud(stringToXMLGregorianCalendar(fechaSolicitudStr));
        }
        
        if (fechaDevolucionStr != null && !fechaDevolucionStr.isEmpty()) {
            prestamoModificado.setFechaDevolucion(stringToXMLGregorianCalendar(fechaDevolucionStr));
        }
        
        // Asignar estado
        if (estadoStr != null && !estadoStr.isEmpty()) {
            prestamoModificado.setEstado(EstadoPmo.fromValue(estadoStr));
        }
        
        // Llamar al servicio para modificar
        try {
            prestamoService.modificarPrestamo(prestamoModificado);
        } catch (PrestamoNoExisteExcepcion_Exception e) {
            throw new Exception("El préstamo no existe: " + e.getMessage());
        }
    }

    /**
     * Convierte un String de fecha en formato "yyyy-MM-dd'T'HH:mm" a XMLGregorianCalendar
     */
    private XMLGregorianCalendar stringToXMLGregorianCalendar(String dateString) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date date = sdf.parse(dateString);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            throw new Exception("Error al convertir la fecha: " + e.getMessage());
        }
    }
}

