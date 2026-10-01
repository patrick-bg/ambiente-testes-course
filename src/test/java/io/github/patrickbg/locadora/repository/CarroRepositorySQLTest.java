package io.github.patrickbg.locadora.repository;

import io.github.patrickbg.locadora.entity.CarroEntity;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class CarroRepositorySQLTest {

    @Autowired
    CarroRepository carroRepository;

    CarroEntity carro;

    @BeforeEach
    void setup() {
        carro = new CarroEntity("Honda Civic", 200.00, 2027);
    }

    @Sql("/sql/popular-carros.sql")
    @Test
    void deveBuscarCarroPorModelo(){
        List<CarroEntity> lista = carroRepository.findByModelo("SUV");

        var carro = lista.stream().findFirst().get();

        assertEquals(1,lista.size());

        assertThat(carro.getModelo()).isEqualTo("SUV");
        assertThat(carro.getValorDiaria()).isEqualTo(150);
    }

    @Test
    void deveBuscaarCarroPorId(){
        var carroSalvo = carroRepository.save(carro);

        Optional<CarroEntity> carroEncontrado = carroRepository.findById(carroSalvo.getId());
        assertThat(carroEncontrado).isPresent();
        assertThat(carroEncontrado.get().getModelo()).isEqualTo("Honda Civic");
    }

    @Test
    void deveAtualizarCarro(){
        var carroSalvo = carroRepository.save(carro);

        carroSalvo.setAno(2028);

        var carroAtualizado = carroRepository.save(carroSalvo);

        assertThat(carroAtualizado.getAno()).isEqualTo(2028);
    }

    @Test
    void deveDeletarCarro(){
        var carroSalvo = carroRepository.save(carro);

        carroRepository.deleteById(carroSalvo.getId());

        Optional<CarroEntity> carroEncontrado = carroRepository.findById(carroSalvo.getId());

        assertThat(carroEncontrado).isEmpty();
    }


}
