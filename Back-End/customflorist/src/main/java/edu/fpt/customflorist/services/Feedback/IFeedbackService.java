package edu.fpt.customflorist.services.Feedback;

import edu.fpt.customflorist.dtos.Feedback.FeedbackDTO;
import edu.fpt.customflorist.models.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFeedbackService {
    Feedback createFeedback(FeedbackDTO feedbackDTO) throws Exception;
    Feedback updateFeedback(Long feedbackId, FeedbackDTO feedbackDTO) throws Exception;
    Page<Feedback> getAllFeedbacks(String keyword, Pageable pageable);
    Feedback getFeedbackById(Long feedbackId) throws Exception;
    Page<Feedback> getFeedbacksByBouquetId(Long bouquetId, Pageable pageable);
    Page<Feedback> getFeedbacksByUserId(Long userId, Pageable pageable);
}
