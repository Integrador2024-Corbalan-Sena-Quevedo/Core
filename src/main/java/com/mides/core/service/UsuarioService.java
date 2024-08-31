package com.mides.core.service;

import com.mides.core.model.Usuario;
import com.mides.core.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioService implements IUsuarioService{
@Autowired
    IUsuarioRepository usuarioRepository;
    @Override
    public void saveUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public boolean existeUsuario(Usuario usuario, List<Usuario> usuariosLista){

        for(Usuario usuarioAux : usuariosLista){
            if(usuarioAux.getUsername().equals(usuario.getUsername())){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuario(String nombre) {
        List<Usuario> lista = usuarioRepository.findAll();
        for(Usuario usuarioAux : lista){
            if(usuarioAux.getUsername().equals(nombre)){
                return usuarioAux;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario getUsuarioById(Long id) {
       return usuarioRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Override
    public List<String> getEmailUsersAdmin() {
       return usuarioRepository.getEmailsUsuariosAdmin(0);
    }


}
