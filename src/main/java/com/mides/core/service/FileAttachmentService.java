package com.mides.core.service;


import com.mides.core.model.Candidato;
import com.mides.core.model.Empleo;
import com.mides.core.model.Empresa;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class FileAttachmentService implements IFileAttachmentService{

    private enum typeFile{
        CANDIDATE,
        COMPANY
    }
    @Autowired
    private CargaInicialService cargaInicialService;
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
    @Autowired
    IEmailService emailService;

    @Autowired
    IEmpresaSevice empresaSevice;
    @Autowired
    ITareaService tareaService;
    @Autowired
    IEmpleoService empleoService;

    List<Map<String,String>> csvData = new ArrayList<>();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forCSVData(BufferedReader bufferedReader, CSVParser csvParser, String type) throws Exception {
        for (CSVRecord csvRecord : csvParser) { // csvRecord son los values del archivo
            Map<String,String> csvRow = new HashMap<>();
            for (String header : csvParser.getHeaderNames()){
                csvRow.put(header, csvRecord.get(header));
            }
            if (type.equals(typeFile.CANDIDATE.toString())){
                csvData.add(csvRow);
                cargaInicialService.procesarCargaSiEsNecesario(csvData);
                this.processCSVDataCandidate(csvData);
                csvData.clear();
            } else if (type.equals(typeFile.COMPANY.toString())) {
                csvData.add(csvRow);
                this.processCSVDataCompany(csvData);
                csvData.clear();
            }

        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void processCSVDataCandidate(List<Map<String, String>> csvData) throws Exception {
            Candidato candidato = candidatoSevice.processCandidato(csvData, ayudaTecnicaService.getAyudaTecnicas(), prestacionService.getPrestaciones(), areaService.getAreas(), apoyoService.getApoyos());
            dirreccionService.processDirreccion(csvData, candidato);
            telefonoService.processTelefono(csvData, candidato);
            educacionService.processEducacion(csvData, candidato, institucionService.getInstituciones());
            habilidadService.processHabilidad(csvData, candidato);
            saludService.processSalud(csvData, candidato);
            idiomaService.processIdioma(csvData);
            candidatoIdiomaService.processCandidatoIdioma(csvData, candidato, idiomaService.getIdiomas());
            datosAdicionalesCandidatoService.processDatosAdicionalesCandidato(csvData, candidato);
            discapacidadService.processDiscapacidad(csvData, tipoDiscapacidadService.getTipoDiscapacidades(), candidato);
            disponibilidadHorariaService.processDisponibilidadHoraria(csvData, turnoService.getTurnos(), candidato);
            experienciaLaboralService.processExperienciaLaboral(csvData, gustoLaboralService.getGustos(), motivoDesempleoService.getMotivos(), actitudService.getActitudes(), candidato);
            encuestaService.processEncuesta(csvData, candidato);
            emailService.processEmail(csvData, candidato);
    }

    @Override
    public void processCSVDataCompany(List<Map<String, String>> csvData) throws Exception {
        Empresa empresa =  empresaSevice.processEmpresa(csvData);
        dirreccionService.processDirreccion(csvData, empresa);
        emailService.processEmail(csvData, empresa);
        telefonoService.processTelefono(csvData, empresa);
        Empleo empleo = empleoService.processEmpleo(csvData, empresa);
        tareaService.processTarea(csvData, empleo);

    }


}
