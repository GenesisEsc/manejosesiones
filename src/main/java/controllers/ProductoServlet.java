package controllers;

/**
 * autor: Génesis
 * fecha: 11/11/2025
 * descripción: servlet encargado de mostrar un listado de productos en formato HTML.
 * Si el usuario ha iniciado sesión, se muestra un mensaje de bienvenida
 * y también se revelan datos adicionales como el precio del producto.
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.LoginService;
import services.LoginServiceSessionImplement;
import services.ProductoService;
import services.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/**
 * @WebServlet define las rutas o URLs con las que este servlet responderá.
 * En este caso: "/productos.html" y "/productos".
 */
@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    /**
     * Metodo doGet: se ejecuta cuando el cliente realiza una petición GET (por ejemplo,
     * al ingresar la URL en el navegador o hacer clic en un enlace).
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Se crea una instancia del servicio de productos
        // que maneja la obtención de la lista de productos disponibles.
        ProductoService service = new ProductoServiceImplement();
        List<Producto> productos = service.listar(); // se obtiene la lista completa

        // Se crea el servicio de autenticación de usuarios
        // para verificar si existe una sesión activa.
        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> usernameOptional = auth.getUsername(req);

        // Configuramos el tipo de contenido de la respuesta como HTML con codificación UTF-8
        resp.setContentType("text/html;charset=UTF-8");

        // try-with-resources para asegurar que el PrintWriter se cierre automáticamente
        try (PrintWriter out = resp.getWriter()) {
            // Inicia la estructura básica de la página HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=utf-8>");
            out.println("<title>Listado de Productos</title>");
            if (usernameOptional.isPresent()) {
                out.println("div style='color: blue;'>Hola" + usernameOptional.get() + "Bienvenido!</div>");
            }
            // Vincula el archivo CSS para los estilos
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/styles.css'>");
            out.println("</head>");
            out.println("<body>");

            // Título principal de la página
            out.println("<h1>Listado de Productos</h1>");

            // Si hay un usuario autenticado, se muestra su nombre y un saludo
            if (usernameOptional.isPresent()) {
                out.println("<h2 class='bienvenido'>Hola <span class='admin'>" + usernameOptional.get() + "</span> ¡Bienvenido!</h2>");
            }

            // Contenedor de la tabla para aplicar estilos CSS fácilmente
            out.println("<div class='tabla-container'>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>NOMBRE</th>");
            out.println("<th>TIPO</th>");

            // Si el usuario está logueado, se muestra también la columna de precios
            if (usernameOptional.isPresent()) {
                out.println("<th>PRECIO</th>");
            }
            out.println("</tr>");

            // Recorremos la lista de productos obtenidos del servicio
            // y generamos una fila HTML por cada producto
            productos.forEach(p -> {
                out.println("<tr>");
                out.print("<td>" + p.getIdProducto() + "</td>");
                out.print("<td>" + p.getNombre() + "</td>");
                out.print("<td>" + p.getTipo() + "</td>");

                // Solo los usuarios con sesión activa pueden ver los precios
                if (usernameOptional.isPresent()) {
                    out.println("<td>" + p.getPrecio() + "</td>");
                    out.println("<td><a href=\""
                            +req.getContextPath()
                            +"/agregar-carro?id=?"
                            +p.getIdProducto()
                            +"\">Agregar Producto al carro</a></td>");
                }
                out.println("</tr>");
            });

            // Cierre de la tabla y el contenedor
            out.println("</table>");
            out.println("</div>");

            // Cierre del cuerpo y del documento HTML
            out.println("</body>");
            out.println("</html>");
        }
    }
}
