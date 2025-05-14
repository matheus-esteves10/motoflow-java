package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.Operador;

public record OperadorResponse(Long id,
                               String nome,
                               Long patio) {

    public OperadorResponse(Operador operador) {
        this(
                operador.getId(),
                operador.getNome(),
                operador.getPatio().getId());
    }

}
