package com.mides.core.service;

import com.mides.core.model.Empresa;
import com.mides.core.repository.IFiltroEmpresaRepository;
import com.mides.core.specification.SearchEmpresaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class FiltroEmpresaService implements IFiltroEmpresaService{

    @Autowired
    IFiltroEmpresaRepository filtroEmpresaRepository;

    @Override
    public Map<Long, Empresa> filtrarEmresas(SearchEmpresaSpecification criteria) {
        List<Empresa> empresas = filtroEmpresaRepository.findAll(criteria);
        Map<Long, Empresa> map = new HashMap<>();
        for (Empresa empresa : empresas) {
            map.put(empresa.getId(), empresa);
        }

        return map;
    }

    @Override
    public Map<Long, Empresa> todasLasEmpresas() {
        List<Empresa> empresas = filtroEmpresaRepository.findAll();
        Map<Long, Empresa> map = new HashMap<>();
        for (Empresa empresa : empresas) {
            map.put(empresa.getId(), empresa);

        }
        return map;
    }
}
