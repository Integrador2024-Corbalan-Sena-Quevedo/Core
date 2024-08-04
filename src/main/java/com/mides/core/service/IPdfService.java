package com.mides.core.service;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IPdfService {

     String readPdfContent(File pdfFile);
     Resource getPdfResource(String documento);
      Resource base64AsPdf(String nombre, String cvBase64);
      byte[] pdfAsBase64(MultipartFile file) throws IOException;
}
