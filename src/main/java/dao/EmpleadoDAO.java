package dao;

import controller.DatosNoCorrectosException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Empleado;

/**
 * Objeto de acceso a datos (DAO) encargado de realizar las operaciones
 * relacionadas con los empleados en la base de datos.
 *
 * @author Juan Miguel Silva Martín
 * @version 1.0
 */
public class EmpleadoDAO {

    /**
     * Inserta un empleado en la tabla empleados.
     *
     * @param emple empleado que se desea insertar
     */
    public static void insertarEmpleado(Empleado emple) {

        String sql = "INSERT INTO empleados "
                + "(dni, nombre, sexo, categoria, anyos) "
                + "VALUES (?,?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar(); PreparedStatement sentencia
                = conexion.prepareStatement(sql)) {

            sentencia.setString(1, emple.dni);
            sentencia.setString(2, emple.nombre);
            sentencia.setString(3, String.valueOf(emple.sexo));
            sentencia.setInt(4, emple.getCategoria());
            sentencia.setInt(5, emple.anyos);

            sentencia.executeUpdate();

            System.out.println(
                    "Empleado insertado correctamente"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filtra los empleados según los criterios indicados. Los criterios que
     * sean {@code null} (o cadenas vacías) no se aplican.
     *
     * @param dni DNI (o parte) del empleado, o null
     * @param nombre nombre (o parte) del empleado, o null
     * @param sexo sexo del empleado, o null
     * @param categoria categoría del empleado, o null
     * @param anyos años trabajados, o null
     * @return lista de empleados que cumplen todos los criterios
     * @throws DatosNoCorrectosException si los datos recuperados no son válidos
     */
    public static ArrayList<Empleado> filtrarEmpleados(String dni, String nombre,
            Character sexo, Integer categoria, Integer anyos)
            throws DatosNoCorrectosException {

        StringBuilder sql = new StringBuilder("SELECT * FROM empleados WHERE 1=1");
        ArrayList<Object> parametros = new ArrayList<>();

        if (dni != null && !dni.isBlank()) {
            sql.append(" AND dni LIKE ?");
            parametros.add("%" + dni.trim() + "%");
        }
        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND nombre LIKE ?");
            parametros.add("%" + nombre.trim() + "%");
        }
        if (sexo != null) {
            sql.append(" AND sexo = ?");
            parametros.add(String.valueOf(sexo));
        }
        if (categoria != null) {
            sql.append(" AND categoria = ?");
            parametros.add(categoria);
        }
        if (anyos != null) {
            sql.append(" AND anyos = ?");
            parametros.add(anyos);
        }

        ArrayList<Empleado> emples = new ArrayList<>();

        try (Connection conexion = Conexion.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                sentencia.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = sentencia.executeQuery()) {
                while (rs.next()) {
                    emples.add(new Empleado(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("sexo").charAt(0),
                            rs.getInt("categoria"),
                            rs.getInt("anyos")
                    ));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return emples;
    }

    /**
     * Recupera todos los empleados almacenados en la base de datos.
     *
     * @return lista con todos los empleados encontrados
     * @throws DatosNoCorrectosException si los datos recuperados de la base de
     * datos no son válidos
     */
    public static ArrayList<Empleado> devolverEmpleados()
            throws DatosNoCorrectosException {

        String sql = "SELECT * FROM empleados";
        ArrayList<Empleado> emples = new ArrayList<>();

        try (Connection conexion = Conexion.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet rs = sentencia.executeQuery()) {

            System.out.println("Base de datos: " + conexion.getCatalog());
            System.out.println("Conexión: " + conexion.getMetaData().getURL());
            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                char sexo = rs.getString("sexo").charAt(0);
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");

                emples.add(new Empleado(
                        dni, nombre, sexo, categoria, anyos
                ));
            }

            System.out.println("Empleados recuperados: " + emples.size());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return emples;
    }

    /**
     * Ejecuta una sentencia SQL de actualización sobre la tabla de empleados.
     *
     * @param sql sentencia SQL que se desea ejecutar
     */
    public static void actualizarEmpleado(String sql) {

        try (Connection conexion = Conexion.conectar(); PreparedStatement sentencia
                = conexion.prepareStatement(sql)) {

            sentencia.executeUpdate();

            System.out.println(
                    "Empleado actualizado correctamente"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un empleado en la base de datos utilizando su DNI.
     *
     * @param dni2 DNI del empleado que se desea buscar
     * @return empleado encontrado o {@code null} si no existe
     * @throws DatosNoCorrectosException si los datos del empleado recuperado no
     * son válidos
     */
    public static Empleado devolverEmpleado(String dni2)
            throws DatosNoCorrectosException {

        String sql
                = "SELECT * FROM empleados WHERE dni = ?";

        try (Connection conexion = Conexion.conectar(); PreparedStatement sentencia
                = conexion.prepareStatement(sql)) {

            sentencia.setString(1, dni2);

            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {

                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                char sexo = rs.getString("sexo").charAt(0);
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");

                return new Empleado(
                        dni,
                        nombre,
                        sexo,
                        categoria,
                        anyos
                );
            }

        } catch (SQLException ex) {

            System.getLogger(EmpleadoDAO.class.getName())
                    .log(
                            System.Logger.Level.ERROR,
                            (String) null,
                            ex
                    );
        }

        return null;
    }
}
