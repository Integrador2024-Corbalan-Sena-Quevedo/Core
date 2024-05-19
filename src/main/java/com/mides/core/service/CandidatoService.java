package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.repository.ICandidatoRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CandidatoService implements ICandidatoSevice{
    @Autowired
    ICandidatoRepositoy candidatoRepositoy;
    @Override
    public List<Candidato> getCandidatos() {
        return null;
    }

    @Override
    public void saveCandidato(Candidato candidato) {
        if(candidatoRepositoy.getCandidatoPorCI(candidato.getCI()) == null){
            candidatoRepositoy.save(candidato);
        }
    }

    @Override
    public void deleteCandidato(Long id) {
    }

    @Override
    public Candidato findCandidato(Long id) {
      return  candidatoRepositoy.findById(id).orElse(null);
    }

    @Override
    public void editCandidato(Candidato candidato) {

    }
}
