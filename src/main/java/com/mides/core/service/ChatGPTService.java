package com.mides.core.service;

import com.mides.core.configuration.dto.ChatGPTRequest;
import com.mides.core.configuration.dto.ChatGPTResponse;
import com.mides.core.repository.IParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.mides.core.constant.Constant.*;

@Service
public class ChatGPTService implements IChatGPTService{
    @Autowired
    private RestTemplate template;
    @Autowired
    private IParametersRepository parametersRepository;

    @Override
    public ResponseEntity<?> callToOpenAiApi(String detalleTarea, StringBuilder pdfText){

        String promt = parametersRepository.getParameterById(PROMT_CHATGPT);
        String model = parametersRepository.getParameterById(MODEL_USE_OPENIA);
        String url = parametersRepository.getParameterById(URL_API_OPENIA);

        String promptChat = promt + detalleTarea + "\nPDF Content:\n" + pdfText;
        ChatGPTRequest request = new ChatGPTRequest(model, promptChat);
        ChatGPTResponse chatGPTResponse = template.postForObject(url, request,ChatGPTResponse.class);
        return new ResponseEntity<>(chatGPTResponse.getChoices().get(0).getMessage().getContent(), HttpStatus.OK);
    }
}
