package br.com.azship.gestaofretesapi.modulos.comum.util;

import br.com.azship.gestaofretesapi.modulos.comum.exception.ValidacaoException;
import io.micrometer.common.util.StringUtils;
import lombok.experimental.UtilityClass;

import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.*;

@UtilityClass
public class CaracterUtil {

    public static void validarNfe(String str) {
        var nfe = str.replaceAll(REGEX_NAO_DIGITOS, VAZIO);

        if (StringUtils.isBlank(nfe) || nfe.length() > NOVE) {
            throw new ValidacaoException(StringUtils.isBlank(nfe)
                    ? "NF-e deve possuir apenas digitos."
                    : "NF-e deve possuir até nove digitos.");
        }
    }
}
