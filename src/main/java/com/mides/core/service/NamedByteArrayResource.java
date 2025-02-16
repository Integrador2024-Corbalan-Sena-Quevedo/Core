package com.mides.core.service;

import org.springframework.core.io.ByteArrayResource;

public class NamedByteArrayResource extends ByteArrayResource {
    private final String filename;

    public NamedByteArrayResource(byte[] byteArray, String filename) {
        super(byteArray);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }
}