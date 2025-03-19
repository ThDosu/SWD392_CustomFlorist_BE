package edu.fpt.customflorist.dtos.ChatBot;

import lombok.Data;
import java.util.List;

@Data
public class ChatResponseDTO {
    private String sessionId;
    private String message;
    private List<ChatMessageDTO> history;
}
