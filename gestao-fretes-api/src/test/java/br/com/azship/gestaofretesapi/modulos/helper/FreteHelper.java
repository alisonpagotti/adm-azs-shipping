package br.com.azship.gestaofretesapi.modulos.helper;

import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteFiltros;
import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteRequest;
import br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao;
import br.com.azship.gestaofretesapi.modulos.frete.model.Frete;
import br.com.azship.gestaofretesapi.modulos.frete.predicate.FretePredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FreteHelper {

    public static FreteRequest umFreteRequest() {
        return FreteRequest
                .builder()
                .nfe("000873456")
                .transportadora("TRANSPORTADORA ZERO43")
                .motorista("AGENOR RONEGA")
                .valor(new BigDecimal("232.32"))
                .build();
    }

    public static Frete umFrete() {
        return Frete
                .builder()
                .id(23)
                .dataCadastro(LocalDateTime.of(2024, 3, 1, 13, 31, 0))
                .nfe("000873456")
                .transportadora("TRANSPORTADORA ZERO43")
                .motorista("AGENOR RONEGA")
                .situacao(ESituacao.ENVIADO)
                .dataEnvio(LocalDate.of(2023, 3, 2))
                .valor(new BigDecimal("320.23"))
                .build();
    }

    public static Page<Frete> umaPageFrete() {
        return new PageImpl<>(List.of(umFrete()));
    }

    public static FreteFiltros umFreteFiltrosCompleto() {
        return FreteFiltros
                .builder()
                .dataCadastroInicial(LocalDate.of(2024, 3, 1))
                .dataCadastroFinal(LocalDate.of(2024, 3, 30))
                .nfe("000873456")
                .transportadora("TRANSPORTADORA ZERO43")
                .motorista("AGENOR RONEGA")
                .situacao(ESituacao.RECEBIDO)
                .dataEnvioInicial(LocalDate.of(2024, 3, 1))
                .dataEnvioFinal(LocalDate.of(2024, 3, 30))
                .dataRecebimentoInicial(LocalDate.of(2024, 3, 1))
                .dataRecebimentoFinal(LocalDate.of(2024, 3, 30))
                .dataCancelamentoInicial(LocalDate.of(2024, 3, 1))
                .dataCancelamentoFinal(LocalDate.of(2024, 3, 30))
                .valor(new BigDecimal("400"))
                .build();
    }

    public static FretePredicate umFretePredicate() {
        return new FretePredicate()
                .comDataCadastro(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 30))
                .comNfe("000873456")
                .comTransportadora("TRANSPORTADORA ZERO43")
                .comMotorista("AGENOR RONEGA")
                .comSituacao(ESituacao.RECEBIDO)
                .comDataEnvio(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 30))
                .comDataRecebimento(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 30))
                .comDataCancelamento(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 30))
                .comValor(new BigDecimal("400"));
    }

    public static FreteFiltros umFreteFiltrosDataCadastro(LocalDate dataInicial, LocalDate dataFinal) {
        return FreteFiltros
                .builder()
                .dataCadastroInicial(dataInicial)
                .dataCadastroFinal(dataFinal)
                .build();
    }

    public static FreteFiltros umFreteFiltrosDataEnvio(LocalDate dataInicial, LocalDate dataFinal) {
        return FreteFiltros
                .builder()
                .dataEnvioInicial(dataInicial)
                .dataEnvioFinal(dataFinal)
                .build();
    }

    public static FreteFiltros umFreteFiltrosDataRecebimento(LocalDate dataInicial, LocalDate dataFinal) {
        return FreteFiltros
                .builder()
                .dataRecebimentoInicial(dataInicial)
                .dataRecebimentoFinal(dataFinal)
                .build();
    }

    public static FreteFiltros umFreteFiltrosDataCancelamento(LocalDate dataInicial, LocalDate dataFinal) {
        return FreteFiltros
                .builder()
                .dataCancelamentoInicial(dataInicial)
                .dataCancelamentoFinal(dataFinal)
                .build();
    }

    public static LocalDate umaDataAtual() {
        return LocalDate.now();
    }

    public static LocalDate umaDataAtualMais(int dias) {
        return umaDataAtual().plusDays(dias);
    }

    public static LocalDate umaDataFinalAnteriorADataInicial() {
        return umaDataAtual().minusDays(1);
    }

    public static LocalDate umaData() {
        return LocalDate.of(2024, 3, 1);
    }
}
