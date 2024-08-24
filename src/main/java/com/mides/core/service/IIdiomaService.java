package com.mides.core.service;



import com.mides.core.model.Idioma;

import java.util.List;
import java.util.Map;

public interface IIdiomaService {

    void saveIdioma(Idioma idioma);
    void processIdioma(List<Map<String, String>> csvData);
    void precargaIdiomas();
    List<Idioma> getIdiomas();

    Idioma getIdiomaNombre(String id);
}
