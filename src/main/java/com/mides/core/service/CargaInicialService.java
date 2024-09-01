package com.mides.core.service;

//import com.mides.core.repository.IParametersRepository;
import com.mides.core.repository.IParametersRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

import static com.mides.core.constant.Constant.CANDIDATO_PRECARGA;

@Service
@Component
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class CargaInicialService implements ICargaInicialService{


    private boolean cargaProcesada;

    @Autowired
    private IParametersRepository parametersRepository;

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
    public void procesarCargaSiEsNecesario(List<Map<String, String>> csvData) throws Exception {
        try {
            String valueParam = parametersRepository.getParameterById(CANDIDATO_PRECARGA);
            if(Integer.parseInt(valueParam) == 0) {
                processCarga(csvData);
                parametersRepository.updateParameterById(CANDIDATO_PRECARGA,"1");
            }
        }catch (Exception e){
            throw new Exception(e);
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
