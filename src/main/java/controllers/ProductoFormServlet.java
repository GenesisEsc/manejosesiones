package controllers;
/**
 * autores; dilan, genesis. christian
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Categoria;
import models.Producto;
import services.ProductoService;
import services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //obtenemos la conexion
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);

        Long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        Producto producto = new Producto();
        producto.setCategoria(new Categoria());

        if (id > 0) {
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) {
                producto = o.get();
            }
        }

        req.setAttribute("categorias", service.listarCategorias());
        req.setAttribute("producto", producto);
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Obtener conexión y servicio
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);

        // 2. Recibir parámetros como Strings puros primero
        String nombre = req.getParameter("nombre");
        String precioParam = req.getParameter("precio");
        String stockParam = req.getParameter("stock");
        String descripcion = req.getParameter("descripcion");
        String fechaElaboracionStr = req.getParameter("fecha_elaboracion");
        String fechaCaducidadStr = req.getParameter("fecha_caducidad");
        String categoriaIdStr = req.getParameter("categoria");

        // 3. Validaciones
        Map<String, String> errores = new HashMap<>();

        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "El nombre no puede estar vacío");
        }

        // Validación y parseo de Precio
        Double precio = null;
        if (precioParam == null || precioParam.isBlank()) {
            errores.put("precio", "El precio no puede estar vacío");
        } else {
            try {
                // Reemplazar coma por punto
                precio = Double.valueOf(precioParam.trim().replace(",", "."));
                if (precio <= 0) {
                    errores.put("precio", "El precio debe ser mayor que 0");
                }
            } catch (NumberFormatException e) {
                errores.put("precio", "El precio debe ser un número válido");
            }
        }

        // Validación y parseo de Stock
        Integer stock = 0;
        try {
            if(stockParam == null || stockParam.isBlank()) {
                errores.put("stock", "El stock no puede estar vacío");
            } else {
                stock = Integer.valueOf(stockParam);
            }
        } catch (NumberFormatException e) {
            errores.put("stock", "El stock debe ser un entero válido");
        }

        // Validación y parseo de Categoría
        Long idCategoria = 0L;
        try {
            idCategoria = Long.parseLong(categoriaIdStr);
            if (idCategoria.equals(0L)) {
                errores.put("categoria", "La categoría es requerida");
            }
        } catch (NumberFormatException e) {
            errores.put("categoria", "Formato de categoría inválido");
        }

        // Validación de Fechas
        if (fechaElaboracionStr == null || fechaElaboracionStr.isBlank()) {
            errores.put("fecha_elaboracion", "La fecha de elaboración es requerida");
        }
        if (fechaCaducidadStr == null || fechaCaducidadStr.isBlank()) {
            errores.put("fecha_caducidad", "La fecha de caducidad es requerida");
        }

        LocalDate fechaElaboracion = null;
        LocalDate fechaCaducidad = null;

        try {
            if(fechaElaboracionStr != null && !fechaElaboracionStr.isBlank())
                fechaElaboracion = LocalDate.parse(fechaElaboracionStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if(fechaCaducidadStr != null && !fechaCaducidadStr.isBlank())
                fechaCaducidad = LocalDate.parse(fechaCaducidadStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            // Si falla el parseo, asignamos null pero el error ya debería haber saltado arriba si estaba vacío
            fechaElaboracion = null;
            fechaCaducidad = null;
        }

        // Obtener ID del producto (hidden input)
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        // 4. Armar el objeto Producto con los datos capturados
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombreProducto(nombre);
        producto.setPrecio(precio);
        producto.setCantidad(stock);
        producto.setDescripcion(descripcion);
        producto.setFechaElaboracion(fechaElaboracion);
        producto.setFechaCaducidad(fechaCaducidad);
        producto.setCondicion(1);

        Categoria c = new Categoria();
        c.setId(idCategoria);
        producto.setCategoria(c);

        // 5. Decisión Final: Guardar o Devolver errores
        if (errores.isEmpty()) {
            // no hay errores, guardamos y nos vamos
            service.guardar(producto);
            resp.sendRedirect(req.getContextPath() + "/productos");
        } else {
            // Camino Triste: Hay errores, volvemos al formulario
            req.setAttribute("errores", errores);
            req.setAttribute("categorias", service.listarCategorias());
            req.setAttribute("producto", producto); // Devolvemos lo que el usuario escribió para no borrarle todo

            getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
        }
    }
}