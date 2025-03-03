package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
