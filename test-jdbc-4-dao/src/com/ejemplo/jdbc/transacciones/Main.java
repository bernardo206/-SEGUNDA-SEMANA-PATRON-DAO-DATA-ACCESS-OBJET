package com.ejemplo.jdbc.transacciones;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static Logger logger = Logger.getLogger(com.heading.example.jdbc.animalitos.Main.class);

    public static void main(String[] args) {

        Cuenta cuenta = null;

        String SQL_CREATE = "CREATE TABLE IF NOT EXISTS cuenta(" +
                "nro_de_cuenta INT," +
                "nombre VARCHAR(255)," +
                "saldo INT);";

        String SQL_INSERT = "INSERT INTO cuenta(nro_de_cuenta, nombre, saldo) values(123, 'Nero', 10)";
        String SQL_QUERY_ALL = "SELECT * FROM cuenta;";
        String SQL_UPDATE_SALDO = "UPDATE cuenta SET saldo = 100 WHERE nro_de_cuenta = 123;";
        String SQL_UPDATE_SALDO2 = "UPDATEEEE cuenta SET saldo = 140 WHERE nro_de_cuenta = 123;";
        String SQL_DELETE_BY_ID = "DELETE FROM usuarios WHERE id = 1";

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
            stmt = conn.createStatement();

            stmt.execute(SQL_CREATE);

//            stmt.executeUpdate(SQL_INSERT);

            ResultSet rs = stmt.executeQuery(SQL_QUERY_ALL);
            cuenta = leerCuentaDelResultSet(rs);

            logger.info("Antes del update => " + cuenta.toString());

            conn.setAutoCommit(false); //Empiezo la transacción

            stmt.executeUpdate(SQL_UPDATE_SALDO);
            stmt.executeUpdate(SQL_UPDATE_SALDO2); //Este va a fallar

            conn.commit(); //Guardo los cambios realizados durante la transacción

            conn.setAutoCommit(true); //Vuelvo a cambiar la configuración de la conexión

            rs = stmt.executeQuery(SQL_QUERY_ALL);
            cuenta = leerCuentaDelResultSet(rs);

            logger.info("Después del update => " + cuenta.toString());


        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
            logger.error(e.getMessage());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Cuenta leerCuentaDelResultSet(ResultSet rs) throws SQLException {
        Cuenta cuenta = null;

        while (rs.next()){
            cuenta = new Cuenta();
            cuenta.setNumeroDeCuenta(rs.getInt("nro_de_cuenta"));
            cuenta.setNombre(rs.getString("nombre"));
            cuenta.setSaldo(rs.getInt("saldo"));
        }

        return cuenta;
    }
}
