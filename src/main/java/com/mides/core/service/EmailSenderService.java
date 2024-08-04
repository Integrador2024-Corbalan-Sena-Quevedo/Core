package com.mides.core.service;

import com.mides.core.dto.EmailDTO;
import com.mides.core.model.Candidato;
import com.mides.core.constant.Constant;
import com.mides.core.model.Empresa;
import com.mides.core.repository.IParametersRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmailSenderService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    IParametersRepository parametersRepository;

    @Autowired
    IEmpresaSevice empresaSevice;

    @Autowired
    ICandidatoSevice candidatoSevice;
    @Autowired
    IPdfService pdfService;
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailsToCompany(List<Candidato> candidatos, Long empresaId) {
        Empresa empresa = empresaSevice.findEmpresaById(empresaId);
        EmailDTO emailDTO = new EmailDTO();
        String subject = parametersRepository.getParameterById(Constant.EMAIL_SUBJECT);
        emailDTO.setSubject(subject);
        String body = parametersRepository.getParameterById(Constant.EMAIL_BODY);
        emailDTO.setBody(body);
        emailDTO.setToEmail(empresa.getEmails().get(0).getEmail());
        List<Resource> resources = new ArrayList<>();
        try {
            for (Candidato candidato : candidatos) {
                Candidato candidatoBase = candidatoSevice.findCandidato(candidato.getDocumento());
                Resource resource = pdfService.base64AsPdf(candidatoBase.getNombre(),candidatoBase.getCsvBase64());
                if (resource != null) {
                    resources.add(resource);
                }
            }
            if(!resources.isEmpty()){
                sendEmailWithCv(emailDTO, resources);
            }
        }catch(MessagingException | IOException e){
            e.printStackTrace();
        }
    }

    public void sendEmailWithCv(EmailDTO emailDTO, List<Resource> resources) throws MessagingException, IOException {

        if (!resources.isEmpty()){
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setTo(emailDTO.getToEmail());
            helper.setSubject(emailDTO.getSubject());
            helper.setText(emailDTO.getBody());
            for (Resource resource : resources) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(resource.getInputStream(), "application/pdf");
                helper.addAttachment(resource.getFilename(), dataSource);
            }
            mailSender.send(message);
        }
    }



}
