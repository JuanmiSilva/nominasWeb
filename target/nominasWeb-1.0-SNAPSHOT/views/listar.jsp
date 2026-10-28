<%-- 
    Document   : listar.jsp
    Created on : 25 oct 2026, 17:08:56
    Author     : juanm
--%>

<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listar Empleados</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <h1>Listar Empleados</h1>
        <table>
            <tr>
                <td>Dni</td>
                <td>Nombre</td>
                <td>Sexo</td>
                <td>Categoria</td>
                <td>Anyos</td>
            </tr>
            <c:forEach var="empleado" items="${lista}">
                <tr>
                    <td><c:out value="${empleado.dni}"></c:out></td>
                    <td><c:out value="${empleado.nombre}"></c:out></td>
                    <td><c:out value="${empleado.sexo}"></c:out></td>
                    <td><c:out value="${empleado.categoria}"></c:out></td>
                    <td><c:out value="${empleado.anyos}"></c:out></td>

                    </tr>
            </c:forEach>
        </table>
        <br>
        <a href="${pageContext.request.contextPath}/index.html">
            <button type="button">Volver al menú</button>
        </a>
    </body>
</html>
