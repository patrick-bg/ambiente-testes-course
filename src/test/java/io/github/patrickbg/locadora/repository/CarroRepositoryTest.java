package io.github.patrickbg.locadora.repository;

import io.github.patrickbg.locadora.entity.CarroEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class CarroRepositoryTest {

    @Autowired
    CarroRepository carroRepository;

    @Test
    void deveSalvarUmCarro(){
        var entity = new CarroEntity("Sedan", 100.00, 2025);

        carroRepository.save(entity);

        Assertions.assertNotNull(entity.getId());
    }

}