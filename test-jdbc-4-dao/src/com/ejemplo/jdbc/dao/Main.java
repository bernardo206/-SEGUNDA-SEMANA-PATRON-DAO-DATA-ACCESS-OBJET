package com.ejemplo.jdbc.dao;

import com.ejemplo.jdbc.dao.dao.IDao;
import com.ejemplo.jdbc.dao.dao.impl.MedicamentoDaoH2;
import com.ejemplo.jdbc.dao.model.Medicamento;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        IDao<Medicamento> daoMedicamento = new MedicamentoDaoH2("jdbc:h2:~/test", "sa", "", "medicinas");

        daoMedicamento.crearLaTabla();
        System.out.println("- La tabla se generó con éxito");

//        Medicamento medicamentoACrear = new Medicamento();
//        medicamentoACrear.setId(2);
//        medicamentoACrear.setPrecio(20);
//        medicamentoACrear.setCodigo("234abc");
//        medicamentoACrear.setLaboratorio("Mr. Burns & Co.");
//        medicamentoACrear.setCantidad(10);
//        medicamentoACrear.setNombre("omeprazol");
//
//        if (daoMedicamento.crear(medicamentoACrear) == 1){
//            System.out.println("El medicamento fue creado con éxito");
//        }else {
//            System.out.println("Ups!!!");
//        }

//        List<Medicamento> medicamentos = daoMedicamento.consultarTodos();
//        medicamentos.forEach(m -> System.out.println(m));

        Medicamento resultadoDeLaConsultaPorId = daoMedicamento.consultarPorId(2);

        if(resultadoDeLaConsultaPorId != null){
            System.out.println(resultadoDeLaConsultaPorId);
        }else {
            System.err.println("Oops! I did it again.");
        }

    }
}
