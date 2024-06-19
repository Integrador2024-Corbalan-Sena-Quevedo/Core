package com.mides.core.repository;

import com.mides.core.model.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEncuestaRepository extends JpaRepository<Encuesta, Long> {
}
