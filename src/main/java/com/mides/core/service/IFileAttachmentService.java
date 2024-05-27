package com.mides.core.service;

import org.apache.commons.csv.CSVParser;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public interface IFileAttachmentService {

    void processCSVData(List<Map<String, String>> csvData);
    void forCSVData(BufferedReader bufferedReader, CSVParser csvParser);
}
