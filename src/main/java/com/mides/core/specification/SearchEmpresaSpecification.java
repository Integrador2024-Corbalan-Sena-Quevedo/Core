package com.mides.core.specification;

import com.mides.core.model.Empresa;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
                    case "":

                        break;

                }

            }
        }

        return criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
    }
}
