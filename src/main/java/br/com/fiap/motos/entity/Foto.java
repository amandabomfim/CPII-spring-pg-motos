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
@Table(name = "TB_FOTO")
public class Foto {
    @Id
    @SequenceGenerator(name = "SQ_FOTO", sequenceName = "SQ_FOTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FOTO")
    @Column(name = "ID_FOTO")
    private Long id;

    @Column(name = "NM_FOTO")
    private String src;
}
