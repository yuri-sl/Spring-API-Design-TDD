package com.bookshop.locadora.controller;


import com.bookshop.locadora.entity.CarroEntity;
import com.bookshop.locadora.service.CarroService;
import lombok.AllArgsConstructor;
import org.hibernate.sql.RestrictionRenderingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

@AllArgsConstructor
@RestController
@RequestMapping("carros")
public class CarroController {

    private final CarroService carroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CarroEntity carro){
        try{
            return ResponseEntity.
                    status(201)
                    .body(carroService.salvar(carro));
        } catch (IllegalArgumentException e) {
            //Unprocessable Entity
            return ResponseEntity
                    .status(422)
                    .body(e.getMessage());
        }
    }


}
