package com.mides.core.repository;

import com.mides.core.model.Candidato;
import com.mides.core.model.Habilidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHabilidadRepository extends JpaRepository<Habilidad, Long> {

    //void getHabilidadesDeCandidato(Candidato candidato)
}
