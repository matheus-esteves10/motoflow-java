package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.Endereco;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class PatioQuantityResponse {
    private Long id;
    private String apelido;
    private Integer area;
    private Integer capacidadeMax;
    private Endereco endereco;
    private Set<String> setoresComPosicoesDisponiveis;
    private Set<String> setoresCheios;

}
