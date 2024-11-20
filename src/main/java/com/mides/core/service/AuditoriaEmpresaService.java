package com.mides.core.service;

import com.mides.core.model.AuditoriaCandidato;
import com.mides.core.model.AuditoriaEmpresa;
import com.mides.core.repository.IAuditoriaCandidatoRepository;
import com.mides.core.repository.IAuditoriaEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuditoriaEmpresaService implements IAuditoriaEmpresaService{

    @Autowired
    IAuditoriaEmpresaRepository auditoriaEmpresaRepository;

    @Override
    public void guardar(AuditoriaEmpresa auditoriaEmpresa) {
        auditoriaEmpresaRepository.save(auditoriaEmpresa);
    }

    @Override
    public List<AuditoriaEmpresa> getAuditorias() {
        return auditoriaEmpresaRepository.findAll();
    }
}
