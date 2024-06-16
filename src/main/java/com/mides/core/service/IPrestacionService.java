package com.mides.core.service;

import com.mides.core.model.Prestacion;

import java.util.List;
import java.util.Map;

public interface IPrestacionService {
    void savePrestacion(Prestacion prestacion);
    void savePrestaciones(List<Prestacion> prestaciones);
    void processPrestacionCarga(List<Map<String,String>> csvData);

    List<Prestacion> getPrestaciones();
}
