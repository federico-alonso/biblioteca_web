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

@WebServlet("/HistorialPrestamosBibliotecarioServlet")
public class HistorialPrestamosBibliotecarioServlet extends HttpServlet {

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

        // if ("historialPrestamosBibliotecario".equals(accion)) {
            String nombreLector = (String) request.getSession().getAttribute("nombreLector");

            DtBibliotecario bibliotecario = new DtBibliotecario();
            bibliotecario.setNombre(nombreLector);

            DtPrestamoArray respuesta = prestamoService.consultarPrestamosBibliotecario(bibliotecario);
            List<DtPrestamo> prestamos = respuesta.getItem();

            request.setAttribute("prestamosBibliotecario", prestamos);
            request.getRequestDispatcher("historialPrestamosBibliotecario.jsp").forward(request, response);
            return;
        //}

    }
}
