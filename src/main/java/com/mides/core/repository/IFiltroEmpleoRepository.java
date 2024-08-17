package com.mides.core.repository;

import com.mides.core.model.Empleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFiltroEmpleoRepository extends JpaRepository<Empleo, Long>, JpaSpecificationExecutor<Empleo> {
}
