package br.com.fiap.motoflow.exceptions;

public class SetorComMotosException extends RuntimeException {

    private final static String MESSAGE = "Não é possível deletar o setor pois existem motos alocadas nele. Realoque as motos e tente novamente";

    public SetorComMotosException() {
        super(MESSAGE);
    }
}
