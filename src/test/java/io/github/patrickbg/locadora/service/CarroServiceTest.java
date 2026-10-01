package io.github.patrickbg.locadora.service;

import io.github.patrickbg.locadora.entity.CarroEntity;
import io.github.patrickbg.locadora.exceptions.EntityNotFoundException;
import io.github.patrickbg.locadora.repository.CarroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    CarroService service;

    @Mock
    CarroRepository carroRepository;

    @Test
    void deveSalvarCarro() {
        CarroEntity carroParaSalvar = new CarroEntity("Sedan", 10.0, 2027);

        CarroEntity carroParaRetornar = new CarroEntity("Sedan", 10.0, 2027);
        carroParaRetornar.setId(1L);

        Mockito.when(carroRepository.save(Mockito.any())).thenReturn(carroParaRetornar);


        var carroSalvo = service.salvar(carroParaSalvar);

        assertNotNull(carroSalvo);
        assertEquals("Sedan", carroSalvo.getModelo());

        Mockito.verify(carroRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("Erro com diaria negativa")
    void deveDarErroAoSAlvarCarroComDiariaNegativa() {
        CarroEntity carro = new CarroEntity("Sedan", 0, 2027);

        var erro = catchThrowable(() -> service.salvar(carro));

        assertThat(erro).isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(carroRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveAtualizarUmCarro() {

        var carroExistente = new CarroEntity("Gol", 80.0, 2020);
        Mockito.when(carroRepository.findById(1L)).thenReturn(Optional.of(carroExistente));

        var carroAtualizado = new CarroEntity("Gol", 80.0, 2020);
        carroAtualizado.setId(1L);
        Mockito.when(carroRepository.save(Mockito.any())).thenReturn(carroAtualizado);

        Long id = 1L;
        var carro = new CarroEntity("Uno", 70.0, 2018);

        var resultado = service.atualizar(id, carro);

        assertEquals(resultado.getModelo(), "Gol");
        Mockito.verify(carroRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deveDarErroAoAtualizarCarroInexistente() {
        Long id = 1L;
        var carro = new CarroEntity("Uno", 70.0, 2018);

        Mockito.when(carroRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> service.atualizar(id, carro));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(carroRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveDarErroAoDeletarCarroInexistente() {
        Long id = 1L;

        Mockito.when(carroRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> service.deletar(id));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(carroRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void deveDeletarCarro() {
        Long id = 1L;
        var carro = new CarroEntity("Uno", 70.0, 2018);

        Mockito.when(carroRepository.findById(Mockito.any())).thenReturn(Optional.of(carro));

        service.deletar(id);

        Mockito.verify(carroRepository, Mockito.times(1)).delete(carro);
    }

    @Test
    void deveBuscarCarroPorId() {
        Long id = 1L;
        var carro = new CarroEntity("Uno", 70.0, 2018);

        Mockito.when(carroRepository.findById(Mockito.any())).thenReturn(Optional.of(carro));

        var carroEncontrado = service.buscarPorId(id);

        assertThat(carroEncontrado.getModelo()).isEqualTo("Uno");
    }

    @Test
    void deveListarTodos(){
        var carro1 = new CarroEntity(1L,"Uno", 70.0, 2018);
        var carro2 = new CarroEntity(2L,"Gol", 80.0, 2020);

        var lista = List.of(carro1, carro2);
        Mockito.when(carroRepository.findAll()).thenReturn(lista);

        List<CarroEntity> resultado = service.buscarTodos();

        assertThat(resultado).hasSize(2);
        Mockito.verify(carroRepository, Mockito.times(1)).findAll();
    }

}