<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, models.*"%>
<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>
<html>
<head>
    <title>Listado de Productos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #b5eaea, #f7d1e3, #c7ffd8);
            background-size: 300% 300%;
            animation: gradientMove 10s ease infinite;
            min-height: 100vh;
            margin: 0;
            padding-top: 40px;
            font-family: "Segoe UI", sans-serif;
        }
        @keyframes gradientMove {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }
        h1 {
            color: #ff4fa3;
            font-weight: bold;
            text-shadow: 0 0 8px rgba(255, 79, 163, 0.4);
        }
        .table-success {
            background-color: #a8e6cf !important;
        }
        .table-light {
            background-color: #ffd3e2 !important;
        }
        .alert-info {
            background-color: #bde0fe;
            color: #000;
            border: none;
            font-weight: 600;
        }
        .btn-success {
            background-color: #caffbf !important;
            border: none;
            color: #000;
            font-weight: 600;
        }
        .btn-primary {
            background-color: #ffd3e2 !important;
            border: none;
            color: #000;
            font-weight: 600;
        }
        .btn-secondary {
            background-color: #bde0fe !important;
            border: none;
            color: #000;
            font-weight: 600;
        }
        .btn:hover {
            transform: scale(1.05);
            transition: 0.2s;
            box-shadow: 0 4px 12px rgba(0,0,0,0.25);
        }
        .container {
            background: rgba(255, 255, 255, 0.25);
            padding: 35px;
            border-radius: 20px;
            backdrop-filter: blur(10px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
        }
    </style>
</head>

<body>

<div>

    <h1 class="text-center mb-4">Listado de Productos</h1>

        <% if (username.isPresent()) { %>
    <div class="alert alert-info text-center">
        Hola <strong><%=username.get()%></strong>, ¡Bienvenido!
    </div>

    <div class="text-center mb-3">
        <a href="<%=request.getContextPath()%>/productos/form" class="btn btn-primary">Crear un producto</a>
    </div>
        <% } %>

    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover align-middle shadow-sm">

            <thead class="table-success">
            <tr>
                <th>Id Producto</th>
                <th>Nombre del Producto</th>
                <th>Categoria</th>
                <th>Stock</th>
                <th>Descripción</th>
                <th>Fecha Elaboración</th>
                <th>Fecha Caducidad</th>
                <th>Condición</th>
                <% if (username.isPresent()) { %>
                <th>Precio</th>
                <th>Acción</th>
                <% } %>
            </tr>
            </thead>

            <tbody>
            <% for (Producto p : productos) { %>
            <tr>
                <td><%=p.getId()%></td>
                <td><%=p.getNombreProducto()%></td>
                <td><%=p.getCategoria().getNombre()%></td>
                <td><%=p.getCantidad()%></td>
                <td><%=p.getDescripcion()%></td>
                <td><%=p.getFechaElaboracion()%></td>
                <td><%=p.getFechaCaducidad()%></td>
                <td><%=p.getCondicion()%>
                </td>

                <% if (username.isPresent()) { %>
                <td>$<%=p.getPrecio()%></td>
                <td>
                    <% if (p.getCondicion() == 1) { %>
                    <a href="<%=request.getContextPath()%>/agregar-carro?id=<%=p.getId()%>"
                       class="btn btn-sm btn-outline-primary" title="Agregar al carro">
                        <i class="bi bi-cart-plus"></i>
                    </a>
                    <a href="<%=request.getContextPath()%>/eliminar?id=<%=p.getId()%>"
                       class="btn btn-sm btn-outline-danger"
                       onclick="return confirm('¿Seguro que desea desactivar?');" title="Desactivar">
                        <i class="bi bi-trash"></i>
                    </a>
                    <% } else { %>
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
        <div class="text-center mb-3">
            <a href="<%=request.getContextPath()%>/index.html" class="btn btn-primary">
                Volver
            </a>

        </div>

    </div>

</body>
</html>