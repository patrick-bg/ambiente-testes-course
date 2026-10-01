package io.github.patrickbg.locadora.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "carro")
@Data
@RequiredArgsConstructor
public class CarroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private double valorDiaria;
    private int ano;

    public CarroEntity(String modelo, double valorDiaria, int ano) {
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.ano = ano;
    }

    public CarroEntity(Long id, String modelo, double valorDiaria, int ano) {
        this.id = id;
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.ano = ano;
    }
}
