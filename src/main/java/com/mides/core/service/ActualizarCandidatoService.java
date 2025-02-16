package com.mides.core.service;

import com.mides.core.model.*;
import com.mides.core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ActualizarCandidatoService implements IActualizarCandidatoService{

    @Autowired
    IPrestacionService prestacionService;

    @Autowired
    IUsuarioService usuarioService;
    @Autowired
    IAyudaTecnicaService ayudaTecnicaService;

    @Autowired
    IAreaService areaService;

    @Autowired
    IApoyoService apoyoService;

    @Autowired
    IEmailService emailService;

    @Autowired
    IGustoLaboralService gustoLaboralService;

    @Autowired
    IActitudService actitudService;

    @Autowired
    IMotivoDesempleoService motivoDesempleoService;

    @Autowired
    ITurnoService turnoService;

    @Autowired
    IInstitucionService institucionService;

    @Autowired
    IActualizarCandidatoRepository actualizarCandidatoRepository;

    @Autowired
    IAuditoriaCandidatoService auditoriaCandidatoService;
    @Autowired
    ITipoDiscapacidadService tipoDiscapacidadService;
    @Autowired
    private IIdiomaService idiomaService;



    @Override
    public void actualizarCampo(String candidatoId, String campo, String userName, String datoAct, String datoAnt, String lista, String subLista) throws Exception {
        long idCandidato = Long.parseLong(candidatoId);
        Candidato candidato = actualizarCandidatoRepository.findById(idCandidato).orElse(null);
        Usuario usuario = usuarioService.getUsuario(userName);


        try {
            if(candidato != null){
                if(!lista.isEmpty() && !subLista.isEmpty()){

                    switch (subLista) {
                        case "candidatoIdiomas":
                            boolean cambio = false;

                            CandidatoIdioma candidatoIdioma= null;
                            List<CandidatoIdioma> candidatoIdiomas = candidato.getCandidatoIdiomas();
                            for(CandidatoIdioma unCandidatoIdioma : candidatoIdiomas){
                                if (unCandidatoIdioma.getId().equals(Long.parseLong(campo))){
                                    unCandidatoIdioma.setNivel(datoAct);
                                    candidatoIdioma = unCandidatoIdioma;
                                    cambio = true;
                                    break;
                                }
                            }
                            if (cambio){
                                auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, "Nivel", datoAnt, datoAct, candidatoIdioma.getIdioma().getNombre(), candidatoIdioma.getId(), LocalDate.now()));
                            }else {
                                throw new Exception("CandidatoIdioma no encontrado");
                            }
                            break;
                        case "tipoDiscapacidades":


                            break;
                    }
                }else{
                    if(!subLista.isEmpty() && lista.isEmpty()) {
                        switch (subLista) {
                            case "datosAdicionalesCandidato":
                                DatosAdicionalesCandidato datosAdicionalesCandidato = candidato.getDatosAdicionalesCandidato();
                                if(datosAdicionalesCandidato!= null){
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, datosAdicionalesCandidato.getId(), LocalDate.now()));
                                    actualizarDatosAdicionales(candidato.getDatosAdicionalesCandidato(), campo, datoAct);
                                    candidato.setDatosAdicionalesCandidato(datosAdicionalesCandidato);
                                    actualizarCandidatoRepository.save(candidato);

                                }else{
                                    throw new Exception("Datos adicionales del candidato no encontrados");
                                }
                                break;
                            case "datosPrincipalesCandidato":
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, candidato.getId(), LocalDate.now()));
                                    actualizarDatosPrincipales(candidato, campo, datoAct);
                                    actualizarCandidatoRepository.save(candidato);
                                break;


                            case "direccion":
                                Direccion direccion = candidato.getDireccion();

                                if(direccion != null){
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, direccion.getId(), LocalDate.now()));
                                    actualizarDireccion(direccion, campo, datoAct);
                                    candidato.setDireccion(direccion);
                                    actualizarCandidatoRepository.save(candidato);

                                }else{
                                    throw new Exception("El campo no puede ser vacio");
                                }

                                break;
                            case "disponibilidadHoraria":
                                DisponibilidadHoraria dh = candidato.getDisponibilidadHoraria();
                                if(dh != null){
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, lista, dh.getId(), LocalDate.now()));
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
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, discapacidad.getId(), LocalDate.now()));
                                    discapacidad.setDiagnostico(datoAct);
                                    candidato.setDiscapacidad(discapacidad);
                                    actualizarCandidatoRepository.save(candidato);
                                }else{
                                    throw new Exception("El campo no puede ser vacio");
                                }

                                break;


                            case "educacion":
                                Educacion educacion = candidato.getEducacion();
                                if(educacion != null){
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, educacion.getId(), LocalDate.now()));
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
                                        break;
                                    }
                                }

                                if(email != null){
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, email.getId(), LocalDate.now()));
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
                                            auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, fechaHoraString, datoAct, subLista, encuestaCandidato.getId(), LocalDate.now()));
                                            actualizarEncuesta(encuestaCandidato, campo, datoAct);
                                            candidato.setEncuestaCandidato(encuestaCandidato);
                                            actualizarCandidatoRepository.save(candidato);
                                        }else{
                                            String fechaHoraString = encuestaCandidato.getFechaFinalizacion().format(formatter);
                                            auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, fechaHoraString, datoAct, subLista, encuestaCandidato.getId(), LocalDate.now()));
                                            actualizarEncuesta(encuestaCandidato, campo, datoAct);
                                            candidato.setEncuestaCandidato(encuestaCandidato);
                                            actualizarCandidatoRepository.save(candidato);

                                        }
                                    }else{
                                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, encuestaCandidato.getId(), LocalDate.now()));
                                        actualizarEncuesta(encuestaCandidato, campo, datoAct);
                                        candidato.setEncuestaCandidato(encuestaCandidato);
                                        actualizarCandidatoRepository.save(candidato);
                                    }

                                }else{
                                    throw new Exception("Encuenta no encontrada");
                                }


                                break;



                            case "habilidad":
                                Habilidad habilidad = candidato.getHabilidad();
                                if(habilidad != null){
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, habilidad.getId(), LocalDate.now()));
                                    actualizarHabilidad(habilidad, campo, datoAct);
                                    candidato.setHabilidad(habilidad);
                                    actualizarCandidatoRepository.save(candidato);
                                }else {
                                    throw new Exception("Habilidad no encontrada");
                                }
                                break;


                            case "salud":
                                Salud salud = candidato.getSalud();
                                if(salud != null){
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, salud.getId(), LocalDate.now()));
                                    actualizarSalud(salud, campo, datoAct);
                                    candidato.setSalud(salud);
                                    actualizarCandidatoRepository.save(candidato);
                                }else {
                                    throw new Exception("Salud no encontrada");
                                }

                                break;

                            case "telefonos":
                                List<Telefono> telefonos = candidato.getTelefonos();
                                Telefono telefono = null;
                                for(Telefono unTelefono : telefonos){
                                    actualizarTelefono(unTelefono, campo, datoAct);
                                    telefono = unTelefono;
                                }

                                if(telefono != null){
                                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, telefono.getId(), LocalDate.now()));
                                    candidato.setTelefonos(telefonos);
                                    actualizarCandidatoRepository.save(candidato);

                                }else {
                                    throw new Exception("Telefonos no encontrada");
                                }



                                break;


                        }


                    }else{
                        if (subLista.isEmpty() && !lista.isEmpty()){
                            switch (lista) {
                                case "experienciaLaboral":
                                    ExperienciaLaboral experienciaLaboral = candidato.getExperienciaLaboral();
                                    if(experienciaLaboral != null){
                                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Modificación",candidato, campo, datoAnt, datoAct, subLista, experienciaLaboral.getId(), LocalDate.now()));
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
                }


            }else{
                throw new Exception("Candidato no encontrado");
            }

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }



    @Override
    public void elimnarSubLista(String candidatoId, String userName, String lista, String subLista, String idAEliminar) throws Exception {

        Candidato candidato = actualizarCandidatoRepository.findById(Long.parseLong(candidatoId)).orElse(null);
        Usuario usuario = usuarioService.getUsuario(userName);


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
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion" ,candidato, "InstitucionesDeseo", ins.getTipo().name(), "", lista, ins.getId(), LocalDate.now()));
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
                            break;
                        }
                    }

                    if(cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Turnos", turno.getTurno().name(), "", lista, turno.getId(), LocalDate.now()));
                    }else {
                        throw new Exception("Turno no encontrada");
                    }
                    break;
                case "experienciaLaboral":
                    eliminarDeExperienciaLaboral(candidato, usuario, subLista, id);
                    break;

                case  "emails" :
                    Email email = null;
                    List<Email> emails = candidato.getEmails();
                    for(Email unEmail : emails){
                        if (unEmail.getId().equals(id)){
                            emails.remove(unEmail);
                            email = unEmail;
                            emailService.deleteById(unEmail.getId());
                            cambio = true;
                            break;
                        }
                    }
                    if(cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Email", email.getEmail(), "", lista, email.getId(), LocalDate.now()));
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
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Apoyos", apoyo.getNombre(), "", subLista, apoyo.getId(), LocalDate.now()));

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
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Areas", area.getNombre(), "", subLista, area.getId(), LocalDate.now()));

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
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Ayudas tecnicas", ayudaTecnica.getNombre(), "", subLista, ayudaTecnica.getId(), LocalDate.now()));

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
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Prestacion", prestacion.getNombre(), "", subLista, prestacion.getId(), LocalDate.now()));

                    }else {
                        throw new Exception("Prestacion no encontrada");
                    }
                    break;
                case "discapacidad":
                    TipoDiscapacidad tipoDiscapacidad= null;
                    List<TipoDiscapacidad> tiposDisc = candidato.getDiscapacidad().getTipoDiscapacidades();
                    for(TipoDiscapacidad unTipoDiscapacidad : tiposDisc){
                        if (unTipoDiscapacidad.getId().equals(id)){
                            tiposDisc.remove(unTipoDiscapacidad);
                            tipoDiscapacidad = unTipoDiscapacidad;
                            cambio = true;
                            break;
                        }
                    }
                    if (cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Discapcidad", tipoDiscapacidad.getNombre(), "", subLista, tipoDiscapacidad.getId(), LocalDate.now()));

                    }else {
                        throw new Exception("Tipo de discapacidad no encontrada");
                    }
                    break;
                case "candidatoIdiomas" :
                    CandidatoIdioma candidatoIdioma= null;
                    List<CandidatoIdioma> idiomas = candidato.getCandidatoIdiomas();
                    for(CandidatoIdioma unCandidatoIdioma : idiomas){
                        if (unCandidatoIdioma.getId().equals(id)){
                            idiomas.remove(unCandidatoIdioma);
                            candidatoIdioma = unCandidatoIdioma;
                            cambio = true;
                            break;
                        }
                    }
                    if (cambio){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Candidato Idioma", candidatoIdioma.getIdioma().getNombre(), "", subLista, candidatoIdioma.getId(), LocalDate.now()));

                    }else {
                        throw new Exception("CandidatoIdioma no encontrado");
                    }
                    break;

            }
        }

    }

    private void eliminarDeExperienciaLaboral(Candidato candidato,Usuario usuario, String subLista, Long id) throws Exception {
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
                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Gustos", gusto.getGusto(), "", subLista, gusto.getId(), LocalDate.now()));

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
                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "Actitudes", actitud.getNombre(), "", subLista, actitud.getId(), LocalDate.now()));

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
                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Eliminacion",candidato, "motivosDesempleo", mds.getMotivo(), "", subLista, mds.getId(), LocalDate.now()));

                }else {
                    throw new Exception("Motivo desempleo no encontrado");
                }
                break;


            default:
                throw new Exception("SubLista no encontrada");

        }
    }

    @Override
    public void agregarASubLista(String candidatoId, String userName, String lista, String subLista, String id) throws Exception{
        Candidato candidato = actualizarCandidatoRepository.findById(Long.parseLong(candidatoId)).orElse(null);
        Usuario usuario = usuarioService.getUsuario(userName);


        if(candidato != null){
            boolean cambio = false;
            switch (lista){
                case "educacion":
                    Institucion ins = institucionService.getUnaInstitucion(id);
                    if(ins != null){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "InstitucionesDeseo", "", ins.getTipo().name(), lista, ins.getId(), LocalDate.now()));
                        candidato.getEducacion().getInstitucionesDeseo().add(ins);
                        actualizarCandidatoRepository.save(candidato);
                    }else{
                        throw new Exception("Institucion no encontrada");
                    }

                    break;
                case "disponibilidadHoraria":
                    Turno turno = turnoService.getUnTurno(id);
                    if(turno != null){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "Turnos", "", turno.getTurno().name(), lista, turno.getId(), LocalDate.now()));
                        candidato.getDisponibilidadHoraria().getTurnos().add(turno);
                        actualizarCandidatoRepository.save(candidato);
                    }else{
                        throw new Exception("Turno no encontrado");
                    }
                    break;
                case "experienciaLaboral":
                    agregarAExperienciaLaboral(candidato, usuario, subLista, id);
                    break;
                case "emails":
                    Email email = new Email(id, candidato);
                    candidato.getEmails().add(email);
                    actualizarCandidatoRepository.save(candidato);
                    break;
                case "apoyos":
                    Apoyo apoyo = apoyoService.getUnApoyo(id);
                    if(apoyo != null){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "Apoyo", "", apoyo.getNombre(), lista, apoyo.getId(), LocalDate.now()));
                        candidato.getApoyos().add(apoyo);
                        actualizarCandidatoRepository.save(candidato);
                    }else {
                        throw new Exception("Apoyo no encontrado");
                    }
                    break;
                case "areas":
                    Area area = areaService.getUnArea(id);
                    if(area != null){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "Area", "", area.getNombre(), lista, area.getId(), LocalDate.now()));
                        candidato.getAreas().add(area);
                        actualizarCandidatoRepository.save(candidato);
                    }else {
                        throw new Exception("Area no encontrada");
                    }
                    break;
                case "ayudaTecnicas":
                    AyudaTecnica ayudaTecnica = ayudaTecnicaService.getUnaAyudaTecnica(id);
                    if(ayudaTecnica != null){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "ayudaTecnicas", "", ayudaTecnica.getNombre(), lista, ayudaTecnica.getId(), LocalDate.now()));
                        candidato.getAyudaTecnicas().add(ayudaTecnica);
                        actualizarCandidatoRepository.save(candidato);

                    }else{
                        throw new Exception("AyudaTecnica no encontrada");
                    }
                    break;
                case "prestaciones":
                    Prestacion prestacion = prestacionService.getUnaPrestacion(id);
                    if(prestacion != null){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "Prestaciones", "", prestacion.getNombre(), lista, prestacion.getId(), LocalDate.now()));
                        candidato.getPrestaciones().add(prestacion);
                        actualizarCandidatoRepository.save(candidato);
                    }
                    else{
                        throw new Exception("Prestacion no encontrada");
                    }
                    break;
                case "discapacidad":
                    TipoDiscapacidad tipoDiscapacidad = tipoDiscapacidadService.getTipoDiscapacidadByNombre(id);
                    if(tipoDiscapacidad != null){
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "Tipo de discapacidad", "", tipoDiscapacidad.getNombre(), lista, tipoDiscapacidad.getId(), LocalDate.now()));
                        candidato.getDiscapacidad().getTipoDiscapacidades().add(tipoDiscapacidad);
                        actualizarCandidatoRepository.save(candidato);
                    }
                    break;
                case "candidatoIdiomas":
                    Idioma idioma = idiomaService.getIdiomaNombre(id);
                    if(idioma != null){
                        CandidatoIdioma candidatoIdioma = new CandidatoIdioma();
                        candidatoIdioma.setIdioma(idioma);
                        candidatoIdioma.setCandidato(candidato);
                        auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "Idioma", "", idioma.getNombre(), lista, idioma.getId(), LocalDate.now()));
                        candidato.getCandidatoIdiomas().add(candidatoIdioma);
                        actualizarCandidatoRepository.save(candidato);
                    }


                    break;
            }
        }
    }

    private void agregarAExperienciaLaboral(Candidato candidato,Usuario usuario, String subLista, String nombre) throws Exception {
        switch (subLista){
            case "gustosLaborales":
                GustoLaboral gusto = gustoLaboralService.getUnGusto(nombre);
                if(gusto != null){
                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "Gustos", "", gusto.getGusto(), subLista, gusto.getId(), LocalDate.now()));
                    candidato.getExperienciaLaboral().getGustosLaborales().add(gusto);
                    actualizarCandidatoRepository.save(candidato);
                }else {
                    throw new Exception("Gusto no encontrado");
                }
                break;
            case "actitudes":
                Actitud actitud = actitudService.getUnActitud(nombre);
                if(actitud != null){
                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "Actitud", "", actitud.getNombre(), subLista, actitud.getId(), LocalDate.now()));
                    candidato.getExperienciaLaboral().getActitudes().add(actitud);
                    actualizarCandidatoRepository.save(candidato);
                }else {
                    throw new Exception("Actitud no encontrada");
                }
                break;
            case "motivosDesempleo":
                MotivoDesempleo mds = motivoDesempleoService.getUnMotivoDesempleo(nombre);
                if(mds != null){
                    auditoriaCandidatoService.guardar(crearAuditoria(usuario,"Agregar", candidato, "motivosDesempleo", "", mds.getMotivo(), subLista, mds.getId(), LocalDate.now()));
                    candidato.getExperienciaLaboral().getMotivosDesempleo().add(mds);
                    actualizarCandidatoRepository.save(candidato);
                }
                break;
            default:
                throw new Exception("SubLista no encontrada");

        }
    }

    private AuditoriaCandidato crearAuditoria(Usuario usuario, String tipo, Candidato candidato, String campo, String datoAnt, String datoAct, String tablaAEditar, Long idTablaAEditar, LocalDate fechaCambio){
        return new AuditoriaCandidato(usuario, tipo, candidato, campo, datoAnt, datoAct, tablaAEditar, idTablaAEditar, fechaCambio);
    }
    private AuditoriaCandidato crearAuditoria(Usuario usuario, String tipo, Candidato candidato, String campo, String datoAnt, String datoAct, String tablaAEditar, LocalDate fechaCambio){
        return new AuditoriaCandidato(usuario, tipo, candidato, campo, datoAnt, datoAct, tablaAEditar, fechaCambio);
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
                    try {
                        educacion.setNivelEducativoEnum(dato);
                    }catch (Exception e){
                        throw new Exception("Nivel de educativo no encontrado");
                    }

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

    private void actualizarDatosPrincipales(Candidato candidato, String campo, String datoAct) {
        switch (campo){
            case "nombre":
                candidato.setNombre(datoAct);
                break;
            case "apellido":
                candidato.setApellido(datoAct);
                break;
            case "documento":
                candidato.setDocumento(datoAct);
                break;
            case "fecha_de_nacimiento":
                candidato.setFecha_de_nacimiento(LocalDate.parse(datoAct));
                break;
            case "sexo":
                candidato.setSexo(datoAct);
                break;
            case "estadoCivil":
                candidato.setEstadoCivil(datoAct);
                break;
            case "tipoDocumento":
                candidato.setTipoDocumento(datoAct);
                break;
            case "identidadGenero":
                candidato.setIdentidadGenero(datoAct);
                break;

        }
    }

    private void actualizarDireccion(Direccion direccion, String campo, String dato) throws Exception {
        if (direccion != null) {
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
