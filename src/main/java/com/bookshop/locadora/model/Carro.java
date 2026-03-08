package com.bookshop.locadora.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Carro {
    private String modelo;
    private double diaria;

    public double calcularValorDiaria(int dias){
        double desconto = 0;
        if(dias>=5){
            desconto = 50;
        }
        return (diaria*dias)-desconto;
    }
}
