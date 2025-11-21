package controllers;

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
import java.util.Optional;

// CORRECCION 1: Cambiamos la ruta para que sea hija de productos
@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        // CORRECCION 2: Agregamos .forward(req, resp) al final
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);

        String nombre = req.getParameter("nombre");
        Double precio;
        try { precio = Double.valueOf(req.getParameter("precio")); } catch (NumberFormatException e) { precio = 0.0; }
        Integer stock;
        try { stock = Integer.valueOf(req.getParameter("stock")); } catch (NumberFormatException e) { stock = 0; }
        String descripcion = req.getParameter("descripcion");
        Long idCategoria = Long.valueOf(req.getParameter("categoria"));

        // Parseo de fechas
        LocalDate fechaElab = LocalDate.parse(req.getParameter("fecha_elaboracion"));
        LocalDate fechaCad = LocalDate.parse(req.getParameter("fecha_caducidad"));

        long id;
        try { id = Long.parseLong(req.getParameter("id")); } catch (NumberFormatException e) { id = 0L; }

        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombreProducto(nombre);
        producto.setPrecio(precio);
        producto.setCantidad(stock);
        producto.setDescripcion(descripcion);
        producto.setFechaElaboracion(fechaElab);
        producto.setFechaCaducidad(fechaCad);
        producto.setCondicion(1);

        Categoria c = new Categoria();
        c.setId(idCategoria);
        producto.setCategoria(c);

        service.guardar(producto);

        // Redireccionamos a la lista principal
        resp.sendRedirect(req.getContextPath() + "/productos");
    }
}