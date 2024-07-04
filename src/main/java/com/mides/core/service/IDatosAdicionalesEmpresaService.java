package com.mides.core.service;

import com.mides.core.model.DatosAdicionalesEmpresa;
import com.mides.core.model.Empresa;

import java.util.List;
import java.util.Map;

public interface IDatosAdicionalesEmpresaService {

    void saveDatosAdicionalesEmpresaSerive(DatosAdicionalesEmpresa datosAdicionalesEmpresa);
    void proessDatosAdicionalesEmpresa(List<Map<String, String>> csvData, Empresa empresa);

}
