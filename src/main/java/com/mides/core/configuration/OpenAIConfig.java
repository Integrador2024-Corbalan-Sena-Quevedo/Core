package com.mides.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:gpt.properties")
public class OpenAIConfig {
    @Value("${openai.api.key}")
    private String apiKey;


    @Bean
    public RestTemplate template(){
        RestTemplate resTemplate = new RestTemplate();
        resTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + apiKey);
            return execution.execute(request, body);
        });
        return resTemplate;
    }
}
