package com.mides.core.service;

import com.mides.core.model.Candidato;

import com.mides.core.model.Institucion;

import java.util.List;
import java.util.Map;

public interface IInstitucionService {
    void saveInstitucion(Institucion institucion);
    Institucion findInstitucion(Long id);
    void editInstitucion(Institucion institucion);

    void precargarInsituciones();
    List<Institucion> getInstituciones();
}
