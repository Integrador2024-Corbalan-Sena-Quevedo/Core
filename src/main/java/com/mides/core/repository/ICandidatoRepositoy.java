package com.mides.core.repository;

import com.mides.core.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICandidatoRepositoy  extends JpaRepository<Candidato, Long> {
}
