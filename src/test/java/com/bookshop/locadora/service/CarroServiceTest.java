package com.bookshop.locadora.service;

import com.bookshop.locadora.entity.CarroEntity;
import com.bookshop.locadora.model.Carro;
import com.bookshop.locadora.model.exception.EntityNotFoundException;
import com.bookshop.locadora.repository.CarroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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

    @Test
    @DisplayName("Deve deletar um carro com sucesso")
    public void deveDeletarCarro(){
        CarroEntity carro = new CarroEntity(1L,"nissan",20,2026);

        Mockito.when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));

        carroService.deletar(1L);

        Mockito.verify(carroRepository,Mockito.times(1)).findById(1L);
        Mockito.verify(carroRepository,Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Must fail in trying to delete a car with unknown ID")
    public void deveFalharEmDeletarCarro(){
        CarroEntity carro = new CarroEntity(1L,"nissan",20,2026);

        Mockito.when(carroRepository.findById(2L)).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> carroService.deletar(2L));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Must find a car by searching its ID")
    public void deveEncontrarCarroPorId(){
        CarroEntity carro = new CarroEntity(1L,"nissan",20,2026);

        Mockito.when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));

        var resposta = carroService.buscarPorId(1L);

        assertThat(resposta).isInstanceOf(CarroEntity.class);
        Mockito.verify(carroRepository,Mockito.times(1)).findById(1L);
        assertThat(resposta.getId()).isEqualTo(1L);
        assertThat(resposta.getAno()).isEqualTo(2026);
        assertThat(resposta.getModelo()).isEqualTo("nissan");

    }

    @Test
    @DisplayName("Must return an error when trying to find a car with unknown ID")
    public void deveRetornarErroAoLocalizarPorID(){
        Mockito.when(carroRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> carroService.buscarPorId(2L));
        Mockito.verify(carroRepository,Mockito.times(1)).findById(2L);
        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    @DisplayName("Should return all cars found")
    public void deveRetornarTodosOsCarrosEncontrados(){
        CarroEntity carro1 = CarroEntity.builder().id(1L).modelo("Nissan").valorDiaria(20).ano(2026).build();
        CarroEntity carro2 = CarroEntity.builder().id(2L).modelo("Onix").valorDiaria(30).ano(2025).build();

        var listaCarros = List.of(carro1,carro2);

        Mockito.when(carroRepository.findAll()).thenReturn(listaCarros);

        var resposta = carroService.listarTodos();


        assertThat(resposta).isInstanceOf(List.class);
        Mockito.verify(carroRepository,Mockito.times(1)).findAll();
        assertThat(resposta.size()).isEqualTo(2);



    }

}