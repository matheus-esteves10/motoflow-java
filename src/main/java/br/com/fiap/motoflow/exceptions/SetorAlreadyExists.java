package br.com.fiap.motoflow.exceptions;

public class SetorAlreadyExists extends RuntimeException {

    private static String DEFAULT_MESSAGE = "Setor %s já existe no pátio %s.";

    private String setor;
    private String apelidoPatio;

    public SetorAlreadyExists(String setor, String apelidoPatio) {
        super(String.format(DEFAULT_MESSAGE, setor, apelidoPatio));
    }
}
