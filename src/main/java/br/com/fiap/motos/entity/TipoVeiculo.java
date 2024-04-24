package br.com.fiap.motos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_TIPO_VEICULO", uniqueConstraints = {
        @UniqueConstraint(name = "UK_NOME", columnNames = "NM_VEICULO")
})
public class TipoVeiculo {
    @Id
    @SequenceGenerator(name = "SQ_VEICULO", sequenceName = "SQ_VEICULO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VEICULO")
    @Column(name = "ID_TIPO_VEICULO")
    private Long id;

    @Column(name = "NM_VEICULO")
    private String nome;
}
