package com.mides.core.repository;

import com.mides.core.model.EncuestaCandidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEncuestaRepository extends JpaRepository<EncuestaCandidato, Long> {
}
