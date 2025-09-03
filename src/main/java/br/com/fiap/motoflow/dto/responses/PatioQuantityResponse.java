package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.Endereco;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PatioQuantityResponse {
    private Long id;
    private String apelido;
    private Integer area;
    private Integer capacidadeMax;
    private int posicoesDisponiveis;
    private int posicoesOcupadas;
    private Endereco endereco;


}
