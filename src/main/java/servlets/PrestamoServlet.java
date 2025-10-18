package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cliente.prestamo.PrestamoPublish;
import cliente.prestamo.PrestamoPublishService;
import cliente.prestamo.DtPrestamo;
import cliente.prestamo.DtMaterial;
import cliente.prestamo.DtMaterialArray;
import cliente.prestamo.DtLector;
import cliente.prestamo.EstadoPmo;
import cliente.prestamo.PrestamoYaExisteExcepcion_Exception;

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

        String nombreLector = request.getParameter("nombreLector");
        String idMaterialStr = request.getParameter("idMaterial");

        try {
            Long idMaterial = Long.parseLong(idMaterialStr);

            // Buscar el material por ID
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

            // Construir el lector
            DtLector lector = new DtLector();
            lector.setNombre(nombreLector);

            // Fecha actual
            GregorianCalendar gc = new GregorianCalendar();
            XMLGregorianCalendar fechaSolicitud = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);

            // Construir el préstamo
            DtPrestamo prestamo = new DtPrestamo();
            prestamo.setMaterial(materialSeleccionado);
            prestamo.setLector(lector);
            prestamo.setFechaSolicitud(fechaSolicitud);
            prestamo.setEstado(EstadoPmo.PENDIENTE);

            // Registrar el préstamo
            prestamoService.altaPrestamo(prestamo);

            DtMaterialArray recarga = prestamoService.getListadoMateriales();
            request.setAttribute("donaciones", recarga.getItem());
            request.setAttribute("nombreLector", nombreLector);
            request.setAttribute("mensaje", "Préstamo registrado correctamente.");
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
