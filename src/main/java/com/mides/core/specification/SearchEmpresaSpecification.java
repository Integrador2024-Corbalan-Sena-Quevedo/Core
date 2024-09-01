package com.mides.core.specification;

import com.mides.core.enums.NivelEducativo;
import com.mides.core.model.Candidato;
import com.mides.core.model.Educacion;
import com.mides.core.model.Empleo;
import com.mides.core.model.Empresa;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SearchEmpresaSpecification implements Specification<Empresa> {

    private List<FiltroEmpresa> filtros;

    @Override
    public Predicate toPredicate(Root<Empresa> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicate = new ArrayList<>();

        if (filtros != null && !filtros.isEmpty()){

            for (FiltroEmpresa filtro : filtros) {
                switch (filtro.getName()){
                    case "nombreEmpresa":
                        addSubFiltrosNombreEmpresa(root, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "actividad_economica":
                        addSubFiltersActividadEconomica(root, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "rama_economica":
                        addSubFiltersRamaEconomica(root, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "rut":
                        addSubFiltersRut(root, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;
                    case "departamento":
                        Join<Empresa, Empleo> joinEmpleo = root.join("empleo", JoinType.INNER);
                        addSUbFiltersDepartamento(joinEmpleo, filtro.getSubFiltros(), criteriaBuilder,predicate);
                        break;
                    case "formacion_Academica":
                        Join<Empresa, Empleo> joinEmpleoAc = root.join("empleo", JoinType.INNER);
                        addSubFiltersFormacionAcademica(joinEmpleoAc, filtro.getSubFiltros(), criteriaBuilder, predicate);
                        break;

                }

            }
        }

        return criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
    }

    private void addSubFiltersFormacionAcademica(Join<Empresa, Empleo> joinEmpleoAc, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if(subFiltros != null && !subFiltros.isEmpty()){
            List<Predicate> subFilters = new ArrayList<>();
            for(String subFiltro : subFiltros){
                int nivelEdu = NivelEducativo.getNivelEducativo(subFiltro);
                Predicate preicate= criteriaBuilder.greaterThanOrEqualTo(joinEmpleoAc.get("formacionNumerico"), nivelEdu);
                subFilters.add(preicate);
            }

            predicates.add(criteriaBuilder.or(subFilters.toArray(new Predicate[0])));
        }
    }

    private void addSUbFiltersDepartamento(Join<Empresa, Empleo> joinEmpleo, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (subFiltros != null && !subFiltros.isEmpty()) {
            List<Predicate> subFilters = new ArrayList<>();
            for (String subFiltro : subFiltros) {
                Predicate pred = criteriaBuilder.equal(joinEmpleo.get("departamento"), subFiltro);
                subFilters.add(pred);
            }

            predicates.add(criteriaBuilder.or(subFilters.toArray(new Predicate[0])));

        }
    }

    private void addSubFiltersRut(Root<Empresa> root, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicate) {
        if (subFiltros != null && !subFiltros.isEmpty()){
            List<Predicate> filterPredicates = new ArrayList<>();

            for (String subFiltro : subFiltros) {
                Predicate pred = criteriaBuilder.equal(criteriaBuilder.upper(root.get("rut")), subFiltro.toUpperCase());
                filterPredicates.add(pred);
            }

            predicate.add(criteriaBuilder.or(filterPredicates.toArray(new Predicate[0])));

        }
    }

    private void addSubFiltersRamaEconomica(Root<Empresa> root, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicate) {
        if (subFiltros != null && !subFiltros.isEmpty()){
            List<Predicate> subPredicats = new ArrayList<>();
            for (String subFiltro : subFiltros){
                String pattern = "%" + subFiltro.toUpperCase() + "%";
                Predicate pred = criteriaBuilder.like(criteriaBuilder.upper(root.get("ramaEconomica")), pattern);
                subPredicats.add(pred);
            }
            predicate.add(criteriaBuilder.or(subPredicats.toArray(new Predicate[0])));
        }
    }

    private void addSubFiltersActividadEconomica(Root<Empresa> root, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicate) {
        if (subFiltros != null && !subFiltros.isEmpty()){
            List<Predicate> subPredicats = new ArrayList<>();
            for (String subFiltro : subFiltros){
                String pattern = "%" + subFiltro.toUpperCase() + "%";
                Predicate pred = criteriaBuilder.like(criteriaBuilder.upper(root.get("actividadEconomica")), pattern);
                subPredicats.add(pred);
            }
            predicate.add(criteriaBuilder.or(subPredicats.toArray(new Predicate[0])));

        }
    }

    private void addSubFiltrosNombreEmpresa(Root<Empresa> root, ArrayList<String> subFiltros, CriteriaBuilder criteriaBuilder, List<Predicate> predicate) {
        if (subFiltros != null && !subFiltros.isEmpty()){
            List<Predicate> filterPredicates = new ArrayList<>();

            for (String subFiltro : subFiltros) {
                Predicate pred = criteriaBuilder.equal(criteriaBuilder.upper(root.get("nombre")), subFiltro.toUpperCase());
                filterPredicates.add(pred);
            }

            predicate.add(criteriaBuilder.or(filterPredicates.toArray(new Predicate[0])));

        }
    }
}
