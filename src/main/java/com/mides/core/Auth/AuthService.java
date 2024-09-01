package com.mides.core.Auth;

import com.mides.core.model.Rol;
import com.mides.core.model.Usuario;
import com.mides.core.repository.IUsuarioRepository;
import com.mides.core.service.IUsuarioService;
import com.mides.core.service.JwtService;
import com.mides.core.service.UsuarioService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    @Autowired
    private final IUsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = usuarioService.getUsuario(request.getUsername());
        Usuario us = usuarioService.getUsuario(user.getUsername());
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).username(user.getUsername()).rol(us.getRol()).build();

    }

    public AuthResponse register(RegisterRequest request) {
        if(usuarioService.getUsuario(request.getUsername()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Ese nombre de usuario ya se encuentra en el sistema");
        }else {
            Usuario usuario = new Usuario();
            usuario.setName(request.getName());
            usuario.setUsername(request.getUsername());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuario.setRol(Rol.OPERADOR_LABORAL_NOVATO);
            usuario.setEmail(request.getEmail());
            usuarioService.saveUsuario(usuario);
            return AuthResponse.builder().token(jwtService.getToken(usuario)).build();
        }

    }

    public void addTestUser(){
        Usuario usuario = new Usuario();
        usuario.setName("Pedro");
        usuario.setUsername("pedrito");
        usuario.setPassword(passwordEncoder.encode("12345"));
        usuario.setRol(Rol.ADMIN);
        usuarioService.saveUsuario(usuario);
    }


}
