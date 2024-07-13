package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Dirreccion;
import com.mides.core.model.Empresa;
import com.mides.core.repository.IDirreccionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;

public interface IDirreccionService {

    List<Dirreccion> getDirrecciones();
    void saveDirreccion(Dirreccion dirreccion);
    void deleteDirreccion(Long id);
    Dirreccion findDirreccion(Long id);
    void editDirreccion(Dirreccion dirreccion);
    void processDirreccion(List<Map<String, String>>  csvData, Candidato candidato);
    void processDirreccion(List<Map<String, String>>  csvData, Empresa empresa);
}
