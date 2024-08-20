package com.mides.core.Menu;

import com.mides.core.model.*;
import com.mides.core.repository.ICandidatoRepositoy;
import com.mides.core.repository.IEmpleoRepository;
import com.mides.core.repository.IEncuestaRepository;
import com.mides.core.repository.IUsuarioRepository;
import com.mides.core.service.ICandidatoSevice;
import com.mides.core.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class MenuService {

    @Autowired
    private IEmpleoRepository empleoRepository;
 private final IUsuarioRepository usuarioRepository;
    @Autowired
 private IEncuestaRepository encuestaRepository;
    @Autowired
    private ICandidatoRepositoy candidatoRepository;
 private final UsuarioService usuarioService;

    public List<Usuario> getAllOperators() {
        return usuarioRepository.findAll().stream()

                .collect(Collectors.toList());
    }

    public void deleteOperator(Long id) {
        usuarioRepository.deleteById(id);
    }


    public Usuario changeRole(Long id, Rol nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        usuario.setRol(nuevoRol);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> ListaOperadores (){
        return usuarioRepository.findAll();
    }

    public Map<String, Long> getEmpleosPorDepartamento() {
        List<Empleo> empleos = empleoRepository.findAll();

        Map<String, Long> empleosPorDepartamento = empleos.stream()
                .collect(Collectors.groupingBy(Empleo::getDepartamento, Collectors.counting()));

        return empleosPorDepartamento;
    }

    public Map<String, Long> getEmpleosPorFormacionAcademica() {
        List<Empleo> empleos = empleoRepository.findAll();

        Map<String, Long> empleosPorFormacionAcademica= empleos.stream()
                .collect(Collectors.groupingBy(Empleo::getFormacionAcademica, Collectors.counting()));

        return empleosPorFormacionAcademica;
    }

    public Map<String, Long> getEmpleosPorCargaHoraria() {
        List<Empleo> empleos = empleoRepository.findAll();

        Map<String, Long> empleosPorCargaHoraria= empleos.stream()
                .collect(Collectors.groupingBy(empleo -> {
                    int cargaHoraria = Integer.parseInt(empleo.getCargaHorariaSemanal().substring(0,2));

                    if (cargaHoraria < 15) {
                        return "No trabaja";
                    }
                    if (cargaHoraria <= 25 && cargaHoraria >= 15) {
                        return "20 horas";
                    } else if (cargaHoraria <= 35 && cargaHoraria >= 26) {
                        return "30 horas";
                    } else if (cargaHoraria >= 36 && cargaHoraria >= 45) {
                        return "40 horas";
                    } else {
                        return "Más de 40 horas";
                    }


                    }, Collectors.counting()));

        return empleosPorCargaHoraria;
    }

    public Map<String, Long> getEntrevistasPorAnio() {
        List<EncuestaCandidato> entrevistas = encuestaRepository.findAll();

        Map<String, Long> entrevistasPorAnio = entrevistas.stream()
                .filter(entrevista -> entrevista.getFechaCreacion().getYear() >= 2013)
                .collect(Collectors.groupingBy(entrevista ->

                     String.valueOf(entrevista.getFechaCreacion().getYear()),
                 Collectors.counting()));

        return entrevistasPorAnio;
    }

    public Map<String, Long> getEntrevistasPorGenero() {
        List<Candidato> entrevistas = candidatoRepository.findAll();

        Map<String, Long> entrevistasPorGenero= entrevistas.stream()
                .collect(Collectors.groupingBy(Candidato::getIdentidadGenero, Collectors.counting()));

        return entrevistasPorGenero;
    }


    public Map<String, Long> getCandidatosPorEdad() {
        List<Candidato> candidatos = candidatoRepository.findAll();

        Map<String, Long> candidatosPorEdad= candidatos.stream()
                .collect(Collectors.groupingBy(candidato -> {
                    int edad = Period.between(candidato.getFecha_de_nacimiento(), LocalDate.now()).getYears();

                    if (edad >= 15 && edad <= 17) {
                        return "15 a 17";
                    }
                    if (edad <= 29 && edad >= 18) {
                        return "18 a 29";
                    } else if (edad <= 39 && edad >= 30) {
                        return "30 a 39";
                    } else if (edad >= 49 && edad >= 40) {
                        return "40 a 49";
                    }
                    else if(edad >= 50 && edad <= 59){
                        return "50 a 59";
                    }else {
                        return "60 o más";
                    }


                }, Collectors.counting()));

        return candidatosPorEdad;
    }



    public Map<String, Long> getCandidatosPorDiscapacidad() {
        List<Candidato> candidatos = candidatoRepository.findAll();

        Map<String, Long> candidatosPorDiscapacidad = candidatos.stream()
                .flatMap(candidato -> {

                    List<TipoDiscapacidad> tiposDiscapacidad = candidato.getDiscapacidad().getTipoDiscapacidades();


                    if (tiposDiscapacidad.size() > 1) {
                        return Stream.of("Mixta o múltiple");
                    }


                    return tiposDiscapacidad.stream()
                            .map(TipoDiscapacidad::getNombre)
                            .distinct();
                })
                .collect(Collectors.groupingBy(tipo -> tipo, Collectors.counting()));

        return candidatosPorDiscapacidad;
    }

    public Map<String, Long> getCandidatosPorFormacionAcademica() {
        List<Candidato> candidatos = candidatoRepository.findAll();

        Map<String, Long> candidatosPorFormacionAcademica= candidatos.stream()
                .collect(Collectors.groupingBy(candidato -> candidato.getEducacion().getNivelEducativo(), Collectors.counting()));

        return candidatosPorFormacionAcademica;
    }

    public Map<String, Long> getCandidatosPorCargaHoraria() {
        List<Candidato> candidatos = candidatoRepository.findAll();

        Map<String, Long> candidatosPorCargaHoraria= candidatos.stream()
                .collect(Collectors.groupingBy(candidato -> {
                    int cargaHoraria = Integer.parseInt(candidato.getDisponibilidadHoraria().getHorasSemanales().substring(0, 2));

                    if (cargaHoraria <= 20) {
                        return "Hasta 20 horas";
                    }
                    if (cargaHoraria <= 30 && cargaHoraria >= 21) {
                        return "Hasta 30 horas";
                    } else if (cargaHoraria <= 40 && cargaHoraria >= 31) {
                        return "Hasta 40 horas";
                    } else if (cargaHoraria > 40) {
                        return "Más de 40 horas";
                    } else {
                        return "Sin restricción";
                    }


                }, Collectors.counting()));

        return candidatosPorCargaHoraria;
    }
}


