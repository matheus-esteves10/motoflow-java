package br.com.fiap.motoflow.dto.responses;

public record SetoresDto(String setor,
                         int capacidadeSetor,
                         int posicoesOcupadas,
                         int vagasDisponiveis) {
}
