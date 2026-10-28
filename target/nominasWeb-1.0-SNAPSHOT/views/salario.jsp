<%-- 
    Document   : salario
    Created on : 26 oct 2026, 10:08:29
    Author     : juanm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Salario</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <h1>Introduce el dni del empleado</h1>
        <form action="${pageContext.request.contextPath}/empleados" method="get">
            <input type="hidden" name="opcion" value="mostrarSalario">
            <input type="text" name="dni" placeholder="DNI" required>
            <button type="submit">Ver salario</button>
        </form>
        <a href="${pageContext.request.contextPath}/index.html">
            <button type="button">Volver al menú</button>
        </a>
    </body>
</html>
