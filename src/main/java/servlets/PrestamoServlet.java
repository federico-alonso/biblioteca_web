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
            
            // Validar que el lector no esté suspendido
            if (lectorCompleto.getEstado() != null && lectorCompleto.getEstado().toString().equals("SUSPENDIDO")) {
                DtMaterialArray recarga = prestamoService.getListadoMateriales();
                request.setAttribute("donaciones", recarga.getItem());
                request.setAttribute("nombreLector", nombreLector);
                request.setAttribute("mensaje", "⚠️ No puedes solicitar préstamos. Tu cuenta está suspendida. Contacta al administrador.");
                request.getRequestDispatcher("donaciones.jsp").forward(request, response);
                return;
            }

            GregorianCalendar gc = new GregorianCalendar();
            XMLGregorianCalendar fechaSolicitud = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);

            // Crear préstamo sin bibliotecario - se asignará cuando el bibliotecario lo autorice o rechace
            DtPrestamo prestamo = new DtPrestamo();
            prestamo.setMaterial(materialSeleccionado);
            prestamo.setLector(lectorCompleto); // Usar el lector completo con zona
            prestamo.setBibliotecario(null); // Se asignará cuando el bibliotecario lo procese
            prestamo.setFechaSolicitud(fechaSolicitud);
            prestamo.setEstado(EstadoPmo.PENDIENTE); // El préstamo queda pendiente de autorización

            prestamoService.altaPrestamo(prestamo);

            DtMaterialArray recarga = prestamoService.getListadoMateriales();
            request.setAttribute("donaciones", recarga.getItem());
            request.setAttribute("nombreLector", nombreLector);
            request.setAttribute("mensaje", "Préstamo registrado y pendiente de autorización. La solicitud será revisada por un bibliotecario.");
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

