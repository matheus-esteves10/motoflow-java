package br.com.fiap.motoflow.dto;

import br.com.fiap.motoflow.model.enums.StatusMoto;
import jakarta.validation.constraints.NotNull;

public record EditarStatusMotoDto(
        @NotNull
        StatusMoto status
) {
}
