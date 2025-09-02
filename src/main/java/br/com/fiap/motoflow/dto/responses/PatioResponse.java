package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.Patio;

public record PatioResponse(Long id,
                            String apelido,
                            Integer capacidade,
                            Integer area,
                            String logradouro,
                            String cidade,
                            String estado,
                            String cep,
                            int numero) {
    public PatioResponse(Patio patio) {
        this(
            patio.getId(),
            patio.getApelido(),
            patio.getCapacidade(),
            patio.getArea(),
            patio.getEndereco().getLogradouro(),
            patio.getEndereco().getCidade(),
            patio.getEndereco().getSiglaEstado(),
            patio.getEndereco().getCep(),
            patio.getEndereco().getNumero()
        );
    }
}

