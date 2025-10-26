package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import cliente.login.LoginPublish;
import cliente.login.LoginPublishService;
import cliente.login.DtLoginResultado;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginPublish loginService;

    @Override
    public void init() throws ServletException {
        LoginPublishService service = new LoginPublishService();
        loginService = service.getLoginPublishPort();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        DtLoginResultado resultado = loginService.login(email, contrasena);

        if (resultado != null) {
            String nombre = resultado.getUsuario().getNombre(); // ✅ correcto
 // ← usamos el nombre real del lector
            request.getSession().setAttribute("nombreLector", nombre); // ← propagamos el nombre
            request.getSession().setAttribute("tipoUsuario", resultado.getTipo()); // ← guardamos el tipo de usuario

            switch (resultado.getTipo()) {
                case "lector":
                    response.sendRedirect(request.getContextPath() + "/menuLector.jsp");
                    break;
                case "bibliotecario":
                    response.sendRedirect(request.getContextPath() + "/menuBibliotecario.jsp");
                    break;
                default:
                    request.setAttribute("mensaje", "Tipo de usuario desconocido.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("mensaje", "Credenciales inválidas.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
