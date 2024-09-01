package com.mides.core.service;

import com.mides.core.model.Area;

import java.util.List;
import java.util.Map;

public interface IAreaService {
    void saveArea(Area area);
    void saveAreas(List<Area> areas);
    void processAreaCarga(List<Map<String,String>> csvData);

    List<Area> getAreas();

    Area getUnArea(String nombre);
}
