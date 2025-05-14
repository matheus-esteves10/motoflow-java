package br.com.fiap.motoflow.exceptions;

public class MotoNotFoundException extends RuntimeException {
    public MotoNotFoundException(String message) {
        super(message);
    }
}
