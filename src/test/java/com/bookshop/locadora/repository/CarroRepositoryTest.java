package com.bookshop.locadora.repository;


import com.bookshop.locadora.entity.CarroEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CarroRepositoryTest {

    @Autowired
    CarroRepository repository;

    CarroEntity carroSalvo = CarroEntity.builder().modelo("Nissan").valorDiaria(150.0).ano(2025).build();

    @Test
    void deveSalvarCarro(){
        repository.save(carroSalvo);

        assertNotNull(carroSalvo.getId());
    }

    @Test
    void deveBuscarCarroPorId(){
        repository.save(carroSalvo);

        Optional<CarroEntity> carro = repository.findById(carroSalvo.getId());

        assertNotNull(repository.findById(carroSalvo.getId()));
        assertThat(carro).isPresent();
        assertThat(carro.get().getModelo()).isEqualTo("Nissan");
    }

    @Test
    public void deveAtualizarCarro(){
        repository.save(carroSalvo);

        carroSalvo.setAno(2028);
        var carroAtualizado = repository.save(carroSalvo);

        assertThat(carroAtualizado.getAno()).isEqualTo(2028);
    }

    @Test
    public void deveDeletarCarro(){
        var carroSalvoDeletar = repository.save(carroSalvo);
        repository.deleteById(carroSalvoDeletar.getId());

        Optional<CarroEntity> carroEncontrado = repository.findById(carroSalvo.getId());
        assertThat(carroEncontrado).isEmpty();
    }


}