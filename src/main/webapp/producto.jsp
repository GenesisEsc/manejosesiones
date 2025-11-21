<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, models.*"%>

<%
    // Aquí recupero desde el request la lista de productos que el servlet envió.
    // Esta lista es la que voy a mostrar en la tabla.
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");

    // También recupero el username, pero viene como Optional.
    // Esto me sirve para saber si el usuario inició sesión o no.
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>

<html>
<head>
    <title>Listado de Productos</title>

    <!-- Bootstrap y los iconos que necesito para los botones -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>

<body>

<div>

    <h1 class="text-center mb-4">Listado de Productos</h1>

    <%
        // Si el usuario está logueado, muestro un saludo y el botón para crear productos.
        // Esto asegura que solo usuarios registrados puedan gestionar productos.
        if (username.isPresent()) {
    %>
    <div class="alert alert-info text-center">
        Hola <strong><%=username.get()%></strong>, ¡Bienvenido!
    </div>

    <div class="text-center mb-3">
        <!-- Enlace para ir al formulario de creación de productos -->
        <a href="<%=request.getContextPath()%>/productos/form" class="btn btn-primary">Crear un producto</a>
    </div>
    <% } %>

    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover align-middle shadow-sm">

            <thead class="table-success">
            <tr>
                <!-- Encabezados de la tabla, uno por cada atributo del producto -->
                <th>Id Producto</th>
                <th>Nombre del Producto</th>
                <th>Categoria</th>
                <th>Stock</th>
                <th>Descripción</th>
                <th>Fecha Elaboración</th>
                <th>Fecha Caducidad</th>
                <th>Condición</th>

                <%
                    // Solo muestro precio y las acciones (botones) si hay usuario logueado.
                    if (username.isPresent()) {
                %>
                <th>Precio</th>
                <th>Acción</th>
                <% } %>
            </tr>
            </thead>

            <tbody>
            <%
                // Recorro la lista de productos y los voy pintando en la tabla.
                for (Producto p : productos) {
            %>
            <tr>
                <!-- Aquí imprimo cada atributo del producto -->
                <td><%=p.getId()%></td>
                <td><%=p.getNombreProducto()%></td>
                <td><%=p.getCategoria().getNombre()%></td>
                <td><%=p.getCantidad()%></td>
                <td><%=p.getDescripcion()%></td>
                <td><%=p.getFechaElaboracion()%></td>
                <td><%=p.getFechaCaducidad()%></td>

                <!-- La condición del producto (por ejemplo 1 activo, 0 inactivo) -->
                <td><%=p.getCondicion()%></td>

                <% if (username.isPresent()) { %>
                <!-- Muestro el precio solo para usuarios con sesión -->
                <td>$<%=p.getPrecio()%></td>

                <td>
                    <%
                        // Si el producto está activo (condición = 1), muestro los botones
                        // para agregar al carro y para desactivar el producto.
                        if (p.getCondicion() == 1) {
                    %>
                    <!-- Agregar al carro -->
                    <a href="<%=request.getContextPath()%>/agregar-carro?id=<%=p.getId()%>"
                       class="btn btn-sm btn-outline-primary" title="Agregar al carro">
                        <i class="bi bi-cart-plus"></i>
                    </a>

                    <!-- Desactivar producto -->
                    <a href="<%=request.getContextPath()%>/eliminar?id=<%=p.getId()%>"
                       class="btn btn-sm btn-outline-danger"
                       onclick="return confirm('¿Seguro que desea desactivar?');"
                       title="Desactivar">
                        <i class="bi bi-trash"></i>
                    </a>

                    <%
                        // Si el producto está inactivo (condición diferente de 1)
                        // muestro la opción para activarlo nuevamente.
                    } else {
                    %>
                    <a href="<%=request.getContextPath()%>/activar?id=<%=p.getId()%>"
                       class="btn btn-sm btn-success" title="Activar producto">
                        <i class="bi bi-check-circle"></i> Activar
                    </a>
                    <% } %>
                </td>
                <% } %>
            </tr>
            <% } %>
            </tbody>
        </table>

        <!-- Botón para volver al inicio -->
        <div class="text-center mb-3">
            <a href="<%=request.getContextPath()%>/index.html" class="btn btn-primary">
                Volver
            </a>
        </div>

    </div>

</div>

</body>
</html>
