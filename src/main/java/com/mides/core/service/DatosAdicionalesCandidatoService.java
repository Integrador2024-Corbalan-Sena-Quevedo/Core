package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.DatosAdicionalesCandidato;
import com.mides.core.repository.IDatosAdicionalesCandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DatosAdicionalesCandidatoService implements IDatosAdicionalesCandidatoService {
    @Autowired
    IDatosAdicionalesCandidatoRepository datosAdicionalesCandidatoRepository;
    @Override
    public void saveDatosAdicionalesCandidato(DatosAdicionalesCandidato datosAdicionalesCandidato) {
        datosAdicionalesCandidatoRepository.save(datosAdicionalesCandidato);
    }

    @Override
//    @Transactional
    public void processDatosAdicionalesCandidato(List<Map<String, String>> csvData, Candidato candidato) {

        DatosAdicionalesCandidato datosAdicionalesCandidato = new DatosAdicionalesCandidato();
        for (Map<String, String> row : csvData){
            datosAdicionalesCandidato.setRegistoEnCNHD(row.get("Registro_CNHD"));
            datosAdicionalesCandidato.setConduce(Integer.parseInt(row.get("Conduce")));
            datosAdicionalesCandidato.setTipoLibreta(row.get("Tipo_libreta"));
            datosAdicionalesCandidato.setEnviaCV(row.get("Envía_CV"));
            datosAdicionalesCandidato.setHijos(row.get("Tiene_hijos"));
            datosAdicionalesCandidato.setCantHijos(Integer.parseInt(row.get("Hijos_cant")));

            if (row.get("Grupo_familiar").isEmpty()){
                datosAdicionalesCandidato.setGrupoFamiliar(row.get("Grupo_familiar - Otros"));
            }
            else {
                datosAdicionalesCandidato.setGrupoFamiliar(row.get("Grupo_familiar"));
            }
            datosAdicionalesCandidato.setAutorizacionDarDatos(row.get("Autorización"));
            datosAdicionalesCandidato.setInfomacionPersonal(row.get("Info_personal"));
            datosAdicionalesCandidato.setCuidados(row.get("Cuidados"));
            datosAdicionalesCandidato.setCandidato(candidato);
        }
        this.saveDatosAdicionalesCandidato(datosAdicionalesCandidato);
    }
}
