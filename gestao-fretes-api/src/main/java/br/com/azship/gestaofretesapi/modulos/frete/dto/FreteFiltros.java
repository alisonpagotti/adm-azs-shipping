package br.com.azship.gestaofretesapi.modulos.frete.dto;

import br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao;
import br.com.azship.gestaofretesapi.modulos.frete.predicate.FretePredicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.*;
import static br.com.azship.gestaofretesapi.modulos.comum.util.DateUtil.validarDataInicialPosteriorADataFinal;
import static br.com.azship.gestaofretesapi.modulos.comum.util.DateUtil.validarPeriodoMaximo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreteFiltros {

    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dataCadastroInicial;
    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dataCadastroFinal;
    private String nfe;
    private String transportadora;
    private String motorista;
    private ESituacao situacao;
    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dataEnvioInicial;
    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dataEnvioFinal;
    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dataRecebimentoInicial;
    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dataRecebimentoFinal;
    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dataCancelamentoInicial;
    @DateTimeFormat(pattern = PATTERN_DATE)
    private LocalDate dataCancelamentoFinal;
    private BigDecimal valor;

    public void validarPeriodoMaiorDoQue60Dias() {
        validarPeriodoMaximo(dataCadastroInicial, dataCadastroFinal, CADASTRO, SESSENTA);
        validarPeriodoMaximo(dataEnvioInicial, dataEnvioFinal, ENVIO, SESSENTA);
        validarPeriodoMaximo(dataRecebimentoInicial, dataRecebimentoFinal, RECEBIMENTO, SESSENTA);
        validarPeriodoMaximo(dataCancelamentoInicial, dataCancelamentoFinal, CANCELAMENTO, SESSENTA);
    }

    public void validarDatasIniciaisPosteriorADatasFinais() {
        validarDataInicialPosteriorADataFinal(dataCadastroInicial, dataCadastroFinal, CADASTRO);
        validarDataInicialPosteriorADataFinal(dataEnvioInicial, dataEnvioFinal, ENVIO);
        validarDataInicialPosteriorADataFinal(dataRecebimentoInicial, dataRecebimentoFinal, RECEBIMENTO);
        validarDataInicialPosteriorADataFinal(dataCancelamentoInicial, dataCancelamentoFinal, CANCELAMENTO);
    }

    public FretePredicate toPredicate() {
        return new FretePredicate()
                .comDataCadastro(dataCadastroInicial, dataCadastroFinal)
                .comNfe(nfe)
                .comTransportadora(transportadora)
                .comMotorista(motorista)
                .comSituacao(situacao)
                .comDataEnvio(dataEnvioInicial, dataEnvioFinal)
                .comDataRecebimento(dataRecebimentoInicial, dataRecebimentoFinal)
                .comDataCancelamento(dataCancelamentoInicial, dataCancelamentoFinal)
                .comValor(valor);
    }
}
