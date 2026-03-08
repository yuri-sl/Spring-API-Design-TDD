package com.bookshop.locadora.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ClienteTest {

    @Test
    void deveCriarClienteComNome(){
        Cliente cliente = Cliente.builder().nome("Maria").build();

        String nome = cliente.getNome();

        //JUnit
        assertNotNull(nome);
        assertEquals("Maria",nome);
        assertTrue(nome.startsWith("M"));
        assertFalse(nome.length()== 100);


        //AssertJ => úti para testes mais completos
        assertThat(nome.startsWith("M"));
        assertThat(nome.length()).isLessThan(100);

        assertThat(nome).contains("ia");




    }
}
