package br.com.fiap.motoflow.dto.web;

import br.com.fiap.motoflow.model.Endereco;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PatioDashboardResponse {
    private Long id;
    private String apelido;
    private Integer area;
    private Integer capacidadeMax;
    private Endereco endereco;
    private List<SetorDashboardDto> setores;
    private int totalMotosOcupadas;
    private int totalVagasLivres;
}
