package com.mides.core.controller;

import com.mides.core.model.Usuario;
import com.mides.core.service.IUsuarioService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST)
public class UsuarioController {
    @Autowired
    IUsuarioService usuarioService;


    @PostMapping("/login-usuario")

    public ResponseEntity<?> loginUsuario(@RequestBody Map<String, String> loginData) {
        String nombreUsuario = loginData.get("usuario");
        String password = loginData.get("password");
        Map<String, Object> response = new HashMap<>();

        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            response.put("mensaje", "Uno o todos los campos están vacíos");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            boolean existe = login(nombreUsuario, password);

            if (existe) {
                UUID uuid = UUID.randomUUID();
                response.put("mensaje", "Login exitoso");
                response.put("codigo", 200);
                response.put("apiKey", uuid.toString());

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("mensaje", "Usuario y/o contraseña incorrectos");
                response.put("codigo", 404);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrió un error durante el login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public boolean login(String nombre, String password){



        if (usuarioService.getUsuario(nombre) != null) {
            Usuario elUsuario = usuarioService.getUsuario(nombre);
            if (elUsuario.getPassword().equals(password)) {
                return true;
            }
        }


        return false;

    }



}
