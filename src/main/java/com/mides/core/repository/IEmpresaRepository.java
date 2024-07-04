package com.mides.core.repository;

import com.mides.core.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpresaRepository extends JpaRepository<Empresa, Long> {
}
