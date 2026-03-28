package com.bookshop.locadora.controller;


import com.bookshop.locadora.entity.CarroEntity;
import com.bookshop.locadora.model.exception.EntityNotFoundException;
import com.bookshop.locadora.service.CarroService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@AllArgsConstructor
@RestController
@RequestMapping("carros")
public class CarroController {

    private final CarroService carroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CarroEntity carro){
        try{
            return status(201)
                    .body(carroService.salvar(carro));
        } catch (IllegalArgumentException e) {
            //Unprocessable Entity
            return status(422)
                    .body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> buscarCarro(@PathVariable Long id){
        try{
            var carroEncontrado = carroService.buscarPorId(id);
            return ResponseEntity.ok(carroEncontrado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<CarroEntity>> buscarTodosCarros(){
        try{
            var listaCarros = carroService.listarTodos();
            return ResponseEntity.ok(listaCarros);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroEntity> atualizarCarro(@PathVariable Long id ,@RequestBody CarroEntity carro){
        try{
            var carroAtualizado = carroService.atualizar(id,carro);
            return ResponseEntity.ok(carroAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCarro(@PathVariable Long id){
        try {
            carroService.deletar(id);
            return ResponseEntity.ok(id.toString()+" - Id do carro deletado");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
