package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cliente.prestamo.PrestamoPublish;
import cliente.prestamo.PrestamoPublishService;
import cliente.prestamo.DtMaterial;
import cliente.prestamo.DtMaterialArray;

import java.io.IOException;
import java.util.List;

@WebServlet("/DonacionesServlet")
public class DonacionesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PrestamoPublish prestamoService;

    @Override
    public void init() throws ServletException {
        PrestamoPublishService service = new PrestamoPublishService();
        prestamoService = service.getPrestamoPublishPort();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nombreLector = request.getParameter("nombreLector");
            request.setAttribute("nombreLector", nombreLector);

            DtMaterialArray resultado = prestamoService.getListadoMateriales();
            List<DtMaterial> lista = resultado.getItem();

            request.setAttribute("donaciones", lista);
            request.getRequestDispatcher("donaciones.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("mensaje", "Error al cargar el catálogo.");
            request.getRequestDispatcher("donaciones.jsp").forward(request, response);
        }
    }
}
