package com.mides.core.configuration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponse {

    private List<Choice> choices;


    @Data
    public static class Choice {
        private int index;
        private Message message;
    }
}
