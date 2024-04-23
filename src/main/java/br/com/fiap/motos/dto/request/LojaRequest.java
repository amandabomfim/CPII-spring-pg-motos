package br.com.fiap.motos.dto.request;

import jakarta.validation.constraints.NotNull;

public record LojaRequest(
        @NotNull(message = "Nome é necessário!")
        String nome
) {
}
