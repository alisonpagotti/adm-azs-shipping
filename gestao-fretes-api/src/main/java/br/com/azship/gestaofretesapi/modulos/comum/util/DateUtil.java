package br.com.azship.gestaofretesapi.modulos.comum.util;

import br.com.azship.gestaofretesapi.modulos.comum.exception.ValidacaoException;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateUtil {

    public static void validarPeriodoMaximo(LocalDate dataInicial,
                                            LocalDate dataFinal,
                                            String tipoPeriodo,
                                            Integer qtdMaximaDias) {
        if (hasDatas(dataInicial, dataFinal) && ChronoUnit.DAYS.between(dataInicial, dataFinal) > qtdMaximaDias) {
            throw new ValidacaoException(String.format("O período da data de %s não deve ser superior a %d dias.",
                    tipoPeriodo,
                    qtdMaximaDias));
        }
    }

    public static void validarDataInicialPosteriorADataFinal(LocalDate dataInicial, LocalDate dataFinal, String tipoData) {
        if (hasDatas(dataInicial, dataFinal) && dataInicial.isAfter(dataFinal)) {
            throw new ValidacaoException(String.format("A data inicial de %s não pode ser posterior a data final.", tipoData));
        }
    }

    public static void validarDataAnteriorADataCadastro(LocalDate data, LocalDate dataCadastro) {
        if (hasDatas(data, dataCadastro) && data.isBefore(dataCadastro)) {
            throw new ValidacaoException("A data não pode ser anterior a data de cadastro.");
        }
    }

    public static void validarDataPosteriorAAtual(LocalDate data) {
        if (data != null && data.isAfter(LocalDate.now())) {
            throw new ValidacaoException("A data não pode ser posterior a data atual.");
        }
    }

    private static boolean hasDatas(LocalDate dataUm, LocalDate dataDois) {
        return dataUm != null && dataDois != null;
    }
}
