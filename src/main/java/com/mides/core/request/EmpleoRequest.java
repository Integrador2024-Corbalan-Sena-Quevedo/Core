package com.mides.core.request;

import com.mides.core.model.Candidato;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class EmpleoRequest {
    private Long empleoId;
    private List<Long> candidatosId;

}
