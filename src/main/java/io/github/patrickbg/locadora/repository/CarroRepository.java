package io.github.patrickbg.locadora.repository;

import io.github.patrickbg.locadora.entity.CarroEntity;
import io.github.patrickbg.locadora.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<CarroEntity, Long> {

    List<CarroEntity> findByModelo(String modelo);
}
