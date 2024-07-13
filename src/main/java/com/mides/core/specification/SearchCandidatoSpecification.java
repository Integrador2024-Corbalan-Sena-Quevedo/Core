package com.mides.core.specification;

import com.mides.core.model.*;
import com.mides.core.service.ExperienciaLaboral;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;


import javax.script.ScriptEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SearchCandidatoSpecification implements Specification<Candidato> {
    private List<FiltroCandidato> filtros;

    @Override
    public Predicate toPredicate(Root<Candidato> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {


        List<Predicate> predicate = new ArrayList<>();

        if (!filtros.isEmpty()){
            for (FiltroCandidato filtro : filtros) {
                System.out.println("Filtro Actual : ++"+filtro.getName());
               if(Objects.equals(filtro.getName(), "Apoyo")){
                   System.out.println("Entro En Apoyo");
                   Join<Candidato, Apoyo> apoyo = root.join("apoyos", JoinType.INNER );
                   System.out.println("Hizo el join");
                    for (String unApoyo : filtro.getSubFiltros()){
                        System.out.println("Nombre de la columna: "+unApoyo);
                        System.out.println("Nombre de la columna: "+apoyo.get("nombre"));
                        Predicate apoyoCandidato = criteriaBuilder.equal(apoyo.get("nombre"), unApoyo);
                        predicate.add(apoyoCandidato);
                    }
               }
                if(Objects.equals(filtro.getName(), "Area")){
                    System.out.println("Entro En Area");

                    Join<Candidato, Area> area = root.join("areas", JoinType.INNER );
                    System.out.println("Hizo el join");
                    for (String unArea : filtro.getSubFiltros()){
                        System.out.println("Nombre de la columna: "+unArea);
                        System.out.println("Nombre de la columna: "+area.get("nombre"));
                        Path<String> areaNombrePath = area.get("nombre");
                        Predicate areaCandidato = criteriaBuilder.equal(areaNombrePath, unArea);
                        predicate.add(areaCandidato);
                    }
                }
                if(Objects.equals(filtro.getName(), "Turno")){
                    Join<Candidato, DisponibilidadHoraria> disponibilidadHorariaJoin = root.join("disponibilidadHoraria", JoinType.INNER );
                    Join<DisponibilidadHoraria, Turno> disponibilidadHorariaTurnoJoin = disponibilidadHorariaJoin.join("turnos", JoinType.INNER );

                    System.out.println("Hizo el join");
                    for (String unTurno : filtro.getSubFiltros()){
                        System.out.println("Nombre de la columna: "+unTurno);
                        System.out.println("Nombre de la columna: "+disponibilidadHorariaTurnoJoin.get("turno"));
                        Path<String> turnoNombrePath = disponibilidadHorariaTurnoJoin.get("turno");
                        Predicate apoyoTurno = criteriaBuilder.equal(turnoNombrePath, unTurno);
                        predicate.add(apoyoTurno);
                    }
                }
                if(Objects.equals(filtro.getName(), "tipo_discapicidad")){
                    Join<Candidato, Discapacidad> candidatoDiscapacidadJoin = root.join("discapacidad", JoinType.INNER );
                    Join<Discapacidad, TipoDiscapacidad> tipoDiscapacidadJoin = candidatoDiscapacidadJoin.join("tipoDiscapacidades", JoinType.INNER );

                    for (String untipoDiscapacidad : filtro.getSubFiltros()){
                        Path<String> discapacidadNombrePath = tipoDiscapacidadJoin.get("nombre");
                        Predicate tipoDiscapacidadCandidato = criteriaBuilder.equal(discapacidadNombrePath, untipoDiscapacidad);
                        predicate.add(tipoDiscapacidadCandidato);
                    }
                }

                if(Objects.equals(filtro.getName(), "motivo_desempleo")){
                    Join<Candidato, ExperienciaLaboral> experienciaLaboralJoin = root.join("experienciaLaboral", JoinType.INNER );
                    Join<ExperienciaLaboral, MotivoDesempleo> motivoDesempleoJoin = experienciaLaboralJoin.join("motivosDesempleo", JoinType.INNER );
                    for (String unMotivoDesempleo: filtro.getSubFiltros()){
                        Predicate motivoDesempleoCandidato = criteriaBuilder.equal(motivoDesempleoJoin.get("motivo"), unMotivoDesempleo);
                        predicate.add(motivoDesempleoCandidato);
                    }
                }
                if(Objects.equals(filtro.getName(), "ayuda_tecnica")){
                    Join<Candidato, AyudaTecnica> ayudaTecnicaJoin = root.join("ayudaTecnicas", JoinType.INNER );
                    for (String unAyudaTecnica: filtro.getSubFiltros()){
                        Predicate ayudaTecnicaCandidato = criteriaBuilder.equal(ayudaTecnicaJoin.get("nombre"), unAyudaTecnica);
                        predicate.add(ayudaTecnicaCandidato);
                    }
                }

                if(Objects.equals(filtro.getName(), "Habilidad")){
                    Join<Candidato, Habilidad> habilidadJoin = root.join("habilidad", JoinType.INNER );
                    for (String unaHabilidad: filtro.getSubFiltros()){
                        Predicate habilidadCandidato = criteriaBuilder.equal(habilidadJoin.get(unaHabilidad), 1);
                        predicate.add(habilidadCandidato);
                    }
                }

                if(Objects.equals(filtro.getName(), "documento"))
                    for (String unDocumento: filtro.getSubFiltros()){
                        Predicate documentoCandidato = criteriaBuilder.equal(root.get("documento"), unDocumento);
                        predicate.add(documentoCandidato);
                    }
                }
            }

        System.out.println("CAntidad de Predicados +++++++++++"+predicate.size());
        query.orderBy(criteriaBuilder.asc(root.get("apellido")));
        return criteriaBuilder.or(predicate.toArray(new Predicate[predicate.size()]));
    }

}


