<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Producto" %>
<%@ page import="models.Categoria" %>
<%@ page import="java.util.List" %>
<%
    Producto producto = (Producto) request.getAttribute("producto");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    String titulo = (producto.getId() != null && producto.getId() > 0) ? "Editar Producto" : "Crear Producto";
%>
<html>
<head>
    <title><%= titulo %></title>

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
            padding-bottom: 40px;
            font-family: "Segoe UI", sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        @keyframes gradientMove {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        /* --- Estilos del Formulario Glassmorphism --- */
        .glass-container {
            background: rgba(255, 255, 255, 0.35); /* Un poco más opaco para leer mejor */
            padding: 40px;
            border-radius: 20px;
            backdrop-filter: blur(12px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 600px; /* Ancho máximo controlado */
            border: 1px solid rgba(255, 255, 255, 0.5);
        }

        h1 {
            color: #ff4fa3;
            font-weight: bold;
            text-shadow: 0 0 8px rgba(255, 79, 163, 0.4);
            text-align: center;
            margin-bottom: 30px;
        }

        /* --- Estilos de Inputs y Labels --- */
        .form-label {
            font-weight: 600;
            color: #444;
            margin-bottom: 5px;
        }
        .form-control, .form-select {
            border-radius: 10px;
            border: 1px solid rgba(255,255,255,0.8);
            background-color: rgba(255,255,255,0.8);
            padding: 10px 15px;
        }
        .form-control:focus, .form-select:focus {
            background-color: #fff;
            border-color: #ff4fa3;
            box-shadow: 0 0 0 0.25rem rgba(255, 79, 163, 0.25);
        }

        .btn-custom-save {
            background-color: #ffd3e2;
            border: none;
            color: #000;
            font-weight: 700;
            padding: 10px 25px;
            border-radius: 10px;
        }
        .btn-custom-save:hover {
            background-color: #ffb7d2;
            transform: scale(1.03);
            transition: 0.2s;
        }

        .btn-custom-cancel {
            background-color: #bde0fe; /* Azul pastel */
            border: none;
            color: #000;
            font-weight: 600;
            padding: 10px 25px;
            border-radius: 10px;
        }
        .btn-custom-cancel:hover {
            background-color: #a0cfee;
            transform: scale(1.03);
            transition: 0.2s;
        }
    </style>
</head>
<body>

<div class="glass-container">
    <h1><i class="bi bi-box-seam-fill"></i> <%= titulo %></h1>

    <form action="<%= request.getContextPath() %>/productos/form" method="post">
        <input type="hidden" name="id" value="<%= (producto.getId() != null) ? producto.getId() : "" %>">

        <div class="mb-3">
            <label class="form-label">Nombre del Producto:</label>
            <input type="text" class="form-control" name="nombre"
                   value="<%= (producto.getNombreProducto() != null) ? producto.getNombreProducto() : "" %>"
                   placeholder="Ej: Galletas de Chocolate" required>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">Precio ($):</label>
                <input type="number" step="0.01" class="form-control" name="precio"
                       value="<%= producto.getPrecio() > 0 ? producto.getPrecio() : "" %>"
                       placeholder="0.00" required>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">Stock (Unidades):</label>
                <input type="number" class="form-control" name="stock"
                       value="<%= producto.getCantidad() %>" required>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">Categoría:</label>
            <select name="categoria" class="form-select" required>
                <option value="">-- Seleccionar Categoría --</option>
                <% for(Categoria c : categorias) { %>
                <option value="<%= c.getId() %>"
                        <%= (producto.getCategoria() != null && c.getId().equals(producto.getCategoria().getId())) ? "selected" : "" %>>
                    <%= c.getNombre() %>
                </option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Descripción:</label>
            <textarea class="form-control" name="descripcion" rows="2" placeholder="Detalles del producto..."><%= (producto.getDescripcion() != null) ? producto.getDescripcion() : "" %></textarea>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">Fecha Elaboración:</label>
                <input type="date" class="form-control" name="fecha_elaboracion"
                       value="<%= producto.getFechaElaboracion() %>" required>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">Fecha Caducidad:</label>
                <input type="date" class="form-control" name="fecha_caducidad"
                       value="<%= producto.getFechaCaducidad() %>" required>
            </div>
        </div>

        <div class="d-grid gap-2 d-md-flex justify-content-md-center mt-4">
            <button type="submit" class="btn btn-custom-save shadow-sm">
                <i class="bi bi-save"></i> Guardar
            </button>
            <a href="<%= request.getContextPath() %>/productos" class="btn btn-custom-cancel shadow-sm">
                <i class="bi bi-x-circle"></i> Cancelar
            </a>
        </div>
    </form>
</div>

</body>
</html>
