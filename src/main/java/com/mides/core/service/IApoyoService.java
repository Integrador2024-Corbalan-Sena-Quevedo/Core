package com.mides.core.service;

import com.mides.core.model.Apoyo;

import java.util.List;
import java.util.Map;

public interface IApoyoService {

    void saveApoyo(Apoyo apoyo);

    void  saveApoyos(List<Apoyo> apoyos);

    void processApoyoCarga(List<Map<String, String>> csvData);

    List<Apoyo> getApoyos();
}
