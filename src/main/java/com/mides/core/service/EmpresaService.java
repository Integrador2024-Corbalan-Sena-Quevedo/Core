package com.mides.core.service;

import com.mides.core.model.DatosAdicionalesEmpresa;
import com.mides.core.model.Empresa;
import com.mides.core.repository.IEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class EmpresaService implements  IEmpresaSevice{

    @Autowired
    IEmpresaRepository empresaRepository;
    @Override
    public void saveEmpresa(Empresa empresa) {
        if(empresaRepository.getEmpresaByRut(empresa.getRut()).isEmpty()){
            empresaRepository.save(empresa);
        }
    }

    @Override
    public Empresa processEmpresa(List<Map<String, String>> csvData) {

        Empresa empresa = new Empresa();
        for (Map<String, String> row : csvData){
            empresa.setCvsEnviados(row.get("Cvs Enviados"));
            empresa.setRut(row.get("RUT de la empresa:"));
            empresa.setActividadEconomica(row.get("Actividad económica que desempea la empresa:"));
            empresa.setNombre(row.get("Empresa U Organización:"));
            empresa.setRamaEconomica(row.get("Rama económica"));
            empresa.setPersonaReferencia(row.get("Persona de referencia:"));
        }
        this.saveEmpresa(empresa);
        return  empresa;
    }
}