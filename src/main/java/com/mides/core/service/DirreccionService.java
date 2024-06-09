package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Dirreccion;
import com.mides.core.repository.IDirreccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DirreccionService implements IDirreccionService{

    @Autowired
    IDirreccionRepository dirreccionRepository;
    @Override
    public List<Dirreccion> getDirrecciones() {
        return null;
    }

    @Override
    public void saveDirreccion(Dirreccion dirreccion) {
        dirreccionRepository.save(dirreccion);
    }

    @Override
    public void deleteDirreccion(Long id) {

    }

    @Override
    public Dirreccion findDirreccion(Long id) {
        return null;
    }

    @Override
    public void editDirreccion(Dirreccion dirreccion) {

    }

    @Override
    public void processDirreccion(List<Map<String, String>> csvData, Candidato candidato) {
        Dirreccion dirreccion = new Dirreccion();
        for (Map<String, String> row : csvData) {
            dirreccion.setApartamento(row.get("Departamento"));
            dirreccion.setLocalidad(row.get("Localidad"));
            dirreccion.setCalle(row.get("Calle"));
            dirreccion.setCalleIncluida(Integer.parseInt(row.get("Calle_incluida")));
            dirreccion.setNumeroPuerta(Integer.parseInt(row.get("Nro_de_puerta")));
            dirreccion.setApartamento(row.get("Apto"));
            dirreccion.setEsquinaUno(row.get("Esq_1"));
            dirreccion.setEsquinaDos(row.get("Esq_2"));
            dirreccion.setCandidato(candidato);
        }
        dirreccionRepository.save(dirreccion);
    }

}
