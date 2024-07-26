package com.mides.core.dto;

import com.mides.core.model.Candidato;
import com.mides.core.model.Email;
import com.mides.core.model.Empresa;
import com.mides.core.model.Tarea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleoDTO {
    private Long id;
    private String nombrePuesto;
    private Long empresaId;
    private String empresaNombre;
    private String tareasEsenciale;
    private List<Tarea> tareas;
    private String detalleTarea;
    private List<Long> candidatosId;
    private String correoEmpresa;
}
