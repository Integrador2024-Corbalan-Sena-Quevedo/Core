package com.mides.core.repository;

import com.mides.core.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmailRepository extends JpaRepository<Email, Long> {
}
