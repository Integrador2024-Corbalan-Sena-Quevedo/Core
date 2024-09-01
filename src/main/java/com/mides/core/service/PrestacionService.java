package com.mides.core.service;

import com.mides.core.model.Prestacion;
import com.mides.core.repository.IPrestacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PrestacionService implements IPrestacionService{
    @Autowired
    IPrestacionRepository prestacionRepository;
    @Override
    public void savePrestacion(Prestacion prestacion) {
        List<Prestacion> prestaciones = prestacionRepository.findAll();

        if(!existNombrePrestacion(prestacion, prestaciones)){
            prestacionRepository.save(prestacion);
        }

        if (prestaciones.isEmpty()){
            prestacionRepository.save(prestacion);
        }

    }

    @Override
    public void savePrestaciones(List<Prestacion> prestaciones) {
        for (Prestacion prestacion : prestaciones){
            this.savePrestacion(prestacion);
        }

    }

    @Override
    public void processPrestacionCarga(List<Map<String, String>> csvData) {

        List<Prestacion> prestaciones = new ArrayList<>();

        for(Map<String, String> row : csvData){
            Prestacion prestacionAux = null;
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("Prestaciones - ")) {
                    String nombrePrestacion = key.substring("Prestaciones - ".length());
                    prestacionAux = new Prestacion(nombrePrestacion);
                    prestaciones.add(prestacionAux);
                }

            }
        }
        this.savePrestaciones(prestaciones);
    }


    private  boolean existNombrePrestacion(Prestacion prestacion, List<Prestacion> prestacions){
        for (Prestacion prestacionAux : prestacions){
            if (prestacionAux.getNombre().equals(prestacion.getNombre())){
                return true;
            }
        }
        return  false;
    }
    @Override
    public List<Prestacion> getPrestaciones() {
        return prestacionRepository.findAll();
    }

    @Override
    public Prestacion getUnaPrestacion(String nombre) {
        for (Prestacion prestacionAux : prestacionRepository.findAll()){
            if (prestacionAux.getNombre().equals(nombre)){
                return prestacionAux;
            }
        }
        return null;
    }
}
