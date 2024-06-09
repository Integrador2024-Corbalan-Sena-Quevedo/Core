package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.repository.ICandidatoRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        if(candidatoRepositoy.getCandidatoPorCI(candidato.getDocumento()) == null){
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

    @Override
    public Candidato processCandidato(List<Map<String, String>> csvData) {
        Candidato candidato = new Candidato();
        for (Map<String, String> row : csvData) {
            candidato.setDocumento(row.get("CI"));
            candidato.setNombre(row.get("Nombre"));
            candidato.setApellido(row.get("apellidos"));
            //candidato.setEdad(Integer.parseInt(row.get("edad")));

        }
        this.saveCandidato(candidato);
        return candidato;
    }


}
