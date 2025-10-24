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
import cliente.prestamo.DtMaterial;
import cliente.prestamo.DtLibro;
import cliente.prestamo.DtArticuloEspecial;
import cliente.prestamo.EstadoPmo;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/MaterialesPopularesServlet")
public class MaterialesPopularesServlet extends HttpServlet {

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

        try {
            // Obtener todos los materiales con préstamos
            DtMaterialConPrestamoArray materialesConPrestamoArray = prestamoService.getMaterialesConPrestamoTodos();
            List<DtMaterialConPrestamo> materialesConPrestamo = materialesConPrestamoArray.getItem();

            // Crear un mapa para contar préstamos por material
            Map<Long, MaterialInfo> materialesCount = new HashMap<>();

            for (DtMaterialConPrestamo item : materialesConPrestamo) {
                DtMaterial material = item.getMaterial();
                Long materialId = material.getId();

                // Inicializar contador si no existe
                if (!materialesCount.containsKey(materialId)) {
                    materialesCount.put(materialId, new MaterialInfo(material));
                }

                // Incrementar contador de préstamos totales
                materialesCount.get(materialId).incrementarTotal();

                // Si el préstamo está activo (EN_CURSO), incrementar contador de pendientes
                if (item.getPrestamo() != null && 
                    item.getPrestamo().getEstado() == EstadoPmo.EN_CURSO) {
                    materialesCount.get(materialId).incrementarPendientes();
                }
            }

            // Filtrar materiales con préstamos pendientes y ordenar por cantidad
            List<MaterialInfo> materialesPopulares = materialesCount.values().stream()
                .filter(material -> material.getPrestamosPendientes() > 0)
                .sorted((m1, m2) -> Integer.compare(m2.getPrestamosPendientes(), m1.getPrestamosPendientes()))
                .collect(Collectors.toList());

            request.setAttribute("materialesPopulares", materialesPopulares);
            request.getRequestDispatcher("materialesPopulares.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error al obtener materiales populares: " + e.getMessage());
            request.getRequestDispatcher("materialesPopulares.jsp").forward(request, response);
        }
    }

    // Clase interna para almacenar información del material
    public static class MaterialInfo {
        private DtMaterial material;
        private int prestamosTotales;
        private int prestamosPendientes;

        public MaterialInfo(DtMaterial material) {
            this.material = material;
            this.prestamosTotales = 0;
            this.prestamosPendientes = 0;
        }

        public void incrementarTotal() {
            this.prestamosTotales++;
        }

        public void incrementarPendientes() {
            this.prestamosPendientes++;
        }

        // Getters
        public DtMaterial getMaterial() { return material; }
        public int getPrestamosTotales() { return prestamosTotales; }
        public int getPrestamosPendientes() { return prestamosPendientes; }
    }
}
