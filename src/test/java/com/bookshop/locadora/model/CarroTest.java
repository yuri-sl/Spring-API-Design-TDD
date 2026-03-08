package com.bookshop.locadora.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarroTest {
    @Test
    @DisplayName("Deve calcular o valor de 3 dias de diária")
    public void deveCalcularValorDiaria(){
        //1. Cenário
        Carro carro = Carro.builder().modelo("Sedan").diaria(100).build();

        //2. Execução
        double total = carro.calcularValorDiaria(3);

        //3. Validação
        Assertions.assertEquals(300,total);
    }


    @Test
    @DisplayName("Deve calcular o valor de 5 dias de diária")
    public void deveCalcularValorDiariaComDesconto(){
        Carro carro = Carro.builder().modelo("Sedan").diaria(100).build();
        double total = carro.calcularValorDiaria(5);

        Assertions.assertEquals(450,total);
    }

}
