package br.com.azship.gestaofretesapi.modulos.frete.model;

import br.com.azship.gestaofretesapi.modulos.frete.dto.FreteRequest;
import br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FRETES")
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_FRETES")
    @SequenceGenerator(name = "SEQ_FRETES", sequenceName = "SEQ_FRETES", allocationSize = 1)
    private Integer id;

    @Column(name = "DATA_CADASTRO", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "NFE", length = 9, nullable = false)
    private String nfe;

    @Column(name = "TRANSPORTADORA", nullable = false)
    private String transportadora;

    @Column(name = "MOTORISTA")
    private String motorista;

    @Column(name = "SITUACAO", nullable = false)
    @Enumerated(EnumType.STRING)
    private ESituacao situacao;

    @Column(name = "DATA_ENVIO")
    private LocalDate dataEnvio;

    @Column(name = "DATA_RECEBIMENTO")
    private LocalDate dataRecebimento;

    @Column(name = "DATA_CANCELAMENTO")
    private LocalDate dataCancelamento;

    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;

    public static Frete of(FreteRequest request) {
        var frete = new Frete();
        BeanUtils.copyProperties(request, frete);
        frete.setSituacao(AGUARDANDO_ENVIO);
        frete.setDataCadastro(LocalDateTime.now());

        return frete;
    }

    public static Frete of(Frete frete, FreteRequest request) {
        BeanUtils.copyProperties(request, frete);

        return frete;
    }

    public void atualizarSituacao(ESituacao situacaoAtualizada, LocalDate data) {
        atualizarSituacaoEnviado(situacaoAtualizada, data);
        atualizarSituacaoRecebido(situacaoAtualizada, data);
        atualizarSituacaoCancelado(situacaoAtualizada, data);
    }

    private void atualizarSituacaoEnviado(ESituacao situacaoAtualizada, LocalDate data) {
        if (situacaoAtualizada == ENVIADO) {
            situacao = ENVIADO;
            dataEnvio = data;
        }
    }

    private void atualizarSituacaoRecebido(ESituacao situacaoAtualizada, LocalDate data) {
        if (situacaoAtualizada == RECEBIDO) {
            situacao = RECEBIDO;
            dataRecebimento = data;
        }
    }

    private void atualizarSituacaoCancelado(ESituacao situacaoAtualizada, LocalDate data) {
        if (situacaoAtualizada == CANCELADO) {
            situacao = CANCELADO;
            dataCancelamento = data;
        }
    }
}
