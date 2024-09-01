package com.mides.core.service;

import com.mides.core.model.Actitud;

import java.util.List;
import java.util.Map;

public interface IActitudService {

    void saveActitud(Actitud actitud);
    void saveActitudes(List<Actitud> actitudes);

    void processActitudCarga(List<Map<String, String>> csvData);

    List<Actitud> getActitudes();

    Actitud getUnActitud(String nombre);
}
