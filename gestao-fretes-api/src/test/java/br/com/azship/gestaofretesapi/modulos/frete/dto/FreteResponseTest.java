package br.com.azship.gestaofretesapi.modulos.frete.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static br.com.azship.gestaofretesapi.modulos.helper.FreteHelper.umFrete;
import static org.assertj.core.api.Assertions.assertThat;

class FreteResponseTest {

    @Test
    void of_deveRetornarFreteResponse_quandoChamado() {
        assertThat(FreteResponse.of(umFrete()))
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
    }
}