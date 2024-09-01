package com.mides.core.service;

import com.mides.core.model.Area;
import com.mides.core.repository.IAreaRepository;
import com.mides.core.repository.IPrestacionRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AreaService implements IAreaService {

    @Autowired
    IAreaRepository areaRepository;
    @Override
    public void saveArea(Area area) {
        List<Area> areas = areaRepository.findAll();
        for (Area areaAux : areas){
            if (!areaAux.getNombre().equals(area.getNombre())){
                areaRepository.save(area);
            }
        }
        if(areas.isEmpty()){
            areaRepository.save(area);
        }

    }

    @Override
    public void saveAreas(List<Area> areas) {
        for (Area area : areas){
            this.saveArea(area);
        }

    }

    @Override
    public void processAreaCarga(List<Map<String, String>> csvData) {

        List<Area> areas = new ArrayList<>();
        for(Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                Area areaAux = null;
                String key = entry.getKey();
                if (key.startsWith("Areas - ")) {
                    String nombreArea = key.substring("Areas - ".length());
                    areaAux = new Area(nombreArea);
                    areas.add(areaAux);
                }
            }
        }
        this.saveAreas(areas);
    }

    @Override
    public List<Area> getAreas() {
        return areaRepository.findAll();
    }

    @Override
    public Area getUnArea(String nombre) {
        for (Area area : getAreas()) {
            if (area.getNombre().equals(nombre)) {
                return area;
            }
        }
        return null;
    }
}
