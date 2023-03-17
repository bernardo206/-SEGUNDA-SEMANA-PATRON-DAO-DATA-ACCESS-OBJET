package com.ejemplo.jdbc.usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

    //TODO: Agregar el logger como atributo de la clase

    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); //TODO: Hacer un log para inidicar el error ocurrido
        }

        try (Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
             Statement stmt = conn.createStatement()){

            String SQL_CREATE = "";
            stmt.execute(SQL_CREATE);

            String SQL_INSERT = "";
            String SQL_INSERT2 = "";
            String SQL_INSERT3 = "";
            stmt.executeUpdate(SQL_INSERT);
            stmt.executeUpdate(SQL_INSERT2);
            stmt.executeUpdate(SQL_INSERT3);

            String SQL_DELETE_BY_ID = "DELETE FROM usuarios WHERE id = 1";
            stmt.executeUpdate(SQL_DELETE_BY_ID);

            String SQL_QUERY_ALL = "SELECT * FROM usuarios;";
            ResultSet rs = stmt.executeQuery(SQL_QUERY_ALL);

            int i = 0;

            while (rs.next()){
                System.out.println(rs.getString("primer_nombre")); //TODO: Hacer un log para inidicar lo que ha ocurrido
                i++;
            }

            System.out.println(i == 2);

            List<String> l = new ArrayList<>();

        } catch (SQLException e) {
            //TODO: Hacer un log para inidicar el error ocurrido
        }
    }
}
