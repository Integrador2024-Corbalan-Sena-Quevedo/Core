package com.mides.core.service;

import com.mides.core.model.Apoyo;
import com.mides.core.model.Candidato;
import com.mides.core.model.Cliente;
import com.mides.core.model.Empresa;
import com.mides.core.repository.IFiltroCandidatosRepository;
import com.mides.core.specification.FiltroCandidato;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FiltroCandidatosSevice implements IFiltroCandidatosService{

    @Autowired
    IFiltroCandidatosRepository filtroCandidatosRepository;

    @Override
    public Map<Long, Cliente> filtrarCandidatos(Specification<Candidato> searchCandidato) {
        List<Candidato> list = filtroCandidatosRepository.findAll(searchCandidato);
        Map<Long, Cliente> ret = new HashMap<>();

        for (Candidato candidato : list) {
            ret.put(candidato.getId(), candidato);
        }
        return ret;
    }

    @Override
    public Map<Long, Cliente> todosCandidatos() {
        List<Candidato> list = filtroCandidatosRepository.findAll();
        Map<Long, Cliente> ret = new HashMap<>();

        for (Candidato candidato : list) {
            ret.put(candidato.getId(), candidato);
        }
        return ret;
    }

    @Override
    public Candidato obtenerElCandidato(long l) {
        Candidato ret = filtroCandidatosRepository.findById(l).orElse(null);
        return ret;
    }

}
