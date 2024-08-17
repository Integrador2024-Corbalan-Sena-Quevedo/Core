package com.mides.core.Menu;

import com.mides.core.model.Rol;
import com.mides.core.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MenuController {
    @Autowired
    private final MenuService menuService;

    @GetMapping("/operadores")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getAllOperators() {
        return ResponseEntity.ok(menuService.getAllOperators());
    }

    @DeleteMapping("/operadores/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        menuService.deleteOperator(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/operadores/{id}/rol")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> changeRole(@PathVariable Long id, @RequestBody String nuevoRol) {
        String rol = nuevoRol.substring(3,nuevoRol.length() -3);
        System.out.println(rol);
        Usuario user = menuService.changeRole(id, Rol.valueOf(rol));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/estadisticas/operadoresPorRol")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> estadisticas() {
        List<Usuario> usuarios = menuService.ListaOperadores();

        // Supongamos que getRol() devuelve un Enum
        Map<String, Long> cantidadPorRol = usuarios.stream()
                .collect(Collectors.groupingBy(usuario -> usuario.getRol().name(), Collectors.counting()));

        return ResponseEntity.ok(cantidadPorRol);
    }

    @GetMapping("estadisticas/empleosDepartamento")
    public ResponseEntity<Map<String, Long>> getEmpleosEnMontevideo() {
        Map<String, Long> empleosEnMontevideo = menuService.getEmpleosPorDepartamento();
        return ResponseEntity.ok(empleosEnMontevideo);
    }

    @GetMapping("estadisticas/empleosNivelFormacion")
    public ResponseEntity<Map<String, Long>> getEmpleosPorFormacionNecesaria() {
        Map<String, Long> empleosFormacionNecesaria = menuService.getEmpleosPorFormacionAcademica();
        return ResponseEntity.ok(empleosFormacionNecesaria);
    }

    @GetMapping("estadisticas/empleosCargaHoraria")
    public ResponseEntity<Map<String, Long>> getEmpleosPorCargaHoraria() {
        Map<String, Long> empleosCargaHoraria = menuService.getEmpleosPorCargaHoraria();
        return ResponseEntity.ok(empleosCargaHoraria);
    }



}
