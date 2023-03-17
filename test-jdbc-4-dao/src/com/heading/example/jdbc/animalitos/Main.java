package com.heading.example.jdbc.animalitos;

import com.heading.example.jdbc.animalitos.model.Animalito;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args){
        // [1] Registrar el Controlador
        try {
            Class.forName("org.h2.Driver");
        }
        catch(ClassNotFoundException ex) {
            logger.error("Error: unable to load driver class! " + ex.getMessage());
            System.exit(1);
        }

        String URL = "jdbc:h2:~/test";
        String USER = "sa";
        String PASS = "";

        //Crear Sentencias SQL
        String SQL_CREAR_TABLA = "CREATE TABLE animales (" +
                "id INT," +
                "nombre VARCHAR(255)," +
                "especie VARCHAR(255)," +
                "raza VARCHAR(255));";

        String SQL_INSERT = "INSERT INTO animales(id, nombre, especie, raza) VALUES(1, 'Pompilia', 'perro', 'bulldog inglés');";
        String SQL_INSERT2 = "INSERT INTO animales(id, nombre, especie, raza) VALUES(1, 'Yuma', 'perro', 'Chandoso');";
        String SQL_INSERT3 = "INSERT INTO animales(id, nombre, especie, raza) VALUES(3, 'Tolo', 'loro', 'cabeza amarilla');";

        // [2] - Establecer la conexión utilizando la URL y los datos de autenticación (opciones)
        // [3] - Obtener una instancia de Statement para poder ejecutar sentencias nativas de la base de datos

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){

            logger.info("Creating table in given database...");

            //[4a] - Utilizar el statement creado para insertar datos en la base de datos
//            stmt.execute(SQL_CREAR_TABLA);
//            int registros = stmt.executeUpdate(SQL_INSERT3); //Se recomienda para INSERT, UPDATE y DELETE
//            logger.info("Se insertaron " + registros + " registros");

            //[4b] - Utilizar ResultSet para obtener los datos provenientes de una consulta SQL
            ResultSet rs = stmt.executeQuery("SELECT * FROM animalesasdas");
            System.out.println(rs);

            List<Animalito> animales = new ArrayList<>();

            while (rs.next()){
                Animalito a = new Animalito();
                a.setId(rs.getInt("id"));
                a.setEspecie(rs.getString(3));
                a.setNombre(rs.getString("nombre"));
                a.setRaza(rs.getString("raza"));

                animales.add(a);
            }

            animales.forEach(a -> System.out.println(a));

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

    }
}
