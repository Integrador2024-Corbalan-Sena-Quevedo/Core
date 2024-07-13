package com.mides.core.repository;

import com.mides.core.model.Empleo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleoRepository extends JpaRepository<Empleo, Long> {
}
