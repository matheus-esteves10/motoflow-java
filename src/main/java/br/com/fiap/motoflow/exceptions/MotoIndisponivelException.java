package br.com.fiap.motoflow.exceptions;

public class MotoIndisponivelException extends RuntimeException {

    private static final String MESSAGE = "Moto com placa %s está indisponível para movimentações.";

    private  String placa;

    public MotoIndisponivelException(String placa) {
        super(String.format(MESSAGE, placa));
        this.placa = placa;
    }
}
