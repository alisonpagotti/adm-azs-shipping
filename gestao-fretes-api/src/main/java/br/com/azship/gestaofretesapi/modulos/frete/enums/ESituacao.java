package br.com.azship.gestaofretesapi.modulos.frete.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ESituacao {

    ENVIADO("ENVIADO", false),
    RECEBIDO("RECEBIDO", false),
    CANCELADO("CANCELADO", false),
    AGUARDANDO_ENVIO("AGUARDANDO ENVIO", true);

    private final String descricao;
    private final boolean editavel;

    public static boolean hasSituacaoEditavel(ESituacao situacao) {
        return Arrays.stream(ESituacao.values())
                .filter(ESituacao::isEditavel)
                .anyMatch(situacao::equals);
    }
}
