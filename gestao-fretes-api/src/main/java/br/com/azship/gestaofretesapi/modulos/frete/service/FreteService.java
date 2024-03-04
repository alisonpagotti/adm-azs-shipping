package br.com.azship.gestaofretesapi.modulos.frete.service;

import br.com.azship.gestaofretesapi.config.PageRequest;
import br.com.azship.gestaofretesapi.modulos.comum.exception.NotFoundException;
import br.com.azship.gestaofretesapi.modulos.comum.exception.ValidacaoException;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteFiltros;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteRequest;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteResponse;
import br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao;
import br.com.azship.gestaofretesapi.modulos.frete.model.Frete;
import br.com.azship.gestaofretesapi.modulos.frete.repository.FreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static br.com.azship.gestaofretesapi.modulos.comum.util.CaracterUtil.validarNfe;
import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.*;
import static br.com.azship.gestaofretesapi.modulos.comum.util.DateUtil.validarDataAnteriorADataCadastro;
import static br.com.azship.gestaofretesapi.modulos.comum.util.DateUtil.validarDataPosteriorAAtual;
import static br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao.*;

@Service
@RequiredArgsConstructor
public class FreteService {

    private final FreteRepository repository;

    public void cadastrar(FreteRequest request) {
        validarNfe(request.getNfe());
        validarFreteComNfeCadastrada(request.getNfe());


        repository.save(Frete.of(request));
    }

    public Page<FreteResponse> listarTodosComPaginacao(PageRequest pageRequest, FreteFiltros filtros) {
        filtros.validarDatasIniciaisPosteriorADatasFinais();
        filtros.validarPeriodoMaiorDoQue60Dias();

        return repository
                .findAll(filtros.toPredicate().build(), pageRequest)
                .map(FreteResponse::of);
    }

    public FreteResponse detalhar(Integer freteId) {
        return FreteResponse.of(findById(freteId));
    }

    public void editar(Integer freteId, FreteRequest request) {
        var frete = findById(freteId);
        validarNfe(request.getNfe());
        validarSituacaoEditavel(frete.getSituacao(), EDITADO);

        repository.save(Frete.of(frete, request));
    }

    public void atualizarSituacao(Integer freteId, LocalDate data, ESituacao situacao) {
        var frete = findById(freteId);
        validarDataAnteriorADataCadastro(data, frete.getDataCadastro().toLocalDate());
        validarDataPosteriorAAtual(data);

        switch (situacao) {
            case ENVIADO -> atualizarFrete(frete, situacao, data, AGUARDANDO_ENVIO, EX_ATUALIZAR_SITUACAO_ENVIADO);
            case RECEBIDO -> atualizarFrete(frete, situacao, data, ENVIADO, EX_ATUALIZAR_SITUACAO_RECEBIDO);
            case CANCELADO -> atualizarFrete(frete, situacao, data, AGUARDANDO_ENVIO, EX_ATUALIZAR_SITUACAO_CANCELADO);
            default -> throw new ValidacaoException(EX_SITUACAO_NAO_ATUALIZADA);
        }
    }

    private void atualizarFrete(Frete frete,
                                ESituacao novaSituacao,
                                LocalDate data,
                                ESituacao situacaoEsperada,
                                String mensagemErro) {
        validarSituacaoAtualizada(frete, situacaoEsperada, mensagemErro);
        frete.atualizarSituacao(novaSituacao, data);
        repository.save(frete);
    }

    private void validarSituacaoAtualizada(Frete frete, ESituacao situacaoEsperada, String mensagemErro) {
        if (frete.getSituacao() != situacaoEsperada) {
            throw new ValidacaoException(mensagemErro);
        }
    }

    public void excluir(Integer freteId) {
        var frete = findById(freteId);
        validarSituacaoEditavel(frete.getSituacao(), EXCLUIDO);

        repository.delete(frete);
    }

    private void validarFreteComNfeCadastrada(String nfe) {
        if (repository.existsByNfe(nfe)) {
            throw new ValidacaoException(EX_FRETE_JA_CADASTRADO_PARA_NFE);
        }
    }

    private Frete findById(Integer freteId) {
        return repository
                .findById(freteId)
                .orElseThrow(() -> new NotFoundException(EX_FRETE_NAO_CADASTRADO));
    }

    private void validarSituacaoEditavel(ESituacao situacao, String acao) {
        if (!hasSituacaoEditavel(situacao)) {
            throw new ValidacaoException(String.format(EX_FRETE_NAO_PODE_SER, acao));
        }
    }
}
