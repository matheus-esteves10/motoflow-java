package br.com.fiap.motoflow.exceptions;

public class PatioNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Pátio com id %s não encontrado.";

    private Long patioId;

    public PatioNotFoundException(Long patioId) {
        super(String.format(MESSAGE, patioId));
        this.patioId = patioId;
    }
}
