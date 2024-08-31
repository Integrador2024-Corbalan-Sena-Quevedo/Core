package com.mides.core.service;

import com.mides.core.dto.DetalleSeguimientoDTO;
import com.mides.core.dto.SeguimientoDTO;
import com.mides.core.model.Seguimiento;

import javax.swing.event.ListDataEvent;
import java.util.List;

public interface ISeguimientoService {

    void saveSeguimiento(Seguimiento seguimiento);

    void processSeguimiento(SeguimientoDTO seguimientoDTO);
    List<SeguimientoDTO> getSeguimientosDTO();

    void processUpdateSeguimiento(DetalleSeguimientoDTO detalleSeguimiento);

    List<Seguimiento> getSeguimientos();


}
