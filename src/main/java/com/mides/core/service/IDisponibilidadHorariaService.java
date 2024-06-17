package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.DisponibilidadHoraria;
import com.mides.core.model.Turno;

import java.util.List;
import java.util.Map;

public interface IDisponibilidadHorariaService {

    void saveDisponibilidadHoraria(DisponibilidadHoraria disponibilidadHoraria);
    void processDisponibilidadHoraria(List<Map<String,String>> csvData, List<Turno> turnos, Candidato candidato);
}
