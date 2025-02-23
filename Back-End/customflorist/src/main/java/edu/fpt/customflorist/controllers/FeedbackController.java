package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.Feedback.FeedbackDTO;
import edu.fpt.customflorist.models.Feedback;
import edu.fpt.customflorist.services.Feedback.IFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    private final IFeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackDTO feedbackDTO) throws Exception {
        Feedback feedback = feedbackService.createFeedback(feedbackDTO);
        return ResponseEntity.ok(feedback);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeedback(@PathVariable Long id, @RequestBody FeedbackDTO feedbackDTO) throws Exception {
        Feedback feedback = feedbackService.updateFeedback(id, feedbackDTO);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedbackById(@PathVariable Long id) throws Exception {
        Feedback feedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping
    public ResponseEntity<Page<?>> getAllFeedbacks(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbacks = feedbackService.getAllFeedbacks(keyword, pageable);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/bouquet/{bouquetId}")
    public ResponseEntity<Page<?>> getFeedbacksByBouquetId(
            @PathVariable Long bouquetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbacks = feedbackService.getFeedbacksByBouquetId(bouquetId, pageable);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<?>> getFeedbacksByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId, pageable);
        return ResponseEntity.ok(feedbacks);
    }

}
