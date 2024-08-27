package com.mides.core.service;

import com.mides.core.dto.EmpleoDTO;
import com.mides.core.enums.NivelEducativo;
import com.mides.core.model.*;
import com.mides.core.repository.ICandidatoRepositoy;
import com.mides.core.repository.IConocimientosEspecificosEmpleoRepository;
import com.mides.core.repository.IEmpleoRepository;
import com.mides.core.request.EmpleoRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class EmpleoService implements IEmpleoService{

    @Autowired
    IEmpleoRepository empleoRepository;
    @Autowired
    ICandidatoRepositoy candidatoRepositoy;
    @Autowired
    IChatGPTService chatGPTService;

    @Autowired
    IPdfService pdfService;

    @Autowired
    IConocimientosEspecificosEmpleoRepository conocimientosEspecificosEmpleoRepository;
    @Override
    public void saveEmpleo(Empleo empleo) {
        empleoRepository.save(empleo);
    }

    @Override
    public void saveConocimientoEspecificosEmplep(ConocimientosEspecificosEmpleo conocimientosEspecificosEmpleo) {
        conocimientosEspecificosEmpleoRepository.save(conocimientosEspecificosEmpleo);
    }

    @Override
    public Empleo processEmpleo(List<Map<String, String>> csvData, Empresa empresa)  {
        Empleo empleo = new Empleo();

        for (Map<String, String> row : csvData){
            empleo.setNroPuestosDisponible(Integer.parseInt(row.get("No. de puestos que solicita:")));
            empleo.setNombrePuesto(row.get("Nombre del o los puestos:"));
            empleo.setContratoATermino(row.get("¿Es un contrato a termino?:"));
            empleo.setPlazoContrato(row.get("El mismo es por un plazo de..."));
            empleo.setDepartamento(row.get("Departamento:"));
            empleo.setLocalidades(row.get("Localidades"));
            empleo.setCodigo(Integer.parseInt(row.get("Código")));
            empleo.setRitmoImpuesto(row.get("Ritmo impuesto:"));
            empleo.setTrabajoAlExterior(row.get("Trabajo exterior al aire libre:"));
            empleo.setImplicaDesplazamientos(row.get("Implica desplazamientos:"));
            empleo.setSupervisores(row.get("Número de supervisores:"));
            empleo.setCompaneros(row.get("Número de compaeros:"));
            empleo.setSubordinados(row.get("Número de subordinados:"));
            empleo.setEdadPreferente(row.get("Edad preferente:"));
            empleo.setRangoDeEdad(row.get("Rango de edad:"));
            empleo.setFormacionAcademica(NivelEducativo.valueOf(row.get("¿Qué formación académica requiere el puesto?:")).toString());
            empleo.setLibretaConducir(row.get("¿Necesita licencia de conducir?:"));
            empleo.setCategoriaLibretaConducir(row.get("Categoría:"));
            empleo.setExperienciaPrevia(row.get("¿Necesita contar con experiencia laboral previa?:"));
            empleo.setTiempoDeExperienciaMinima(row.get("¿De qué período de tiempo?:"));
            empleo.setDisponibilidadHoraria(row.get("Disponibilidad para la tarea:"));
            empleo.setCargaHorariaSemanal(row.get("Carga horaria semanal del puesto:"));
            empleo.setCargaHorariaTipo(row.get("Carga horaria semanal del puesto: - Otros"));
            empleo.setDiasDeSemanaParaCubrirPuesto(row.get("Días de la semana para cubrir el puesto solicitado:"));
            empleo.setDiasDeSemanaParaCubrirPuestoOtro(row.get("Días de la semana para cubrir el puesto solicitado: - Otros"));
            empleo.setDetalleSalarial(row.get("Detalle salarial:"));
            empleo.setRemuneracionOfrecida(row.get("Detalle salarial:"));
            empleo.setTipoRemuneracion(row.get("La remuneración ofrecida es:"));
            empleo.setTipoRemuneracionOtro(row.get("La remuneración ofrecida es: - Otros"));
        }
        empleo.setCategoria("Sin definir");
        empleo.setEmpresa(empresa);
        this.saveEmpleo(empleo);
        return empleo;
    }

    @Override
    public void processConocimientoEspecificosEmpleo(List<Map<String, String>> csvData, Empleo empleo) {
        ConocimientosEspecificosEmpleo conocimientosEspecificosEmpleo = new ConocimientosEspecificosEmpleo();

        for (Map<String, String> row : csvData) {
            conocimientosEspecificosEmpleo.setComputacion(row.get("Computación:"));
            conocimientosEspecificosEmpleo.setOtrosComputacion(row.get("Otros:"));
            conocimientosEspecificosEmpleo.setIdiomas(row.get("Idiomas:"));
            conocimientosEspecificosEmpleo.setIdiomaCompetenciaYrequisito(row.get("Competencias y requisitos_Especificar:"));
            conocimientosEspecificosEmpleo.setIngles(row.get("Inglés:"));
            conocimientosEspecificosEmpleo.setLee(row.get("Lee"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosEscrituraIngles(row.get("Idiomas requeridos_Escribe:"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosHablaIngles(row.get("Idiomas requeridos_Habla:"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosLecturaIngles(row.get("Idiomas requeridos_Lee:"));
            conocimientosEspecificosEmpleo.setPortgues(row.get("Portugués:"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosEscrituraPortugues(row.get("Idiomas requeridos_Escribe:_2"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosHablaPortugues(row.get("Idiomas requeridos_Habla:_2"));
            conocimientosEspecificosEmpleo.setNivelIdiomasRequeridosLecturaPortugues(row.get("Idiomas requeridos_Lee:_2"));
        }

        conocimientosEspecificosEmpleo.setEmpleo(empleo);
        this.saveConocimientoEspecificosEmplep(conocimientosEspecificosEmpleo);

    }

    @Override
    public List<Candidato> getCandidatosParaEmpleo(Empleo empleo, List<Candidato> candidatos) {
        QueryFilterEmpleo queryFilterEmpleo = new QueryFilterEmpleo();

        if (empleo.getRemuneracionOfrecida() != null) {
            queryFilterEmpleo.setRemuneracionOfrecida(empleo.getRemuneracionOfrecida());
        }

        if (empleo.getConocimientosEspecificosEmpleo() != null) {
            String ingles = empleo.getConocimientosEspecificosEmpleo().getIngles();
            queryFilterEmpleo.setIngles("SI".equalsIgnoreCase(ingles) ? ingles : "");

            String portugues = empleo.getConocimientosEspecificosEmpleo().getPortgues();
            queryFilterEmpleo.setPortugues("SI".equalsIgnoreCase(portugues) ? portugues : "");

            String computacion = empleo.getConocimientosEspecificosEmpleo().getComputacion();
            queryFilterEmpleo.setComputacion(computacion != null ? computacion : "");
        }

        if (empleo.getCargaHorariaSemanal() != null) {
            String cargaHoraria = empleo.getCargaHorariaSemanal().replaceAll("[^\\d]", "");
            queryFilterEmpleo.setCargaHorariaSemanal(cargaHoraria);
        }
        queryFilterEmpleo.setDepartamento(empleo.getDepartamento() != null ? empleo.getDepartamento() : "");
        queryFilterEmpleo.setExperienciaMinima(empleo.getExperienciaPrevia() != null ? empleo.getExperienciaPrevia() : "");
        queryFilterEmpleo.setLocalidades(empleo.getLocalidades() != null ? empleo.getLocalidades() : "");
        String libretaConducir = empleo.getLibretaConducir();
        queryFilterEmpleo.setConduce("SI".equalsIgnoreCase(libretaConducir) ? 1 : 0);
        queryFilterEmpleo.setLibretaConducir(empleo.getLibretaConducir() != null ? empleo.getLibretaConducir() : "");
        queryFilterEmpleo.setImplicaDesplazamientos(empleo.getImplicaDesplazamientos() != null ? empleo.getImplicaDesplazamientos() : "");
        queryFilterEmpleo.setFormacionAcademica(empleo.getFormacionAcademica() != null
                                                ? NivelEducativo.valueOf(empleo.getFormacionAcademica().toUpperCase().replace(" ", "_"))
                                                : null);
        if (empleo.getRangoDeEdad() != null && !empleo.getRangoDeEdad().isEmpty()) {
            int[] ageRange = extractAgeRange(empleo.getRangoDeEdad());
            queryFilterEmpleo.setEdadMinima(ageRange[0]);
            queryFilterEmpleo.setEdadMaxima(ageRange[1]);
        }
        else {
            queryFilterEmpleo.setEdadMinima(14);
            queryFilterEmpleo.setEdadMaxima(99);
        }
      return candidatoRepositoy.findCandidatosByFilter(queryFilterEmpleo);

    }

    public int[] extractAgeRange(String rangoDeEdad) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(rangoDeEdad);
        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        if (numbers.size() == 2) {
            return new int[]{numbers.get(0), numbers.get(1)};
        } else {
           return new int[]{14, 99};
        }
    }

    @Override
    public Empleo findById(Long id) {
      return  empleoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Empleo> getEmpleos() {
        return empleoRepository.findAll();
    }

    @Override
    public List<EmpleoDTO> getEmpleosDTO() {
        List<EmpleoDTO> empleos = this.getEmpleos().stream().map(empleo -> {
            EmpleoDTO empleoDTO = new EmpleoDTO();
            empleoDTO.setId(empleo.getId());
            empleoDTO.setNombrePuesto(empleo.getNombrePuesto());
            empleoDTO.setTareas(empleo.getTareas());
            empleoDTO.setEmpresaId(empleo.getEmpresa().getId());
            empleoDTO.setEmpresaNombre(empleo.getEmpresa().getNombre());
            empleoDTO.setCorreoEmpresa(empleo.getEmpresa().getEmails().get(0).getEmail());
            empleoDTO.setLocalidad(empleo.getLocalidades());
            empleoDTO.setIdEncuesta(empleo.getEmpresa().getEncuestaEmpresa().getIdEncuesta());
            empleoDTO.setActivo(empleo.getActivo());
            return empleoDTO;
        }).toList();

        return empleos;
    }

    @Override
    public ResponseEntity<?> candidatosSegueridosParaEmpleo(EmpleoRequest empleoRequest) throws IOException {
        try {
            List<Candidato> candidatos = candidatoRepositoy.findAllById(empleoRequest.getCandidatosId());

            if (candidatos.isEmpty()){
                return new ResponseEntity<>("No hay candidatos para sugerir", HttpStatus.OK);
            }

            List<File> pdfFiles = new ArrayList<>();
            StringBuilder pdfContentText = new StringBuilder();

            EmpleoDTO empleoDTO = obetenerEmpleoDto(empleoRequest);
            EmpleoDTO empleoDetalleDto = crearEmpleoDTO(empleoDTO.getId());

            String detalleTareaEmpelo = empleoDetalleDto.getDetalleTarea();


            for (Candidato candidato : candidatos){
                File file = pdfService.base64AsPdfFile(candidato.getNombre(), candidato.getCsvBase64());
                if(file != null){
                    pdfFiles.add(file);
                }
            }

            for (File file : pdfFiles){
                pdfContentText.append(pdfService.readPdfContent(file));
            }

            return chatGPTService.callToOpenAiApi(detalleTareaEmpelo, pdfContentText);

        }catch (Exception e){
            return new ResponseEntity<>("Ha ocurrido un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR) ;
        }

    }

    @Override
    public void updateActive(int i, Long id) {
        empleoRepository.updateActive(i, id);
    }

    private EmpleoDTO crearEmpleoDTO(Long empleoId) {
        Empleo empleo = this.findById(empleoId);
        EmpleoDTO empleoDTO = new EmpleoDTO();
        String detallesTarea = empleo.getTareas().stream().filter(tarea -> tarea instanceof TareaEsencial)
                                .flatMap(tarea -> tarea.getDetalleTarea().stream())
                                .map(detalleTarea -> detalleTarea.getDetalle())
                                .collect(Collectors.joining(" | "));
        empleoDTO.setDetalleTarea(detallesTarea);
        return empleoDTO;
    }

    private EmpleoDTO obetenerEmpleoDto(EmpleoRequest empleoRequest) {
        EmpleoDTO empleoDTO = new EmpleoDTO();
        empleoDTO.setId(empleoRequest.getEmpleoId());
        empleoDTO.setCandidatosId(empleoRequest.getCandidatosId());
        return empleoDTO;
    }


}
