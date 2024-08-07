package com.mides.core.service;

import com.mides.core.dto.SeguimientoDTO;
import com.mides.core.model.*;
import com.mides.core.repository.IDetalleSeguimientoRepository;
import com.mides.core.repository.ISeguimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguimientoService implements ISeguimientoService {

    @Autowired
    ISeguimientoRepository seguimientoRepository;
    @Autowired
    IDetalleSeguimientoRepository detalleSeguimientoRepository;

    @Autowired
    IEmpresaSevice empresaSevice;

    @Autowired
    ICandidatoSevice candidatoSevice;

    @Autowired
    IEmpleoService empleoService;

    @Override
    @Transactional
    public void saveSeguimiento(Seguimiento seguimiento) {
        if (seguimiento.getCandidato() != null){
            seguimientoRepository.save(seguimiento);
        }
    }
    @Transactional
    private void saveDetalleSeguimiento(List<DetalleSeguimiento> detalleSeguimientos) {
        for (DetalleSeguimiento detalleSeguimiento : detalleSeguimientos){
            if (detalleSeguimiento != null){
                detalleSeguimientoRepository.save(detalleSeguimiento);
            }
        }
    }


    @Override
    @Transactional
    public void processSeguimiento(SeguimientoDTO seguimientoDTO) {
        Seguimiento seguimiento = new Seguimiento();
        if (seguimientoDTO.getSeguimientoId() != null){
            seguimiento.setId(seguimientoDTO.getSeguimientoId());
        }
        List<DetalleSeguimiento> detallesSeguimiento = processDetalleSeguimiento(seguimientoDTO);

        seguimiento.setDetalleSeguimientos(detallesSeguimiento);

        Candidato candidato = candidatoSevice.findCandidato(seguimientoDTO.getDocumentoEmpleado());
        if (candidato != null) {
            seguimiento.setCandidato(candidato);
            String cel = candidato.getTelefonos().stream().map(Telefono::getNumeroUno).collect(Collectors.joining());
            if (cel.isEmpty()){
                cel = candidato.getTelefonos().stream().map(Telefono::getNumeroDos).collect(Collectors.joining());
            }
            seguimiento.setTelefonoEmpleado(cel);
            seguimiento.setNombreEmpleado(candidato.getNombre() + " " + candidato.getApellido());
            seguimiento.setDocumentoEmpleado(candidato.getDocumento());
        }

        Empresa empresa = empresaSevice.findEmpresaById(seguimientoDTO.getEmpresaId());
        if (empresa != null){
            seguimiento.setEmpresa(empresa);
        }

        seguimiento.setDiscapacidades(seguimientoDTO.getDiscapacidades());
        seguimiento.setOperadorLaboral(seguimientoDTO.getOperadorLaboral());
        seguimiento.setLocalidad(seguimientoDTO.getLocalidad());
        seguimiento.setTramite(seguimientoDTO.getTramite());
        seguimiento.setNombreEncargado(seguimientoDTO.getNombreEncargado());
        seguimiento.setEmailEncargado(seguimientoDTO.getEmailEncargado());


        for (DetalleSeguimiento detalleSeguimiento : detallesSeguimiento) {
            detalleSeguimiento.setSeguimiento(seguimiento);
        }

        Empleo empleo = empleoService.findById(seguimientoDTO.getEmpleoId());
        if (empleo != null){
            seguimiento.setEmpleo(empleo);
            empleoService.updateActive(1,empleo.getId());
        }
        saveSeguimiento(seguimiento);
        if (seguimiento.getId() == null){
            saveDetalleSeguimiento(detallesSeguimiento);
        }

    }

    @Override
    public List<SeguimientoDTO> getSeguimientosDTO() {

        List<SeguimientoDTO> seguimientos = seguimientoRepository.findAll().stream().map(seguimiento -> {
            List<String> detalleSeguimiento = seguimiento.getDetalleSeguimientos().stream()
                    .map(DetalleSeguimiento::getDetalle)
                    .collect(Collectors.toList());
            SeguimientoDTO seguimientoDTO = new SeguimientoDTO();
            seguimientoDTO.setSeguimientoId(seguimiento.getId());
            seguimientoDTO.setDocumentoEmpleado(seguimiento.getDocumentoEmpleado());
            seguimientoDTO.setEmailEncargado(seguimiento.getEmailEncargado());
            seguimientoDTO.setFechaIngresoEmpleado(seguimiento.getFechaIngresoEmpleado());
            seguimientoDTO.setLocalidad(seguimiento.getLocalidad());
            seguimientoDTO.setNombreEmpleado(seguimiento.getNombreEmpleado());
            seguimientoDTO.setNombreEncargado(seguimiento.getNombreEncargado());
            seguimientoDTO.setTelefonoEmpleado(seguimiento.getTelefonoEmpleado());
            seguimientoDTO.setTramite(seguimiento.getTramite());
            seguimientoDTO.setNombreEmpresa(seguimiento.getEmpresa().getNombre());
            seguimientoDTO.setEmpresaId(seguimiento.getEmpresa().getId());
            seguimientoDTO.setNombreEmpleo(seguimiento.getEmpleo().getNombrePuesto());
            seguimientoDTO.setEmpleoId(seguimiento.getEmpleo().getId());
            seguimientoDTO.setDiscapacidades(seguimiento.getDiscapacidades());
            seguimientoDTO.setOperadorLaboral(seguimiento.getOperadorLaboral());
            seguimientoDTO.setDetalles(detalleSeguimiento);
            return seguimientoDTO;
        }).collect(Collectors.toList());

        return seguimientos;
    }


    @Transactional
    private List<DetalleSeguimiento> processDetalleSeguimiento(SeguimientoDTO seguimientoDTO) {
        List<DetalleSeguimiento> detalleSeguimientos = new ArrayList<>();
        for (String detalle : seguimientoDTO.getDetalles()) {
            DetalleSeguimiento detalleSeguimiento = new DetalleSeguimiento(detalle, LocalDate.now());
            detalleSeguimientos.add(detalleSeguimiento);
        }
        return detalleSeguimientos;
    }
}
