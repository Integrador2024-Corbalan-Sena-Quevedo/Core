package com.mides.core.service;

import com.mides.core.model.Area;
import com.mides.core.model.TipoDiscapacidad;

import java.util.List;
import java.util.Map;

public interface ITipoDiscapacidadService {

    void saveTipoDiscapacidades(List<TipoDiscapacidad> tipoDiscapacidad);
    void saveTipoDiscapacidad(TipoDiscapacidad tipoDiscapacidad);
    void processTipoDiscapacidadCarga(List<Map<String,String>> csvData);

    List<TipoDiscapacidad> getTipoDiscapacidades();
}
