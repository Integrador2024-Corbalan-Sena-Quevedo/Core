package com.mides.core.repository;

import com.mides.core.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query(value = "SELECT * FROM empresa WHERE rut = ?", nativeQuery = true)
    List<Empresa> getEmpresaByRut(String rut);
}
