package com.mides.core.service;


import com.mides.core.model.Candidato;
import com.mides.core.model.Empleo;
import com.mides.core.model.Empresa;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.BufferedReader;
import java.util.*;

@Service
public class FileAttachmentService implements IFileAttachmentService{

    private enum typeFile{
        CANDIDATE,
        COMPANY
    }
    @Autowired
    private ICargaInicialService cargaInicialService;
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
    @Autowired
    IDatosAdicionalesEmpresaService datosAdicionalesEmpresaService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    List<Map<String,String>> csvData = new ArrayList<>();

    @Override
    public List<Integer> forCSVData(BufferedReader bufferedReader, CSVParser csvParser, String type) throws Exception {
        List<Integer> linesWithError = new ArrayList<>();
        int lineNumber = 1;
        for (CSVRecord csvRecord : csvParser) {
            Map<String, String> csvRow = new HashMap<>();
            for (String header : csvParser.getHeaderNames()) {  // csvRecord son los values del archivo
                csvRow.put(header, csvRecord.get(header));
            }
            List<Integer> errorsInCurrentRow =  processCSVDataRow(csvRow, type, lineNumber);
            linesWithError.addAll(errorsInCurrentRow);
            lineNumber++;
        }
        return linesWithError;
    }
    private List<Integer> processCSVDataRow(Map<String, String> csvRow, String type, int lineNumber) throws Exception {
        List<Map<String, String>> csvData = Collections.singletonList(csvRow);
        List<Integer> linesWithError = new ArrayList<>();
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            if (type.equals(typeFile.CANDIDATE.toString())) {
                cargaInicialService.procesarCargaSiEsNecesario(csvData);
                this.processCSVDataCandidate(csvData);
                transactionManager.commit(status); // commit si salio bien
            } else if (type.equals(typeFile.COMPANY.toString())) {
                this.processCSVDataCompany(csvData);
                transactionManager.commit(status);
            }
        } catch (Exception e) {
            transactionManager.rollback(status); // rollback si hubo algun error
            linesWithError.add(lineNumber);
        }
        return linesWithError;
    }
    @Override
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
            encuestaService.processEncuestaCandidato(csvData, candidato);
            emailService.processEmail(csvData, candidato);
    }

    @Override
    public void processCSVDataCompany(List<Map<String, String>> csvData) throws Exception {

            Empresa empresa = empresaSevice.processEmpresa(csvData);
            datosAdicionalesEmpresaService.proessDatosAdicionalesEmpresa(csvData, empresa);
            dirreccionService.processDirreccion(csvData, empresa);
            emailService.processEmail(csvData, empresa);
            telefonoService.processTelefono(csvData, empresa);
            Empleo empleo = empleoService.processEmpleo(csvData, empresa);
            tareaService.processTarea(csvData, empleo);
            empleoService.processConocimientoEspecificosEmpleo(csvData, empleo);
            encuestaService.processEncuestaEmpresa(csvData, empresa);
    }


}
