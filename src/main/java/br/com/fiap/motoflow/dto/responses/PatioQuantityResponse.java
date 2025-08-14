package br.com.fiap.motoflow.dto.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PatioQuantityResponse {

    private Integer capacidadeMax;

    private int posicoesDisponiveis;

    private int quantidadeAlugadas;



}
