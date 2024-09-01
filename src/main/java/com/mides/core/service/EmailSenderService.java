package com.mides.core.service;

import com.mides.core.dto.EmailDTO;
import com.mides.core.model.Candidato;
import com.mides.core.constant.Constant;
import com.mides.core.model.Empleo;
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

import static com.mides.core.constant.Constant.REGISTER_NOTIFICATION_BODY;
import static com.mides.core.constant.Constant.REGISTER_NOTIFICATION_SUBJECT;

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
    IEmpleoService empleoService;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IPdfService pdfService;
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailsToCompany(List<Candidato> candidatos, Long empresaId, Long empleoId, String emailUser) {
        Empresa empresa = empresaSevice.findEmpresaById(empresaId);
        Empleo empleo = empleoService.findById(empleoId);
        EmailDTO emailDTO = new EmailDTO();
        String subject = parametersRepository.getParameterById(Constant.EMAIL_SUBJECT);
        emailDTO.setSubject(subject);
        String body = parametersRepository.getParameterById(Constant.EMAIL_BODY);
        body += empleo.getNombrePuesto();
        emailDTO.setBody(body);
        emailDTO.setToEmail(empresa.getEmails().get(0).getEmail());
        if(emailUser != null)
        {
            emailDTO.setToEmailCc(emailUser);
        }
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
                empresaSevice.updateCvEnviados(resources.size(),empresaId);
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
            if(emailDTO.getToEmailCc() != null){
                helper.addCc(emailDTO.getToEmailCc());
            }
            for (Resource resource : resources) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(resource.getInputStream(), "application/pdf");
                helper.addAttachment(resource.getFilename(), dataSource);
            }
            mailSender.send(message);
        }
    }

    public void sendEmailToAdmis(String userEmail) throws MessagingException {
        try{
            EmailDTO emailDTO = new EmailDTO();
            String subject = parametersRepository.getParameterById(REGISTER_NOTIFICATION_SUBJECT);
            String body = parametersRepository.getParameterById(REGISTER_NOTIFICATION_BODY);
            emailDTO.setSubject(subject);
            body += userEmail;
            emailDTO.setBody(body);

            List<String> emailsUsersAdmin = usuarioService.getEmailUsersAdmin();

            for (String email : emailsUsersAdmin){
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message,true);
                if (email != null){
                    helper.setTo(email);
                    helper.setSubject(emailDTO.getSubject());
                    helper.setText(emailDTO.getBody());
                    mailSender.send(message);
                }
            }
        }catch (MessagingException e){
            throw new MessagingException();
        }


    }

}
