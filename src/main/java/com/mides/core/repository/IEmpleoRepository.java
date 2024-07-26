package com.mides.core.repository;

import com.mides.core.model.Candidato;
import com.mides.core.model.Empleo;
import com.mides.core.model.QueryFilterEmpleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEmpleoRepository extends JpaRepository<Empleo, Long> {
}
