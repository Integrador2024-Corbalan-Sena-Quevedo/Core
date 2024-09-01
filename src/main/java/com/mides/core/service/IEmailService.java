package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Cliente;
import com.mides.core.model.Email;

import java.util.List;
import java.util.Map;

public interface IEmailService {
    void saveEmail(Email email);
    void processEmail(List<Map<String, String>> csvData, Cliente cliente) throws Exception;
    void deleteById(Long id);
}
