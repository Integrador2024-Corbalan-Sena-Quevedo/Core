package com.mides.core.repository;

import com.mides.core.model.Prestacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrestacionRepository extends JpaRepository<Prestacion, Long> {
}
