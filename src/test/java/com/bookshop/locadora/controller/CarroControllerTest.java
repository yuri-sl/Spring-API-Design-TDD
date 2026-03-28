package com.bookshop.locadora.controller;

import com.bookshop.locadora.entity.CarroEntity;
import com.bookshop.locadora.model.exception.EntityNotFoundException;
import com.bookshop.locadora.service.CarroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(CarroController.class)
class CarroControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CarroService carroService;

    @Test
    public void deveSalvarCarro() throws Exception{
        CarroEntity carro = CarroEntity.builder().id(1L).modelo("Nissan").valorDiaria(250).ano(2026).build();

        when(carroService.salvar(any(CarroEntity.class))).thenReturn(carro);

        String json = """
                {
                    "modelo":"Nissan",
                    "valorDiaria":250,
                    "ano":2026
                }
                """;

        //execução
        ResultActions result = mockMvc.perform(
                post("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        //verificando
        result
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Nissan"));
    }

    @Test
    @DisplayName("Deve buscar informações do carro")
    public void deveBuscarCarro() throws Exception{
        CarroEntity carro = CarroEntity.builder().id(2L).modelo("Onix").valorDiaria(26).ano(2027).build();

        carroService.salvar(carro);

        when(carroService.buscarPorId(Mockito.any())).thenReturn(carro);

        String json = """
                {
                 "modelo":"Onix",
                 "valorDiaria":26,
                 "ano":2027
                }
                """;

        ResultActions resultado = mockMvc.perform(
                get("/carros/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        resultado.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Onix"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ano").value(2027));

    }

    @Test
    @DisplayName("Deve dar erro ao buscar informações do carro")
    public void deveRetornarNotFoundAoBuscarDadosDeCarroInexistente() throws Exception{
        CarroEntity carro = CarroEntity.builder().id(2L).modelo("Onix").valorDiaria(26).ano(2027).build();

        carroService.salvar(carro);

        when(carroService.buscarPorId(Mockito.any())).thenThrow(EntityNotFoundException.class);

        String json = """
                {
                 "modelo":"Onix",
                 "valorDiaria":26,
                 "ano":2027
                }
                """;

        ResultActions resultado = mockMvc.perform(
                get("/carros/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        resultado.andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @DisplayName("Deve buscar todos os carros")
    public void deveBuscarTodosCarros() throws  Exception{
        CarroEntity carro1 = CarroEntity.builder().id(1L).modelo("Onix").valorDiaria(150).ano(2026).build();
        CarroEntity carro2 = CarroEntity.builder().id(2L).modelo("Corola").valorDiaria(250).ano(2027).build();

        var listaCarros = List.of(carro1,carro2);

        carroService.salvar(carro1);
        carroService.salvar(carro2);

        when(carroService.listarTodos()).thenReturn(listaCarros);

        String json = """
                [
                    {
                    
                     "modelo":"Onix",
                     "valorDiaria":150,
                     "ano":2026
                    },
                    {
                     "modelo":"Corola",
                     "valorDiaria":250,
                     "ano":2027
                    },
                ]
                """;
        ResultActions resultado = mockMvc.perform(
                get("/carros/listAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );
        resultado.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].modelo").value("Onix"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].valorDiaria").value(150))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ano").value(2026))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].modelo").value("Corola"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].valorDiaria").value(250))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ano").value(2027));


    }

    @Test
    @DisplayName("Deve atualizar carro")
    public void deveAtualizarCarro()throws Exception{
        CarroEntity carro1 = CarroEntity.builder().id(1L).modelo("Onix").valorDiaria(150).ano(2026).build();
        CarroEntity carro2 = CarroEntity.builder().id(2L).modelo("Corola").valorDiaria(250).ano(2027).build();

        carroService.salvar(carro1);

        when(carroService.atualizar(Mockito.any(),Mockito.any())).thenReturn(carro2);

        String json = """
                    {
                     "modelo":"Corola",
                     "valorDiaria":250,
                     "ano":2027
                    },
                """;
        ResultActions resultado = mockMvc.perform(
                put("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );
        resultado.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Corola"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorDiaria").value(250))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ano").value(2027));
    }

    @Test
    @DisplayName("Deve dar erro ao atualizar informações do carro")
    public void deveRetornarNotFoundAoAtualizarDadosDeCarroInexistente() throws Exception{
        CarroEntity carro = CarroEntity.builder().id(2L).modelo("Onix").valorDiaria(26).ano(2027).build();

        carroService.salvar(carro);

        when(carroService.atualizar(Mockito.any(),Mockito.any())).thenThrow(EntityNotFoundException.class);

        String json = """
                {
                 "modelo":"Onix",
                 "valorDiaria":26,
                 "ano":2027
                }
                """;

        ResultActions resultado = mockMvc.perform(
                put("/carros/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        resultado.andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    @DisplayName("Deve deletar carro")
    public void deveDeletarCarro()throws Exception{
        CarroEntity carro1 = CarroEntity.builder().id(1L).modelo("Onix").valorDiaria(150).ano(2026).build();
        CarroEntity carro2 = CarroEntity.builder().id(2L).modelo("Corola").valorDiaria(250).ano(2027).build();

        carroService.salvar(carro1);

        String retorno = carro1.getId() + " - Id do carro deletado";

        when(carroService.deletar(Mockito.anyLong()));

        String json = """
                    {
                     "modelo":"Corola",
                     "valorDiaria":250,
                     "ano":2027
                    },
                """;
        ResultActions resultado = mockMvc.perform(
                delete("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );
        resultado.andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @DisplayName("Deve dar erro ao atualizar informações do carro")
    public void deveRetornarNotFoundAoAtualizarDadosDeCarroInexistente() throws Exception{
        CarroEntity carro = CarroEntity.builder().id(2L).modelo("Onix").valorDiaria(26).ano(2027).build();

        carroService.salvar(carro);

        when(carroService.deletar(Mockito.any()).thenThrow(EntityNotFoundException.class);

        ResultActions resultado = mockMvc.perform(
                put("/carros/2")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultado.andExpect(MockMvcResultMatchers.status().isNotFound());

    }








}