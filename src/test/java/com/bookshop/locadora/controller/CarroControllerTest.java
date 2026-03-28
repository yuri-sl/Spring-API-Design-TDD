package com.bookshop.locadora.controller;

import com.bookshop.locadora.entity.CarroEntity;
import com.bookshop.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


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







}