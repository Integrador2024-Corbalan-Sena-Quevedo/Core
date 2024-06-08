package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Educacion;
import com.mides.core.model.Institucion;
import com.mides.core.repository.IEducacionRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EducacionService implements IEducacionService{

    @Autowired
    IEducacionRepository educacionRepository;

    @Override
    public void saveEducacion(Educacion educacion) {
        educacionRepository.save(educacion);
    }

    @Override
    public Educacion findEducacion(Long id) {
        return null;
    }

    @Override
    public void editEducacion(Educacion educacion) {

    }

    @Override
    public void processEducacion(List<Map<String, String>> csvData, Candidato candidato, List<Institucion> institucions) {

        Educacion educacion = new Educacion();

        for (Map<String, String> row : csvData) {
            educacion.setSituacionActual(row.get("Asist_educ"));
            educacion.setNivelEducativo(row.get("Nivel_educ"));
            educacion.setAniosEducacion(Integer.parseInt(row.get("Anios_educ")));
            educacion.setParticipacionInstitucion(row.get("Particip_inst"));
            educacion.setInstitucionesDeseo(getInstitucionesDeseo(csvData, institucions));
            educacion.setDeseoDeOtrasInstituciones(row.get("Tipo_inst_deseo - Otros"));
            educacion.setNombreInstitucion(row.get("Nombre_inst"));
            educacion.setCandidato(candidato);
            educacion.setEducacionNoFormal(row.get("Educ_no_formal"));
            educacion.setRazonDejaEstudios(row.get("Razon_deja_estudios"));
            educacion.setDeseaParticiparEnAlgunaInstitucion(row.get("Deseo_particip"));
        }
        this.saveEducacion(educacion);

    }


    public List<Institucion> getInstitucionesDeseo(List<Map<String, String>> csvData, List<Institucion> institucions){
        List<Institucion> institucionesDeseo = new ArrayList<>();
        boolean deseaInstitucion;
        for (Map<String, String> row : csvData) {
            deseaInstitucion = row.get("Tipo_inst_deseo - Educativa").equals("1");
            if(deseaInstitucion){
                institucionesDeseo.add(institucions.get(0));
            }
            deseaInstitucion = row.get("Tipo_inst_deseo - Recreativa").equals("1");
            if(deseaInstitucion){
                institucionesDeseo.add(institucions.get(1));
            }
            deseaInstitucion = row.get("Tipo_inst_deseo - RehabilitaciÃ³n").equals("1");
            if(deseaInstitucion){
                institucionesDeseo.add(institucions.get(2));
            }
            deseaInstitucion = row.get("Tipo_inst_deseo - Otra").equals("1");
            if(deseaInstitucion){
                institucionesDeseo.add(institucions.get(3));
            }
        }
        return  institucionesDeseo;

    }

}
