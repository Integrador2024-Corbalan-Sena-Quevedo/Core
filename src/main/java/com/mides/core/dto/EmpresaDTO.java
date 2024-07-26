package com.mides.core.dto;

import com.mides.core.model.Empleo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {

    private String nombre;
    private String rut;
    private String telefono;
    private Long empleoId;
}
