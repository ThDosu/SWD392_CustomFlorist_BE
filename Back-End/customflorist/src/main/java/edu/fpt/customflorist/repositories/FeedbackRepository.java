package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findByCommentContaining(String keyword, Pageable pageable);
    Page<Feedback> findByBouquet_BouquetId(Long bouquetId, Pageable pageable);
    Page<Feedback> findByUser_UserId(Long userId, Pageable pageable);
}

