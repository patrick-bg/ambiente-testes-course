package io.github.patrickbg.locadora.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import io.github.patrickbg.locadora.exceptions.ReservaInvalidaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservaTest {

    Cliente cliente;
    Carro carro;

    @BeforeEach
    void setUp(){
        cliente = new Cliente("Joseph");
        carro = new Carro ("Sport", 50.0);
    }

    @Test
    void deveCriarUmaReserva() {
        // cenário
        var dias = 5;

        //Execução
        var reserva = new Reserva(carro,cliente,dias);

        //Verificação
        Assertions.assertNotNull(reserva);
    }

    @Test
    void deveDarErroAoCriarUmaReservaComDiasNegativos(){

        // JUnit
        assertThrows(ReservaInvalidaException.class, () -> new Reserva(carro,cliente,0));
        assertDoesNotThrow(() -> new Reserva(carro,cliente,1));

        //AssertJ
        var erro = catchThrowable(() -> new Reserva(carro,cliente,-1));

        assertThat(erro).isInstanceOf(ReservaInvalidaException.class)
                .hasMessage("A reserva não pode ser menor que 1.");
    }

    @Test
    void deveCalcularOTotalDoAluguel(){
        var reserva = new Reserva(carro,cliente,3);

        var total = reserva.calcularTotal();
        assertThat(total).isEqualTo(150);

    }

}
