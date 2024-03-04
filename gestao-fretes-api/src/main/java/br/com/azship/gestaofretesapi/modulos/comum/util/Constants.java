package br.com.azship.gestaofretesapi.modulos.comum.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final int ZERO = 0;
    public static final int NOVE = 9;
    public static final int DEZ = 10;
    public static final int SESSENTA = 60;
    public static final String VAZIO = "";
    public static final String ENVIO = "envio";
    public static final String EDITADO = "editado";
    public static final String CADASTRO = "cadastro";
    public static final String EXCLUIDO = "excluído";
    public static final String REGEX_NAO_DIGITOS = "[\\D]";
    public static final String RECEBIMENTO = "recebimento";
    public static final String PATTERN_DATE = "dd/MM/yyyy";
    public static final String CANCELAMENTO = "cancelamento";
    public static final String PATTERN_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
    public static final String EX_FRETE_NAO_PODE_SER = "Frete não pode ser %s.";
    public static final String EX_FRETE_NAO_CADASTRADO = "Frete não cadastrado.";
    public static final String EX_SITUACAO_NAO_ATUALIZADA = "Situação não pode ser atualizada.";
    public static final String EX_FRETE_JA_CADASTRADO_PARA_NFE = "Frete já cadastrado para esta NF-e.";
    public static final String EX_ATUALIZAR_SITUACAO_RECEBIDO = "Frete deve estar enviado para alterar para recebido.";
    public static final String EX_ATUALIZAR_SITUACAO_ENVIADO = "Frete deve estar aguardando envio para alterar para enviado.";
    public static final String EX_ATUALIZAR_SITUACAO_CANCELADO = "Frete deve estar aguardando envio para alterar para cancelado.";
}
