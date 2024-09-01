package com.mides.core.service;

import org.springframework.http.ResponseEntity;

public interface IChatGPTService {

    public ResponseEntity<?> callToOpenAiApi(String detalleTarea, StringBuilder pdfText);
}
