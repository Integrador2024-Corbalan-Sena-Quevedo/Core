package com.mides.core.service;

import com.mides.core.model.*;
import com.mides.core.repository.IExperienciaLaboralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExperienciaLaboralService implements  IExperienciaLaboralService{
    @Autowired
    IExperienciaLaboralRepository experienciaLaboralRepository;
    @Override
    public void saveExperienciaLaboral(ExperienciaLaboral experienciaLaboral) {
        experienciaLaboralRepository.save(experienciaLaboral);
    }

    @Override
//    @Transactional
    public void processExperienciaLaboral(List<Map<String, String>> csvData, List<GustoLaboral> gustoLaborales, List<MotivoDesempleo> motivoDesempleos, List<Actitud> actitudes, Candidato candidato) {
        ExperienciaLaboral experienciaLaboral = new ExperienciaLaboral();
        for (Map<String, String> row : csvData){
            experienciaLaboral.setTrabajoAlgunaVez(row.get("Trab_alguna_vez"));
            experienciaLaboral.setSituacionLaboral(row.get("Sit_laboral"));
            experienciaLaboral.setExperienciaLaboral(row.get("Exp_laboral"));
            experienciaLaboral.setDescripcionSituacionLaboral(row.get("Desc_sit_laboral"));
            experienciaLaboral.setUltimoPuesto(row.get("ult_puesto"));
            experienciaLaboral.setTipoTrabajo(row.get("Tipo_trab"));
            experienciaLaboral.setPuestoActual(row.get("Puesto_actual"));
            experienciaLaboral.setInicioTrabajo(row.get("Inicio_trab"));
            experienciaLaboral.setFinTrabajo(row.get("Fin_trabajo"));
            experienciaLaboral.setTareas(row.get("Tareas"));
            experienciaLaboral.setCandidato(candidato);
            experienciaLaboral.setGustosLaborales(getGustos(csvData, gustoLaborales));
            experienciaLaboral.setMotivosDesempleo(getMotivos(csvData, motivoDesempleos));
            experienciaLaboral.setActitudes(getActitudes(csvData, actitudes, experienciaLaboral));
        }
        this.saveExperienciaLaboral(experienciaLaboral);
    }

    private List<Actitud> getActitudes(List<Map<String, String>> csvData, List<Actitud> actitudes, ExperienciaLaboral experienciaLaboral) {
        List<Actitud> actitudesDelCandidato = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Actitud_empleo - ") && "1".equals(value)) {
                    String nombreActitud = key.substring("Actitud_empleo - ".length());
                    for (Actitud actitud : actitudes) {
                        if (actitud.getNombre().equals(nombreActitud)) {
                            actitud.setExperienciaLaboral(experienciaLaboral);
                            actitudesDelCandidato.add(actitud);
                        }
                    }
                }
            }
        }
        return  actitudesDelCandidato;
    }

    private List<MotivoDesempleo> getMotivos(List<Map<String, String>> csvData, List<MotivoDesempleo> motivoDesempleos) {
        List<MotivoDesempleo> motivosDelCandidato = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Motivo_desempleo - ") && "1".equals(value)) {
                    String nombreMotivo = key.substring("Motivo_desempleo - ".length());
                    switch (nombreMotivo) {
                        case "Debió dedicarse a los quehaceres del hogar o cuidados de otras personas":
                            nombreMotivo = "HACERES_HOGAR_CUIDADOS_PERSONAS";
                            break;
                        case "Tenía problemas vinculares con los compañeros de trabajo":
                            nombreMotivo = "PROBLEMAS_CON_COMPANIERO";
                            break;
                        case "Le resultaba poca remuneración":
                            nombreMotivo = "REMUNERACION_BAJA";
                            break;
                        case "Fue despedida/o o no se renovó el contrato":
                            nombreMotivo = "DESPIDO_NO_RENOVACION";
                            break;
                        case "Motivos de salúd":
                            nombreMotivo = "SALUD";
                            break;
                        case "Motivos de accesibilidad":
                            nombreMotivo = "ACCESIBILIDAD";
                            break;
                        case "Motivos de discriminación":
                            nombreMotivo = "DISCRIMINACION";
                            break;
                        case "No tenía interés / Le interesaba trabajar en otras cosas":
                            nombreMotivo = "FALTA_INTERES";
                            break;
                        case "Otro":
                            nombreMotivo = row.get("Motivo_desempleo - Otros").replaceAll(" ", "_").toUpperCase();
                            break;
                        default:
                            break;
                    }
                    for (MotivoDesempleo motivoDesempleo : motivoDesempleos) {
                        if (motivoDesempleo.getMotivo().equals(nombreMotivo)) {
                            motivosDelCandidato.add(motivoDesempleo);
                        }
                    }
                }
            }
        }
        return  motivosDelCandidato;
    }

    private List<GustoLaboral> getGustos(List<Map<String, String>> csvData, List<GustoLaboral> gustoLaborales) {
        List<GustoLaboral> gustosDelCandidato = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Gustos - ") && "1".equals(value)) {
                    String nombreGusto = key.substring("Gustos - ".length()).replaceAll(" ", "_");

                    switch (nombreGusto) {
                        case "Las_tareas_que_desempeñaba":
                            nombreGusto = "TAREAS_QUE_DESEMPENIABA";
                            break;
                        case "La_remuneración":
                            nombreGusto = "REMUNERACION";
                            break;
                        case "El_relacionamiento_laboral":
                            nombreGusto = "RELACIONAMIENTO_LABORAL";
                            break;
                        case "Carga_horaria":
                            nombreGusto = "CARGA_HORARIA";
                            break;
                        case "Accesibilidad_del_puesto_de_trabajo":
                            nombreGusto = "ACCESIBILIDAD";
                            break;
                        case "Otros":
                            if (row.get("Gustos - Otros") != null && !row.get("Gustos - Otros").equals("0") && !row.get("Gustos - Otros").equals("1")) {
                                nombreGusto = row.get("Gustos - Otros").replaceAll(" ", "_").toUpperCase();
                            }
                            break;
                        default:
                            break;
                    }

                    for (GustoLaboral gustoLaboral : gustoLaborales) {
                        if (gustoLaboral.getGusto().equals(nombreGusto)) {
                            gustosDelCandidato.add(gustoLaboral);
                        }
                    }
                }
            }
        }
        return gustosDelCandidato;
    }
}
