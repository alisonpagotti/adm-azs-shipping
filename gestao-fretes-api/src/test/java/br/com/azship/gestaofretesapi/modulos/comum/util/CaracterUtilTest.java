package br.com.azship.gestaofretesapi.modulos.comum.util;

import br.com.azship.gestaofretesapi.modulos.comum.exception.ValidacaoException;
import org.junit.jupiter.api.Test;

import static br.com.azship.gestaofretesapi.modulos.comum.util.CaracterUtil.validarNfe;
import static org.assertj.core.api.Assertions.assertThatCode;

class CaracterUtilTest {

    @Test
    void validarNfe_deveLancarException_seNfeNaoPossuirDigitos() {
        assertThatCode(() -> validarNfe("XXXXXXXXX"))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("NF-e deve possuir apenas digitos.");
    }

    @Test
    void validarNfe_deveLancarException_seNfePossuirMaisDeNoveDigitos() {
        assertThatCode(() -> validarNfe("00096754324"))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("NF-e deve possuir até nove digitos.");
    }

    @Test
    void validarNfe_naoDeveLancarException_seNfePossuirAteNoveDigitos() {
        assertThatCode(() -> validarNfe("009786543"))
                .doesNotThrowAnyException();
    }
}