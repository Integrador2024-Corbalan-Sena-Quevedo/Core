package com.mides.core.configuration;

import com.mides.core.repository.IParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import static com.mides.core.constant.Constant.API_KEY;

@Configuration
public class OpenAIConfig {

    @Autowired
    IParametersRepository parametersRepository;

    @Bean
    public RestTemplate template(){
        RestTemplate resTemplate = new RestTemplate();
        String apiKey = parametersRepository.getParameterById(API_KEY);
        resTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + apiKey);
            return execution.execute(request, body);
        });
        return resTemplate;
    }
}
