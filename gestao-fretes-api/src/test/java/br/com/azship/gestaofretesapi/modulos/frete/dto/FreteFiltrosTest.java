package br.com.azship.gestaofretesapi.modulos.frete.dto;

import br.com.azship.gestaofretesapi.modulos.comum.exception.ValidacaoException;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Test;

import static br.com.azship.gestaofretesapi.modulos.helper.FreteHelper.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FreteFiltrosTest {

    @Test
    void validarPeriodoMaiorDoQue60Dias_deveLancarException_sePeriodoDataCadastroMaiorDoQue60Dias() {
        assertThatCode(() -> umFreteFiltrosDataCadastro(umaDataAtual(), umaDataAtualMais(61))
                .validarPeriodoMaiorDoQue60Dias())
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de cadastro não deve ser superior a 60 dias.");
    }

    @Test
    void validarPeriodoMaiorDoQue60Dias_naoDeveLancarException_sePeriodoDataCadastroMenorOuIgualA60Dias() {
        assertThatCode(() -> umFreteFiltrosDataCadastro(umaDataAtual(), umaDataAtualMais(60))
                .validarPeriodoMaiorDoQue60Dias())
                .doesNotThrowAnyException();
    }

    @Test
    void validarPeriodoMaiorDoQue60Dias_deveLancarException_sePeriodoDataEnvioMaiorDoQue60Dias() {
        assertThatCode(() -> umFreteFiltrosDataEnvio(umaDataAtual(), umaDataAtualMais(61))
                .validarPeriodoMaiorDoQue60Dias())
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de envio não deve ser superior a 60 dias.");
    }

    @Test
    void validarPeriodoMaiorDoQue60Dias_naoDeveLancarException_sePeriodoDataEnvioMenorOuIgualA60Dias() {
        assertThatCode(() -> umFreteFiltrosDataEnvio(umaDataAtual(), umaDataAtualMais(60))
                .validarPeriodoMaiorDoQue60Dias())
                .doesNotThrowAnyException();
    }

    @Test
    void validarPeriodoMaiorDoQue60Dias_deveLancarException_sePeriodoDataRecebimentoMaiorDoQue60Dias() {
        assertThatCode(() -> umFreteFiltrosDataRecebimento(umaDataAtual(), umaDataAtualMais(61))
                .validarPeriodoMaiorDoQue60Dias())
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de recebimento não deve ser superior a 60 dias.");
    }

    @Test
    void validarPeriodoMaiorDoQue60Dias_naoDeveLancarException_sePeriodoDataRecebimentoMenorOuIgualA60Dias() {
        assertThatCode(() -> umFreteFiltrosDataRecebimento(umaDataAtual(), umaDataAtualMais(60))
                .validarPeriodoMaiorDoQue60Dias())
                .doesNotThrowAnyException();
    }

    @Test
    void validarPeriodoMaiorDoQue60Dias_deveLancarException_sePeriodoDataCancelamentoMaiorDoQue60Dias() {
        assertThatCode(() -> umFreteFiltrosDataCancelamento(umaDataAtual(), umaDataAtualMais(61))
                .validarPeriodoMaiorDoQue60Dias())
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O período da data de cancelamento não deve ser superior a 60 dias.");
    }

    @Test
    void validarPeriodoMaiorDoQue60Dias_naoDeveLancarException_sePeriodoDataCancelamentoMenorOuIgualA60Dias() {
        assertThatCode(() -> umFreteFiltrosDataCancelamento(umaDataAtual(), umaDataAtualMais(60))
                .validarPeriodoMaiorDoQue60Dias())
                .doesNotThrowAnyException();
    }

    @Test
    void validarDatasIniciaisPosteriorADatasFinais_deveLancarException_seDataCadastroInicialPosteriorADataFinal() {
        assertThatCode(() -> umFreteFiltrosDataCadastro(umaDataAtual(), umaDataFinalAnteriorADataInicial())
                .validarDatasIniciaisPosteriorADatasFinais())
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de cadastro não pode ser posterior a data final.");
    }

    @Test
    void validarDatasIniciaisPosteriorADatasFinais_naoDeveLancarException_seDataCadastroInicialAnteriorADataFinal() {
        assertThatCode(() -> umFreteFiltrosDataCadastro(umaDataAtual(), umaDataAtualMais(30))
                .validarDatasIniciaisPosteriorADatasFinais())
                .doesNotThrowAnyException();
    }

    @Test
    void validarDatasIniciaisPosteriorADatasFinais_deveLancarException_seDataEnvioInicialPosteriorADataFinal() {
        assertThatCode(() -> umFreteFiltrosDataEnvio(umaDataAtual(), umaDataFinalAnteriorADataInicial())
                .validarDatasIniciaisPosteriorADatasFinais())
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de envio não pode ser posterior a data final.");
    }

    @Test
    void validarDatasIniciaisPosteriorADatasFinais_naoDeveLancarException_seDataEnvioInicialAnteriorADataFinal() {
        assertThatCode(() -> umFreteFiltrosDataEnvio(umaDataAtual(), umaDataAtualMais(30))
                .validarDatasIniciaisPosteriorADatasFinais())
                .doesNotThrowAnyException();
    }

    @Test
    void validarDatasIniciaisPosteriorADatasFinais_deveLancarException_seDataRecebimentoInicialPosteriorADataFinal() {
        assertThatCode(() -> umFreteFiltrosDataRecebimento(umaDataAtual(), umaDataFinalAnteriorADataInicial())
                .validarDatasIniciaisPosteriorADatasFinais())
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de recebimento não pode ser posterior a data final.");
    }

    @Test
    void validarDatasIniciaisPosteriorADatasFinais_naoDeveLancarException_seDataRecebimentoInicialAnteriorADataFinal() {
        assertThatCode(() -> umFreteFiltrosDataRecebimento(umaDataAtual(), umaDataAtualMais(30))
                .validarDatasIniciaisPosteriorADatasFinais())
                .doesNotThrowAnyException();
    }

    @Test
    void validarDatasIniciaisPosteriorADatasFinais_deveLancarException_seDataCancelamentoInicialPosteriorADataFinal() {
        assertThatCode(() -> umFreteFiltrosDataCancelamento(umaDataAtual(), umaDataFinalAnteriorADataInicial())
                .validarDatasIniciaisPosteriorADatasFinais())
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("A data inicial de cancelamento não pode ser posterior a data final.");
    }

    @Test
    void validarDatasIniciaisPosteriorADatasFinais_naoDeveLancarException_seDataCancelamentoInicialAnteriorADataFinal() {
        assertThatCode(() -> umFreteFiltrosDataCancelamento(umaDataAtual(), umaDataAtualMais(30))
                .validarDatasIniciaisPosteriorADatasFinais())
                .doesNotThrowAnyException();
    }

    @Test
    void toPredicate_deveMontarPredicate_quantoPossuirFiltros() {
        assertEquals(umFreteFiltrosCompleto().toPredicate().build(), umFretePredicate().build());
    }

    @Test
    void toPredicate_naoDeveMontarPredicate_quantoNaoPossuirFiltros() {
        assertEquals(new FreteFiltros().toPredicate().build(), new BooleanBuilder());
    }
}