package com.mides.core.service;

import java.util.List;
import java.util.Map;

public interface ICargaInicialService {
    void procesarCargaSiEsNecesario(List<Map<String, String>> csvData);
    void processCarga(List<Map<String, String>> csvData);
}
