package br.com.fiap.motoflow.exceptions;

public class PosicaoNotFoundException extends RuntimeException {
    public PosicaoNotFoundException(String message) {
        super(message);
    }
}
