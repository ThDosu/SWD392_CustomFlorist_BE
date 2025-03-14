package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.BouquetComposition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BouquetCompositionRepository extends JpaRepository<BouquetComposition, Long> {
}
