package com.mides.core.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FiltroEmpresa {
    private String name;

    private ArrayList<String> subFiltros;
}
