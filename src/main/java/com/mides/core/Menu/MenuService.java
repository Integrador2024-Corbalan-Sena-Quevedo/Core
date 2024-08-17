package com.mides.core.Menu;

import com.mides.core.model.Empleo;
import com.mides.core.model.Rol;
import com.mides.core.model.Usuario;
import com.mides.core.repository.IEmpleoRepository;
import com.mides.core.repository.IUsuarioRepository;
import com.mides.core.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class MenuService {

    @Autowired
    private IEmpleoRepository empleoRepository;
 private final IUsuarioRepository usuarioRepository;
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
                    int cargaHoraria = Integer.parseInt(empleo.getCargaHorariaSemanal().substring(0, empleo.getCargaHorariaSemanal().length() - 6));

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
}


