package br.com.fiap.motos.dto.response;

import br.com.fiap.motos.entity.Foto;
import lombok.Builder;

@Builder
public record FabricanteResponse(
        Long id,
        String nome,
        String nomeFantasia,
        Foto logo
) {
}
