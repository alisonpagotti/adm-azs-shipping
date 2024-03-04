package br.com.azship.gestaofretesapi.modulos.frete.enums;

import org.junit.jupiter.api.Test;

import static br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ESituacaoTest {

    @Test
    void hasSituacaoEditavel_deveRetornarTrue_seSituacaoForAguardandoEnvio() {
        assertTrue(hasSituacaoEditavel(AGUARDANDO_ENVIO));
    }

    @Test
    void hasSituacaoEditavel_deveRetornarFalse_seSituacaoForEnviado() {
        assertFalse(hasSituacaoEditavel(ENVIADO));
    }

    @Test
    void hasSituacaoEditavel_deveRetornarFalse_seSituacaoForRecebido() {
        assertFalse(hasSituacaoEditavel(RECEBIDO));
    }

    @Test
    void hasSituacaoEditavel_deveRetornarFalse_seSituacaoForCancelado() {
        assertFalse(hasSituacaoEditavel(CANCELADO));
    }
}