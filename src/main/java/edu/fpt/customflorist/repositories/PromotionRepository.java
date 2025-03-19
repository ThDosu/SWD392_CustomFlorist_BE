package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByIsActiveTrueAndValidFromBeforeAndValidToAfter(LocalDate start, LocalDate end);
    Optional<Promotion> findByPromotionCode(String code);

}
