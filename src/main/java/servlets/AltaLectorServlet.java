package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;

import cliente.altaLector.AltaLectorPublishService;
import cliente.altaLector.AltaLectorPublish;
import cliente.altaLector.Zona;
import cliente.altaLector.LectorRepetidoExcepcion_Exception;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

@WebServlet("/AltaLectorServlet")
public class AltaLectorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String direccion = request.getParameter("direccion");
        String fechaRegistroStr = request.getParameter("fechaRegistro");
        String zonaStr = request.getParameter("zona");

        try {
            // Convertir fecha
            LocalDate localDate = LocalDate.parse(fechaRegistroStr, DateTimeFormatter.ISO_DATE);
            GregorianCalendar gc = GregorianCalendar.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()));
            XMLGregorianCalendar fechaRegistro = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);

            // Convertir zona
            Zona zona = Zona.valueOf(zonaStr);

            // Invocar servicio SOAP
            AltaLectorPublishService service = new AltaLectorPublishService();
            AltaLectorPublish port = service.getAltaLectorPublishPort();

            port.altaLector(nombre, email, contrasena, direccion, fechaRegistro, zona);

            request.setAttribute("mensaje", "Lector registrado correctamente.");
        } catch (LectorRepetidoExcepcion_Exception e) {
            request.setAttribute("mensaje", "Error: el lector ya existe.");
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error inesperado: " + e.getMessage());
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
