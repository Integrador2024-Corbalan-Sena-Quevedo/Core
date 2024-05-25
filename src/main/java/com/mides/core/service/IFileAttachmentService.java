package com.mides.core.service;

import java.util.List;
import java.util.Map;

public interface IFileAttachmentService {

    void processCSVData(List<Map<String, String>> csvData);
}
