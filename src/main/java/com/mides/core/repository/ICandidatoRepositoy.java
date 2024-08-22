package com.mides.core.repository;

import com.mides.core.model.Candidato;
import com.mides.core.model.QueryFilterEmpleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICandidatoRepositoy  extends JpaRepository<Candidato, Long> {

    @Query(value = "SELECT * FROM candidato WHERE documento = ?", nativeQuery = true)
    public Candidato getCandidatoPorCI(@Param("documento") String CI);

    @Query(value = "SELECT DISTINCT c.* FROM candidato c " +
            "INNER JOIN cliente cli ON c.id = cli.id " +
            "INNER JOIN datos_adicionales_candidato dac ON  dac.candidato_id = c.id " +
            "INNER JOIN habilidad h ON h.candidato_id = c.id " +
            "INNER JOIN dirreccion d ON d.cliente_id = cli.id " +
            "INNER JOIN educacion e ON e.candidato_id = c.id " +
            "LEFT JOIN candidato_idioma ci ON ci.candidato_id = c.id " +
            "LEFT JOIN idioma i ON ci.idioma_id = i.id " +
            "INNER JOIN disponibilidad_horaria dh ON dh.candidato_id = c.id " +
            "WHERE (d.departamento LIKE CONCAT('%', :#{#filter.departamento}, '%') OR :#{#filter.departamento} = '') " +
            "AND (:#{#filter.formacionAcademica} IS NULL OR e.nivel_numerico >= :#{#filter.formacionAcademica.ordinal()}) " +
            "AND (:#{#filter.ingles} = '' OR i.nombre LIKE 'Ingl%') " +
            "AND (:#{#filter.portugues} = '' OR i.nombre LIKE 'Portu%' ) " +
            "AND (DATE_PART('year', AGE(c.fecha_de_nacimiento )) >= :#{#filter.edadMinima}) " +
            "AND (DATE_PART('year', AGE(c.fecha_de_nacimiento )) <= :#{#filter.edadMaxima}) " +
            "AND (:#{#filter.cargaHorariaSemanal} IS NULL OR :#{#filter.cargaHorariaSemanal} = '' " +
            "OR ABS(CAST(TRIM(SPLIT_PART(dh.horas_semanales, ' ', 1)) AS INTEGER) - CAST(:#{#filter.cargaHorariaSemanal} AS INTEGER)) <= 7 " +
            "OR CAST(TRIM(SPLIT_PART(dh.horas_semanales, ' ', 1)) AS INTEGER) > CAST(:#{#filter.cargaHorariaSemanal} AS INTEGER))", nativeQuery = true)
    List<Candidato> findCandidatosByFilter(@Param("filter") QueryFilterEmpleo filter);

    @Modifying
    @Transactional
    @Query(value = "UPDATE candidato SET csv_base64 = null WHERE id = ?", nativeQuery = true)
    void deleteCv(@Param("id") Long id);

}
