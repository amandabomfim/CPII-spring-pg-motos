package br.com.fiap.motos.dto.request;

import jakarta.validation.constraints.NotNull;

public record FabricanteRequest(
        @NotNull(message = "Nome é necessário!")
        String nome,
        @NotNull(message = "Nome fantasia é necessário!")
        String nomeFantasia
) {
}
