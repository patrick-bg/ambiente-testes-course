package io.github.patrickbg.locadora.controller;

import io.github.patrickbg.locadora.entity.CarroEntity;
import io.github.patrickbg.locadora.exceptions.EntityNotFoundException;
import io.github.patrickbg.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarroController.class)
class CarroControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarroService carroService;

    @Test
    void deveSalvarUmCarro() throws Exception {
        //Cenário
        CarroEntity carro = new CarroEntity(1L, "Honda Civic", 150.0, 2027);

        Mockito.when(carroService.salvar(Mockito.any())).thenReturn(carro);

        String json = """
                {
                    "modelo": "Honda Civic",
                    "valorDiaria": 150,
                    "ano": 2027
                }
                """;

        //Execução
        ResultActions result = mvc.perform(
                post("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        //Verificação
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Honda Civic"))
                .andExpect(jsonPath("$.valorDiaria").value(150));
    }

    @Test
    void deveObterDetalhesUmCarro() throws Exception {
        Mockito.when(carroService.buscarPorId(Mockito.any())).thenReturn(new CarroEntity(
                1L, "Honda Civic", 250.0, 2028
        ));

        mvc.perform(
                        MockMvcRequestBuilders.get("/carros/1")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Honda Civic"))
                .andExpect(jsonPath("$.valorDiaria").value(250))
                .andExpect(jsonPath("$.ano").value(2028));
    }

    @Test
    void deveRetornarNotFoundAoTentarObterDetalhesUmCarroInexistente() throws Exception {
        Mockito.when(carroService.buscarPorId(Mockito.any())).thenThrow(EntityNotFoundException.class);

        mvc.perform(
                MockMvcRequestBuilders.get("/carros/1")
        ).andExpect(status().isNotFound());
    }

    @Test
    void deveListarCarros() throws Exception {
        var listagem = List.of(
                new CarroEntity(1L, "Argo", 175, 2024),
                new CarroEntity(2L, "Celta", 80, 2022)
        );

        Mockito.when(carroService.buscarTodos()).thenReturn(listagem);

        mvc.perform(
                        MockMvcRequestBuilders.get("/carros")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Argo"))
                .andExpect(jsonPath("$[1].modelo").value("Celta"));

    }

    @Test
    void deveAtualizarCarro() throws Exception {
        Mockito.when(carroService.atualizar(Mockito.any(), Mockito.any())).thenReturn(new CarroEntity(
                1L, "Honda Civic", 250.0, 2028
        ));

        String json = """
                {
                    "modelo": "Honda Civic",
                    "valorDiaria": 150,
                    "ano": 2027
                }
                """;

        mvc.perform(MockMvcRequestBuilders.put("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoTentarAtualizarUmCarroInexistente() throws Exception {
        Mockito.when(carroService.atualizar(Mockito.any(), Mockito.any())).thenThrow(EntityNotFoundException.class);

        String json = """
                {
                    "modelo": "Honda Civic",
                    "valorDiaria": 150,
                    "ano": 2027
                }
                """;

        mvc.perform(MockMvcRequestBuilders.put("/carros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarUmCarro() throws Exception {
        Mockito.doNothing().when(carroService).deletar(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.delete("/carros/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoDeletarUmCarroInexistente() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(carroService).deletar(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.delete("/carros/1"))
                .andExpect(status().isNotFound());
    }
}