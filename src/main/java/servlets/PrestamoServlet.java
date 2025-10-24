package servlets;

import cliente.prestamo.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;

@WebServlet("/RegistrarPrestamoServlet")
public class PrestamoServlet extends HttpServlet {

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

        String accion = request.getParameter("accion");

        if ("verMisPrestamos".equals(accion)) {
            String nombreLector = (String) request.getSession().getAttribute("nombreLector");
        
            // Obtener información completa del lector desde la base de datos
            DtLectorArray lectores = prestamoService.getListadoLectores();
            DtLector lectorCompleto = null;
            for (DtLector l : lectores.getItem()) {
                if (l.getNombre().equals(nombreLector)) {
                    lectorCompleto = l;
                    break;
                }
            }
            
            if (lectorCompleto == null) {
                request.setAttribute("error", "Lector no encontrado.");
                request.getRequestDispatcher("verPrestamos.jsp").forward(request, response);
                return;
            }
        
            DtMaterialConPrestamoArray respuesta = prestamoService.getMaterialesConPrestamo(lectorCompleto);
            List<DtMaterialConPrestamo> materiales = respuesta.getItem();
        
            request.getSession().setAttribute("materialesConPrestamo", materiales);
         // request.getSession().setAttribute("nombreLector", nombreLector);
            request.getRequestDispatcher("verPrestamos.jsp").forward(request, response);
            return;
        }

        // Acción por defecto: registrar préstamo
        String nombreLector = (String) request.getSession().getAttribute("nombreLector");
        String idMaterialStr = request.getParameter("idMaterial");

        try {
            Long idMaterial = Long.parseLong(idMaterialStr);

            DtMaterialArray materiales = prestamoService.getListadoMateriales();
            DtMaterial materialSeleccionado = null;
            for (DtMaterial mat : materiales.getItem()) {
                if (mat.getId() == idMaterial) {
                    materialSeleccionado = mat;
                    break;
                }
            }

            if (materialSeleccionado == null) {
                DtMaterialArray recarga = prestamoService.getListadoMateriales();
                request.setAttribute("donaciones", recarga.getItem());
                request.setAttribute("nombreLector", nombreLector);
                request.setAttribute("mensaje", "Material no encontrado.");
                request.getRequestDispatcher("donaciones.jsp").forward(request, response);
                return;
            }

            DtLector lector = new DtLector();
            lector.setNombre(nombreLector);
            
            // Obtener información completa del lector desde la base de datos
            DtLectorArray lectores = prestamoService.getListadoLectores();
            DtLector lectorCompleto = null;
            for (DtLector l : lectores.getItem()) {
                if (l.getNombre().equals(nombreLector)) {
                    lectorCompleto = l;
                    break;
                }
            }
            
            if (lectorCompleto == null) {
                DtMaterialArray recarga = prestamoService.getListadoMateriales();
                request.setAttribute("donaciones", recarga.getItem());
                request.setAttribute("nombreLector", nombreLector);
                request.setAttribute("mensaje", "Lector no encontrado.");
                request.getRequestDispatcher("donaciones.jsp").forward(request, response);
                return;
            }

            GregorianCalendar gc = new GregorianCalendar();
            XMLGregorianCalendar fechaSolicitud = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);

            // Obtener un bibliotecario para asociar al préstamo
            DtBibliotecarioArray bibliotecarios = prestamoService.getListadoBibliotecarios();
            DtBibliotecario bibliotecario = null;
            if (bibliotecarios.getItem() != null && !bibliotecarios.getItem().isEmpty()) {
                // Asignación balanceada: rotar entre bibliotecarios disponibles
                java.util.Random random = new java.util.Random();
                int index = random.nextInt(bibliotecarios.getItem().size());
                bibliotecario = bibliotecarios.getItem().get(index);
            } else {
                DtMaterialArray recarga = prestamoService.getListadoMateriales();
                request.setAttribute("donaciones", recarga.getItem());
                request.setAttribute("nombreLector", nombreLector);
                request.setAttribute("mensaje", "No hay bibliotecarios disponibles para procesar el préstamo.");
                request.getRequestDispatcher("donaciones.jsp").forward(request, response);
                return;
            }

            DtPrestamo prestamo = new DtPrestamo();
            prestamo.setMaterial(materialSeleccionado);
            prestamo.setLector(lectorCompleto); // Usar el lector completo con zona
            prestamo.setBibliotecario(bibliotecario); // Asociar bibliotecario
            prestamo.setFechaSolicitud(fechaSolicitud);
            prestamo.setEstado(EstadoPmo.EN_CURSO);

            prestamoService.altaPrestamo(prestamo);

            DtMaterialArray recarga = prestamoService.getListadoMateriales();
            request.setAttribute("donaciones", recarga.getItem());
            request.setAttribute("nombreLector", nombreLector);
            String mensajeExito = "Préstamo registrado correctamente.";
            if (bibliotecario != null) {
                mensajeExito += " Procesado por: " + bibliotecario.getNombre();
            }
            request.setAttribute("mensaje", mensajeExito);
            request.getRequestDispatcher("donaciones.jsp").forward(request, response);

        } catch (PrestamoYaExisteExcepcion_Exception e) {
            DtMaterialArray recarga = prestamoService.getListadoMateriales();
            request.setAttribute("donaciones", recarga.getItem());
            request.setAttribute("nombreLector", nombreLector);
            request.setAttribute("mensaje", "Ya existe un préstamo activo para este material.");
            request.getRequestDispatcher("donaciones.jsp").forward(request, response);
        } catch (Exception e) {
            DtMaterialArray recarga = prestamoService.getListadoMateriales();
            request.setAttribute("donaciones", recarga.getItem());
            request.setAttribute("nombreLector", nombreLector);
            request.setAttribute("mensaje", "Error al registrar el préstamo.");
            request.getRequestDispatcher("donaciones.jsp").forward(request, response);
        }
    }

}

