package com.bookshop.locadora.repository;

import com.bookshop.locadora.entity.CarroEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class CarroRepositorySQLTest {

    @Autowired
    CarroRepository repository;

    @Sql("/sql/popularCarros.sql")
    @Test
    void deveBuscarCarroModelo(){
        List<CarroEntity> lista = repository.findByModelo("SUV");

        Assertions.assertEquals(1,lista.size());


        Assertions.assertEquals("SUV",lista.getFirst().getModelo());
        Assertions.assertEquals(150.0,lista.getFirst().getValorDiaria());
    }
}
