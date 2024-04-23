package br.com.fiap.motos.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Year;

public record VeiculoRequest(

        @NotNull(message = "Nome é necessário!")
        String nome,
        @NotNull(message = "Preço é necessário!")
        Double preco,
        @NotNull(message = "Modelo é necessário!")
        String modelo,
        @NotNull(message = "Ano é necessário!")
        Year anoDeFabricacao,
        @NotNull(message = "Cor é necessário!")
        String cor,
        @NotNull(message = "Palavra de efeito é necessário!")
        String palavraDeEfeito,
        @NotNull(message = "Cilindradas é necessário!")
        Short cilindradas,
        @NotNull(message = "Nome é necessário!")
        @Valid @NotNull(message = "É necessário informar o tipo")
        AbstractRequest tipo,
        @Valid @NotNull(message = "É necessário informar os dados do fabricante")
        AbstractRequest fabricante



) {
}
