package br.com.fiap.motos.dto.request;

import jakarta.validation.constraints.NotNull;

public record AcessorioRequest(
        @NotNull(message = "Nome é necessário!")
        String nome,
        @NotNull(message = "Preço é necessário!")
        Double preco
) {
}
