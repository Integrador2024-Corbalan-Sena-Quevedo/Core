package com.mides.core.Menu;

import com.mides.core.model.Rol;
import com.mides.core.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        String rol = nuevoRol.substring(1,nuevoRol.length() -1);
        Usuario user = menuService.changeRole(id, Rol.valueOf(rol));
        return ResponseEntity.ok(user);
    }



}
