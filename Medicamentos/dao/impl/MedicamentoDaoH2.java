package BaseDeDatos.PatronDAO.Medicamentos.dao.impl;

import BaseDeDatos.PatronDAO.Medicamentos.dao.IDao;
import BaseDeDatos.PatronDAO.Medicamentos.model.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDaoH2 implements IDao<Medicamento> {
    public static Logger logger = Logger.getLogger(MedicamentoDaoH2.class);

    private String url;
    private String username;
    private String password;

    private final String TABLA;
    private final String SQL_INSERT;
    public MedicamentoDaoH2(String url, String username, String password, String nombreDeLaTabla) {
        this.TABLA = nombreDeLaTabla;
        this.SQL_INSERT = "INSERT INTO " + TABLA + "(id, codigo, precio, nombre, laboratorio, cantidad) values(?, ?, ?, ?, ?, ?)";
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Boolean crearLaTabla() {
        Boolean resultado = false;
        //[1] Obtener y levantar el controlador
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }

        //[2] Abrir la conexión a la base de datos, y usar esa conexión para crear un objeto tipo PreparedStatement
        try(Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
            Statement stmt = conn.createStatement()){

            resultado = stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLA + "(" +
                                    "id INT PRIMARY KEY," +
                                    "codigo VARCHAR," +
                                    "precio INT," +
                                    "nombre VARCHAR," +
                                    "laboratorio VARCHAR," +
                                    "cantidad INT);");

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        return resultado;
    }

    @Override
    public Integer crear(Medicamento entidad) {
        Integer resultado = -1;

        //[1] Obtener y levantar el controlador
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }

        //[2] Abrir la conexión a la base de datos, y usar esa conexión para crear un objeto tipo PreparedStatement
        try(Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)){

            stmt.setInt(1, entidad.getId());
            stmt.setString(2, entidad.getCodigo());
            stmt.setInt(3, entidad.getPrecio());
            stmt.setString(4, entidad.getNombre());
            stmt.setString(5, entidad.getLaboratorio());
            stmt.setInt(6, entidad.getCantidad());

            resultado = stmt.executeUpdate();

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        return resultado;
    }

    @Override
    public List<Medicamento> consultarTodos() {

        List<Medicamento> resultado = new ArrayList<>();

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
                Medicamento m = new Medicamento();

                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setCodigo(rs.getString("codigo"));
                m.setLaboratorio(rs.getString("laboratorio"));
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
    public Medicamento consultarPorId(Integer id) {
        Medicamento resultado = null;

        //[1] Obtener y levantar el controlador
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }

        //[2] Abrir la conexión a la base de datos, y usar esa conexión para crear un objeto tipo PreparedStatement
        try(Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TABLA + " WHERE id = ?")){

            stmt.setInt(1, id);

            //[2a] Ejecutar la sentencia SQL, procesar su respuesta
//            ResultSet rs = stmt.executeQuery("SELECT * FROM Medicamento WHERE id = " + id +";");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                resultado = new Medicamento();

                resultado.setId(rs.getInt("id"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setCantidad(rs.getInt("cantidad"));
                resultado.setCodigo(rs.getString("codigo"));
                resultado.setLaboratorio(rs.getString("laboratorio"));
                resultado.setPrecio(rs.getInt("precio"));

            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        //[3] Retornar la lista de registros encontrados.
        return resultado;
    }

    @Override
    public Medicamento consultarPorNombre(String nombre) {
        Medicamento resultado = null;

        //[1] Obtener y levantar el controlador
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }

        //[2] Abrir la conexión a la base de datos, y usar esa conexión para crear un objeto tipo PreparedStatement
        try(Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TABLA + " WHERE nombre = ?")){

            stmt.setString(1, nombre);

            //[2a] Ejecutar la sentencia SQL, procesar su respuesta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                resultado = new Medicamento();

                resultado.setId(rs.getInt("id"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setCantidad(rs.getInt("cantidad"));
                resultado.setCodigo(rs.getString("codigo"));
                resultado.setLaboratorio(rs.getString("laboratorio"));
                resultado.setPrecio(rs.getInt("precio"));

            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        //[3] Retornar la lista de registros encontrados.
        return resultado;

    }
}
