package br.com.fiap.motos.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CaracteristicaRequest(

        @NotNull(message = "Nome é necessário!")
        String nome,
        @NotNull(message = "Descricao é necessária!")
        String descricao,
        @Valid @NotNull(message = "É necessário informar os dados do veiculo")
                AbstractRequest veiculo
        ) {
}
