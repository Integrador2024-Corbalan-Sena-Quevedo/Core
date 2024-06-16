package com.mides.core.service;

import com.mides.core.model.Turno;

import java.util.List;
import java.util.Map;

public interface ITurnoService {

    void processTurnoPrecarga(List<Map<String, String>> csvData);
    void saveTurno(Turno turno);
    void saveTurnos(List<Turno> turnos);

    List<Turno> getTurnos();

}
