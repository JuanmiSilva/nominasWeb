/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.EmpleadoDAO;
import dao.NominasDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import modelo.Empleado;

/**
 * Servlet controlador de la aplicación de nóminas. Atiende las peticiones GET
 * dirigidas a {@code /empleados} y, según el parámetro {@code opcion}, carga
 * los datos necesarios y reenvía la petición a la vista JSP correspondiente.
 *
 * <p>
 * Opciones admitidas:</p>
 * <ul>
 * <li>{@code listar}: muestra todos los empleados ({@code listar.jsp}).</li>
 * <li>{@code salario}: muestra el formulario para pedir un DNI
 * ({@code salario.jsp}).</li>
 * <li>{@code mostrarSalario}: muestra el salario del empleado indicado
 * ({@code mostrarSalario.jsp}).</li>
 * <li>{@code modificar}: muestra el formulario de filtrado
 * ({@code modificar.jsp}).</li>
 * <li>{@code filtrado}: muestra los empleados que cumplen los criterios
 * indicados ({@code listar.jsp}).</li>
 * </ul>
 *
 * @author Juan Miguel Silva Martín
 * @version 1.0
 */
@WebServlet("/empleados")
public class Controller extends HttpServlet {

    /**
     * Procesa las peticiones GET según el valor del parámetro {@code opcion}.
     * Para {@code listar}, {@code filtrado} y {@code mostrarSalario} deja los
     * resultados en atributos de la petición ({@code lista}, {@code sueldo} y
     * {@code dni}) antes de reenviarla a la vista.
     *
     * @param request petición HTTP recibida; contiene el parámetro
     * {@code opcion} y, según el caso, {@code dni}, {@code nombre},
     * {@code sexo}, {@code categoria} y {@code anyos}
     * @param response respuesta HTTP; se usa para enviar errores 400 (datos
     * numéricos no válidos) o 500 (fallo al filtrar)
     * @throws ServletException si ocurre un error al reenviar la petición a la
     * vista
     * @throws IOException si ocurre un error de entrada/salida al gestionar la
     * respuesta
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(">>> HE ENTRADO EN EL CONTROLLER <<<");

        String opcion = request.getParameter("opcion");
        System.out.println("Opción recibida: " + opcion);

        switch (opcion) {
            case "salario":
                request.getRequestDispatcher("/views/salario.jsp").forward(request, response);
                break;
            case "modificar":
                request.getRequestDispatcher("/views/modificar.jsp").forward(request, response);
                break;
            case "listar":
                try {
                    List<Empleado> lista = EmpleadoDAO.devolverEmpleados();

                    System.out.println("Empleados encontrados: " + lista.size());

                    request.setAttribute("lista", lista);

                    RequestDispatcher reqDis
                            = request.getRequestDispatcher("/views/listar.jsp");

                    reqDis.forward(request, response);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "mostrarSalario":
                String dni = request.getParameter("dni");
                System.out.println("DNI recibido: " + dni);
                int nomina = NominasDAO.devolverNomina(dni);

                System.out.println("Nomina recibida: " + nomina);
                request.setAttribute("sueldo", nomina);
                request.setAttribute("dni", dni);

                RequestDispatcher reqDis = request.getRequestDispatcher("/views/mostrarSalario.jsp");
                reqDis.forward(request, response);
                break;

            case "filtrado": {
                try {
                    String dniFiltrado = request.getParameter("dni");
                    String nombre = request.getParameter("nombre");
                    String sexoStr = request.getParameter("sexo");
                    String catStr = request.getParameter("categoria");
                    String anyStr = request.getParameter("anyos");

                    Character sexo = (sexoStr != null && !sexoStr.isBlank())
                            ? Character.toUpperCase(sexoStr.trim().charAt(0)) : null;
                    Integer categoria = (catStr != null && !catStr.isBlank())
                            ? Integer.valueOf(catStr.trim()) : null;
                    Integer anyos = (anyStr != null && !anyStr.isBlank())
                            ? Integer.valueOf(anyStr.trim()) : null;

                    ArrayList<Empleado> listaFiltrada = EmpleadoDAO.filtrarEmpleados(
                            dniFiltrado, nombre, sexo, categoria, anyos);
                    request.setAttribute("lista", listaFiltrada);
                    request.getRequestDispatcher("/views/listar.jsp").forward(request, response);

                } catch (NumberFormatException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Categoría y años deben ser numéricos");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            "Error al filtrar");
                }
                break;
            }
        }
    }
}
