package com.mides.core.service;

import com.mides.core.model.Usuario;
import com.mides.core.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioService implements IUsuarioService{
@Autowired
    IUsuarioRepository usuarioRepository;
    @Override
    public void saveUsuario(Usuario usuario) {
        List<Usuario> lista = usuarioRepository.findAll();

            if(!existeUsuario(usuario, lista)){
                usuarioRepository.save(usuario);
            }
            if(lista.isEmpty()){
                usuarioRepository.save(usuario);
            }

    }

    public boolean existeUsuario(Usuario usuario, List<Usuario> usuariosLista){

        for(Usuario usuarioAux : usuariosLista){
            if(usuarioAux.getNombreUsuario().equals(usuario.getNombreUsuario())){
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
            if(usuarioAux.getNombreUsuario().equals(nombre)){
                return usuarioAux;
            }
        }
        return null;
    }
}
