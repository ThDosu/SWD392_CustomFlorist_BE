package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
