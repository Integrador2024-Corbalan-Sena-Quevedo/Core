package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.DatosAdicionalesEmpresa;
import com.mides.core.model.Empresa;
import com.mides.core.repository.IDatosAdicionalesEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class DatosAdicionalesEmpresaService implements  IDatosAdicionalesEmpresaService{

    @Autowired
    IDatosAdicionalesEmpresaRepository datosAdicionalesEmpresaRepository;
    @Override
    public void saveDatosAdicionalesEmpresaSerive(DatosAdicionalesEmpresa datosAdicionalesEmpresa) {
        datosAdicionalesEmpresaRepository.save(datosAdicionalesEmpresa);
    }

    @Override
    public void proessDatosAdicionalesEmpresa(List<Map<String, String>> csvData, Empresa empresa) {
        DatosAdicionalesEmpresa datosAdicionalesEmpresa = new DatosAdicionalesEmpresa();
        DateTimeFormatter fechaRespuestaEmpresa = DateTimeFormatter.ofPattern("dd/MM/yy");

        for (Map<String, String> row : csvData){
            datosAdicionalesEmpresa.setEmpresaDesierta(Integer.parseInt(row.get("Empresa Desierta")));
            datosAdicionalesEmpresa.setEmpresaSinRespuesta(Integer.parseInt(row.get("Empresas sin repsuesta")));
            datosAdicionalesEmpresa.setEmpleadosContratadosConDiscapacidad(row.get("¿La empresa tiene contratado actualmente personas con discapacidad?:"));
            datosAdicionalesEmpresa.setTuvoEmpleadosConDiscapacidad(row.get("¿Alguna vez tuvo contratadas personas con discapacidad?:"));
            LocalDate fechaRespuesta = LocalDate.parse(row.get("Fecha de respuesta"), fechaRespuestaEmpresa);
            datosAdicionalesEmpresa.setFechaRespuesta(fechaRespuesta);
        }
        datosAdicionalesEmpresa.setEmpresa(empresa);
        this.saveDatosAdicionalesEmpresaSerive(datosAdicionalesEmpresa);
    }
}
