package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.order.orderId = :orderId")
    Optional<Payment> findByOrderId(@Param("orderId") Long orderId);
}
