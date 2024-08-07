package com.mides.core.service;

import com.mides.core.dto.SeguimientoDTO;
import com.mides.core.model.Seguimiento;

import java.util.List;

public interface ISeguimientoService {

    void saveSeguimiento(Seguimiento seguimiento);

    void processSeguimiento(SeguimientoDTO seguimientoDTO);
    List<SeguimientoDTO> getSeguimientosDTO();
}
