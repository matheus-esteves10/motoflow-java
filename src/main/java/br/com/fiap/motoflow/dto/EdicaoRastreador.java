package br.com.fiap.motoflow.dto;

import jakarta.validation.constraints.NotBlank;

public record EdicaoRastreador(@NotBlank String codRastreador) {
}
