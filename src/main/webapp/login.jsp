<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>


    <style>
        body {
            background: linear-gradient(135deg, #b5eaea, #f7d1e3, #c7ffd8);
            background-size: 300% 300%;
            animation: gradientMove 10s ease infinite;
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #ffffff;
            font-family: "Segoe UI", sans-serif;
        }

        @keyframes gradientMove {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .login-container {
            background: rgba(255, 255, 255, 0.25);
            padding: 40px;
            border-radius: 20px;
            width: 350px;
            backdrop-filter: blur(12px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
            text-align: center;
        }

        h1 {
            color: #ff4fa3;
            margin-bottom: 25px;
            font-weight: bold;
            text-shadow: 0 0 8px rgba(255, 79, 163, 0.4);
        }

        label {
            font-weight: 600;
            color: #000;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            margin-bottom: 15px;
            border-radius: 10px;
            border: 1px solid #ccc;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #ffd3e2;
            color: #000;
            border: none;
            padding: 12px;
            font-weight: 600;
            border-radius: 12px;
            cursor: pointer;
            transition: .2s;
        }

        input[type="submit"]:hover {
            transform: scale(1.05);
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>

<div class="login-container">
    <h1>Iniciar sesión</h1>

    <form action="<%= request.getContextPath() %>/login" method="post">
        <div>
            <label for="user">Usuario</label><br>
            <input type="text" id="username" name="username" placeholder="Ingrese su usuario" required>
        </div>

        <div>
            <label for="password">Contraseña</label><br>
            <input type="password" id="password" name="password" placeholder="Ingrese su contraseña" required>
        </div>

        <div>
            <input type="submit" value="Entrar">
        </div>
    </form>
</div>

</body>
</html>

