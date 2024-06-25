package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Institucion;
import com.mides.core.repository.IInsititucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
public class InstitucionService implements IInstitucionService{
    @Autowired
    IInsititucionRepository insititucionRepository;
    @Override
    public void saveInstitucion(Institucion institucion) {
        insititucionRepository.save(institucion);
    }

    @Override
    public Institucion findInstitucion(Long id) {
        return null;
    }

    @Override
    public void editInstitucion(Institucion institucion) {

    }

    @Override
    @Transactional
    public void precargarInsituciones() {
        Institucion institucionEducativa = new Institucion(Institucion.Instituciondeseo.EDUCATIVA);
        Institucion institucionRecreativa = new Institucion(Institucion.Instituciondeseo.RECREATIVA);
        Institucion institucionRehabilitacion = new Institucion(Institucion.Instituciondeseo.REHABILITACION);
        Institucion institucionOtra = new Institucion(Institucion.Instituciondeseo.OTRA);
        this.saveInstitucion(institucionEducativa);
        this.saveInstitucion(institucionRecreativa);
        this.saveInstitucion(institucionRehabilitacion);
        this.saveInstitucion(institucionOtra);
    }

    @Override
    public List<Institucion> getInstituciones() {
       return insititucionRepository.getInstituciones();
    }
}
