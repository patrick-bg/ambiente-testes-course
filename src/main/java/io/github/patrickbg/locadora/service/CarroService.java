package io.github.patrickbg.locadora.service;

import io.github.patrickbg.locadora.entity.CarroEntity;
import io.github.patrickbg.locadora.exceptions.EntityNotFoundException;
import io.github.patrickbg.locadora.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    private final CarroRepository carroRepository;

    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public CarroEntity salvar(CarroEntity carro){
        if(carro.getValorDiaria() <= 0){
            throw new IllegalArgumentException("Preço da diária não pode ser negativo");
        }
        return carroRepository.save(carro);
    }

    public CarroEntity atualizar(Long id, CarroEntity carroAtualizado){
        var carroExistente = carroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado!"));

        carroExistente.setAno(carroAtualizado.getAno());
        carroExistente.setValorDiaria(carroAtualizado.getValorDiaria());
        carroExistente.setModelo(carroAtualizado.getModelo());

        return carroRepository.save(carroExistente);
    }

    public void deletar(Long id){
        var carroExistente = carroRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Carro não encontrado!"));
        carroRepository.delete(carroExistente);
    }

    public CarroEntity buscarPorId(Long id){
        return carroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado!"));
    }

    public List<CarroEntity> buscarTodos(){
        return carroRepository.findAll();
    }

}
