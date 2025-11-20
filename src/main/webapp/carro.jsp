<%@ page contentType="text/html;charset=UTF-8" language="java" import="models.*" %>
<%@ page import="models.DetalleCarro" %>
<%@ page import="models.ItemCarro" %>
<%
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
%>

<html>
<head>
    <title>Carro de compras</title>
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
<div class="container mt-5">
    <h1 class="mb-4">Carro de Compras</h1>

    <%
        if(detalleCarro == null || detalleCarro.getItem().isEmpty() ) {%>
    <div class="alert alert-info">
        <p>Lo sentimos no hay productos en el carro de compras</p>
    </div>
    <% } else {%>

    <table class="table table-striped table-hover align-middle">
        <thead class="table-success">
        <tr>
            <th>ID Producto</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (ItemCarro item: detalleCarro.getItem()) {%>
        <tr>
            <td><%=item.getProducto().getId()%></td>
            <td><%=item.getProducto().getNombreProducto()%></td>
            <td>$<%=item.getProducto().getPrecio()%></td>
            <td><%=item.getCantidad()%></td>
            <td>$<%=item.getSubtotal()%></td>
        </tr>
        <% }%>
        </tbody>
        <tfoot>
        <tr class="table-light">
            <td colspan="4" class="text-end fw-semibold">Subtotal:</td>
            <td class="fw-semibold">$<%=String.format("%.2f",detalleCarro.getSubtotal())%></td>
        </tr>
        <tr class="table-light">
            <td colspan="4" class="text-end fw-semibold">IVA (15%):</td>
            <td class="fw-semibold">$<%=String.format("%.2f", detalleCarro.getTotalIva())%></td>
        </tr>
        <tr class="table-success">
            <td colspan="4" class="text-end fw-bold">Total a Pagar:</td>
            <td class="fw-bold">$<%=String.format("%.2f", detalleCarro.getTotal())%></td>
        </tr>
        </tfoot>
    </table>
    <%}%>

    <div class="mt-4 d-flex gap-3">
        <a href="<%=request.getContextPath()%>/carro/pdf" class="btn btn-success">
            <i class="bi bi-file-pdf"></i> Descargar Factura PDF
        </a>
        <a href="<%=request.getContextPath()%>/productos" class="btn btn-primary">
            <i class="bi bi-cart-plus"></i> Seguir Comprando
        </a>
        <a href="<%=request.getContextPath()%>/index.html" class="btn btn-secondary">
            <i class="bi bi-house"></i> Volver
        </a>
    </div>
</div>
</body>
</html>

