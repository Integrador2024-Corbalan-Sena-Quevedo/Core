package com.mides.core.service;

import org.apache.commons.csv.CSVParser;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public interface IFileAttachmentService {

    void processCSVDataCandidate(List<Map<String, String>> csvData) throws Exception;

    void processCSVDataCompany(List<Map<String, String>> csvData) throws Exception;
    List<Integer> forCSVData(BufferedReader bufferedReader, CSVParser csvParser, String type) throws Exception;
}
