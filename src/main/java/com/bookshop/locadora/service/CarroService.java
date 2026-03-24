package com.bookshop.locadora.service;

import com.bookshop.locadora.entity.CarroEntity;
import com.bookshop.locadora.model.exception.EntityNotFoundException;
import com.bookshop.locadora.repository.CarroRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Service
public class CarroService {

    private final CarroRepository carroRepository;

    public CarroEntity atualizar(Long id,CarroEntity dados){
        var carroExistente = carroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));


        carroExistente.setValorDiaria(dados.getValorDiaria());
        carroExistente.setModelo(dados.getModelo());
        carroExistente.setAno(dados.getAno());

        return carroRepository.save(carroExistente);
    }
    public CarroEntity salvar(CarroEntity carro){
        if(carro.getValorDiaria() <= 0){
            throw new IllegalArgumentException("Preço de diária n pode ser negativo");
        }
        return  carroRepository.save(carro);
    }

    public void deletar(Long id){
        var carroExistente = carroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));

        carroRepository.deleteById(id);
    }
    public CarroEntity buscarPorId(Long id){
        return carroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));
    }

    public List<CarroEntity> listarTodos(){
        return carroRepository.findAll().stream().toList();

    }





}
