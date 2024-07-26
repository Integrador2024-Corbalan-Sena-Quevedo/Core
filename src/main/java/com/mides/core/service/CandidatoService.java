package com.mides.core.service;

import com.mides.core.model.*;
import com.mides.core.repository.ICandidatoRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CandidatoService implements ICandidatoSevice{
    @Autowired
    ICandidatoRepositoy candidatoRepositoy;
    @Override
    public List<Candidato> getCandidatos() {
        return candidatoRepositoy.findAll();
    }

    @Override
    public void saveCandidato(Candidato candidato) {
        if(candidatoRepositoy.getCandidatoPorCI(candidato.getDocumento()) == null){
            candidatoRepositoy.save(candidato);
        }
    }

    @Override
    public void deleteCandidato(Long id) {
    }

    @Override
    public Candidato findCandidato(String documento) {
      return  candidatoRepositoy.getCandidatoPorCI(documento);
    }

    @Override
    public Candidato findCandidatoById(Long id) {
        return  candidatoRepositoy.findById(id).orElse(null);
    }

    @Override
    public void editCandidato(Candidato candidato) {

    }

    @Override
//    @Transactional
    public Candidato processCandidato(List<Map<String, String>> csvData, List<AyudaTecnica> ayudaTecnicas, List<Prestacion> prestaciones,List<Area> areas, List<Apoyo> apoyos) throws ParseException {
        Candidato candidato = new Candidato();
        DateTimeFormatter fechaNacimientoFormato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Map<String, String> row : csvData) {
            if(!row.get("NroCI").isEmpty()){
                candidato.setDocumento(row.get("NroCI"));
                candidato.setTipoDocumento("Nacional");
            }else{
                candidato.setDocumento(row.get("Nro_doc_ext"));
                candidato.setTipoDocumento("Extranjero");
            }
            candidato.setNombre(row.get("Nombre"));
            candidato.setApellido(row.get("Apellidos"));
            candidato.setSexo(row.get("Sexo"));
            candidato.setIdentidadGenero(row.get("Id_genero"));
            candidato.setEstadoCivil(row.get("Est_civil"));
            LocalDate fechaNacimiento = LocalDate.parse(row.get("Fecha_de_nacimiento"), fechaNacimientoFormato);
            candidato.setFechaDeNacimiento(fechaNacimiento);
            candidato.setAyudaTecnicas(getAyudaTecnicas(csvData, ayudaTecnicas));
            candidato.setPrestaciones(getPrestaciones(csvData,prestaciones));
            candidato.setApoyos(getApoyos(csvData, apoyos));
            candidato.setAreas(getAreas(csvData, areas));

        }
        this.saveCandidato(candidato);
        return candidato;
    }

    private List<Apoyo> getApoyos(List<Map<String, String>> csvData, List<Apoyo> apoyos) {

        List<Apoyo> apoyosDelCandidato = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Apoyo_cognitivo - ") && value.equals("1")) {
                    String nombreApoyoCognitivo = key.substring("Apoyo_cognitivo - ".length());
                    for (Apoyo apoyo : apoyos) {
                        if (apoyo instanceof Cognitivo) {
                            if (apoyo.getNombre().equals(nombreApoyoCognitivo)) {
                                Cognitivo cognitivo = (Cognitivo) apoyo;
                                cognitivo.setDescripcion(row.get("Descripción apoyo técnico cognitivo"));
                                apoyosDelCandidato.add(cognitivo);
                            }
                        }
                    }
                }
                else if (key.startsWith("Apoyo_sensorial - ") && value.equals("1")){
                    String nombreApoyoSensorial= key.substring("Apoyo_sensorial - ".length());
                    for (Apoyo apoyo : apoyos) {
                        if (apoyo instanceof Sensorial) {
                            if (apoyo.getNombre().equals(nombreApoyoSensorial)) {
                                Sensorial sensorial = (Sensorial) apoyo;
                                sensorial.setDescripcion(row.get("Descripción apoyo técnico sensorial"));
                                apoyosDelCandidato.add(sensorial);
                            }
                        }
                    }
                }
                else if (key.startsWith("apoyos_fisicos - ") && value.equals("1")){
                    String nombreApoyoFisico= key.substring("apoyos_fisicos - ".length());
                    for (Apoyo apoyo : apoyos) {
                        if (apoyo.getNombre().equals(nombreApoyoFisico)) {
                            if (apoyo instanceof Fisico) {
                                Fisico fisico = (Fisico) apoyo;
                                fisico.setDescripcion(row.get("Descripción apoyo técnico físico"));
                                apoyosDelCandidato.add(fisico);
                            }
                        }
                    }
                }
            }
        }
        return  apoyosDelCandidato;
    }

    private List<Area> getAreas(List<Map<String, String>> csvData, List<Area> areas) {
        List<Area> areasDelCandidato = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Areas - ") && "1".equals(value)) {
                    String nombreArea = key.substring("Areas - ".length());
                    for (Area area : areas) {
                        if (area.getNombre().equals(nombreArea)) {
                            areasDelCandidato.add(area);
                        }
                    }
                }
            }
        }
        return areasDelCandidato;

    }

    private List<Prestacion> getPrestaciones(List<Map<String, String>> csvData, List<Prestacion> prestaciones) {
        List<Prestacion> prestacionesDelCandidato = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Prestaciones - ") && "1".equals(value)) {
                    String nombrePrestacion = key.substring("Prestaciones - ".length());
                    for (Prestacion prestacion : prestaciones) {
                        if (prestacion.getNombre().equals(nombrePrestacion)) {
                            prestacionesDelCandidato.add(prestacion);
                        }
                    }
                }
            }
        }
        return  prestacionesDelCandidato;
    }

    private List<AyudaTecnica> getAyudaTecnicas(List<Map<String, String>> csvData, List<AyudaTecnica> ayudaTecnicas) {
        List<AyudaTecnica> ayudasTecnicasDelCandidato = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key.startsWith("Ayudas_tec_cuales - ") && "1".equals(value)) {
                    String nombreAyuda = key.substring("Ayudas_tec_cuales - ".length());
                   for (AyudaTecnica ayudaTecnica : ayudaTecnicas){
                       if (ayudaTecnica.getNombre().equals(nombreAyuda)){
                           ayudasTecnicasDelCandidato.add(ayudaTecnica);
                       }
                   }
                }
            }
        }
        return  ayudasTecnicasDelCandidato;
    }


}
