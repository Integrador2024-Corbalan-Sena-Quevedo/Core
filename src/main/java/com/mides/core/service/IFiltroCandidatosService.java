package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Cliente;
import com.mides.core.specification.FiltroCandidato;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IFiltroCandidatosService {
    Map<Long, Cliente> filtrarCandidatos(Specification<Candidato> searchCandidato);

    Map<Long, Cliente> todosCandidatos();

    Candidato obtenerElCandidato(long l);
}
