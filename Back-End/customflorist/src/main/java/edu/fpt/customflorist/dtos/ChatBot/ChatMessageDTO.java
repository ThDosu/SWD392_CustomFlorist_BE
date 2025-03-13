package edu.fpt.customflorist.dtos.ChatBot;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private Long id;
    private String content;
    private boolean isFromUser;
    private LocalDateTime timestamp;
}
