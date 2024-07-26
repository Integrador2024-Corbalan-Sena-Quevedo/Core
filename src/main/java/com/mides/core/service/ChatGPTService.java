package com.mides.core.service;

import com.mides.core.configuration.dto.ChatGPTRequest;
import com.mides.core.configuration.dto.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGPTService implements IChatGPTService{

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String urlApi;
    @Value("${openai.api.promt}")
    private String prompt;

    @Autowired
    private RestTemplate template;

    @Override
    public ResponseEntity<?> callToOpenAiApi(String detalleTarea, StringBuilder pdfText){

        String promptChat = prompt + detalleTarea + "\nPDF Content:\n" + pdfText;
        ChatGPTRequest request = new ChatGPTRequest(model, promptChat);
        ChatGPTResponse chatGPTResponse = template.postForObject(urlApi, request,ChatGPTResponse.class);
        return new ResponseEntity<>(chatGPTResponse.getChoices().get(0).getMessage().getContent(), HttpStatus.OK);
    }
}
