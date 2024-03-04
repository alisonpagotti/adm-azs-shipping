package br.com.azship.gestaofretesapi.modulos.frete.service;

import br.com.azship.gestaofretesapi.config.PageRequest;
import br.com.azship.gestaofretesapi.modulos.comum.exception.NotFoundException;
import br.com.azship.gestaofretesapi.modulos.comum.exception.ValidacaoException;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteFiltros;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteResponse;
import br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao;
import br.com.azship.gestaofretesapi.modulos.frete.model.Frete;
import br.com.azship.gestaofretesapi.modulos.frete.repository.FreteRepository;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.azship.gestaofretesapi.modulos.helper.FreteHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FreteServiceTest {

    @InjectMocks
    private FreteService service;
    @Mock
    private FreteRepository repository;
    @Captor
    private ArgumentCaptor<Frete> freteCaptor;

    @Test
    void cadastrar_deveLancarException_seNfeNaoPossuirDigitos() {
        var request = umFreteRequest();
        request.setNfe("XXXXXXXXX");

        assertThatCode(() -> service
                .cadastrar(request))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("NF-e deve possuir apenas digitos.");

        verify(repository, never()).existsByNfe(any());
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void cadastrar_deveLancarException_seNfePossuirMaisDeNoveDigitos() {
        var request = umFreteRequest();
        request.setNfe("0009863421");

        assertThatCode(() -> service
                .cadastrar(request))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("NF-e deve possuir até nove digitos.");

        verify(repository, never()).existsByNfe(any());
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void cadastrar_deveLancarException_seNfeJaCadastradaParaAlgumFrete() {
        doReturn(true)
                .when(repository)
                .existsByNfe("000873456");

        assertThatCode(() -> service
                .cadastrar(umFreteRequest()))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("Frete já cadastrado para esta NF-e.");

        verify(repository).existsByNfe("000873456");
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void cadastrar_deveCadastrarFrete_quandoTudoOk() {
        doReturn(false)
                .when(repository)
                .existsByNfe("000873456");

        assertThatCode(() -> service
                .cadastrar(umFreteRequest()))
                .doesNotThrowAnyException();

        verify(repository).existsByNfe("000873456");
        verify(repository).save(freteCaptor.capture());

        assertThat(freteCaptor.getValue())
                .extracting(Frete::getNfe, Frete::getTransportadora, Frete::getMotorista, Frete::getSituacao)
                .containsExactly("000873456", "TRANSPORTADORA ZERO43", "AGENOR RONEGA", ESituacao.AGUARDANDO_ENVIO);

        assertThat(freteCaptor.getValue().getDataCadastro())
                .isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void listarTodosComPaginacao_deveLancarException_seDataCadastroInicialPosteriorADataFinal() {
        assertThatCode(() -> service
                .listarTodosComPaginacao(new PageRequest(), umFreteFiltrosDataCadastro(umaDataAtual(), umaDataFinalAnteriorADataInicial())))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de cadastro não pode ser posterior a data final.");

        verify(repository, never()).findAll(any(BooleanBuilder.class), any(PageRequest.class));
    }

    @Test
    void listarTodosComPaginacao_deveLancarException_seDataEnvioInicialPosteriorADataFinal() {
        assertThatCode(() -> service
                .listarTodosComPaginacao(new PageRequest(), umFreteFiltrosDataEnvio(umaDataAtual(), umaDataFinalAnteriorADataInicial())))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de envio não pode ser posterior a data final.");

        verify(repository, never()).findAll(any(BooleanBuilder.class), any(PageRequest.class));
    }

    @Test
    void listarTodosComPaginacao_deveLancarException_seDataRecebimentoInicialPosteriorADataFinal() {
        assertThatCode(() -> service
                .listarTodosComPaginacao(new PageRequest(), umFreteFiltrosDataRecebimento(umaDataAtual(), umaDataFinalAnteriorADataInicial())))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de recebimento não pode ser posterior a data final.");

        verify(repository, never()).findAll(any(BooleanBuilder.class), any(PageRequest.class));
    }

    @Test
    void listarTodosComPaginacao_deveLancarException_seDataCancelamentoInicialPosteriorADataFinal() {
        assertThatCode(() -> service
                .listarTodosComPaginacao(new PageRequest(), umFreteFiltrosDataCancelamento(umaDataAtual(), umaDataFinalAnteriorADataInicial())))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de cancelamento não pode ser posterior a data final.");

        verify(repository, never()).findAll(any(BooleanBuilder.class), any(PageRequest.class));
    }

    @Test
    void listarTodosComPaginacao_deveLancarException_sePeriodoDataCadastroMaiorDoQue60Dias() {
        assertThatCode(() -> service
                .listarTodosComPaginacao(new PageRequest(), umFreteFiltrosDataCadastro(umaDataAtual(), umaDataAtualMais(61))))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de cadastro não deve ser superior a 60 dias.");

        verify(repository, never()).findAll(any(BooleanBuilder.class), any(PageRequest.class));
    }

    @Test
    void listarTodosComPaginacao_deveLancarException_sePeriodoDataEnvioMaiorDoQue60Dias() {
        assertThatCode(() -> service
                .listarTodosComPaginacao(new PageRequest(), umFreteFiltrosDataEnvio(umaDataAtual(), umaDataAtualMais(61))))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de envio não deve ser superior a 60 dias.");

        verify(repository, never()).findAll(any(BooleanBuilder.class), any(PageRequest.class));
    }

    @Test
    void listarTodosComPaginacao_deveLancarException_sePeriodoDataRecebimentoMaiorDoQue60Dias() {
        assertThatCode(() -> service
                .listarTodosComPaginacao(new PageRequest(), umFreteFiltrosDataRecebimento(umaDataAtual(), umaDataAtualMais(61))))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de recebimento não deve ser superior a 60 dias.");

        verify(repository, never()).findAll(any(BooleanBuilder.class), any(PageRequest.class));
    }

    @Test
    void listarTodosComPaginacao_deveLancarException_sePeriodoDataCancelamentoMaiorDoQue60Dias() {
        assertThatCode(() -> service
                .listarTodosComPaginacao(new PageRequest(), umFreteFiltrosDataCancelamento(umaDataAtual(), umaDataAtualMais(61))))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de cancelamento não deve ser superior a 60 dias.");

        verify(repository, never()).findAll(any(BooleanBuilder.class), any(PageRequest.class));
    }

    @Test
    void listarTodosComPaginacao_deveRetornarPageFreteResponse_quandoTudoOk() {
        var umFreteFiltro = new FreteFiltros();
        var umaPageRequest = new PageRequest();

        doReturn(umaPageFrete())
                .when(repository)
                .findAll(umFreteFiltro.toPredicate().build(), umaPageRequest);

        assertThat(service.listarTodosComPaginacao(umaPageRequest, umFreteFiltro))
                .hasSize(1)
                .extracting(
                        FreteResponse::getId,
                        FreteResponse::getDataCadastro,
                        FreteResponse::getNfe,
                        FreteResponse::getTransportadora,
                        FreteResponse::getMotorista,
                        FreteResponse::getSituacao,
                        FreteResponse::getDataEnvio,
                        FreteResponse::getDataRecebimento,
                        FreteResponse::getDataCancelamento,
                        FreteResponse::getValor
                )
                .containsExactly(
                        tuple(
                                23,
                                LocalDateTime.of(2024, 3, 1, 13, 31, 0),
                                "000873456",
                                "TRANSPORTADORA ZERO43",
                                "AGENOR RONEGA",
                                "ENVIADO",
                                LocalDate.of(2023, 3, 2),
                                null,
                                null,
                                new BigDecimal("320.23")
                        )
                );

        verify(repository).findAll(umFreteFiltro.toPredicate().build(), umaPageRequest);
    }

    @Test
    void detalhar_deveLancarException_seFreteNaoCadastrado() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(10);

        assertThatCode(() -> service
                .detalhar(10))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Frete não cadastrado.");

        verify(repository).findById(10);
    }

    @Test
    void detalhar_deveRetornarFreteResponse_seFreteCadastrado() {
        doReturn(Optional.of(umFrete()))
                .when(repository)
                .findById(23);

        assertThat(service.detalhar(23))
                .extracting(
                        FreteResponse::getId,
                        FreteResponse::getDataCadastro,
                        FreteResponse::getNfe,
                        FreteResponse::getTransportadora,
                        FreteResponse::getMotorista,
                        FreteResponse::getSituacao,
                        FreteResponse::getDataEnvio,
                        FreteResponse::getDataRecebimento,
                        FreteResponse::getDataCancelamento,
                        FreteResponse::getValor
                )
                .containsExactly(
                        23,
                        LocalDateTime.of(2024, 3, 1, 13, 31, 0),
                        "000873456",
                        "TRANSPORTADORA ZERO43",
                        "AGENOR RONEGA",
                        "ENVIADO",
                        LocalDate.of(2023, 3, 2),
                        null,
                        null,
                        new BigDecimal("320.23")
                );

        verify(repository).findById(23);
    }

    @Test
    void editar_deveLancarException_seFreteNaoCadastrado() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(10);

        assertThatCode(() -> service
                .editar(10, umFreteRequest()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Frete não cadastrado.");

        verify(repository).findById(10);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void editar_deveLancarException_seNfeNaoPossuirDigitos() {
        var request = umFreteRequest();
        request.setNfe("XXXXXXXXX");

        doReturn(Optional.of(umFrete()))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .editar(23, request))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("NF-e deve possuir apenas digitos.");

        verify(repository).findById(23);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void editar_deveLancarException_seNfePossuirMaisDeNoveDigitos() {
        var request = umFreteRequest();
        request.setNfe("0009863421");

        doReturn(Optional.of(umFrete()))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .editar(23, request))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("NF-e deve possuir até nove digitos.");

        verify(repository).findById(23);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void editar_deveLancarException_seSituacaoNaoEditavel() {
        var frete = umFrete();
        frete.setSituacao(ESituacao.RECEBIDO);

        doReturn(Optional.of(frete))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .editar(23, umFreteRequest()))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("Frete não pode ser editado.");

        verify(repository).findById(23);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void editar_deveEditarFrete_seTudoOk() {
        var frete = umFrete();
        frete.setSituacao(ESituacao.AGUARDANDO_ENVIO);

        var request = umFreteRequest();
        request.setMotorista("ROGER REGOR");

        doReturn(Optional.of(frete))
                .when(repository)
                .findById(23);

        assertEquals("AGENOR RONEGA", frete.getMotorista());

        assertThatCode(() -> service
                .editar(23, request))
                .doesNotThrowAnyException();

        verify(repository).findById(23);
        verify(repository).save(freteCaptor.capture());

        assertEquals("ROGER REGOR", freteCaptor.getValue().getMotorista());
    }

    @Test
    void atualizarSituacao_deveLancarException_seFreteNaoCadastrado() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(10);

        assertThatCode(() -> service
                .atualizarSituacao(10, LocalDate.of(2024, 3, 20), ESituacao.ENVIADO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Frete não cadastrado.");

        verify(repository).findById(10);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void atualizarSituacao_deveLancarException_seDataAnteriorADataCadastro() {
        doReturn(Optional.of(umFrete()))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .atualizarSituacao(23, LocalDate.of(2024, 2, 20), ESituacao.ENVIADO))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data não pode ser anterior a data de cadastro.");

        verify(repository).findById(23);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void atualizarSituacao_deveLancarException_seDataPosteriorAAtual() {
        doReturn(Optional.of(umFrete()))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .atualizarSituacao(23, umaDataAtualMais(2), ESituacao.ENVIADO))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data não pode ser posterior a data atual.");

        verify(repository).findById(23);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void atualizarSituacao_deveLancarException_seSituacaoAtualizadaEnviadoEFreteSituacaoForDiferenteDeAguardandoEnvio() {
        var dataDeOntem = LocalDate.now().minusDays(1);
        var frete = umFrete();
        frete.setSituacao(ESituacao.RECEBIDO);

        doReturn(Optional.of(frete))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .atualizarSituacao(23, dataDeOntem, ESituacao.ENVIADO))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("Frete deve estar aguardando envio para alterar para enviado.");

        verify(repository).findById(23);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void atualizarSituacao_deveAtualizarSituacao_seSituacaoAtualizadaEnviadoEFreteSituacaoForAguardandoEnvio() {
        var dataDeOntem = LocalDate.now().minusDays(1);
        var frete = umFrete();
        frete.setSituacao(ESituacao.AGUARDANDO_ENVIO);
        frete.setDataEnvio(null);

        doReturn(Optional.of(frete))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .atualizarSituacao(23, dataDeOntem, ESituacao.ENVIADO))
                .doesNotThrowAnyException();

        verify(repository).findById(23);
        verify(repository).save(freteCaptor.capture());

        assertThat(freteCaptor.getValue())
                .extracting(Frete::getSituacao, Frete::getDataEnvio)
                .containsExactly(ESituacao.ENVIADO, dataDeOntem);
    }

    @Test
    void atualizarSituacao_deveLancarException_seSituacaoAtualizadaRecebidoEFreteSituacaoForDiferenteDeEnviado() {
        var dataDeOntem = LocalDate.now().minusDays(1);
        var frete = umFrete();
        frete.setSituacao(ESituacao.AGUARDANDO_ENVIO);

        doReturn(Optional.of(frete))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .atualizarSituacao(23, dataDeOntem, ESituacao.RECEBIDO))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("Frete deve estar enviado para alterar para recebido.");

        verify(repository).findById(23);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void atualizarSituacao_deveAtualizarSituacao_seSituacaoAtualizadaRecebidoEFreteSituacaoForEnviado() {
        var dataDeOntem = LocalDate.now().minusDays(1);

        doReturn(Optional.of(umFrete()))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .atualizarSituacao(23, dataDeOntem, ESituacao.RECEBIDO))
                .doesNotThrowAnyException();

        verify(repository).findById(23);
        verify(repository).save(freteCaptor.capture());

        assertThat(freteCaptor.getValue())
                .extracting(Frete::getSituacao, Frete::getDataRecebimento)
                .containsExactly(ESituacao.RECEBIDO, dataDeOntem);
    }

    @Test
    void atualizarSituacao_deveLancarException_seSituacaoAtualizadaCanceladoEFreteSituacaoForDiferenteDeAguardandoEnvio() {
        var dataDeOntem = LocalDate.now().minusDays(1);

        doReturn(Optional.of(umFrete()))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .atualizarSituacao(23, dataDeOntem, ESituacao.CANCELADO))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("Frete deve estar aguardando envio para alterar para cancelado.");

        verify(repository).findById(23);
        verify(repository, never()).save(any(Frete.class));
    }

    @Test
    void atualizarSituacao_deveAtualizarSituacao_seSituacaoAtualizadaCanceladoEFreteSituacaoForAguardandoEnvio() {
        var dataDeOntem = LocalDate.now().minusDays(1);
        var frete = umFrete();
        frete.setSituacao(ESituacao.AGUARDANDO_ENVIO);

        doReturn(Optional.of(frete))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .atualizarSituacao(23, dataDeOntem, ESituacao.CANCELADO))
                .doesNotThrowAnyException();

        verify(repository).findById(23);
        verify(repository).save(freteCaptor.capture());

        assertThat(freteCaptor.getValue())
                .extracting(Frete::getSituacao, Frete::getDataCancelamento)
                .containsExactly(ESituacao.CANCELADO, dataDeOntem);
    }

    @Test
    void excluir_deveLancarException_seFreteNaoCadastrado() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(10);

        assertThatCode(() -> service
                .excluir(10))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Frete não cadastrado.");

        verify(repository).findById(10);
        verify(repository, never()).delete(any());
    }

    @Test
    void excluir_deveLancarException_seFreteComSituacaoNaoEditavel() {
        doReturn(Optional.of(umFrete()))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .excluir(23))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("Frete não pode ser excluído.");

        verify(repository).findById(23);
        verify(repository, never()).delete(any());
    }

    @Test
    void excluir_deveExcluirFrete_seFreteCadastradoESituacaoEditavel() {
        var frete = umFrete();
        frete.setSituacao(ESituacao.AGUARDANDO_ENVIO);

        doReturn(Optional.of(frete))
                .when(repository)
                .findById(23);

        assertThatCode(() -> service
                .excluir(23))
                .doesNotThrowAnyException();

        verify(repository).findById(23);
        verify(repository).delete(frete);
    }
}