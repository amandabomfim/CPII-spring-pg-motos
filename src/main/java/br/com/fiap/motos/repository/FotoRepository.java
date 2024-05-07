package br.com.fiap.motos.repository;

import br.com.fiap.motos.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {
}
