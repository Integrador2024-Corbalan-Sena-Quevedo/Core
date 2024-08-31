package com.mides.core.configuration;

import com.mides.core.repository.IParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static com.mides.core.constant.Constant.EMAIL_FROM;
import static com.mides.core.constant.Constant.EMAIL_PASSWORD;

@Configuration
public class EmailConfig {

    @Autowired
    IParametersRepository parametersRepository;

    private Properties getMailProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(getMailProperties());
        String emailFrom = parametersRepository.getParameterById(EMAIL_FROM);
        String password = parametersRepository.getParameterById(EMAIL_PASSWORD);
        mailSender.setUsername(emailFrom);
        mailSender.setPassword(password);
        return  mailSender;
    }

    @Bean
    public ResourceLoader resourceLoader(){
        return new DefaultResourceLoader();
    }

}
