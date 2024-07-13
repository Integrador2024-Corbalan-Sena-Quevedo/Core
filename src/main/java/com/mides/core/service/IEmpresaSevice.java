package com.mides.core.service;

import com.mides.core.model.DatosAdicionalesEmpresa;
import com.mides.core.model.Empresa;

import java.util.List;
import java.util.Map;

public interface IEmpresaSevice {
    void saveEmpresa(Empresa empresa);
    Empresa processEmpresa(List<Map<String, String>> csvData);
}
