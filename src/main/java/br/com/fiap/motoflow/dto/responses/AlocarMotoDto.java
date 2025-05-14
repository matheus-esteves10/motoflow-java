package br.com.fiap.motoflow.dto.responses;

public record AlocarMotoDto(
        String placa,
        String posicaoHorizontal,
        int posicaoVertical
) {}
