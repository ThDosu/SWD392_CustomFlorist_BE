package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Bouquet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BouquetRepository extends JpaRepository<Bouquet, Long>  {
    List<Bouquet> findAllByIsActiveTrue();
}
