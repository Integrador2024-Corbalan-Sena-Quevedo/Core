package com.mides.core.service;

import com.mides.core.model.*;
import com.mides.core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ActualizarCandidatoService implements IActualizarCandidatoService{

    @Autowired
    IPrestacionRepository prestacionRepository;
    @Autowired
    IAyudaTecnicaRepository ayudaTecnicaRepository;
    @Autowired
    IAreaRepository areaRepository;
    @Autowired
    IApoyoRepository apoyoRepository;
    @Autowired
    IEmailRepository emailRepository;
    @Autowired
    IGustoLaboralRepository gustoLaboralRepository;

    @Autowired
    IActitudRepository actitudRepository;

    @Autowired
    IMotivoDesempleoRepository motivoDesempleoRepository;

    @Autowired
    ITurnoRepository iturnoRepository;

    @Autowired
    IInsititucionRepository insititucionRepository;
    @Autowired
    IActualizarCandidatoRepository actualizarCandidatoRepository;

    @Autowired
    IAuditoriaCandidatoService auditoriaCandidatoService;




    @Override
    public void actualizarCampo(String candidatoId, String campo, String datoAct, String datoAnt, String lista, String subLista) throws Exception {
        long idCandidato = Long.parseLong(candidatoId);
        Candidato candidato = actualizarCandidatoRepository.findById(idCandidato).orElse(null);


        try {
            if(candidato != null){
                if(!lista.isEmpty() && !subLista.isEmpty()){
                    System.out.println("Campo: "+campo);
                    System.out.println("Dato: "+datoAct);
                    System.out.println("Lista: "+lista);
                    System.out.println("SubLista: "+subLista);

                    switch (subLista) {
                        case "candidatoIdiomas":
                            // Actualiza el campo candidatoIdiomas
                            break;
                        case "tipoDiscapacidades":
                            // edito con sublista tipoDiscapcidades
                            break;
                    }
                }

                if(!subLista.isEmpty() && lista.isEmpty()) {
                    System.out.println("Campo: "+campo);
                    System.out.println("Dato: "+datoAct);
                    System.out.println("Dato: "+datoAnt);
                    System.out.println("Lista: "+lista);
                    System.out.println("SubLista: "+subLista);
                    switch (subLista) {
                        case "datosAdicionalesCandidato":
                            DatosAdicionalesCandidato datosAdicionalesCandidato = candidato.getDatosAdicionalesCandidato();
                            if(datosAdicionalesCandidato!= null){
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, datosAdicionalesCandidato.getId(), LocalDate.now()));
                                actualizarDatosAdicionales(candidato.getDatosAdicionalesCandidato(), campo, datoAct);
                                candidato.setDatosAdicionalesCandidato(datosAdicionalesCandidato);
                                actualizarCandidatoRepository.save(candidato);

                            }else{
                                throw new Exception("Datos adicionales del candidato no encontrados");
                            }
                            break;
                        case "apellido":
                            auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, LocalDate.now()));
                            candidato.setApellido(datoAct);
                            actualizarCandidatoRepository.save(candidato);
                            break;
                        case "direccion":
                            Dirreccion dirreccion = candidato.getDirreccion();

                            if(dirreccion != null){
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, dirreccion.getId(), LocalDate.now()));
                                actualizarDireccion(dirreccion, campo, datoAct);
                                candidato.setDirreccion(dirreccion);
                                actualizarCandidatoRepository.save(candidato);

                            }else{
                                throw new Exception("El campo no puede ser vacio");
                            }

                            break;
                        case "disponibilidadHoraria":
                            DisponibilidadHoraria dh = candidato.getDisponibilidadHoraria();
                            if(dh != null){
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, lista, dh.getId(), LocalDate.now()));
                                actualizarDisponibilidadHoraria(dh, campo, datoAct);
                                candidato.setDisponibilidadHoraria(dh);
                                actualizarCandidatoRepository.save(candidato);
                            }else {
                                throw new Exception("El campo no puede ser vacio");
                            }
                            break;

                        case "discapacidad":
                            Discapacidad discapacidad = candidato.getDiscapacidad();
                            if (discapacidad != null) {
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, discapacidad.getId(), LocalDate.now()));
                                discapacidad.setDiagnostico(datoAct);
                                candidato.setDiscapacidad(discapacidad);
                                actualizarCandidatoRepository.save(candidato);
                            }else{
                                throw new Exception("El campo no puede ser vacio");
                            }

                            break;

                        case "documento":
                            auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, LocalDate.now()));
                            candidato.setDocumento(datoAct);
                            actualizarCandidatoRepository.save(candidato);
                            break;
                        case "educacion":
                            Educacion educacion = candidato.getEducacion();
                            if(educacion != null){
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, educacion.getId(), LocalDate.now()));
                                actualizarEducacion(educacion, campo, datoAct);
                                candidato.setEducacion(educacion);
                                actualizarCandidatoRepository.save(candidato);

                            }else{
                                throw new Exception("El campo no puede ser vacio");
                            }
                            break;
                        case "emails":
                            List<Email> emails = candidato.getEmails();
                            Email email = null;
                            for (Email unEmail : emails) {
                                if(unEmail.getId() == Long.parseLong(campo)){
                                    email = unEmail;
                                    unEmail.setEmail(datoAct);
                                }
                            }

                            if(email != null){
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, email.getId(), LocalDate.now()));
                                candidato.setEmails(emails);
                                actualizarCandidatoRepository.save(candidato);
                            }else{
                                throw new Exception("Email no encontrado");
                            }

                            break;
                        case "encuestaCandidato":
                            EncuestaCandidato encuestaCandidato = candidato.getEncuestaCandidato();
                            if(encuestaCandidato != null){
                                if(campo.equals("fechaCreacion") || campo.equals("fechaFinalizacion")){
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    if(campo.equals("fechaCreacion")){
                                        String fechaHoraString = encuestaCandidato.getFechaCreacion().format(formatter);
                                        auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, fechaHoraString, datoAct, subLista, encuestaCandidato.getId(), LocalDate.now()));
                                        actualizarEncuesta(encuestaCandidato, campo, datoAct);
                                        candidato.setEncuestaCandidato(encuestaCandidato);
                                        actualizarCandidatoRepository.save(candidato);
                                    }else{
                                        String fechaHoraString = encuestaCandidato.getFechaFinalizacion().format(formatter);
                                        auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, fechaHoraString, datoAct, subLista, encuestaCandidato.getId(), LocalDate.now()));
                                        actualizarEncuesta(encuestaCandidato, campo, datoAct);
                                        candidato.setEncuestaCandidato(encuestaCandidato);
                                        actualizarCandidatoRepository.save(candidato);

                                    }
                                }else{
                                    auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, encuestaCandidato.getId(), LocalDate.now()));
                                    actualizarEncuesta(encuestaCandidato, campo, datoAct);
                                    candidato.setEncuestaCandidato(encuestaCandidato);
                                    actualizarCandidatoRepository.save(candidato);
                                }

                            }else{
                                throw new Exception("Encuenta no encontrada");
                            }


                            break;
                        case "estadoCivil":
                            auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, LocalDate.now()));
                            candidato.setEstadoCivil(datoAct);
                            actualizarCandidatoRepository.save(candidato);
                            break;

                        case "fecha_de_nacimiento":
                            auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, LocalDate.now()));
                            candidato.setFecha_de_nacimiento(LocalDate.parse(datoAct));
                            actualizarCandidatoRepository.save(candidato);
                            break;
                        case "habilidad":
                            Habilidad habilidad = candidato.getHabilidad();
                            if(habilidad != null){
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, habilidad.getId(), LocalDate.now()));
                                actualizarHabilidad(habilidad, campo, datoAct);
                                candidato.setHabilidad(habilidad);
                                actualizarCandidatoRepository.save(candidato);
                            }else {
                                throw new Exception("Habilidad no encontrada");
                            }
                            break;
                        case "identidadGenero":
                            auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, LocalDate.now()));
                            candidato.setIdentidadGenero(datoAct);
                            actualizarCandidatoRepository.save(candidato);
                            break;
                        case "nombre":
                            auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, LocalDate.now()));
                            candidato.setNombre(datoAct);
                            actualizarCandidatoRepository.save(candidato);
                            break;
                        case "salud":
                            Salud salud = candidato.getSalud();
                            if(salud != null){
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, salud.getId(), LocalDate.now()));
                                actualizarSalud(salud, campo, datoAct);
                                candidato.setSalud(salud);
                                actualizarCandidatoRepository.save(candidato);
                            }else {
                                throw new Exception("Salud no encontrada");
                            }

                            break;
                        case "sexo":
                            auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, LocalDate.now()));
                            candidato.setSexo(datoAct);
                            actualizarCandidatoRepository.save(candidato);
                            break;
                        case "telefonos":
                            List<Telefono> telefonos = candidato.getTelefonos();
                            Telefono telefono = null;
                            for(Telefono unTelefono : telefonos){
                                actualizarTelefono(unTelefono, campo, datoAct);
                                telefono = unTelefono;
                            }

                            if(telefono != null){
                                auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, telefono.getId(), LocalDate.now()));
                                candidato.setTelefonos(telefonos);
                                actualizarCandidatoRepository.save(candidato);

                            }else {
                                throw new Exception("Telefonos no encontrada");
                            }



                            break;
                        case "tipoDocumento":
                            auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, LocalDate.now()));
                            candidato.setTipoDocumento(datoAct);
                            actualizarCandidatoRepository.save(candidato);
                            break;

                    }


                }else{
                    if (subLista.isEmpty() && !lista.isEmpty()){
                        switch (lista) {
                            case "experienciaLaboral":
                                ExperienciaLaboral experienciaLaboral = candidato.getExperienciaLaboral();
                                if(experienciaLaboral != null){
                                    auditoriaCandidatoService.guardar(crearAuditoria("Modificación",candidato, campo, datoAnt, datoAct, subLista, experienciaLaboral.getId(), LocalDate.now()));
                                    actualizarExperienciaLaboral(experienciaLaboral, campo, datoAct);
                                    candidato.setExperienciaLaboral(experienciaLaboral);
                                    actualizarCandidatoRepository.save(candidato);
                                } else {
                                    throw new Exception("Experiencia Laboral no encontrada");
                                }
                                break;
                        }

                    }
                }
            }else{
                throw new Exception("Candidato no encontrado");
            }

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public void elimnarSubLista(String candidatoId, String lista, String subLista, String idAEliminar) throws Exception {
        System.out.println("Candidato: "+candidatoId);
        System.out.println("idAEliminar: "+idAEliminar);
        System.out.println("Lista: "+lista);
        System.out.println("SubLista: "+subLista);

        Candidato candidato = actualizarCandidatoRepository.findById(Long.parseLong(candidatoId)).orElse(null);

        if(candidato != null){
            Long id = Long.parseLong(idAEliminar);
            boolean cambio = false;
            switch (lista){

                case "educacion":
                    Institucion ins = null;
                    List<Institucion> instituciones = candidato.getEducacion().getInstitucionesDeseo();
                    for(Institucion unInstitucion : instituciones){
                        if (unInstitucion.getId().equals(id)){
                            instituciones.remove(unInstitucion);
                            cambio = true;
                            ins = unInstitucion;
                            break;
                        }
                    }
                    if(cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion" ,candidato, "InstitucionesDeseo", ins.getTipo().name(), "", lista, ins.getId(), LocalDate.now()));
                    }else {
                        throw new Exception("Institucion no encontrada");
                    }

                    break;
                case "disponibilidadHoraria":
                    Turno turno = null;
                    List<Turno> turnos = candidato.getDisponibilidadHoraria().getTurnos();

                    for(Turno unTurno : turnos){
                        if (unTurno.getId().equals(id)){
                            turnos.remove(unTurno);
                            cambio = true;
                            turno = unTurno;
                            System.out.println("Encontro");
                            break;
                        }
                        System.out.println("Id del turno:" + unTurno.getId());
                    }

                    if(cambio){
                        System.out.println("Cambio" + cambio);

                        auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "Turnos", turno.getTurno().name(), "", lista, turno.getId(), LocalDate.now()));
                    }else {
                        System.out.println("Turno no encontrada"+ cambio);

                        throw new Exception("Turno no encontrada");
                    }
                    break;
                case "experienciaLaboral":
                    eliminarDeExperienciaLaboral(candidato, subLista, id);
                    break;

                case  "emails" :
                    Email email = null;
                    List<Email> emails = candidato.getEmails();
                    for(Email unEmail : emails){
                        if (unEmail.getId().equals(id)){
                            emails.remove(unEmail);
                            email = unEmail;
                            emailRepository.delete(unEmail);
                            cambio = true;
                            break;
                        }
                    }
                    if(cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "Email", email.getEmail(), "", lista, email.getId(), LocalDate.now()));
                    }else {
                        throw new Exception("Email no encontrado");
                    }
                    break;
                case "apoyos":
                    Apoyo apoyo = null;
                    List<Apoyo> apoyos = candidato.getApoyos();
                    for(Apoyo unApoyo : apoyos){
                        if (unApoyo.getId().equals(id)) {
                            apoyos.remove(unApoyo);
                            apoyo = unApoyo;
                            cambio = true;
                            break;
                        }
                    }
                    if (cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "Apoyos", apoyo.getNombre(), "", subLista, apoyo.getId(), LocalDate.now()));

                    }else {
                        throw new Exception("Apoyo no encontrado");
                    }
                    break;
                case "areas":
                    Area area = null;
                    List<Area> areas = candidato.getAreas();
                    for(Area unArea : areas){
                        if (unArea.getId().equals(id)){
                            areas.remove(unArea);
                            area = unArea;
                            cambio = true;
                            break;
                        }
                    }
                    if (cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "Areas", area.getNombre(), "", subLista, area.getId(), LocalDate.now()));

                    }else {
                        throw new Exception("Area no encontrada");
                    }

                    break;
                case "ayudaTecnicas" :
                    AyudaTecnica ayudaTecnica = null;
                    List<AyudaTecnica> ayudaTec = candidato.getAyudaTecnicas();
                    for (AyudaTecnica unaAyudaTecnica : ayudaTec){
                        if (unaAyudaTecnica.getId().equals(id)){
                            ayudaTec.remove(unaAyudaTecnica);
                            ayudaTecnica = unaAyudaTecnica;
                            cambio = true;
                            break;
                        }
                    }
                    if (cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "Ayudas tecnicas", ayudaTecnica.getNombre(), "", subLista, ayudaTecnica.getId(), LocalDate.now()));

                    }else {
                        throw new Exception("Ayuda tecnica no encontrada");
                    }
                    break;
                case "prestaciones":
                    Prestacion prestacion = null;
                    List<Prestacion> prestaciones = candidato.getPrestaciones();
                    for(Prestacion unPrestacion : prestaciones){
                        if (unPrestacion.getId().equals(id)){
                            prestaciones.remove(unPrestacion);
                            prestacion = unPrestacion;
                            cambio = true;
                            break;
                        }
                    }
                    if (cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "Prestacion", prestacion.getNombre(), "", subLista, prestacion.getId(), LocalDate.now()));

                    }else {
                        throw new Exception("Prestacion no encontrada");
                    }
                    break;

            }
        }

    }
    private void eliminarDeExperienciaLaboral(Candidato candidato, String subLista, Long id) throws Exception {
        boolean cambio = false;
        switch (subLista){

            case "gustosLaborales":
                GustoLaboral gusto = null;
                List<GustoLaboral> gustos = candidato.getExperienciaLaboral().getGustosLaborales();
                for(GustoLaboral unGusto : gustos){
                    if (unGusto.getId().equals(id)) {
                        gustos.remove(unGusto);
                        cambio = true;
                        gusto = unGusto;
                        break;
                    }
                }
                if (cambio) {
                    auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "Gustos", gusto.getGusto(), "", subLista, gusto.getId(), LocalDate.now()));

                }else {


                    throw new Exception("Gusto no encontrado");
                }

                break;
            case "actitudes":
                Actitud actitud = null;
                List<Actitud> actitudes = candidato.getExperienciaLaboral().getActitudes();
                for(Actitud unActitud : actitudes){
                    if (unActitud.getId().equals(id)) {
                        actitudes.remove(unActitud);
                        actitud = unActitud;
                        cambio = true;
                       break;
                    }
                }

                if (cambio) {
                    auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "Actitudes", actitud.getNombre(), "", subLista, actitud.getId(), LocalDate.now()));

                }else {
                    throw new Exception("Actitud no encontrada");

                }

                break;
            case "motivosDesempleo":
                MotivoDesempleo mds = null;
                List<MotivoDesempleo> motivoDesempleos = candidato.getExperienciaLaboral().getMotivosDesempleo();
                for (MotivoDesempleo unMotivoDesempleo : motivoDesempleos){
                    if (unMotivoDesempleo.getId().equals(id)) {
                        motivoDesempleos.remove(unMotivoDesempleo);
                        mds = unMotivoDesempleo;
                        cambio = true;
                        break;

                    }
                }
                if (cambio) {
                    auditoriaCandidatoService.guardar(crearAuditoria("Eliminacion",candidato, "motivosDesempleo", mds.getMotivo(), "", subLista, mds.getId(), LocalDate.now()));

                }else {
                    throw new Exception("Motivo desempleo no encontrado");
                }
                break;


            default:
                throw new Exception("SubLista no encontrada");

        }
    }

    @Override
    public void agregarASubLista(String candidatoId, String lista, String subLista, String id) throws Exception{
        Candidato candidato = actualizarCandidatoRepository.findById(Long.parseLong(candidatoId)).orElse(null);

        if(candidato != null){
            boolean cambio = false;
            switch (lista){
                case "educacion":
                    Institucion ins = insititucionRepository.findById(Long.parseLong(id)).orElse(null);
                    if(ins != null){
                        auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "InstitucionesDeseo", "", ins.getTipo().name(), lista, ins.getId(), LocalDate.now()));
                        candidato.getEducacion().getInstitucionesDeseo().add(ins);
                        actualizarCandidatoRepository.save(candidato);
                    }else{
                        throw new Exception("Institucion no encontrada");
                    }

                    break;
                case "disponibilidadHoraria":
                    Turno turno = iturnoRepository.findById(Long.parseLong(id)).orElse(null);
                    if(turno != null){
                        auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "Turnos", "", turno.getTurno().name(), lista, turno.getId(), LocalDate.now()));
                        candidato.getDisponibilidadHoraria().getTurnos().add(turno);
                        actualizarCandidatoRepository.save(candidato);
                    }else{
                        throw new Exception("Turno no encontrado");
                    }
                    break;
                case "experienciaLaboral":
                    agregarAExperienciaLaboral(candidato, subLista, Long.parseLong(id));
                    break;
                case "emails":
                    Email email = new Email(id, candidato);
                    candidato.getEmails().add(email);
                    actualizarCandidatoRepository.save(candidato);
                    break;
                case "apoyos":
                    Apoyo apoyo = apoyoRepository.findById(Long.parseLong(id)).orElse(null);
                    if(apoyo != null){
                        auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "Apoyo", "", apoyo.getNombre(), lista, apoyo.getId(), LocalDate.now()));
                        candidato.getApoyos().add(apoyo);
                        actualizarCandidatoRepository.save(candidato);
                    }else {
                        throw new Exception("Apoyo no encontrado");
                    }
                    break;
                case "areas":
                    Area area = areaRepository.findById(Long.parseLong(id)).orElse(null);
                    if(area != null){
                        auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "Area", "", area.getNombre(), lista, area.getId(), LocalDate.now()));
                        candidato.getAreas().add(area);
                        actualizarCandidatoRepository.save(candidato);
                    }else {
                        throw new Exception("Area no encontrada");
                    }
                    break;
                case "ayudaTecnicas":
                    AyudaTecnica ayudaTecnica = ayudaTecnicaRepository.findById(Long.parseLong(id)).orElse(null);
                    if(ayudaTecnica != null){
                        auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "ayudaTecnicas", "", ayudaTecnica.getNombre(), lista, ayudaTecnica.getId(), LocalDate.now()));
                        candidato.getAyudaTecnicas().add(ayudaTecnica);
                        actualizarCandidatoRepository.save(candidato);

                    }else{
                        throw new Exception("AyudaTecnica no encontrada");
                    }
                    break;
                case "prestaciones":
                    Prestacion prestacion = prestacionRepository.findById(Long.parseLong(id)).orElse(null);
                    if(prestacion != null){
                        auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "Prestaciones", "", prestacion.getNombre(), lista, prestacion.getId(), LocalDate.now()));
                        candidato.getPrestaciones().add(prestacion);
                        actualizarCandidatoRepository.save(candidato);
                    }
                    else{
                        throw new Exception("Prestacion no encontrada");
                    }
                    break;
            }
        }
    }

    private void agregarAExperienciaLaboral(Candidato candidato, String subLista, Long idAgregar) throws Exception {
        switch (subLista){
            case "gustosLaborales":
                GustoLaboral gusto = gustoLaboralRepository.findById(idAgregar).orElse(null);
                if(gusto != null){
                    auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "Gustos", "", gusto.getGusto(), subLista, gusto.getId(), LocalDate.now()));
                    candidato.getExperienciaLaboral().getGustosLaborales().add(gusto);
                    actualizarCandidatoRepository.save(candidato);
                }else {
                    throw new Exception("Gusto no encontrado");
                }
                break;
            case "actitudes":
                Actitud actitud = actitudRepository.findById(idAgregar).orElse(null);
                if(actitud != null){
                    auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "Actitud", "", actitud.getNombre(), subLista, actitud.getId(), LocalDate.now()));
                    candidato.getExperienciaLaboral().getActitudes().add(actitud);
                    actualizarCandidatoRepository.save(candidato);
                }else {
                    throw new Exception("Actitud no encontrada");
                }
                break;
            case "motivosDesempleo":
                MotivoDesempleo mds = motivoDesempleoRepository.findById(idAgregar).orElse(null);
                if(mds != null){
                    auditoriaCandidatoService.guardar(crearAuditoria("Agregar", candidato, "motivosDesempleo", "", mds.getMotivo(), subLista, mds.getId(), LocalDate.now()));
                    candidato.getExperienciaLaboral().getMotivosDesempleo().add(mds);
                    actualizarCandidatoRepository.save(candidato);
                }
                break;
            default:
                throw new Exception("SubLista no encontrada");

        }
    }

    private AuditoriaCandidato crearAuditoria(String tipo, Candidato candidato, String campo, String datoAnt, String datoAct, String tablaAEditar, Long idTablaAEditar, LocalDate fechaCambio){
        return new AuditoriaCandidato(tipo, candidato, campo, datoAnt, datoAct, tablaAEditar, idTablaAEditar, fechaCambio);
    }
    private AuditoriaCandidato crearAuditoria(String tipo, Candidato candidato, String campo, String datoAnt, String datoAct, String tablaAEditar, LocalDate fechaCambio){
        return new AuditoriaCandidato(tipo, candidato, campo, datoAnt, datoAct, tablaAEditar, fechaCambio);
    }

    private void actualizarTelefono(Telefono telefono, String campo, String dato) throws Exception{
        if(telefono != null){
            switch (campo){
                case "duenioDos":
                    telefono.setDuenioDos(dato);
                    break;

                case "duenioUno":
                    telefono.setDuenioUno(dato);
                    break;

                case "numeroDos":
                    telefono.setNumeroDos(dato);
                    break;

                case "numeroUno":
                    telefono.setNumeroUno(dato);
                    break;
                default:
                    throw new Exception("Campo no encontrado");
            }

        }else {
            throw new Exception("Telefono no encontrada");
        }
    }

    private void actualizarSalud(Salud salud, String campo, String dato) throws Exception{
        if(salud != null){
            switch (campo){
                case "atencionMedica":
                    salud.setAtencionMedica(dato);
                    break;

                case "carnetSalud":
                    salud.setCarnetSalud(dato);
                    break;

                case "cualesMedicamentos":
                    salud.setCualesMedicamentos(dato);
                    break;

                case "medicamento":
                    salud.setMedicamento(dato);
                    break;

                case "saludMental":
                    salud.setSaludMental(dato);
                    break;
                default:
                    throw new Exception("Campo no encontrado");
            }
        }else {
            throw new Exception("Salud no encontrado");
        }
    }

    private void actualizarHabilidad(Habilidad habilidad, String campo, String dato) throws Exception{
        if(habilidad != null){
            switch (campo){
                case "autonomia_en_transporte_publico":
                    habilidad.setAutonomia_en_transporte_publico(Integer.parseInt(dato));
                    break;

                case "descripcion":
                    habilidad.setDescripcion(dato);
                    break;

                case "excel":
                    habilidad.setExcel(Integer.parseInt(dato));
                    break;

                case "imagen_personal":
                    habilidad.setImagen_personal(Integer.parseInt(dato));
                    break;

                case "internet":
                    habilidad.setInternet(Integer.parseInt(dato));
                    break;

                case "lsu":
                    habilidad.setLSU(Integer.parseInt(dato));
                    break;

                case "manejo_de_dinero":
                    habilidad.setManejo_de_dinero(Integer.parseInt(dato));
                    break;

                case "otrasHabilidades":
                    habilidad.setOtrasHabilidades(Integer.parseInt(dato));
                    break;

                case "power_point":
                    habilidad.setPower_point(Integer.parseInt(dato));
                    break;

                case "word":
                    habilidad.setWord(Integer.parseInt(dato));
                    break;
                default:
                    throw new Exception("Campo no encontrado");

            }
        }else{
            throw new Exception("Habilidad no encontrada");
        }
    }

    private void actualizarExperienciaLaboral(ExperienciaLaboral experienciaLaboral, String campo, String dato) throws Exception{
        if(experienciaLaboral != null){
            switch (campo){
                case "descripcionSituacionLaboral":
                    experienciaLaboral.setDescripcionSituacionLaboral(dato);
                    break;

                case "experienciaLaboral":
                    experienciaLaboral.setExperienciaLaboral(dato);
                    break;

                case "finTrabajo":
                    experienciaLaboral.setFinTrabajo(dato);
                    break;

                case "inicioTrabajo":
                    experienciaLaboral.setInicioTrabajo(dato);
                    break;

                case "puestoActual":
                    experienciaLaboral.setPuestoActual(dato);
                    break;

                case "situacionLaboral":
                    experienciaLaboral.setSituacionLaboral(dato);
                    break;

                case "tareas":
                    experienciaLaboral.setTareas(dato);
                    break;

                case "tipoDeTrabajoOtros":
                    experienciaLaboral.setTipoDeTrabajoOtros(dato);
                    break;

                case "tipoTrabajo":
                    experienciaLaboral.setTipoTrabajo(dato);
                    break;

                case "trabajoAlgunaVez":
                    experienciaLaboral.setTrabajoAlgunaVez(dato);
                    break;

                case "ultimoPuesto":
                    experienciaLaboral.setUltimoPuesto(dato);
                    break;
                default:
                    throw new Exception("No se logro actualizar experiencia laboral");

            }

        }else {
            throw new Exception("Experiencia laboral no encontrada");
        }
    }

    private void actualizarEncuesta(EncuestaCandidato encuestaCandidato, String campo, String dato) throws Exception{
        if (encuestaCandidato != null){
            switch (campo){
                case "creadaPor":
                    encuestaCandidato.setCreadaPor(dato);
                    break;

                case "estado":
                    encuestaCandidato.setEstado(dato);
                    break;

                case "fechaCreacion":
                    String fechaIni = dato+"T00:00:00";
                    encuestaCandidato.setFechaCreacion(LocalDateTime.parse(fechaIni));
                    break;

                case "fechaFinalizacion":
                    String fechaFinal = dato+"T00:00:00";
                    encuestaCandidato.setFechaFinalizacion(LocalDateTime.parse(fechaFinal));
                    System.out.println("Formato de fechaFinalizacion: "+dato);
                    break;

                case "idFlow":
                    encuestaCandidato.setIdFlow(dato);
                    break;

                case "idFlowAFAM":
                    encuestaCandidato.setIdFlowAFAM(dato);
                    break;
                default:
                    throw new Exception("Campo no encontrado");
            }
        }else{
            throw new Exception("Encuenta no encontrada");
        }
    }

    private void actualizarEducacion(Educacion educacion, String campo, String dato) throws Exception{
        if(educacion != null){
            switch (campo) {
                case "aniosEducacion":
                    educacion.setAniosEducacion(Integer.parseInt(dato));
                    break;
                case "deseaParticiparEnAlgunaInstitucion":
                    educacion.setDeseaParticiparEnAlgunaInstitucion(dato);
                    break;
                case "deseoDeOtrasInstituciones":
                    educacion.setDeseoDeOtrasInstituciones(dato);
                    break;
                case "educacionNoFormal":
                    educacion.setEducacionNoFormal(dato);
                    break;
                case "nivelEducativo":
                    educacion.setNivelEducativo(dato);
                    break;
                case "nombreInstitucion":
                    educacion.setNombreInstitucion(dato);
                    break;
                case "participacionInstitucion":
                    educacion.setParticipacionInstitucion(dato);
                    break;
                case "razonDejaEstudios":
                    educacion.setRazonDejaEstudios(dato);
                    break;
                case "situacionActual":
                    educacion.setSituacionActual(dato);
                    break;
                default:
                    throw new Exception("Campo no encontrado");
            }
        }else{
            throw new Exception("Educacion no encontrada");
        }
    }

    private void actualizarDisponibilidadHoraria(DisponibilidadHoraria dh, String campo, String dato) throws Exception{
        if(dh != null){
            switch (campo){
                case "diasDeLaSemana":
                    dh.setDiasDeLaSemana(dato);
                    break;
                case "horasSemanales":
                    dh.setHorasSemanales(dato);
                    break;
                case "otroDepartamento":
                    dh.setOtroDepartamento(dato);
                    break;
                default:
                    throw new Exception("Campo no encontrado");
            }
        }else{
            throw new Exception("Disponibilidad horaria no encontrada");
        }
    }

    private void actualizarDatosAdicionales( DatosAdicionalesCandidato datosAdicionales, String campo, String dato) throws Exception {
        if (datosAdicionales != null) {
            System.out.println("Campo: "+campo);
            switch (campo) {

                case "autorizacionDarDatos":
                    datosAdicionales.setAutorizacionDarDatos(dato);
                    break;
                case "cantHijos":
                    datosAdicionales.setCantHijos(Integer.parseInt(dato));
                    break;
                case "conduce":
                    datosAdicionales.setConduce(Integer.parseInt(dato));
                    break;
                case "cuidados":
                    datosAdicionales.setCuidados(dato);
                    break;
                case "enviaCV":
                    datosAdicionales.setEnviaCV(dato);
                    break;
                case "grupoFamiliar":
                    datosAdicionales.setGrupoFamiliar(dato);
                    break;
                case "hijos":
                    datosAdicionales.setHijos(dato);
                    break;
                case "infomacionPersonal":
                    datosAdicionales.setInfomacionPersonal(dato);
                    break;
                case "registoEnCNHD":
                    datosAdicionales.setRegistoEnCNHD(dato);
                    break;
                case "tipoLibreta":
                    datosAdicionales.setTipoLibreta(dato);
                    break;
                default:
                    throw new Exception("Campo no encontrado");
            }

        } else {
            throw new Exception("Datos adicionales del candidato no encontrados");
        }
    }

    private void actualizarDireccion(Dirreccion direccion, String campo, String dato) throws Exception {
        if (direccion != null) {
            System.out.println("Campo: "+campo);
            switch (campo) {
                case "apartamento":
                    direccion.setApartamento(dato);
                    break;
                case "calleIncluida":
                    direccion.setCalleIncluida(Integer.parseInt(dato));
                    break;
                case "calle":
                    direccion.setCalle(dato);
                    break;
                case "departamento":
                    direccion.setDepartamento(dato);
                    break;
                case "esquinaDos":
                    direccion.setEsquinaDos(dato);
                    break;
                case "esquinaUno":
                    direccion.setEsquinaUno(dato);
                    break;
                case "kilometro":
                    direccion.setKilometro(dato);
                    break;
                case "localidad":
                    direccion.setLocalidad(dato);
                    break;
                case "numeroPuerta":
                    direccion.setNumeroPuerta(Integer.parseInt(dato));
                    break;
                case "observacionesDireccion":
                    direccion.setObservacionesDireccion(dato);
                    break;
                default:
                    throw new Exception(campo+" no encontrado");
            }


        } else {
            throw new Exception("Direccion del candidato no encontrada");
        }
    }
}
