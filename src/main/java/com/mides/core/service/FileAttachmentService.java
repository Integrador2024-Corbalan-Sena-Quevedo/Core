package com.mides.core.service;


import com.mides.core.model.Candidato;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileAttachmentService implements IFileAttachmentService{

    @Autowired
    ICandidatoSevice candidatoSevice;
    @Autowired
    IDirreccionService dirreccionService;

    @Autowired
    ITelefonoService  telefonoService;

    @Autowired
    IEducacionService educacionService;

    @Autowired
    IHabilidadService habilidadService;

    @Autowired
    IInstitucionService institucionService;
    @Autowired
    ISaludService saludService;
    @Autowired
    IIdiomaService idiomaService;

    @Autowired
     ICandidatoIdiomaService candidatoIdiomaService;
    @Autowired
    IDatosAdicionalesCandidatoService datosAdicionalesCandidatoService;
    @Autowired
    IAyudaTecnicaService ayudaTecnicaService;

    @Autowired
    IPrestacionService prestacionService;

    @Autowired
    IAreaService areaService;

    @Autowired
     ITipoDiscapacidadService tipoDiscapacidadService;

    @Autowired
    IDiscapacidadService discapacidadService;

    @Autowired
    ITurnoService turnoService;
    @Autowired
    IDisponibilidadHorariaService disponibilidadHorariaService;
    @Autowired
    IMotivoDesempleoService motivoDesempleoService;
    @Autowired
    IGustoLaboralService gustoLaboralService;

    @Autowired
    IActitudService actitudService;

    @Autowired
    IExperienciaLaboralService experienciaLaboralService;

    @Autowired
    IApoyoService apoyoService;

    @Autowired
    IEncuestaService encuestaService;

    List<Map<String,String>> csvData = new ArrayList<>();

    @Override
    public void forCSVData(BufferedReader bufferedReader, CSVParser csvParser) throws ParseException {

        for (CSVRecord csvRecord : csvParser) { // csvRecord son los values del archivo
            Map<String,String> csvRow = new HashMap<>();
            for (String header : csvParser.getHeaderNames()){
                csvRow.put(header, csvRecord.get(header));
            }
            csvData.add(csvRow);
        }

        this.processCSVData(csvData);
    }

    @Override
    public void processCSVData(List<Map<String, String>> csvData) throws ParseException {
            apoyoService.processApoyoCarga(csvData);
            actitudService.processActitudCarga(csvData);
            gustoLaboralService.processGustoLaboralCarga(csvData);
            motivoDesempleoService.processMotivoDesempleoCarga(csvData);
            turnoService.processTurnoPrecarga(csvData);
            tipoDiscapacidadService.processTipoDiscapacidadCarga(csvData);
            areaService.processAreaCarga(csvData);
            prestacionService.processPrestacionCarga(csvData);
            ayudaTecnicaService.processAyudaTecnicaCarga(csvData);
            Candidato candidato = candidatoSevice.processCandidato(csvData, ayudaTecnicaService.getAyudaTecnicas(), prestacionService.getPrestaciones(), areaService.getAreas(), apoyoService.getApoyos());
            dirreccionService.processDirreccion(csvData, candidato);
            telefonoService.processTelefono(csvData, candidato);
            institucionService.precargarInsituciones();
            educacionService.processEducacion(csvData, candidato, institucionService.getInstituciones());
            habilidadService.processHabilidad(csvData, candidato);
            saludService.processSalud(csvData, candidato);
            idiomaService.precargaIdiomas();
            idiomaService.processIdioma(csvData);
            candidatoIdiomaService.processCandidatoIdioma(csvData,candidato,idiomaService.getIdiomas());
            datosAdicionalesCandidatoService.processDatosAdicionalesCandidato(csvData, candidato);
            discapacidadService.processDiscapacidad(csvData,tipoDiscapacidadService.getTipoDiscapacidades(),candidato);
            disponibilidadHorariaService.processDisponibilidadHoraria(csvData,turnoService.getTurnos(),candidato);
            experienciaLaboralService.processExperienciaLaboral(csvData,gustoLaboralService.getGustos(),motivoDesempleoService.getMotivos(),actitudService.getActitudes(), candidato);
            encuestaService.processEncuesta(csvData,candidato);
    }

}
