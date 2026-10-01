package io.github.patrickbg.locadora.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    @Test
    void deveCriarClienteComNome(){
        var cliente = new Cliente("Maria");

        String nome = cliente.getNome();

        assertNotNull(nome);

        assertThat(nome).isEqualTo("Maria");
    }

}
