package com.mides.core.repository;

import com.mides.core.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
     Optional<Usuario> findByUsername(String username) ;

     @Query(value = "SELECT email FROM usuario WHERE rol=?", nativeQuery = true)
     List<String> getEmailsUsuariosAdmin(@Param("rol") int rol);


    }

