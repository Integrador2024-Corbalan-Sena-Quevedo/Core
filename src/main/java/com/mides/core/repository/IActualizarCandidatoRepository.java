package com.mides.core.repository;

import com.mides.core.model.Apoyo;
import com.mides.core.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActualizarCandidatoRepository extends JpaRepository<Candidato, Long> {
}
