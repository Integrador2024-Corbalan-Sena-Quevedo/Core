package com.mides.core.specification;

import com.mides.core.enums.NivelEducativo;
import com.mides.core.model.*;
import com.mides.core.model.ExperienciaLaboral;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SearchCandidatoSpecification implements Specification<Candidato> {
    private List<FiltroCandidato> filtros;

    @Override
    public Predicate toPredicate(Root<Candidato> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {


        List<Predicate> predicate = new ArrayList<>();

        if (filtros != null && !filtros.isEmpty()){

            for (FiltroCandidato filtro : filtros) {

                switch (filtro.getName()){
                    case "Apoyo" :
                        Join<Candidato, Apoyo> apoyo = root.join("apoyos", JoinType.INNER );
                        addSubFilters(apoyo, "nombre", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "Area" :
                        Join<Candidato, Area> area = root.join("areas", JoinType.INNER );
                        addSubFilters(area, "nombre", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "Turno" :
                        Join<Candidato, DisponibilidadHoraria> disponibilidadHorariaJoin = root.join("disponibilidadHoraria", JoinType.INNER );
                        Join<DisponibilidadHoraria, Turno> disponibilidadHorariaTurnoJoin = disponibilidadHorariaJoin.join("turnos", JoinType.INNER );
                        addSubFilters(disponibilidadHorariaTurnoJoin, "turno", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "tipo_discapicidad" :
                        Join<Candidato, Discapacidad> candidatoDiscapacidadJoin = root.join("discapacidad", JoinType.INNER );
                        Join<Discapacidad, TipoDiscapacidad> tipoDiscapacidadJoin = candidatoDiscapacidadJoin.join("tipoDiscapacidades", JoinType.INNER );
                        addSubFilters(tipoDiscapacidadJoin, "nombre", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "motivo_desempleo" :
                        Join<Candidato, ExperienciaLaboral> experienciaLaboralJoin = root.join("experienciaLaboral", JoinType.INNER );
                        Join<ExperienciaLaboral, MotivoDesempleo> motivoDesempleoJoin = experienciaLaboralJoin.join("motivosDesempleo", JoinType.INNER );
                        addSubFilters(motivoDesempleoJoin, "motivo", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "ayuda_tecnica" :
                        Join<Candidato, AyudaTecnica> ayudaTecnicaJoin = root.join("ayudaTecnicas", JoinType.INNER );
                        addSubFilters(ayudaTecnicaJoin, "nombre", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "Habilidad":
                        Join<Candidato, Habilidad> habilidadJoin = root.join("habilidad", JoinType.INNER );
                        addSubFiltersHabilidad(habilidadJoin, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "documento" :
                        addSubFilters(root, "documento", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "mayores a":
                        addFiltersMayores(root, "fecha_de_nacimiento", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "menores a":
                        addFiltersMenores(root, "fecha_de_nacimiento", filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "departamento":
                        Join<Candidato, Direccion> direccionJoin = root.join("direccion", JoinType.INNER );
                        addSubFiltersDepartamento(direccionJoin, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "idioma":
                        Join<Candidato, CandidatoIdioma> candidatoCandidatoIdiomaJoin = root.join("candidatoIdiomas", JoinType.INNER );
                        Join<CandidatoIdioma, Idioma> idiomaJoin = candidatoCandidatoIdiomaJoin.join("idioma", JoinType.INNER );
                        addSubFiltersIdioma(idiomaJoin, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "libreta_Conducir":
                        Join<Candidato, DatosAdicionalesCandidato> datosAdicionalesCandidatoJoin = root.join("datosAdicionalesCandidato", JoinType.INNER );
                        Predicate pred = criteriaBuilder.notEqual(datosAdicionalesCandidatoJoin.get("tipoLibreta"), "");
                        predicate.add(pred);
                        break;
                    case "Registro Nacional de Persona con Discapacidad":
                        Join<Candidato, DatosAdicionalesCandidato> datosAdicionalesCandidatoJoin1 = root.join("datosAdicionalesCandidato", JoinType.INNER );
                        Predicate predDatoAd = criteriaBuilder.equal(criteriaBuilder.upper(datosAdicionalesCandidatoJoin1.get("registoEnCNHD")),"SI");
                        predicate.add(predDatoAd);
                        break;
                    case "nivel_Educativo":
                        Join<Candidato, Educacion> educacionJoin = root.join("educacion", JoinType.INNER );
                        addSubFiltersNivelEducativo(educacionJoin, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                }
            }
        }

        System.out.println("CAntidad de Predicados +++++++++++"+predicate.size());
        query.orderBy(criteriaBuilder.asc(root.get("apellido")));
        return criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
    }

    private void addSubFiltersNivelEducativo(Join<Candidato, Educacion> educacionJoin, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if(subFiltros != null && !subFiltros.isEmpty()){
            List<Predicate> subFilters = new ArrayList<>();
            for(String subFiltro : subFiltros){
                int nivelEdu = NivelEducativo.getNivelEducativo(subFiltro);
                Predicate preicate= criteriaBuilder.greaterThanOrEqualTo(educacionJoin.get("nivelNumerico"), nivelEdu);
                subFilters.add(preicate);
            }

            predicates.add(criteriaBuilder.or(subFilters.toArray(new Predicate[0])));
        }
    }

    private void addSubFiltersIdioma(Join<CandidatoIdioma, Idioma> idiomaJoin, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (subFiltros != null && !subFiltros.isEmpty()){
            List<Predicate> subFilters = new ArrayList<>();
            for (String subFiltro : subFiltros){
                Predicate predicate = criteriaBuilder.equal(idiomaJoin.get("nombre"), subFiltro);
                subFilters.add(predicate);
            }

            predicates.add(criteriaBuilder.or(subFilters.toArray(new Predicate[0])));
        }
    }

    private void addSubFiltersDepartamento(Join<Candidato, Direccion> direccionJoin, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (subFiltros != null && !subFiltros.isEmpty()) {
            List<Predicate> subFilters = new ArrayList<>();
            for (String subFiltro : subFiltros) {
                Predicate predicate = criteriaBuilder.equal(direccionJoin.get("departamento"), subFiltro);
                subFilters.add(predicate);
            }

            predicates.add(criteriaBuilder.or(subFilters.toArray(new Predicate[0])));

        }
    }

    private void addFiltersMayores(Path<?> path, String attributeName, List<String> subFilters, CriteriaBuilder criteriaBuilder, List<Predicate> predicates){
        if (subFilters != null && !subFilters.isEmpty()){

            for (String subFilter : subFilters){
                LocalDate anioNacimiento = calcularAnioNacimiento(Integer.parseInt(subFilter));
                Predicate predicate = criteriaBuilder.lessThanOrEqualTo(path.get(attributeName), anioNacimiento);
                predicates.add(predicate);
                System.out.println(path.get(attributeName) + " " + anioNacimiento);
            }
        }
    }

    private void addFiltersMenores(Path<?> path, String attributeName, List<String> subFilters, CriteriaBuilder criteriaBuilder, List<Predicate> predicates){
        if (subFilters != null && !subFilters.isEmpty()){

            for (String subFilter : subFilters){
                LocalDate anioNacimiento = calcularAnioNacimiento(Integer.parseInt(subFilter));
                Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(path.get(attributeName), anioNacimiento);
                predicates.add(predicate);
                System.out.println(path.get(attributeName) + " " + anioNacimiento);
            }
        }
    }

    private static LocalDate calcularAnioNacimiento(int edad) {
        int anioActual = LocalDate.now().getYear();
        int anioNacimiento = anioActual - edad;
        // Asumimos que nacieron el 1 de enero de ese año para simplificar
        return LocalDate.of(anioNacimiento, 1, 1);
    }



    private void addSubFilters(Path<?> path, String attributeName, List<String> subFilters, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (subFilters != null && !subFilters.isEmpty()) {
            List<Predicate> filterPredicates = new ArrayList<>();
            for (String subFilter : subFilters) {
                Predicate predicate = criteriaBuilder.equal(path.get(attributeName), subFilter);
                filterPredicates.add(predicate);
            }
            if (!filterPredicates.isEmpty()) {
                predicates.add(criteriaBuilder.or(filterPredicates.toArray(new Predicate[0])));
            }
        }
    }

    private void addSubFiltersHabilidad(Path<?> path, List<String> subFilters, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        for (String subFilter : subFilters) {
            Predicate predicate = criteriaBuilder.equal(path.get(subFilter), 1);
            predicates.add(predicate);
        }
    }

}