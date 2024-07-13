package com.mides.core.specification;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FiltroCandidato {

    private String name;

    private ArrayList<String> subFiltros;


    @Override
    public String toString() {
        return "FiltroCandidato{" +
                "name='" + name + '\'' +
                ", subFiltros=" + subFiltros +
                '}';
    }

    public FiltroCandidato() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSubFiltros() {
        return subFiltros;
    }

    public void setSubFiltros(ArrayList<String> subFiltros) {
        this.subFiltros = subFiltros;
    }
}
