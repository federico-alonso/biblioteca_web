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

        DtMaterialConPrestamoArray respuesta = prestamoService.getMaterialesConPrestamoTodos();
        List<DtMaterialConPrestamo> materiales = respuesta.getItem();

        request.setAttribute("materialesConPrestamo", materiales);
        request.getRequestDispatcher("gestionPrestamos.jsp").forward(request, response);
    }
}
