package com.bookshop.locadora.service;

import com.bookshop.locadora.entity.CarroEntity;
import com.bookshop.locadora.model.exception.EntityNotFoundException;
import com.bookshop.locadora.repository.CarroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    //Service que vai ser testado e receber os Mocks
    @InjectMocks
    private CarroService carroService;

    //Instancia "Falsa" do repository
    @Mock
    private CarroRepository carroRepository;

    //Teste unitário
    @Test
    @DisplayName("Deve salvar um carro criado")
    public void createNewCarTest(){
        CarroEntity carroSalvar = CarroEntity.builder()
                .modelo("Nissan").valorDiaria(60).ano(2026).build();
        CarroEntity carroRetorno = CarroEntity.builder()
                .modelo("Nissan").valorDiaria(60).ano(2026).build();
        carroRetorno.setId(1L);

        Mockito.when(carroRepository.save(Mockito.any())).thenReturn(carroRetorno);

        var carroSalvo =carroService.salvar(carroSalvar);
        assertNotNull(carroSalvo);
        assertEquals(carroSalvo.getModelo(),carroRetorno.getModelo());

        //Verifica se o service chegou a passar pela linha de repository save
        Mockito.verify(carroRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("Deve dar erro ao tentar criar um carro com reserva zero de dias")
    public void deveDarErroAoTentarSalvarCarroComDiariaNegativa(){
        CarroEntity carro = new CarroEntity(1L,"nissan",0,2026);

        var erro = catchThrowable(() -> carroService.salvar(carro));

        assertThat(erro).isInstanceOf(IllegalArgumentException.class);

        Mockito.verifyNoInteractions(carroRepository);
    }

    @Test
    @DisplayName("Deve atualizar um carro")
    public void deveAtualizarUmCarro(){
        CarroEntity carroSalvo = new CarroEntity(1L,"nissan",10,2026);

        //CADA CHAMADA DA FUNÇÃO DE CARRO REPOSITORY DEVE SER MOCKADA E TESTADA
        Mockito.when(carroRepository.findById(1L)).thenReturn(Optional.of(carroSalvo));

        CarroEntity carroAtualizarDados = new CarroEntity(1L,"onix",10,2026);

        Mockito.when(carroRepository.save(Mockito.any())).thenReturn(carroAtualizarDados);

        var carroAtualizadoSalvo = carroService.atualizar(1L,carroAtualizarDados);

        assertNotNull(carroAtualizadoSalvo);
        assertEquals(carroAtualizarDados.getId(),carroAtualizadoSalvo.getId());
        assertEquals(carroAtualizarDados.getModelo(),carroAtualizadoSalvo.getModelo());
        assertEquals(carroAtualizarDados.getAno(),carroAtualizadoSalvo.getAno());
    }

    @Test
    @DisplayName("Deve falhar ao tentar atualizar um carro inexistente")
    public void deveFalharEncontrarCarro(){

        CarroEntity carroSalvo = new CarroEntity(1L,"nissan",10,2026);

        Mockito.when(carroRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        CarroEntity carroAtualizarDados = new CarroEntity(1L,"onix",10,2026);

        var erro = catchThrowable(() -> carroService.atualizar(1L,carroAtualizarDados));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
    }

}