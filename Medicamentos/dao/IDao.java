package BaseDeDatos.PatronDAO.Medicamentos.dao;

import java.util.List;

public interface IDao<E> {

    Boolean crearLaTabla();
    Integer crear(E entidad);

    List<E> consultarTodos();

    E consultarPorId(Integer id);
    E consultarPorNombre(String nombre);
}
