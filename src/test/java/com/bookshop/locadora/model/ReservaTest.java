package com.bookshop.locadora.model;

import com.bookshop.locadora.model.exception.ReservaInvalida;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    Cliente cliente;
    Carro carro;
    int dias;

    @BeforeEach
    void setUp(){
        cliente = Cliente.builder().nome("Pedro").build();
        carro = Carro.builder().modelo("nissan").diaria(12).build();
    }

    @Test
    @DisplayName("Deve criar uma reserva com sucesso")
    void deveCriarUmaReservaComSucesso(){
        dias = 3;

        Reserva reserva = Reserva.builder().cliente(cliente).carro(carro).dias(dias).build();
        assertNotNull(reserva);
        assertNotNull(reserva.getCliente());
        assertNotNull(reserva.getCarro());
        assertThat(reserva.getDias()).isGreaterThan(0);
        assertThat(reserva.totalReserva()).isEqualTo(36);

    }

    @Test
    @DisplayName("Deve falhar em criar uma reserva com dia negativo")
    void deveFalharEmCriarReserva(){
        dias = -3;

        //JUnit //Uso de expressão lambda
        ReservaInvalida reserva = assertThrows(ReservaInvalida.class, () -> {
            Reserva.builder().cliente(cliente).carro(carro).dias(dias).build();
        });
        //assertDoesNotThrow(() -> new Reserva(cliente,carro,1));

        //AssertJ
        var erro = Assertions.catchThrowable(() -> new Reserva(cliente,carro,1));

        Assertions.assertThat(erro).isInstanceOf(ReservaInvalida.class)
                        .hasMessage("numero de dias deve ser positivo");

        assertThat(dias).isLessThan(0);
        assertEquals("numero de dias deve ser positivo",reserva.getMessage());
    }

    @Test
    @DisplayName("Deve criar uma reserva com dia desconto")
    void deveCriarReservaComDesconto(){
        dias = 10;

        Reserva reserva = Reserva.builder().cliente(cliente).carro(carro).dias(dias).build();
        assertNotNull(reserva);
        assertNotNull(reserva.getCliente());
        assertNotNull(reserva.getCarro());
        assertThat(reserva.getDias()).isGreaterThan(5);
        assertThat(reserva.totalReserva()).isBetween(60.0,80.0);
    }



}
