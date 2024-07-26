package com.mides.core.service;

import org.springframework.core.io.Resource;

import java.io.File;
import org.springframework.core.io.Resource;

public interface IPdfService {

     String readPdfContent(File pdfFile);
     Resource getPdfResource(String documento);
}
