package br.com.fiap.motoflow.exceptions;

public class EmptyPositionException extends RuntimeException {

    private static final String DEFAULT_MESSAGE="Não há motos no setor %s";

    public EmptyPositionException(String posicaoHorizontal) {
        super(String.format(DEFAULT_MESSAGE, posicaoHorizontal));
    }
}
