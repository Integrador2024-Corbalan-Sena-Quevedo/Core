package com.mides.core.service;

import com.mides.core.model.AuditoriaEmpresa;

import java.util.List;

public interface IAuditoriaEmpresaService {
    void guardar(AuditoriaEmpresa auditoriaEmpresa);

    List<AuditoriaEmpresa> getAuditorias();
}
