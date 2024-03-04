package br.com.azship.gestaofretesapi.modulos.frete.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao.*;
import static br.com.azship.gestaofretesapi.modulos.helper.FreteHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

class FreteTest {

    @Test
    void of_deveRetornarFrete_quandoParametroForFreteRequest() {
        var frete = Frete.of(umFreteRequest());

        assertThat(frete)
                .extracting(
                        Frete::getNfe,
                        Frete::getTransportadora,
                        Frete::getMotorista,
                        Frete::getSituacao,
                        Frete::getDataEnvio,
                        Frete::getDataRecebimento,
                        Frete::getDataCancelamento,
                        Frete::getValor
                )
                .containsExactly(
                        "000873456",
                        "TRANSPORTADORA ZERO43",
                        "AGENOR RONEGA",
                        AGUARDANDO_ENVIO,
                        null,
                        null,
                        null,
                        new BigDecimal("232.32")
                );

        assertThat(frete.getDataCadastro())
                .isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void of_deveRetornarFrete_quandoParametroForSituacaoEData() {
        var frete = Frete.of(umFrete(), umFreteRequest());

        assertThat(frete)
                .extracting(
                        Frete::getDataCadastro,
                        Frete::getNfe,
                        Frete::getTransportadora,
                        Frete::getMotorista,
                        Frete::getSituacao,
                        Frete::getDataEnvio,
                        Frete::getDataRecebimento,
                        Frete::getDataCancelamento,
                        Frete::getValor
                )
                .containsExactly(
                        LocalDateTime.of(2024, 3, 1, 13, 31, 0),
                        "000873456",
                        "TRANSPORTADORA ZERO43",
                        "AGENOR RONEGA",
                        ENVIADO,
                        LocalDate.of(2023, 3, 2),
                        null,
                        null,
                        new BigDecimal("232.32")
                );
    }

    @Test
    void atualizarSituacao_deveAtualizarDataEnvioESituacaoParaEnviado_seSituacaoAtualizadaForEnviado() {
        var frete = new Frete();
        frete.atualizarSituacao(ENVIADO, umaData());

        assertThat(frete)
                .extracting(Frete::getSituacao, Frete::getDataEnvio)
                .containsExactly(ENVIADO, umaData());
    }

    @Test
    void atualizarSituacao_deveAtualizarDataRecebimentoESituacaoParaRecebido_seSituacaoAtualizadaForRecebido() {
        var frete = new Frete();
        frete.atualizarSituacao(RECEBIDO, umaData());

        assertThat(frete)
                .extracting(Frete::getSituacao, Frete::getDataRecebimento)
                .containsExactly(RECEBIDO, umaData());
    }

    @Test
    void atualizarSituacao_deveAtualizarDataCancelamentoESituacaoParaCancelado_seSituacaoAtualizadaForCancelado() {
        var frete = new Frete();
        frete.atualizarSituacao(CANCELADO, umaData());

        assertThat(frete)
                .extracting(Frete::getSituacao, Frete::getDataCancelamento)
                .containsExactly(CANCELADO, umaData());
    }
}