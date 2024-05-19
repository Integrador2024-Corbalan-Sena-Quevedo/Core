package com.mides.core.repository;

import com.mides.core.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICandidatoRepositoy  extends JpaRepository<Candidato, Long> {

    @Query(value = "SELECT * FROM Candidato WHERE CI = ?", nativeQuery = true)
    public Candidato getCandidatoPorCI(@Param("CI") String CI);
}
