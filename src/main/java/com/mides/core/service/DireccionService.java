package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Direccion;
import com.mides.core.model.Empresa;
import com.mides.core.repository.IDireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DireccionService implements IDireccionService {

    @Autowired
    IDireccionRepository direccionRepository;

    @Override
    public void saveDireccion(Direccion direccion) {
        direccionRepository.save(direccion);
    }

    @Override
    public void processDireccion(List<Map<String, String>> csvData, Candidato candidato) {
        Direccion direccion = new Direccion();
        for (Map<String, String> row : csvData) {
            direccion.setDepartamento(row.get("Departamento"));
            direccion.setLocalidad(row.get("Localidad"));
            direccion.setCalle(row.get("Calle"));
            direccion.setCalleIncluida(Integer.parseInt(row.get("Calle_incluida")));
            direccion.setNumeroPuerta(Integer.parseInt(row.get("Nro_de_puerta")));
            direccion.setApartamento(row.get("Apto"));
            direccion.setEsquinaUno(row.get("Esq_1"));
            direccion.setEsquinaDos(row.get("Esq_2"));
            direccion.setCliente(candidato);
        }
        this.saveDireccion(direccion);
    }

    @Override
    public void processDireccion(List<Map<String, String>> csvData, Empresa empresa) {
        Direccion direccion = new Direccion();
        for (Map<String, String> row : csvData) {
            direccion.setDepartamento(row.get("Departamento"));
            direccion.setLocalidad(row.get("Localidad"));
            direccion.setCalle(row.get("Calleruta:"));
            direccion.setNumeroPuerta(Integer.parseInt(row.get("Número:")));
            direccion.setApartamento(row.get("Apartamento:"));
            direccion.setKilometro(row.get("Kilómetro:"));
            direccion.setEsquinaUno(row.get("Esquina 1:"));
            direccion.setEsquinaDos(row.get("Esquina 2:"));
            direccion.setObservacionesDireccion(row.get("Observaciones Dirección:"));
            direccion.setCliente(empresa);
        }
        this.saveDireccion(direccion);
    }

}
