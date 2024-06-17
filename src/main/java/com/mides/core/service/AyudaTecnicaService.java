package com.mides.core.service;

import com.mides.core.model.AyudaTecnica;
import com.mides.core.repository.IAyudaTecnicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AyudaTecnicaService implements IAyudaTecnicaService {

    @Autowired
    IAyudaTecnicaRepository ayudaTecnicaRepository;
    @Override
    public void saveAyudaTecnica(AyudaTecnica ayudaTecnica) {
        List<AyudaTecnica> ayudaTecnicas = ayudaTecnicaRepository.findAll();
        for (AyudaTecnica ayudaTec : ayudaTecnicas){
            if (!ayudaTec.getNombre().equals(ayudaTecnica.getNombre())){
                ayudaTecnicaRepository.save(ayudaTecnica);
            }
        }
        if(ayudaTecnicas.isEmpty()){
            ayudaTecnicaRepository.save(ayudaTecnica);
        }
    }

    @Override
    public void saveAyudaTecnica(List<AyudaTecnica> ayudaTecnicas) {
        for (AyudaTecnica ayudaTecnica : ayudaTecnicas){
            this.saveAyudaTecnica(ayudaTecnica);
        }
    }

    @Override
    public void processAyudaTecnicaCarga(List<Map<String, String>> csvData) {
        List<AyudaTecnica> listAyudas = new ArrayList<>();

        for (Map<String, String> row : csvData) {
            AyudaTecnica ayudaTecnicaAux = null;
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("Ayudas_tec_cuales - ")) {
                    String nombreAyuda = key.substring("Ayudas_tec_cuales - ".length());
                    ayudaTecnicaAux = new AyudaTecnica(nombreAyuda);
                    listAyudas.add(ayudaTecnicaAux);
                }

            }
        }
        this.saveAyudaTecnica(listAyudas);

    }

    @Override
    public List<AyudaTecnica> getAyudaTecnicas() {
       return ayudaTecnicaRepository.findAll();
    }

}
