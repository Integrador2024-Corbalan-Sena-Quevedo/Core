package com.mides.core.repository;

import com.mides.core.model.AuditoriaEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAuditoriaEmpresaRepository extends JpaRepository<AuditoriaEmpresa, Long> {
    @Query("SELECT a FROM AuditoriaEmpresa a JOIN FETCH a.empresa")
    List<AuditoriaEmpresa> findAllWithEmpresa();
}
