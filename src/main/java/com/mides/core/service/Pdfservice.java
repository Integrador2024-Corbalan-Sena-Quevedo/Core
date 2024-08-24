package com.mides.core.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

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
    @Override
    public Resource base64AsPdf(String nombre, String cvBase64){
        try {
            byte[] pdfData = Base64.getDecoder().decode(cvBase64);
            String filename = nombre + "Cv.pdf";
            return new NamedByteArrayResource(pdfData, filename);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir la cadena Base64 a PDF", e);
        }
    }

    public File base64AsPdfFile(String nombre, String cvBase64) {
        if(cvBase64 == null ){
            return null;
        }
        try {
            byte[] pdfData = Base64.getDecoder().decode(cvBase64);
            File tempFile = File.createTempFile(nombre + "Cv", ".pdf");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(pdfData);
            }
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException("Error al convertir la cadena Base64 a PDF", e);
        }
    }

    @Override
    public byte[] pdfAsBase64(MultipartFile file) throws IOException {

        byte[] pdfData = Base64.getEncoder().encode(file.getBytes());
        return pdfData;
    }

}
