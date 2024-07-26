package com.mides.core.Menu;

import com.mides.core.model.Rol;
import com.mides.core.model.Usuario;
import com.mides.core.repository.IUsuarioRepository;
import com.mides.core.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class MenuService {
 private final IUsuarioRepository usuarioRepository;
 private final UsuarioService usuarioService;
    public List<Usuario> getAllOperators() {
        return usuarioRepository.findAll().stream()
                .filter(user -> user.getRol() != Rol.ADMIN)
                .collect(Collectors.toList());
    }

    public void deleteOperator(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void deleteAdmin(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    public Usuario changeRole(Long id, Rol nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        usuario.setRol(nuevoRol);
        return usuarioRepository.save(usuario);
    }
}
