package com.ejemplo.jdbc.dao.dao.impl;

import com.ejemplo.jdbc.dao.dao.IDao;
import com.ejemplo.jdbc.dao.model.Maquillaje;
import com.ejemplo.jdbc.dao.model.Medicamento;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaquillajeDaoH2 implements IDao<Maquillaje> {

    public static Logger logger = Logger.getLogger(MaquillajeDaoH2.class);

    private String url;
    private String username;
    private String password;

    private final String TABLA = "maquillaje";

    @Override
    public Boolean crearLaTabla() {
        return null;
    }

    @Override
    public Integer crear(Maquillaje entidad) {
        return null;
    }

    @Override
    public List<Maquillaje> consultarTodos() {
        List<Maquillaje> resultado = new ArrayList<>();

        //[1] Obtener y levantar el controlador
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }

        //[2] Abrir la conexión a la base de datos, y usar esa conexión para crear un objeto tipo PreparedStatement
        try(Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
            Statement stmt = conn.createStatement()){

            //[2a] Ejecutar la sentencia SQL, procesar su respuesta
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLA + ";");

            while (rs.next()){
                Maquillaje m = new Maquillaje();

                m.setId(rs.getInt("id"));
                m.setMarca(rs.getString("marca"));
                m.setTipo(rs.getString("tipo"));
                m.setPrecio(rs.getInt("precio"));

                resultado.add(m);
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        //[3] Retornar la lista de registros encontrados.
        return resultado;
    }

    @Override
    public Maquillaje consultarPorId(Integer id) {
        return null;
    }
}
