package com.mides.core.service;

import com.mides.core.model.Empleo;
import com.mides.core.model.Empresa;
import com.mides.core.model.Tarea;
import com.mides.core.repository.IEmpleoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmpleoService implements IEmpleoService{

    @Autowired
    IEmpleoRepository empleoRepository;
    @Override
    public void saveEmpleo(Empleo empleo) {
        empleoRepository.save(empleo);
    }

    @Override
    public Empleo processEmpleo(List<Map<String, String>> csvData, Empresa empresa)  {
        Empleo empleo = new Empleo();

        for (Map<String, String> row : csvData){
            empleo.setNroPuestosDisponible(Integer.parseInt(row.get("No. de puestos que solicita:")));
            empleo.setNombrePuesto(row.get("Nombre del o los puestos:"));
            empleo.setContratoATermino(row.get("¿Es un contrato a termino?:"));
            empleo.setPlazoContrato(row.get("El mismo es por un plazo de..."));
            empleo.setDepartamento(row.get("Departamento:"));
            empleo.setLocalidades(row.get("Localidades"));
        }
        empleo.setEmpresa(empresa);
        this.saveEmpleo(empleo);
        return empleo;
    }


}
