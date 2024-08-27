package com.mides.core.service;

import com.mides.core.model.*;
import com.mides.core.repository.IActualizarEmpresaRepository;
import com.mides.core.repository.IAuditoriaEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActualizarEmpresaService implements IActualizarEmpresaService{

    @Autowired
    IActualizarEmpresaRepository actualizarEmpresaRepository;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IAuditoriaEmpresaRepository auditoriaEmpresaRepository;

    @Override
    public void actualizarCampo(String empresaId, String empleoId, String userName, String campo, String datoAct, String datoAnt, String lista, String subLista) throws Exception {
        if (empresaId.isEmpty() || empleoId.isEmpty() || userName.isEmpty()){
            throw new Exception("Empresa, empleo o ususario no pueden ser vacios.");
        }else{
            Long idEmpresa = Long.parseLong(empresaId);
            Long idEmpleo = Long.parseLong(empleoId);
            Empresa empresa = actualizarEmpresaRepository.findById(idEmpresa).orElse(null);
            Usuario usuario = usuarioService.getUsuario(userName);

            if (empresa == null || usuario == null ){
                throw new Exception("La empresa o el usuario no existe");
            }else{

                Empleo empleo = empresa.getUnEmpleo(idEmpleo);
                if (empleo == null){
                    throw new Exception("Empleo no encontrado");
                }else {
                    if (lista.isEmpty() && subLista.isEmpty()){
                        if(campo.isEmpty()){
                            throw new Exception("El campo a editar no puede ser vacio");
                        }else{
                            AuditoriaEmpresa auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", campo, datoAnt, datoAct, lista, LocalDate.now());

                            switch (campo){
                                case "nombre":
                                    auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                    empresa.setNombre(datoAct);
                                    actualizarEmpresaRepository.save(empresa);
                                    break;
                                case "ramaEconomica":
                                    auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                    empresa.setRamaEconomica(datoAct);
                                    actualizarEmpresaRepository.save(empresa);
                                    break;
                                case "rut":
                                    auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                    empresa.setRamaEconomica(datoAct);
                                    actualizarEmpresaRepository.save(empresa);
                                    break;
                                case "personaReferencia":
                                    auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                    empresa.setPersonaReferencia(datoAct);
                                    actualizarEmpresaRepository.save(empresa);
                                    break;
                                case "cvsEnviados":
                                    auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                    empresa.setCvsEnviados(datoAct);
                                    actualizarEmpresaRepository.save(empresa);

                                    break;
                                case "actividadEconomica":
                                    auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                    empresa.setActividadEconomica(datoAct);
                                    actualizarEmpresaRepository.save(empresa);
                                    break;
                                default:
                                    break;

                            }

                        }


                    }else{
                        AuditoriaEmpresa auditoriaEmpresa = null;
                        if(!lista.isEmpty() && subLista.isEmpty()){
                            if(campo.isEmpty()){
                                throw new Exception("El campo a editar no puede ser vacio");
                            }else{
                                switch (lista){
                                    case "datosAdicionalesEmpresa":
                                        DatosAdicionalesEmpresa dhe = empresa.getDatosAdicionalesEmpresa();
                                        auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", campo, datoAnt, datoAct, lista, dhe.getId(), LocalDate.now());
                                        auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                        actualizarDatosAdicionalesEmpresa(dhe, campo, datoAct);
                                        empresa.setDatosAdicionalesEmpresa(dhe);
                                        actualizarEmpresaRepository.save(empresa);
                                        break;
                                    case "direccion":
                                        Direccion direccion = empresa.getDireccion();
                                        auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", campo, datoAnt, datoAct, lista, direccion.getId(), LocalDate.now());
                                        auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                        actualizarDireccion(direccion, campo, datoAct);
                                        empresa.setDireccion(direccion);
                                        actualizarEmpresaRepository.save(empresa);
                                        break;
                                    case "emails":
                                        List<Email> emails = empresa.getEmails();
                                        Email email = null;
                                        for (Email unEmail : emails) {
                                            if(unEmail.getId() == Long.parseLong(campo)){
                                                email = unEmail;
                                                unEmail.setEmail(datoAct);
                                                break;
                                            }
                                        }

                                        if(email != null){
                                            auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", "Emails", datoAnt, datoAct, lista, email.getId(), LocalDate.now());
                                            auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                            empresa.setEmails(emails);
                                            actualizarEmpresaRepository.save(empresa);
                                        }else{
                                            throw new Exception("Email no encontrado");
                                        }
                                        break;
                                    case "empleo":
                                        auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", campo, datoAnt, datoAct, lista, empleo.getId(), LocalDate.now());
                                        auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                        actualizarEmpleoDatosPrincipales(empleo, campo, datoAct);
                                        empresa.setUnEmpleo(empleo);
                                        actualizarEmpresaRepository.save(empresa);


                                        break;
                                    case "encuestaEmpresa":
                                        EncuestaEmpresa encuestaEmpresa = empresa.getEncuestaEmpresa();
                                        auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", campo, datoAnt, datoAct, lista, encuestaEmpresa.getId(), LocalDate.now());
                                        auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                        actualizarEncuestaEmpresa(encuestaEmpresa, campo, datoAct);
                                        empresa.setEncuestaEmpresa(encuestaEmpresa);
                                        actualizarEmpresaRepository.save(empresa);
                                        break;
                                    case "telefonos":
                                        List<Telefono> telefonos = empresa.getTelefonos();
                                        Telefono telefono = null;
                                        for(Telefono unTelefono : telefonos){
                                            actualizarTelefono(unTelefono, campo, datoAct);
                                            telefono = unTelefono;
                                        }

                                        if(telefono != null){
                                            auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", campo, datoAnt, datoAct, lista, telefono.getId(), LocalDate.now());
                                            auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                            empresa.setTelefonos(telefonos);
                                            actualizarEmpresaRepository.save(empresa);

                                        }else {
                                            throw new Exception("Telefono no encontrad");
                                        }
                                        break;

                                }
                            }
                        }else{
                            if(!lista.isEmpty() && lista.equals("empleo")){

                                switch (subLista){
                                    case "conocimientosEspecificosEmpleo":
                                        ConocimientosEspecificosEmpleo cee = empleo.getConocimientosEspecificosEmpleo();
                                        auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", campo, datoAnt, datoAct, lista, cee.getId(), LocalDate.now());
                                        auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                        actualizarConocimientosEspecificosEmpleo(cee, campo, datoAct);
                                        empleo.setConocimientosEspecificosEmpleo(cee);
                                        empresa.setUnEmpleo(empleo);
                                        actualizarEmpresaRepository.save(empresa);
                                        break;
                                    case "tareas":
                                        String[] partes = campo.split("-");
                                        String indexTarea = partes[0];
                                        String campoAEditar = partes[1];
                                        Tarea tarea = empleo.getTareas().get(Integer.parseInt(indexTarea));

                                        if(campoAEditar.equals("nombre")){
                                            tarea.setNombre(datoAct);
                                            auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", "Nombre", datoAnt, datoAct, subLista, tarea.getId(), LocalDate.now());

                                        }else{
                                            tarea.setOtras(datoAct);
                                            auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", "Otras", datoAnt, datoAct, subLista, tarea.getId(), LocalDate.now());
                                        }

                                        auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                        empleo.setUnaTarea(tarea);
                                        empresa.setUnEmpleo(empleo);
                                        actualizarEmpresaRepository.save(empresa);
                                        break;
                                }
                            }else{
                                if(!lista.isEmpty() && lista.equals("tareas")){
                                    if (subLista.equals("detalleTarea")) {
                                        String[] partes = campo.split("-");
                                        String indexTarea = partes[0];
                                        String indexDetalleTarea = partes[1];
                                        String idDetalleTarea = partes[2];
                                        Long idDetalle = Long.parseLong(idDetalleTarea);

                                        Tarea tarea = empleo.getTareas().get(Integer.parseInt(indexTarea));
                                        DetalleTarea detalleTarea = tarea.getDetalleTarea().get(Integer.parseInt(indexDetalleTarea));

                                        if(detalleTarea.getId().equals(idDetalle)){
                                            detalleTarea.setDetalle(datoAct);
                                            auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Modificacion", "Detalle Tarea", datoAnt, datoAct, subLista, detalleTarea.getId(), LocalDate.now());
                                            auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                            tarea.setUnDetalleTarea(detalleTarea);
                                            empleo.setUnaTarea(tarea);
                                            empresa.setUnEmpleo(empleo);
                                            actualizarEmpresaRepository.save(empresa);

                                        }else{
                                            throw new Exception("Detalle de tarea no encontrada");
                                        }





                                    }

                                }
                            }

                        }
                    }

                }

            }

        }

    }

    @Override
    public void agregarASubLista(String empresaId, String empleoId, String userName, String lista, String subLista, String id) throws Exception {
        if (empresaId.isEmpty() || empleoId.isEmpty() || userName.isEmpty()){
            throw new Exception("Empresa, empleo o ususario no pueden ser vacios.");
        }else{
            Long idEmpresa = Long.parseLong(empresaId);
            Long idEmpleo = Long.parseLong(empleoId);
            Empresa empresa = actualizarEmpresaRepository.findById(idEmpresa).orElse(null);
            Usuario usuario = usuarioService.getUsuario(userName);

            if (empresa == null || usuario == null ){
                throw new Exception("La empresa o el usuario no existe");
            }else{
                Empleo empleo = empresa.getUnEmpleo(idEmpleo);
                if (empleo == null){
                    throw new Exception("Empleo no encontrado");
                }else{
                    if (!lista.isEmpty() && !subLista.isEmpty()){
                        AuditoriaEmpresa auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Agregar", "", "", "", subLista, LocalDate.now());

                        switch (subLista){
                            case "tareas-Esencial":
                                TareaEsencial tareaEsencial = new TareaEsencial();
                                tareaEsencial.setNombre(id);
                                tareaEsencial.setEmpleo(empleo);
                                empleo.getTareas().add(tareaEsencial);
                                auditoriaEmpresa.setCampo("Nueva tarea esencial");
                                auditoriaEmpresa.setDatoAct(id);
                                auditoriaEmpresaRepository.save(auditoriaEmpresa);
                            break;
                            case "tareas-NoEsencial":
                                TareaNoEsencial tareaNoEsencial = new TareaNoEsencial();
                                tareaNoEsencial.setNombre(id);
                                tareaNoEsencial.setEmpleo(empleo);
                                empleo.getTareas().add(tareaNoEsencial);
                                auditoriaEmpresa.setCampo("Nueva tarea No esencial");
                                auditoriaEmpresa.setDatoAct(id);
                                auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                break;
                            case "detalleTarea":
                                Tarea tarea = empleo.getTareas().get(Integer.parseInt(lista));
                                if (tarea == null){
                                    throw new Exception("Tarea no encontrada");
                                }else{
                                    DetalleTarea detalleTarea = new DetalleTarea();
                                    detalleTarea.setTarea(tarea);
                                    detalleTarea.setDetalle(id);
                                    List<Tarea> tareas = empleo.getTareas();
                                    for(Tarea untarea : tareas){
                                        if (untarea.getId().equals(tarea.getId())){
                                            untarea.getDetalleTarea().add(detalleTarea);
                                            break;
                                        }
                                    }
                                    empleo.setTareas(tareas);
                                    auditoriaEmpresa.setCampo("Nuevo detalle tarea");
                                    auditoriaEmpresa.setDatoAct(id);
                                    auditoriaEmpresaRepository.save(auditoriaEmpresa);
                                }

                                break;
                        }

                    }
                }


            }
        }

    }

    @Override
    public void elimnarSubLista(String empresaId, String empleoId, String userName, String lista, String subLista, String id, String nombreAEliminar, String posAElim) throws Exception {
        if (empresaId.isEmpty() || empleoId.isEmpty() || userName.isEmpty()){
            throw new Exception("Empresa, empleo o ususario no pueden ser vacios.");
        }else {
            Long idEmpresa = Long.parseLong(empresaId);
            Long idEmpleo = Long.parseLong(empleoId);
            Empresa empresa = actualizarEmpresaRepository.findById(idEmpresa).orElse(null);
            Usuario usuario = usuarioService.getUsuario(userName);

            if (empresa == null || usuario == null) {
                throw new Exception("La empresa o el usuario no existe");
            } else {
                Empleo empleo = empresa.getUnEmpleo(idEmpleo);
                if (empleo == null) {
                    throw new Exception("Empleo no encontrado");
                } else {
                    if (!lista.isEmpty() && !subLista.isEmpty()){
                        AuditoriaEmpresa auditoriaEmpresa = crearAuditoriaEmpresa(empresa, usuario, "Eliminar", "", "", "", subLista,null, LocalDate.now());

                        switch (subLista){
                            case "detalleTarea":
                                Tarea tarea = empleo.getTareas().get(Integer.parseInt(posAElim));
                                List<DetalleTarea> detalleTareas = tarea.getDetalleTarea();
                                for (DetalleTarea detalleTarea : detalleTareas){
                                    if (detalleTarea.getId().equals(Long.parseLong(id))){
                                        detalleTareas.remove(detalleTarea);
                                        auditoriaEmpresa.setIdTablaAEditar(detalleTarea.getId());
                                        break;
                                    }
                                }
                                tarea.setDetalleTarea(detalleTareas);
                                List<Tarea> tareas = empleo.getTareas();
                                for(Tarea unaTarea : tareas){
                                    if (unaTarea.getId().equals(tarea.getId())){
                                        tareas.remove(unaTarea);
                                        tareas.add(tarea);
                                        break;
                                    }
                                }
                                empleo.setTareas(tareas);
                                auditoriaEmpresa.setCampo("Eliminar detalle tarea");
                                auditoriaEmpresa.setDatoAnt(nombreAEliminar);
                                auditoriaEmpresaRepository.save(auditoriaEmpresa);


                                break;
                        }

                    }
                }
            }
        }



    }

    private void actualizarConocimientosEspecificosEmpleo(ConocimientosEspecificosEmpleo cee, String campo, String datoAct) {
        switch (campo) {
            case "computacion":
                cee.setComputacion(datoAct);
                break;
            case "idiomaCompetenciaYrequisito":
                cee.setIdiomaCompetenciaYrequisito(datoAct);
                break;
            case "idiomas":
                cee.setIdiomas(datoAct);
                break;
            case "ingles":
                cee.setIngles(datoAct);
                break;
            case "lee":
                cee.setLee(datoAct);
                break;
            case "nivelIdiomasRequeridosEscrituraIngles":
                cee.setNivelIdiomasRequeridosEscrituraIngles(datoAct);
                break;
            case "nivelIdiomasRequeridosEscrituraPortugues":
                cee.setNivelIdiomasRequeridosEscrituraPortugues(datoAct);
                break;
            case "nivelIdiomasRequeridosHablaIngles":
                cee.setNivelIdiomasRequeridosHablaIngles(datoAct);
                break;
            case "nivelIdiomasRequeridosHablaPortugues":
                cee.setNivelIdiomasRequeridosHablaPortugues(datoAct);
                break;
            case "nivelIdiomasRequeridosLecturaIngles":
                cee.setNivelIdiomasRequeridosLecturaIngles(datoAct);
                break;
            case "nivelIdiomasRequeridosLecturaPortugues":
                cee.setNivelIdiomasRequeridosLecturaPortugues(datoAct);
                break;
            case "otrosComputacion":
                cee.setOtrosComputacion(datoAct);
                break;
            case "portgues":
                cee.setPortgues(datoAct);
                break;
            default:
                break;
        }

    }

    private void actualizarTelefono(Telefono unTelefono, String campo, String datoAct) {
        switch (campo){
            case "duenioDos":
                unTelefono.setDuenioDos(datoAct);
                break;

            case "duenioUno":
                unTelefono.setDuenioUno(datoAct);
                break;

            case "numeroDos":
                unTelefono.setNumeroDos(datoAct);
                break;

            case "numeroUno":
                unTelefono.setNumeroUno(datoAct);
                break;
        }
    }

    private void actualizarEncuestaEmpresa(EncuestaEmpresa encuestaEmpresa, String campo, String datoAct) {
        switch (campo) {
            case "calificacionEncuesta":
                encuestaEmpresa.setCalificacionEncuesta(datoAct);
                break;
            case "comentarios":
                encuestaEmpresa.setComentarios(datoAct);
                break;
            case "fechaDeCreacion":
                encuestaEmpresa.setFechaDeCreacion(LocalDate.parse(datoAct));
                break;
            case "idEncuesta":
                encuestaEmpresa.setIdEncuesta(Long.parseLong(datoAct));
                break;
            case "observaciones":
                encuestaEmpresa.setObservaciones(datoAct);
                break;
            default:
                break;
        }

    }

    private void actualizarEmpleoDatosPrincipales(Empleo empleo, String campo, String datoAct) {
        switch (campo) {
            case "activo":
                empleo.setActivo(Integer.parseInt(datoAct));
                break;
            case "cargaHorariaSemanal":
                empleo.setCargaHorariaSemanal(datoAct);
                break;
            case "cargaHorariaTipo":
                empleo.setCargaHorariaTipo(datoAct);
                break;
            case "categoria":
                empleo.setCategoria(datoAct);
                break;
            case "categoriaLibretaConducir":
                empleo.setCategoriaLibretaConducir(datoAct);
                break;
            case "codigo":
                empleo.setCodigo(Integer.parseInt(datoAct));
                break;
            case "contratoATermino":
                empleo.setContratoATermino(datoAct);
                break;
            case "companeros":
                empleo.setCompaneros(datoAct);
                break;
            case "departamento":
                empleo.setDepartamento(datoAct);
                break;
            case "diasDeSemanaParaCubrirPuesto":
                empleo.setDiasDeSemanaParaCubrirPuesto(datoAct);
                break;
            case "diasDeSemanaParaCubrirPuestoOtro":
                empleo.setDiasDeSemanaParaCubrirPuestoOtro(datoAct);
                break;
            case "detalleSalarial":
                empleo.setDetalleSalarial(datoAct);
                break;
            case "disponibilidadHoraria":
                empleo.setDisponibilidadHoraria(datoAct);
                break;
            case "edadPreferente":
                empleo.setEdadPreferente(datoAct);
                break;
            case "experienciaPrevia":
                empleo.setExperienciaPrevia(datoAct);
                break;
            case "formacionAcademica":
                empleo.setFormacionAcademica(datoAct);
                break;
            case "implicaDesplazamientos":
                empleo.setImplicaDesplazamientos(datoAct);
                break;
            case "libretaConducir":
                empleo.setLibretaConducir(datoAct);
                break;
            case "nombrePuesto":
                empleo.setNombrePuesto(datoAct);
                break;
            case "localidades":
                empleo.setLocalidades(datoAct);
                break;
            case "nroPuestosDisponible":
                empleo.setNroPuestosDisponible(Integer.parseInt(datoAct));
                break;
            case "plazoContrato":
                empleo.setPlazoContrato(datoAct);
                break;
            case "rangoDeEdad":
                empleo.setRangoDeEdad(datoAct);
                break;
            case "remuneracionOfrecida":
                empleo.setRemuneracionOfrecida(datoAct);
                break;
            case "ritmoImpuesto":
                empleo.setRitmoImpuesto(datoAct);
                break;
            case "supervisores":
                empleo.setSupervisores(datoAct);
                break;
            case "subordinados":
                empleo.setSubordinados(datoAct);
                break;
            case "tiempoDeExperienciaMinima":
                empleo.setTiempoDeExperienciaMinima(datoAct);
                break;
            case "tipoRemuneracion":
                empleo.setTipoRemuneracion(datoAct);
                break;
            case "trabajoAlExterior":
                empleo.setTrabajoAlExterior(datoAct);
                break;
            case "tipoRemuneracionOtro":
                empleo.setTipoRemuneracionOtro(datoAct);
                break;
            default:
                break;
        }

    }

    private void actualizarDireccion(Direccion direccion, String campo, String datoAct) {
        switch (campo){
            case "apartamento":
                direccion.setApartamento(datoAct);
                break;
            case "calle":
                direccion.setCalle(datoAct);
                break;
            case "calleIncluida":
                direccion.setCalleIncluida(Integer.parseInt(datoAct));
                break;
            case "departamento":
                direccion.setDepartamento(datoAct);
                break;
            case "esquinaDos":
                direccion.setEsquinaDos(datoAct);
                break;
            case "esquinaUno":
                direccion.setEsquinaUno(datoAct);
                break;
            case "kilometro":
                direccion.setKilometro(datoAct);
                break;
            case "localidad":
                direccion.setLocalidad(datoAct);
                break;
            case "numeroPuerta":
                direccion.setNumeroPuerta(Integer.parseInt(datoAct));
                break;
            case "observacionesDireccion":
                direccion.setObservacionesDireccion(datoAct);
                break;

        }
    }

    private void actualizarDatosAdicionalesEmpresa(DatosAdicionalesEmpresa dhe, String campo, String datoAct) {
        switch (campo){
            case "empleadosContratadosConDiscapacidad":
                dhe.setEmpleadosContratadosConDiscapacidad(datoAct);
                break;
            case "empresaDesierta":
                dhe.setEmpresaDesierta(Integer.parseInt(datoAct));
                break;
            case "empresaSinRespuesta":
                dhe.setEmpresaSinRespuesta(Integer.parseInt(datoAct));
                break;
            case "fechaRespuesta":
                dhe.setFechaRespuesta(LocalDate.parse(datoAct));
                break;
            case "tuvoEmpleadosConDiscapacidad":
                dhe.setTuvoEmpleadosConDiscapacidad(datoAct);
                break;

        }
    }

    private AuditoriaEmpresa crearAuditoriaEmpresa(Empresa empresa, Usuario usuario, String tipo, String campo, String datoAnt, String datoAct, String tablaAEditar, LocalDate fechaCambio){
        return new AuditoriaEmpresa(empresa, usuario, tipo, campo, datoAnt, datoAct, tablaAEditar, fechaCambio);
    }
    private AuditoriaEmpresa crearAuditoriaEmpresa(Empresa empresa, Usuario usuario, String tipo, String campo, String datoAnt, String datoAct, String tablaAEditar, Long idTablaAEditar, LocalDate fechaCambio){
        return new AuditoriaEmpresa(empresa, usuario, tipo, campo, datoAnt, datoAct, tablaAEditar, idTablaAEditar, fechaCambio);
    }
}
