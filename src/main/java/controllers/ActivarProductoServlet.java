package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.ProductoService;
import services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

// se define el endpoint, este servlet atenderá las peticiones a "/activar"
@WebServlet("/activar")
public class ActivarProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Recupero la conexión a la BBDD
        Connection conn = (Connection) req.getAttribute("conn");

        // 2. Instancio el servicio pasándole la conexión necesaria para trabajar
        ProductoService service = new ProductoServiceJdbcImpl(conn);

        long id;
        try {
            // 3. Intento parsear el ID que viene como parámetro en la URL
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            // Si el ID viene mal formado o nulo, asigno 0 para evitar errores y que no entre en el 'if' posterior
            id = 0L;
        }

        // 4. Lógica de negocio
        if (id > 0) {
            // Busco si el producto realmente existe antes de intentar nada
            Optional<Producto> o = service.porId(id);

            if (o.isPresent()) {
                // Si existe, procedo a cambiar su estado usando el método específico del servicio
                service.activar(id);
            }
        }

        // 5. Finalización
        // Independientemente de si se activó o hubo un error con el ID, redirijo al usuario al listado principal
        resp.sendRedirect(req.getContextPath() + "/productos");
    }
}