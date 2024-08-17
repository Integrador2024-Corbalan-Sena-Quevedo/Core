package com.mides.core.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class DetalleSeguimientoDTO {
    private String detalle;
    private String operadorLaboral;
    private LocalDate fechaDetalle;
    private Long seguimientoId;
}
