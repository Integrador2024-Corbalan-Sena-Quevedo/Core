package com.mides.core.Auth;

import com.mides.core.model.Rol;
import com.mides.core.model.Usuario;
import com.mides.core.repository.IUsuarioRepository;
import com.mides.core.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {

 private final   IUsuarioRepository usuarioRepository;
private final JwtService jwtService;
private final AuthenticationManager authenticationManager;
private final PasswordEncoder passwordEncoder;
    public AuthResponse login(LoginRequest request){
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
UserDetails user = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
String token = jwtService.getToken(user);
return AuthResponse.builder().token(token).username(user.getUsername()).build();

    }

    public AuthResponse register(RegisterRequest request){
        Usuario usuario = new Usuario();
        usuario.setName(request.getName());
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.USER);
        usuarioRepository.save(usuario);

        return AuthResponse.builder().token(jwtService.getToken(usuario)).build();
    }

    public void addTestUser(){
        Usuario usuario = new Usuario();
        usuario.setName("Pedro");
        usuario.setUsername("pedrito");
        usuario.setPassword(passwordEncoder.encode("12345"));
        usuario.setRol(Rol.ADMIN);
        usuarioRepository.save(usuario);
    }
}
