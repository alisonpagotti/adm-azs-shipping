package br.com.azship.gestaofretesapi.modulos.comum.util;

import br.com.azship.gestaofretesapi.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.Test;

import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.ENVIO;
import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.SESSENTA;
import static br.com.azship.gestaofretesapi.modulos.comum.util.DateUtil.*;
import static br.com.azship.gestaofretesapi.modulos.helper.FreteHelper.*;
import static org.assertj.core.api.Assertions.assertThatCode;

class DateUtilTest {

    @Test
    void validarPeriodoMaximo_deveLancarException_sePeriodoMaiorDaQtdMaximaDeDias() {
        assertThatCode(() -> validarPeriodoMaximo(umaDataAtual(), umaDataAtualMais(61), ENVIO, SESSENTA))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de envio não deve ser superior a 60 dias.");
    }

    @Test
    void validarPeriodoMaximo_naoDeveLancarException_sePeriodoMenorDaQtdMaximaDeDias() {
        assertThatCode(() -> validarPeriodoMaximo(umaDataAtual(), umaDataAtualMais(60), ENVIO, SESSENTA))
                .doesNotThrowAnyException();
    }

    @Test
    void validarPeriodoMaximo_naoDeveLancarException_seDataInicialNula() {
        assertThatCode(() -> validarPeriodoMaximo(null, umaDataAtualMais(60), ENVIO, SESSENTA))
                .doesNotThrowAnyException();
    }

    @Test
    void validarPeriodoMaximo_naoDeveLancarException_seDataFinalNula() {
        assertThatCode(() -> validarPeriodoMaximo(umaDataAtual(), null, ENVIO, SESSENTA))
                .doesNotThrowAnyException();
    }

    @Test
    void validarPeriodoMaximo_naoDeveLancarException_seDataInicialEFinalNulas() {
        assertThatCode(() -> validarPeriodoMaximo(null, null, ENVIO, SESSENTA))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataInicialPosteriorADataFinal_deveLancarException_seDataInicialPosteriorADataFinal() {
        assertThatCode(() -> validarDataInicialPosteriorADataFinal(umaDataAtual(), umaDataFinalAnteriorADataInicial(), ENVIO))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de envio não pode ser posterior a data final.");
    }

    @Test
    void validarDataInicialPosteriorADataFinal_naoDeveLancarException_seDataInicialAnterioADataFinal() {
        assertThatCode(() -> validarDataInicialPosteriorADataFinal(umaDataAtual(), umaDataAtualMais(2), ENVIO))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataInicialPosteriorADataFinal_naoDeveLancarException_seDataInicialNula() {
        assertThatCode(() -> validarDataInicialPosteriorADataFinal(null, umaDataAtualMais(2), ENVIO))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataInicialPosteriorADataFinal_naoDeveLancarException_seDataFinalNula() {
        assertThatCode(() -> validarDataInicialPosteriorADataFinal(umaDataAtual(), null, ENVIO))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataInicialPosteriorADataFinal_naoDeveLancarException_seDataInicialEFinalNulas() {
        assertThatCode(() -> validarDataInicialPosteriorADataFinal(null, null, ENVIO))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataAnteriorADataCadastro_deveLancarException_seDataAnteriorADataCadastro() {
        assertThatCode(() -> validarDataAnteriorADataCadastro(umaDataAtual(), umaDataAtualMais(4)))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data não pode ser anterior a data de cadastro.");
    }

    @Test
    void validarDataAnteriorADataCadastro_naoDeveLancarException_seDataPosteriorADataCadastro() {
        assertThatCode(() -> validarDataAnteriorADataCadastro(umaDataAtual(), umaDataFinalAnteriorADataInicial()))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataAnteriorADataCadastro_naoDeveLancarException_seDataNula() {
        assertThatCode(() -> validarDataAnteriorADataCadastro(null, umaDataFinalAnteriorADataInicial()))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataAnteriorADataCadastro_naoDeveLancarException_seDataCadastroNula() {
        assertThatCode(() -> validarDataAnteriorADataCadastro(umaDataAtual(), null))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataAnteriorADataCadastro_naoDeveLancarException_seDataEDataCadastroNulas() {
        assertThatCode(() -> validarDataAnteriorADataCadastro(null, null))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataPosteriorAAtual_deveLancarException_seDataPosteriorAAtual() {
        assertThatCode(() -> validarDataPosteriorAAtual(umaDataAtualMais(5)))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data não pode ser posterior a data atual.");
    }

    @Test
    void validarDataPosteriorAAtual_naoDeveLancarException_seDataAnteriorAAtual() {
        assertThatCode(() -> validarDataPosteriorAAtual(umaDataAtual().minusDays(1)))
                .doesNotThrowAnyException();
    }

    @Test
    void validarDataPosteriorAAtual_naoDeveLancarException_seDataNula() {
        assertThatCode(() -> validarDataPosteriorAAtual(null))
                .doesNotThrowAnyException();
    }
}