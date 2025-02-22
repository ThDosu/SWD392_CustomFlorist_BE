package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRepository extends JpaRepository<Flower, Long> {
}
