package br.com.azship.gestaofretesapi.modulos.frete.predicate;

import br.com.azship.gestaofretesapi.config.PredicateBase;
import br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao;
import io.micrometer.common.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static br.com.azship.gestaofretesapi.modulos.frete.model.QFrete.frete;

public class FretePredicate extends PredicateBase {

    public FretePredicate comDataCadastro(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            builder.and(frete.dataCadastro.between(dataInicial.atStartOfDay(), dataFinal.atTime(LocalTime.MAX)));
        }

        return this;
    }

    public FretePredicate comNfe(String nfe) {
        if (StringUtils.isNotBlank(nfe)) {
            builder.and(frete.nfe.eq(nfe));
        }

        return this;
    }

    public FretePredicate comTransportadora(String transportadora) {
        if (StringUtils.isNotBlank(transportadora)) {
            builder.and(frete.transportadora.equalsIgnoreCase(transportadora));
        }

        return this;
    }

    public FretePredicate comMotorista(String motorista) {
        if (StringUtils.isNotBlank(motorista)) {
            builder.and(frete.motorista.equalsIgnoreCase(motorista));
        }

        return this;
    }

    public FretePredicate comSituacao(ESituacao situacao) {
        if (situacao != null) {
            builder.and(frete.situacao.eq(situacao));
        }

        return this;
    }

    public FretePredicate comDataEnvio(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            builder.and(frete.dataEnvio.between(dataInicial, dataFinal));
        }

        return this;
    }

    public FretePredicate comDataRecebimento(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            builder.and(frete.dataRecebimento.between(dataInicial, dataFinal));
        }

        return this;
    }

    public FretePredicate comDataCancelamento(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            builder.and(frete.dataCancelamento.between(dataInicial, dataFinal));
        }

        return this;
    }

    public FretePredicate comValor(BigDecimal valor) {
        if (valor != null) {
            builder.and(frete.valor.eq(valor));
        }

        return this;
    }
}
