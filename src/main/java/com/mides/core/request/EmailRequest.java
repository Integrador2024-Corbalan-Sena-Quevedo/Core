package com.mides.core.request;

import com.mides.core.dto.CandidatoDTO;
import com.mides.core.dto.EmpresaDTO;
import lombok.Data;

import java.util.List;

@Data
public class EmailRequest {
    private List<Long> candidatosId;
    private Long empresaId;
    private String empresaMail;
}
