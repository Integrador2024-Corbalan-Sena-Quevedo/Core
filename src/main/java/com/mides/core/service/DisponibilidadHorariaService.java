package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.DisponibilidadHoraria;
import com.mides.core.model.Turno;
import com.mides.core.repository.IDisponibilidadHorariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DisponibilidadHorariaService implements  IDisponibilidadHorariaService {

    @Autowired
    IDisponibilidadHorariaRepository disponibilidadHorariaRepository;
    @Override
    public void saveDisponibilidadHoraria(DisponibilidadHoraria disponibilidadHoraria) {
        disponibilidadHorariaRepository.save(disponibilidadHoraria);
    }

    @Override
    public void processDisponibilidadHoraria(List<Map<String, String>> csvData, List<Turno> turnos, Candidato candidato) {
        DisponibilidadHoraria disponibilidadHoraria = new DisponibilidadHoraria();

        for (Map<String, String> row : csvData) {
            disponibilidadHoraria.setCandidato(candidato);
            disponibilidadHoraria.setHorasSemanales(row.get("Horas_semanales"));
            disponibilidadHoraria.setTurnos(getTurnos(csvData, turnos));
            disponibilidadHoraria.setDiasDeLaSemana(row.get("Dias"));
            disponibilidadHoraria.setOtroDepartamento(row.get("Otro_dpto"));
        }
        this.saveDisponibilidadHoraria(disponibilidadHoraria);
    }

    private List<Turno> getTurnos(List<Map<String, String>> csvData, List<Turno> turnos) {
        List<Turno> turnosDelCandidato = new ArrayList<>();

        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Diponibilidad - ") && "1".equals(value)) {
                    String nombreTurno = key.substring("Diponibilidad - ".length());
                    for (Turno turno : turnos) {
                        if (nombreTurno.equals("Mañana")){
                            nombreTurno = "Maniana";
                        }
                        if (turno.getTurno().name().equals(nombreTurno.toUpperCase())) {
                            turnosDelCandidato.add(turno);
                        }
                    }
                }
            }
        }
        return turnosDelCandidato;
    }
}
