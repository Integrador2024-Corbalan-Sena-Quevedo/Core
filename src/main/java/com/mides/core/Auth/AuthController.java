package com.mides.core.Auth;

import com.mides.core.model.Rol;
import com.mides.core.model.Usuario;
import com.mides.core.repository.IUsuarioRepository;
import com.mides.core.service.EmailSenderService;
import com.mides.core.service.IUsuarioService;
import com.mides.core.service.UsuarioService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://mides-web.s3-website-us-east-1.amazonaws.com", methods = RequestMethod.POST)
public class AuthController {

    private final AuthService authService;

    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){

       return ResponseEntity.ok(authService.login(request));

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) throws MessagingException {
        AuthResponse authResponse =  authService.register(request);
        if (authResponse != null){
            emailSenderService.sendEmailToAdmis(request.getEmail());
            return ResponseEntity.ok(HttpStatus.OK);
        }
        else {
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}