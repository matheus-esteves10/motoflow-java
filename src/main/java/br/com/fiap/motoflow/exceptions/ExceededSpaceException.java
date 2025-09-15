package br.com.fiap.motoflow.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExceededSpaceException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Atualmente o patio %s conta com %s posições, com o seu insert indo para %s posições. A capacidade máxima desse pátio é de: %d motos";

    private final String apelidoPatio;
    private final int posicoesAtuais;
    private final int indoPara;
    private final int capacidade;

    public ExceededSpaceException(String apelidoPatio, int posicoesAtuais, int indoPara, int capacidade) {
        super(String.format(DEFAULT_MESSAGE, apelidoPatio, posicoesAtuais, indoPara, capacidade));
        this.apelidoPatio = apelidoPatio;
        this.posicoesAtuais = posicoesAtuais;
        this.indoPara = indoPara;
        this.capacidade = capacidade;
    }
}
