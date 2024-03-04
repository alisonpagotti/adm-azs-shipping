package br.com.azship.gestaofretesapi.modulos.frete.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFrete is a Querydsl query type for Frete
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFrete extends EntityPathBase<Frete> {

    private static final long serialVersionUID = -1712680552L;

    public static final QFrete frete = new QFrete("frete");

    public final DateTimePath<java.time.LocalDateTime> dataCadastro = createDateTime("dataCadastro", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> dataCancelamento = createDate("dataCancelamento", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> dataEnvio = createDate("dataEnvio", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> dataRecebimento = createDate("dataRecebimento", java.time.LocalDate.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath motorista = createString("motorista");

    public final StringPath nfe = createString("nfe");

    public final EnumPath<br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao> situacao = createEnum("situacao", br.com.azship.gestaofretesapi.modulos.frete.enums.ESituacao.class);

    public final StringPath transportadora = createString("transportadora");

    public final NumberPath<java.math.BigDecimal> valor = createNumber("valor", java.math.BigDecimal.class);

    public QFrete(String variable) {
        super(Frete.class, forVariable(variable));
    }

    public QFrete(Path<? extends Frete> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFrete(PathMetadata metadata) {
        super(Frete.class, metadata);
    }

}

