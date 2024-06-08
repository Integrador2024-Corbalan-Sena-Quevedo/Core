package com.mides.core.repository;

import com.mides.core.model.Institucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IInsititucionRepository extends JpaRepository<Institucion, Long> {

    @Query(value = "SELECT * FROM Institucion", nativeQuery = true)
    public List<Institucion> getInstituciones();
}
