package com.mides.core.service;

import com.mides.core.model.Prestacion;
import com.mides.core.model.Usuario;

import java.util.List;
import java.util.Map;

public interface IUsuarioService {
    void saveUsuario(Usuario usuario);
    List<Usuario> getUsuarios();

    Usuario getUsuario(String nombre);
    void deleteById(Long id);
    Usuario getUsuarioById(Long id);
    List<String> getEmailUsersAdmin();
}
