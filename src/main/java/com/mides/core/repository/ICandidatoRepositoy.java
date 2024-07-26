package com.mides.core.repository;

import com.mides.core.model.Candidato;
import com.mides.core.model.QueryFilterEmpleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICandidatoRepositoy  extends JpaRepository<Candidato, Long> {

    @Query(value = "SELECT * FROM Candidato WHERE documento = ?", nativeQuery = true)
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
            "WHERE (:#{#filter.departamento} = '' OR d.departamento LIKE %:#{#filter.departamento}%) " +
            "AND (:#{#filter.formacionAcademica} = '' OR e.nivel_educativo LIKE %:#{#filter.formacionAcademica}%) " +
            "AND (:#{#filter.ingles} = '' OR i.nombre IS NOT NULL ) " +
            "AND (:#{#filter.portugues} = '' OR i.nombre IS NOT NULL ) " +
            "AND (:#{#filter.edadMinima} IS NULL OR DATE_PART('year', AGE(c.fecha_de_nacimiento )) >= :#{#filter.edadMinima}) " +
            "AND (:#{#filter.edadMaxima} IS NULL OR DATE_PART('year', AGE(c.fecha_de_nacimiento )) <= :#{#filter.edadMaxima}) " +
            "AND (:#{#filter.cargaHorariaSemanal} IS NULL OR :#{#filter.cargaHorariaSemanal} = '' OR ABS(CAST(SPLIT_PART(dh.horas_semanales, ' ', 1) AS INTEGER) - CAST(:#{#filter.cargaHorariaSemanal} AS INTEGER)) <= 7)" , nativeQuery = true)
    List<Candidato> findCandidatosByFilter(@Param("filter") QueryFilterEmpleo filter);

}
