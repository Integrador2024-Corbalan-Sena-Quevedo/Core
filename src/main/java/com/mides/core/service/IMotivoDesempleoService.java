package com.mides.core.service;

import com.mides.core.model.MotivoDesempleo;

import java.util.List;
import java.util.Map;

public interface IMotivoDesempleoService {

    void saveMotivoDesempleo(MotivoDesempleo motivoDesempleo);
    void saveMotivosDesempleo(List<MotivoDesempleo> motivosDesempleo);

    void processMotivoDesempleoCarga(List<Map<String, String>> csvData);

    List<MotivoDesempleo> getMotivos();
}
