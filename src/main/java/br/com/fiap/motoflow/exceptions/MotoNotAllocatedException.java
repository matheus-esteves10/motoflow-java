package br.com.fiap.motoflow.exceptions;

public class MotoNotAllocatedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Moto %s não está alocada em nenhuma posição do pátio.";

    public MotoNotAllocatedException(String placa) {
        super(String.format(DEFAULT_MESSAGE, placa));
    }
}

