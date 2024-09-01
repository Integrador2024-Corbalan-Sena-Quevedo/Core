package com.mides.core.repository;

import com.mides.core.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IIdiomaRepository extends JpaRepository<Idioma, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO idioma (nombre) VALUES ('Ingles'), ('Portugues')", nativeQuery = true)
    void precargaIdiomas();

    @Query(value = "SELECT nombre FROM idioma WHERE nombre=?", nativeQuery = true)
    Idioma getIdiomaPorNombre(@Param(value = "nombre") String nombre);
}
