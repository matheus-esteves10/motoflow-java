package br.com.fiap.motoflow.dto.web;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SetorDashboardDto {
    private String nomeSetor;
    private int capacidadeTotal;
    private int posicoesOcupadas;
    private int vagasLivres;
}
