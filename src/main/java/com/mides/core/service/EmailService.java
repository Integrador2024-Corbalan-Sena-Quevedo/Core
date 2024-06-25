package com.mides.core.service;

import com.mides.core.model.Candidato;
import com.mides.core.model.Email;
import com.mides.core.repository.IEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class EmailService implements IEmailService {
    @Autowired
    IEmailRepository emailRepository;
    @Override
    public void saveEmail(Email email) {
        emailRepository.save(email);
    }

    @Override
    public void processEmail(List<Map<String, String>> csvData, Candidato candidato) throws Exception {
        Email email = new Email();
            for (Map<String, String> row : csvData){
                email.setEmail(row.get("email"));
                email.setCandidato(candidato);
            }

        this.saveEmail(email);
    }
}
