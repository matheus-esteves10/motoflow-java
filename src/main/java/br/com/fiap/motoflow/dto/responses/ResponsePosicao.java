package br.com.fiap.motoflow.dto.responses;

public record ResponsePosicao(String placaMoto,
                              String posicaoHorizontal,
                              int posicaoVertical,
                              Long idPatio) {

}
