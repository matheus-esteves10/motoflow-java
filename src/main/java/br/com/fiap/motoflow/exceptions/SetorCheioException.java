package br.com.fiap.motoflow.exceptions;

public class SetorCheioException extends RuntimeException {

    private static String DEFAULT_MESSAGE = "Setor %s está cheio, não é possível alocar mais motos neste setor.";

    private String setor;

    public SetorCheioException(String setor) {
        super(String.format(DEFAULT_MESSAGE, setor));
    }
}
