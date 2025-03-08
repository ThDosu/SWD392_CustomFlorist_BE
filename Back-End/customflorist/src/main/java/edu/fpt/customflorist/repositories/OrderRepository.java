package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.Enums.Status;
import edu.fpt.customflorist.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT o FROM Order o
        WHERE (:minOrderDate IS NULL OR o.orderDate >= :minOrderDate)
        AND (:maxOrderDate IS NULL OR o.orderDate <= :maxOrderDate)
        AND (:minPrice IS NULL OR o.totalPrice >= :minPrice)
        AND (:maxPrice IS NULL OR o.totalPrice <= :maxPrice)
        AND (:status IS NULL OR o.status = :status)
    """)
    Page<Order> findAllByFilters(
            @Param("minOrderDate") LocalDateTime minOrderDate,
            @Param("maxOrderDate") LocalDateTime maxOrderDate,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("status") Status status,
            Pageable pageable
    );

    @Query("""
        SELECT o FROM Order o
        WHERE o.isActive = true
        AND (:minOrderDate IS NULL OR o.orderDate >= :minOrderDate)
        AND (:maxOrderDate IS NULL OR o.orderDate <= :maxOrderDate)
        AND (:minPrice IS NULL OR o.totalPrice >= :minPrice)
        AND (:maxPrice IS NULL OR o.totalPrice <= :maxPrice)
        AND (:status IS NULL OR o.status = :status)
    """)
    Page<Order> findActiveByFilters(
            @Param("minOrderDate") LocalDateTime minOrderDate,
            @Param("maxOrderDate") LocalDateTime maxOrderDate,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("status") Status status,
            Pageable pageable
    );
}
