package edu.fpt.customflorist.services.AIChatBot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AIChatbotService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateAIResponse(String userMessage, List<Map<String, String>> conversationHistory) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Tạo danh sách tin nhắn cho API
            List<Map<String, String>> messages = new ArrayList<>(conversationHistory);
            messages.add(Map.of("role", "user", "content", userMessage));

            // Tạo request body
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", model);
            requestBody.set("messages", objectMapper.valueToTree(messages));
            requestBody.put("max_tokens", 500);
            requestBody.put("temperature", 0.7);

            // Gọi API
            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            String response = restTemplate.postForObject(apiUrl, request, String.class);

            // Xử lý response
            JsonNode responseJson = objectMapper.readTree(response);
            String aiMessage = responseJson.path("choices").get(0).path("message").path("content").asText();

            return aiMessage;
        } catch (Exception e) {
            log.error("Error calling OpenAI API", e);
            return "Xin lỗi, tôi không thể xử lý tin nhắn của bạn lúc này. Vui lòng thử lại sau.";
        }
    }
}