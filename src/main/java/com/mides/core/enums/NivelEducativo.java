package com.mides.core.enums;

import lombok.Data;


public enum NivelEducativo {
    SIN_INSTRUCCION(1),
    PREESCOLAR(2),
    PRIMARIA_INCOMPLETA(3),
    PRIMARIA_COMPLETA(4),
    CICLO_BASICO_INCOMPLETO(5),
    CICLO_BASICO_COMPLETO(6),
    BACHILLERATO_INCOMPLETO(7),
    BACHILLERATO_COMPLETO(8),
    EDUCACION_TECNICA_INCOMPLETA(9),
    EDUCACION_TECNICA_COMPLETA(10),
    EDUCACION_MILITAR_INCOMPLETA(11),
    EDUCACION_MILITAR_COMPLETA(12),
    EDUCACION_POLICIAL_INCOMPLETA(13),
    EDUCACION_POLICIAL_COMPLETA(14),
    TERCIARIA_NO_UNIVERSITARIA_INCOMPLETA(15),
    TERCIARIA_NO_UNIVERSITARIA_COMPLETA(16),
    UNIVERSIDAD_O_SIMILAR_INCOMPLETA(17),
    UNIVERSIDAD_O_SIMILAR_COMPLETA(18);

    private final int nivel;
    NivelEducativo(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public static NivelEducativo getNivelEducativo(int nivel) {
        for (NivelEducativo n : values()) {
            if (n.getNivel() == nivel) {
                return n;
            }
        }
        throw new IllegalArgumentException("Nivel educativo no válido: " + nivel);
    }
}
