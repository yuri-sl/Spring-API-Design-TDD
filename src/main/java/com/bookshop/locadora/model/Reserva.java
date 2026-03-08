package com.bookshop.locadora.model;


import com.bookshop.locadora.model.exception.ReservaInvalida;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class Reserva {
    private Cliente cliente;
    private Carro carro;
    private int dias;

    public Reserva(Cliente cliente,Carro carro, int dias){
        if (dias <= 0){
            throw new ReservaInvalida("numero de dias deve ser positivo");
        }
        this.cliente = cliente;
        this.carro = carro;
        this.dias = dias;
    }

    public double totalReserva(){
        return carro.calcularValorDiaria(this.dias);
    }



}
