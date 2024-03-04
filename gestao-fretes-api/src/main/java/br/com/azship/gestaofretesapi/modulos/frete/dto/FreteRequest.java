package br.com.azship.gestaofretesapi.modulos.frete.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreteRequest {

    @NotBlank
    private String nfe;
    @NotBlank
    private String transportadora;
    private String motorista;
    @NotNull
    private BigDecimal valor;
}
