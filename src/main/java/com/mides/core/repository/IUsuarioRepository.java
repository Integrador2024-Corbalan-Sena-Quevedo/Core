package com.mides.core.repository;

import com.mides.core.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
}
