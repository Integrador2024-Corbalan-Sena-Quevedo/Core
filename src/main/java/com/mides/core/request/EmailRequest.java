package com.mides.core.request;

import lombok.Data;

import java.util.List;

@Data
public class EmailRequest {
    private List<Long> candidatosId;
    private Long empresaId;
    private String empresaMail;
}
