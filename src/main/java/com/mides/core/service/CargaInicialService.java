package com.mides.core.service;

import com.mides.core.service.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
@Component
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class CargaInicialService implements ICargaInicialService{



    private boolean cargaProcesada;

    @Autowired
    private IApoyoService apoyoService;

    @Autowired
    private IIdiomaService idiomaService;

    @Autowired
    private IActitudService actitudService;

    @Autowired
    private IGustoLaboralService gustoLaboralService;

    @Autowired
    private IMotivoDesempleoService motivoDesempleoService;

    @Autowired
    private ITurnoService turnoService;

    @Autowired
    private ITipoDiscapacidadService tipoDiscapacidadService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IPrestacionService prestacionService;

    @Autowired
    private IAyudaTecnicaService ayudaTecnicaService;
    @Autowired
    IInstitucionService institucionService;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void procesarCargaSiEsNecesario(List<Map<String, String>> csvData) {
        if (!cargaProcesada) {
            processCarga(csvData);
            cargaProcesada = true;
        }
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void processCarga(List<Map<String, String>> csvData) {
        apoyoService.processApoyoCarga(csvData);
        idiomaService.precargaIdiomas();
        actitudService.processActitudCarga(csvData);
        gustoLaboralService.processGustoLaboralCarga(csvData);
        motivoDesempleoService.processMotivoDesempleoCarga(csvData);
        turnoService.processTurnoPrecarga(csvData);
        tipoDiscapacidadService.processTipoDiscapacidadCarga(csvData);
        areaService.processAreaCarga(csvData);
        prestacionService.processPrestacionCarga(csvData);
        ayudaTecnicaService.processAyudaTecnicaCarga(csvData);
        institucionService.precargarInsituciones();
    }
}
