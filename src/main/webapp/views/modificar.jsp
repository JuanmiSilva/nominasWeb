<%-- 
    Document   : modificar
    Created on : 26 oct 2026, 13:24:50
    Author     : juanm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Empleado</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <h1>Introduce los datos del empleado a buscar</h1>
        <form action="${pageContext.request.contextPath}/empleados" method="get">
            <input type="hidden" name="opcion" value="filtrado">
            <br>
            <input type="text" name="dni" placeholder="DNI">
            <br>
            <input type="text" name="nombre" placeholder="Nombre">
            <br>
            <input type="text" name="sexo" placeholder="M o F">
            <br>
            <input type="number" name="categoria" placeholder="categoria">
            <br>
            <input type="number" name="anyos" placeholder="Anyo">
            <br>

            <button type="submit">Filtrar Empleados</button>
        </form>
        <a href="${pageContext.request.contextPath}/index.html">
            <button type="button">Volver al menú</button>
        </a>
    </body>
</html>
