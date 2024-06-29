package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.DatosAdicionalesEmpresa;
import com.mides.core.model.Empresa;

import java.util.List;
import java.util.Map;

public class DatosAdicionalesEmpresaService implements  IDatosAdicionalesEmpresaService{
    @Override
    public void saveDatosAdicionalesEmpresaSerive(DatosAdicionalesEmpresa datosAdicionalesEmpresa) {

    }

    @Override
    public void proessDatosAdicionalesEmpresa(List<Map<String, String>> csvData, Empresa empresa) {
        DatosAdicionalesEmpresa datosAdicionalesEmpresa = new DatosAdicionalesEmpresa();

        for (Map<String, String> row : csvData){
            datosAdicionalesEmpresa.setEmpresaDesierta(Integer.parseInt(row.get("Empresa Desierta")));
            datosAdicionalesEmpresa.setEmpresaSinRespuesta(Integer.parseInt(row.get("Empresas sin repsuesta")));
            datosAdicionalesEmpresa.setEmpleadosContratadosConDiscapacidad(row.get("¿La empresa tiene contratado actualmente personas con discapacidad?:"));
            datosAdicionalesEmpresa.setTuvoEmpleadosConDiscapacidad(row.get("¿Alguna vez tuvo contratadas personas con discapacidad?:"));
        }
        datosAdicionalesEmpresa.setEmpresa(empresa);
    }
}
