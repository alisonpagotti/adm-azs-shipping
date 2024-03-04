package br.com.azship.gestaofretesapi.modulos.frete.controller;

import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteRequest;
import br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao;
import br.com.azship.gestaofretesapi.modulos.frete.service.FreteService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static br.com.azship.gestaofretesapi.helper.TestHelper.convertObjectToJsonBytes;
import static br.com.azship.gestaofretesapi.modulos.helper.FreteHelper.umFreteRequest;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FreteController.class)
class FreteControllerTest {

    private static final String API_URL = "/api/frete";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FreteService service;

    @Test
    @SneakyThrows
    void cadastrar_deveRetornarBadRequest_quandoDadosObrigatoriosNaoPreenchidos() {
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(new FreteRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].message", containsInAnyOrder(
                        "O campo nfe é obrigatório.",
                        "O campo transportadora é obrigatório.",
                        "O campo valor é obrigatório."))
                );

        verify(service, never()).cadastrar(any());
    }

    @Test
    @SneakyThrows
    void cadastrar_deveRetornarCadastrarFrete_quandoTudoOk() {
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(umFreteRequest())))
                .andExpect(status().isOk());

        verify(service).cadastrar(umFreteRequest());
    }

    @Test
    @SneakyThrows
    void listarTodosComPaginacao_deveRetornarListaFretes_quandoChamado() {
        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk());

        verify(service).listarTodosComPaginacao(any(), any());
    }

    @Test
    @SneakyThrows
    void detalhar_deveRetornarFrete_quandoChamado() {
        mockMvc.perform(get(API_URL + "/{id}/detalhar", 1))
                .andExpect(status().isOk());

        verify(service).detalhar(1);
    }

    @Test
    @SneakyThrows
    void editar_deveRetornarBadRequest_quandoDadosObrigatoriosNaoPreenchidos() {
        mockMvc.perform(put(API_URL + "/{id}/editar", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(new FreteRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].message", containsInAnyOrder(
                        "O campo nfe é obrigatório.",
                        "O campo transportadora é obrigatório.",
                        "O campo valor é obrigatório."))
                );

        verify(service, never()).editar(anyInt(), any());
    }

    @Test
    @SneakyThrows
    void editar_deveEditarFrete_quandoChamado() {
        mockMvc.perform(put(API_URL + "/{id}/editar", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(umFreteRequest())))
                .andExpect(status().isOk());

        verify(service).editar(anyInt(), any());
    }

    @Test
    @SneakyThrows
    void atualizarSituacao_deveAtualizarSituacao_quandoChamado() {
        mockMvc.perform(put(API_URL + "/{id}/atualizar/situacao", 1)
                        .param("data", "02/03/2024")
                        .param("situacao", "ENVIADO"))
                .andExpect(status().isOk());

        verify(service).atualizarSituacao(1, LocalDate.of(2024, 3, 2), ESituacao.ENVIADO);
    }

    @Test
    @SneakyThrows
    void excluir_deveExcluirFrete_quandoChamado() {
        mockMvc.perform(delete(API_URL + "/{id}/excluir", 1))
                .andExpect(status().isOk());

        verify(service).excluir(1);
    }
}