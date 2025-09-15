package br.com.fiap.motoflow.exceptions;

public class SetorNaoExisteException extends RuntimeException {

    private static String DEFAULT_MESSAGE = "O Setor informado (Setor %s) não existe no pátio.";

    private String setor;

    public SetorNaoExisteException(String setor) {
        super(String.format(DEFAULT_MESSAGE, setor));
        this.setor = setor;
    }
}
