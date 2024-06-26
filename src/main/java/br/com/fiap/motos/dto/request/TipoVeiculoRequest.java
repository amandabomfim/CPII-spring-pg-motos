package br.com.fiap.motos.dto.request;

import jakarta.validation.constraints.NotNull;

public record TipoVeiculoRequest(
        @NotNull(message = "Nome é necessário!")
        String nome
) {
}
