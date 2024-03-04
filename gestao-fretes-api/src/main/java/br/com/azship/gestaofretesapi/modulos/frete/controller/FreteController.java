package br.com.azship.gestaofretesapi.modulos.frete.controller;

import br.com.azship.gestaofretesapi.config.PageRequest;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteFiltros;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteRequest;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteResponse;
import br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao;
import br.com.azship.gestaofretesapi.modulos.frete.service.FreteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.PATTERN_DATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/frete")
public class FreteController {

    private final FreteService service;

    @PostMapping
    public void cadastrar(@RequestBody @Valid FreteRequest request) {
        service.cadastrar(request);
    }

    @GetMapping
    public Page<FreteResponse> listarTodosComPaginacao(PageRequest pageRequest, FreteFiltros filtros) {
        return service.listarTodosComPaginacao(pageRequest, filtros);
    }

    @GetMapping("{id}/detalhar")
    public FreteResponse detalhar(@PathVariable Integer id) {
        return service.detalhar(id);
    }

    @PutMapping("{id}/editar")
    public void editar(@PathVariable Integer id, @RequestBody @Valid FreteRequest request) {
        service.editar(id, request);
    }

    @PutMapping("{id}/atualizar/situacao")
    public void atualizarSituacao(@PathVariable Integer id,
                                  @RequestParam @DateTimeFormat(pattern = PATTERN_DATE) LocalDate data,
                                  @RequestParam ESituacao situacao) {
        service.atualizarSituacao(id, data, situacao);
    }

    @DeleteMapping("{id}/excluir")
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}
