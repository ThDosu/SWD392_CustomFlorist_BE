package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.ChatBot.ChatMessageDTO;
import edu.fpt.customflorist.dtos.ChatBot.ChatRequestDTO;
import edu.fpt.customflorist.dtos.ChatBot.ChatResponseDTO;
import edu.fpt.customflorist.services.ChatBot.IChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/api/v1/chatbot")
@RequiredArgsConstructor
public class ChatbotController {
    private final IChatBotService chatbotService;

    @PostMapping("/message")
    public ResponseEntity<ChatResponseDTO> processMessage(@RequestBody ChatRequestDTO request) {
        return ResponseEntity.ok(chatbotService.processMessage(request));
    }

    @GetMapping("/history/{sessionId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(@PathVariable String sessionId) {
        return ResponseEntity.ok(chatbotService.getChatHistory(sessionId));
    }
}
