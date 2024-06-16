package com.mides.core.service;

import com.mides.core.model.Area;
import com.mides.core.model.Turno;
import com.mides.core.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TurnoService implements ITurnoService {

    @Autowired
    ITurnoRepository turnoRepository;
    @Override
    public void processTurnoPrecarga(List<Map<String, String>> csvData) {

            List<Turno> turnos = new ArrayList<>();
            for(Map<String, String> row : csvData) {
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    Turno turnoAux = null;
                    String key = entry.getKey();
                    if (key.startsWith("Diponibilidad - ")) {
                        String nombreTurno= key.substring("Diponibilidad - ".length());
                        if (nombreTurno.equals("Mañana")){
                            nombreTurno = "Maniana";
                        }
                        turnoAux = new Turno(Turno.turnoDisponible.valueOf(nombreTurno.toUpperCase()));
                        turnos.add(turnoAux);
                    }
                }
            }
            this.saveTurnos(turnos);
        }



    @Override
    public void saveTurno(Turno turno) {
        List<Turno> turnosEnBase = turnoRepository.findAll();
        for (Turno turnoAux : turnosEnBase){
            if (!turnoAux.getTurno().name().equals(turno.getTurno().name())){
                turnoRepository.save(turno);
            }
        }
        if(turnosEnBase.isEmpty()){
            turnoRepository.save(turno);
        }
    }

    @Override
    public void saveTurnos(List<Turno> turnos) {
        for (Turno turno : turnos){
            this.saveTurno(turno);
        }
    }

    @Override
    public List<Turno> getTurnos() {
       return turnoRepository.findAll();
    }
}
