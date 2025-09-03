package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.model.enums.Role;

public record OperadorResponse(Long id,
                               String nome,
                               Role role) {

    public OperadorResponse(Operador operador) {
        this(
                operador.getId(),
                operador.getNome(),
                operador.getRole());
    }

}
