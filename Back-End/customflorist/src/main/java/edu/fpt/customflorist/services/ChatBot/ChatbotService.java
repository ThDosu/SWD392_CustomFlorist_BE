package edu.fpt.customflorist.services.ChatBot;

import edu.fpt.customflorist.dtos.ChatBot.ChatMessageDTO;
import edu.fpt.customflorist.dtos.ChatBot.ChatRequestDTO;
import edu.fpt.customflorist.dtos.ChatBot.ChatResponseDTO;
import edu.fpt.customflorist.models.ChatMessage;
import edu.fpt.customflorist.models.ChatSession;
import edu.fpt.customflorist.repositories.ChatMessageRepository;
import edu.fpt.customflorist.repositories.ChatSessionRepository;
import edu.fpt.customflorist.services.AIChatBot.AIChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatbotService implements IChatBotService {
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final AIChatbotService aiChatbotService;

    @Transactional
    public ChatResponseDTO processMessage(ChatRequestDTO request) {
        // Lấy hoặc tạo phiên chat mới
        ChatSession session;
        if (request.getSessionId() == null || request.getSessionId().isEmpty()) {
            session = new ChatSession();
            chatSessionRepository.save(session);
        } else {
            Optional<ChatSession> existingSession = chatSessionRepository.findById(request.getSessionId());
            if (existingSession.isPresent()) {
                session = existingSession.get();
                session.setLastActivity(LocalDateTime.now());
            } else {
                session = new ChatSession();
                chatSessionRepository.save(session);
            }
        }

        // Lưu tin nhắn từ người dùng
        ChatMessage userMessage = new ChatMessage();
        userMessage.setSession(session);
        userMessage.setContent(request.getMessage());
        userMessage.setFromUser(true);
        chatMessageRepository.save(userMessage);

        // Lấy lịch sử chat để gửi cho AI
        List<ChatMessage> chatHistory = chatMessageRepository.findBySessionIdOrderByTimestampAsc(session.getId());
        List<Map<String, String>> aiChatHistory = new ArrayList<>();

        // Chuyển đổi lịch sử chat sang định dạng cho API AI
        for (ChatMessage message : chatHistory) {
            Map<String, String> aiMessage = new HashMap<>();
            aiMessage.put("role", message.isFromUser() ? "user" : "assistant");
            aiMessage.put("content", message.getContent());
            aiChatHistory.add(aiMessage);
        }

        // Thêm thông tin về shop và các chức năng
        String systemPrompt = "Bạn là trợ lý ảo của shop hoa FlowerShop. " +
                "Bạn có thể trả lời các câu hỏi về sản phẩm, khuyến mãi, đặt hàng, giao hàng. " +
                "Shop mở cửa từ 8:00 sáng đến 20:00 tối, từ thứ Hai đến Chủ Nhật. " +
                "Hotline liên hệ: 1900-xxxx. " +
                "Trả lời ngắn gọn và thân thiện. " +
                "Nếu không biết câu trả lời, hãy đề nghị khách hàng liên hệ với shop qua hotline hoặc email.";

        aiChatHistory.add(0, Map.of("role", "system", "content", systemPrompt));

        // Gọi API AI để lấy phản hồi
        String botResponse = aiChatbotService.generateAIResponse(request.getMessage(), aiChatHistory);

        // Lưu tin nhắn phản hồi từ bot
        ChatMessage botMessage = new ChatMessage();
        botMessage.setSession(session);
        botMessage.setContent(botResponse);
        botMessage.setFromUser(false);
        chatMessageRepository.save(botMessage);

        // Tạo phản hồi DTO
        ChatResponseDTO response = new ChatResponseDTO();
        response.setSessionId(session.getId());
        response.setMessage(botResponse);

        // Thêm lịch sử chat
        List<ChatMessageDTO> historyDTO = chatMessageRepository.findBySessionIdOrderByTimestampAsc(session.getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        response.setHistory(historyDTO);

        return response;
    }

    public List<ChatMessageDTO> getChatHistory(String sessionId) {
        return chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ChatMessageDTO convertToDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setFromUser(message.isFromUser());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
}