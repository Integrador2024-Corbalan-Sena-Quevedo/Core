package com.mides.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private String subject;
    public String body;
    private String toEmail;
    private String toEmailCc;
    private String toEmailCo;
}
