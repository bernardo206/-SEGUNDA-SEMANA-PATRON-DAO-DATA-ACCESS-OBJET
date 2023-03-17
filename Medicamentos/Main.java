package BaseDeDatos.PatronDAO.Medicamentos;

import BaseDeDatos.PatronDAO.Medicamentos.dao.IDao;
import BaseDeDatos.PatronDAO.Medicamentos.dao.impl.MedicamentoDaoH2;
import BaseDeDatos.PatronDAO.Medicamentos.model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        IDao<Medicamento> daoMedicamento = new MedicamentoDaoH2("jdbc:h2:~/test", "sa", "", "medicinas");

        daoMedicamento.crearLaTabla();
        System.out.println("- La tabla se generó con éxito");

       Medicamento medicamentoACrear = new Medicamento();
        medicamentoACrear.setId(1);
        medicamentoACrear.setPrecio(20);
        medicamentoACrear.setCodigo("234abc");
        medicamentoACrear.setLaboratorio("Mr. Burns & Co.");
       medicamentoACrear.setCantidad(10);
        medicamentoACrear.setNombre("ibuprofeno");

        if (daoMedicamento.crear(medicamentoACrear) == 1){
            System.out.println("El medicamento fue creado con éxito");
        }else {
            System.out.println("Ups!!!");
       }

        //List<Medicamento> medicamentos = daoMedicamento.consultarTodos();
        //        medicamentos.forEach(m -> System.out.println(m));

        Medicamento resultadoDeLaConsultaPorNombre = daoMedicamento.consultarPorNombre("omeprazol");

        if(resultadoDeLaConsultaPorNombre != null){
            System.out.println(resultadoDeLaConsultaPorNombre);
        }else {
            System.err.println("Oops! I did it again.");
        }

    }
}
