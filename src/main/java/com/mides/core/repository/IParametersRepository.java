package com.mides.core.repository;

import com.mides.core.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface IParametersRepository extends JpaRepository<Parameter, String> {
    @Query(value = "SELECT p.value FROM parameters p WHERE p.id = ?", nativeQuery = true)
    String getParameterById(String nombre);

    @Modifying
    @Query(value = "UPDATE parameters SET value = ?2 WHERE id = ?1", nativeQuery = true)
    void updateParameterById(String id, String value);

}
