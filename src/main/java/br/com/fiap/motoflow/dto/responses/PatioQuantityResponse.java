package br.com.fiap.motoflow.dto.responses;

import lombok.Builder;

@Builder
public class PatioQuantityResponse {

    private Integer capacidadeMax;

    private int posicoesDisponiveis;

    private int quantidadeAlugadas;



}
