package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión con la base de datos MariaDB ({@code empleados}) que
 * utiliza la aplicación. Carga el driver JDBC de MariaDB y abre las conexiones
 * que solicitan los DAO.
 *
 * @author Juan Miguel Silva Martín
 * @version 1.0
 */
public class Conexion {

    /**
     * Establece una conexión con la base de datos de empleados.
     *
     * @return conexión activa con la base de datos
     * @throws SQLException si no es posible establecer la conexión
     */
    public static Connection conectar() throws SQLException {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MariaDB no encontrado", e);
        }

        String url = "jdbc:mariadb://localhost:3307/empleados";
        String usuario = "root";
        String password = "0607";

        return DriverManager.getConnection(
                url,
                usuario,
                password
        );
    }
}
