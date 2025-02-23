package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Bouquet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BouquetRepository extends JpaRepository<Bouquet, Long>  {
}
