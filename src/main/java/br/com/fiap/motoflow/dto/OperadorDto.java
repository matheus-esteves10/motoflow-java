package br.com.fiap.motoflow.dto;

import br.com.fiap.motoflow.model.Patio;

public record OperadorDto(String nome,
                          String senha,
                          Patio patio) {
}
