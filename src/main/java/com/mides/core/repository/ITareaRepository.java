package com.mides.core.repository;

import com.mides.core.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITareaRepository extends JpaRepository<Tarea, Long> {
}
