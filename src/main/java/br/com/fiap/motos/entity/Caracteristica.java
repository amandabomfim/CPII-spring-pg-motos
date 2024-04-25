package br.com.fiap.motos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_CARACTERISTICA", uniqueConstraints = {
        @UniqueConstraint(name = "UK_NOME", columnNames = "NM_CARACTERISTICA")
})
public class Caracteristica {

    @Id
    @SequenceGenerator(name = "SQ_CARACTERISTICA", sequenceName = "SQ_CARACTERISTICA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CARACTERISTICA")
    @Column(name = "ID_CARACTERISTICA")
    private Long id;

    //30 digitos
    @Column(name = "NM_CARACTERISTICA")
    private String nome;

    //20 digitos
    @Size(min = 20)
    @Column(name = "DS_CARACTERISTICA")
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "ID_VEICULO",
            referencedColumnName = "ID_VEICULO",
            foreignKey = @ForeignKey(
                    name = "FK_CARACTERISTICA_VEICULO"
            )
    )
    private Veiculo veiculo;


}
