package com.mides.core.repository;

import com.mides.core.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFiltroCandidatosRepository extends JpaRepository<Candidato, Long>, JpaSpecificationExecutor<Candidato> {
}
