package servlets;

import cliente.listarPrestamosZona.DtEstadoPorZona;
import cliente.listarPrestamosZona.DtEstadoPorZonaArray;
import cliente.listarPrestamosZona.ListarPrestamosZonaPublish;
import cliente.listarPrestamosZona.ListarPrestamosZonaPublishService;
import cliente.listarPrestamosZona.ResumenEstado;
import cliente.listarPrestamosZona.Zona;
import cliente.listarPrestamosZona.EstadoPmo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet para generar reporte de préstamos por zona.
 * Muestra estadísticas de préstamos agrupados por zona geográfica.
 */
@WebServlet("/ReportePrestamosZonaServlet")
public class ReportePrestamosZonaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ListarPrestamosZonaPublish zonaService;

    @Override
    public void init() throws ServletException {
        ListarPrestamosZonaPublishService service = new ListarPrestamosZonaPublishService();
        zonaService = service.getListarPrestamosZonaPublishPort();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener resumen de préstamos por zona desde el Web Service
            DtEstadoPorZonaArray resumenArray = zonaService.getResumenPrestamosPorZona();
            List<DtEstadoPorZona> resumenPorZona = resumenArray.getItem();

            // Crear estructura de datos para el JSP con totales calculados
            Map<Zona, ZonaInfo> zonasInfo = new HashMap<>();
            int totalGeneral = 0;

            for (DtEstadoPorZona dtZona : resumenPorZona) {
                Zona zona = dtZona.getZona();
                List<ResumenEstado> resumen = dtZona.getResumen();
                
                ZonaInfo info = new ZonaInfo(zona);
                
                // Procesar cada estado y contar totales
                for (ResumenEstado resumenEstado : resumen) {
                    EstadoPmo estado = resumenEstado.getEstado();
                    int cantidad = resumenEstado.getCantidad();
                    
                    switch (estado) {
                        case PENDIENTE:
                            info.setPendientes(cantidad);
                            break;
                        case EN_CURSO:
                            info.setActivos(info.getActivos() + cantidad);
                            break;
                        case DEVUELTO:
                            info.setDevueltos(cantidad);
                            break;
                        case RECHAZADO:
                            // Los rechazados no se contabilizan en los totales
                            break;
                    }
                    
                    info.setTotal(info.getTotal() + cantidad);
                }
                
                zonasInfo.put(zona, info);
                totalGeneral += info.getTotal();
            }

            // Establecer atributos para la vista
            request.setAttribute("zonasInfo", zonasInfo);
            request.setAttribute("totalGeneral", totalGeneral);
            request.setAttribute("zonas", Zona.values());

        } catch (Exception e) {
            request.setAttribute("error", "Error al obtener el reporte de préstamos por zona: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("reportePrestamosPorZona.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Clase interna para almacenar información agregada de una zona
     */
    public static class ZonaInfo {
        private Zona zona;
        private int pendientes;
        private int activos;
        private int devueltos;
        private int total;

        public ZonaInfo(Zona zona) {
            this.zona = zona;
            this.pendientes = 0;
            this.activos = 0;
            this.devueltos = 0;
            this.total = 0;
        }

        // Getters y Setters
        public Zona getZona() {
            return zona;
        }

        public String getNombreZona() {
            return formatearNombreZona(zona);
        }

        public int getPendientes() {
            return pendientes;
        }

        public void setPendientes(int pendientes) {
            this.pendientes = pendientes;
        }

        public int getActivos() {
            return activos;
        }

        public void setActivos(int activos) {
            this.activos = activos;
        }

        public int getDevueltos() {
            return devueltos;
        }

        public void setDevueltos(int devueltos) {
            this.devueltos = devueltos;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        /**
         * Formatea el nombre de la zona para mostrar en la UI
         */
        private String formatearNombreZona(Zona zona) {
            if (zona == null) return "Sin Zona";
            
            String nombre = zona.name().replace("_", " ");
            String[] palabras = nombre.split(" ");
            StringBuilder resultado = new StringBuilder();
            
            for (String palabra : palabras) {
                if (resultado.length() > 0) {
                    resultado.append(" ");
                }
                resultado.append(palabra.charAt(0))
                         .append(palabra.substring(1).toLowerCase());
            }
            
            return resultado.toString();
        }
    }
}

