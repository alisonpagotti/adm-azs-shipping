package br.com.azship.gestaofretesapi.modulos.frete.dto;

import br.com.azship.gestaofretesapi.modulos.frete.model.Frete;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.PATTERN_DATE;
import static br.com.azship.gestaofretesapi.modulos.comum.util.Constants.PATTERN_DATE_TIME;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreteResponse {

    private Integer id;
    @JsonFormat(pattern = PATTERN_DATE_TIME)
    private LocalDateTime dataCadastro;
    private String nfe;
    private String transportadora;
    private String motorista;
    private String situacao;
    @JsonFormat(pattern = PATTERN_DATE)
    private LocalDate dataEnvio;
    @JsonFormat(pattern = PATTERN_DATE)
    private LocalDate dataRecebimento;
    @JsonFormat(pattern = PATTERN_DATE)
    private LocalDate dataCancelamento;
    private BigDecimal valor;

    public static FreteResponse of(Frete frete) {
        var response = new FreteResponse();
        BeanUtils.copyProperties(frete, response);
        response.setSituacao(frete.getSituacao().getDescricao());

        return response;
    }
}
