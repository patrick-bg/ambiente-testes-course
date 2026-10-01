package io.github.patrickbg.locadora.controller;

import io.github.patrickbg.locadora.entity.CarroEntity;
import io.github.patrickbg.locadora.exceptions.EntityNotFoundException;
import io.github.patrickbg.locadora.service.CarroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carros")
public class CarroController {

    private CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CarroEntity carroEntity) {
        try {
            var carroSalvo = carroService.salvar(carroEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvo);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_CONTENT)
                    .body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<CarroEntity> detalhesCarro(@PathVariable Long id) {
        try{
            var CarroEncontrado = carroService.buscarPorId(id);
            return ResponseEntity.ok(CarroEncontrado);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CarroEntity>> listarCarros() {
        return ResponseEntity.ok(carroService.buscarTodos());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody CarroEntity carroAtualizado) {
        try {
            carroService.atualizar(id, carroAtualizado);
            return ResponseEntity.noContent().build();
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            carroService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
