package io.github.patrickbg.locadora.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarroTest {

    @Test
    @DisplayName("Deve calcular o valor correto do aluguel")
    void deveCalcularValorAluguel(){
        Carro carro = new Carro("Sedan", 100.0);
        double total = carro.calcularValorAluguel(3);
        Assertions.assertEquals(300.0, total);
    }

    @Test
    @DisplayName("Deve calcular o valor do aluguel com desconto")
    void deveCalcularValorAluguelComDesconto(){
        // Cenário do teste
        Carro carro = new Carro("Sedan", 100.0);
        int qtdDias = 5;

        // Execução
        double total = carro.calcularValorAluguel(qtdDias);

        // Verificação
        Assertions.assertEquals(450, total);
    }
}
