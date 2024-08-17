package com.mides.core.service;

import com.mides.core.model.Empresa;
import com.mides.core.specification.SearchEmpresaSpecification;

import java.util.Map;

public interface IFiltroEmpresaService {
    Map<Long, Empresa> filtrarEmresas(SearchEmpresaSpecification criteria);

    Map<Long, Empresa> todasLasEmpresas();
}
