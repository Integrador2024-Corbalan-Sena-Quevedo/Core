package com.mides.core.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Pdfservice  implements IPdfService {

    @Value("${file.upload-dir}")
    private String pathDir;


    @Override
    public String readPdfContent(File pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource getPdfResource(String documento) {
            File folder = new File(pathDir, documento);
            if (folder.exists() && folder.isDirectory()) {
                File[] pdfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
                if (pdfFiles != null && pdfFiles.length > 0) {
                    return new FileSystemResource(pdfFiles[0]);
                }
            }
            return null;
    }
}
