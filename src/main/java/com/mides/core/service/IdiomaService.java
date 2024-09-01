package com.mides.core.service;

import com.mides.core.model.Idioma;
import com.mides.core.repository.IIdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class IdiomaService implements IIdiomaService{

    @Autowired
    IIdiomaRepository idiomaRepository;
    @Override
    public void saveIdioma(Idioma idiomaParam) {

        List<Idioma> idiomas = idiomaRepository.findAll();
        Idioma idiomaAux = null;
        for (Idioma idioma : idiomas){
            if (idioma.getNombre().equals(idiomaParam.getNombre())){
                idiomaAux = idioma;
            }
        }
        if (idiomaAux == null){
            idiomaRepository.save(idiomaParam);
        }
    }

    @Override
//    @Transactional
    public void processIdioma(List<Map<String, String>> csvData) {
        Idioma idiomaOtro = new Idioma();
        for (Map<String, String> row : csvData){
            if(row.get("Otro_idioma").equals("1")){
                idiomaOtro.setNombre(row.get("Cual_otro"));
                this.saveIdioma(idiomaOtro);
            }
        }


    }

    @Override
    public void precargaIdiomas() {
        Idioma idiomaIngles = new Idioma("Ingles");
        Idioma idiomaPortugues = new Idioma("Portugues");
        this.saveIdioma(idiomaIngles);
        this.saveIdioma(idiomaPortugues);
    }

    @Override
    public List<Idioma> getIdiomas() {
        return idiomaRepository.findAll();
    }

    @Override
    public Idioma getIdiomaNombre(String id) {
        for (Idioma idioma : idiomaRepository.findAll()) {
            if (idioma.getNombre().equals(id)) {
                return idioma;
            }
        }
        return null;
    }
}
