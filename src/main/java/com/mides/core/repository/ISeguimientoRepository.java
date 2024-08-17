package com.mides.core.repository;

import com.mides.core.model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ISeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    @Query(value = "SELECT * FROM seguimiento WHERE empleo_id =? and documento_empleado =?", nativeQuery = true)
    Seguimiento findByEmpleoIdAndCandidatoId(Long empleoId, String documento);
}
