<%-- 
    Document   : mostrarSalario
    Created on : 26 oct 2026, 12:28:07
    Author     : juanm
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mostrar Salario</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <c:choose>
            <c:when test = "${sueldo != 0}">
                <h1>El salario de "${dni}" es "${sueldo}"</h1>
            </c:when>
            <c:otherwise>
                <h1>No se ha encontrado salario para el dni: "${dni}"</h1>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/index.html">
            <button type="button">Volver al menú</button>
        </a>
    </body>
</html>
