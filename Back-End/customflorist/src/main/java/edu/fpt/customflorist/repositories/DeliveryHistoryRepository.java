package edu.fpt.customflorist.repositories;

import edu.fpt.customflorist.models.DeliveryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {

    @Query("SELECT DISTINCT d FROM DeliveryHistory d " +
            "JOIN d.statusHistories s " +
            "WHERE (:startDate IS NULL OR s.changedAt >= :startDate) " +
            "AND (:endDate IS NULL OR s.changedAt <= :endDate)")
    Page<DeliveryHistory> findAllByStatusChangedAtBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT DISTINCT d FROM DeliveryHistory d " +
            "JOIN d.statusHistories s " +
            "WHERE d.user.userId = :userId " +
            "AND (:startDate IS NULL OR s.changedAt >= :startDate) " +
            "AND (:endDate IS NULL OR s.changedAt <= :endDate)")
    Page<DeliveryHistory> findByUserUserIdAndStatusChangedAtBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT DISTINCT d FROM DeliveryHistory d " +
            "JOIN d.statusHistories s " +
            "WHERE d.courier.userId = :courierId " +
            "AND (:startDate IS NULL OR s.changedAt >= :startDate) " +
            "AND (:endDate IS NULL OR s.changedAt <= :endDate)")
    Page<DeliveryHistory> findByCourierUserIdAndStatusChangedAtBetween(
            @Param("courierId") Long courierId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT DISTINCT d FROM DeliveryHistory d " +
            "JOIN d.statusHistories s " +
            "WHERE d.isActive = true " +
            "AND d.user.userId = :userId " +
            "AND (:startDate IS NULL OR s.changedAt >= :startDate) " +
            "AND (:endDate IS NULL OR s.changedAt <= :endDate)")
    Page<DeliveryHistory> findActiveByUserUserIdAndStatusChangedAtBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT DISTINCT d FROM DeliveryHistory d " +
            "JOIN d.statusHistories s " +
            "WHERE d.isActive = true " +
            "AND d.courier.userId = :courierId " +
            "AND (:startDate IS NULL OR s.changedAt >= :startDate) " +
            "AND (:endDate IS NULL OR s.changedAt <= :endDate)")
    Page<DeliveryHistory> findActiveByCourierUserIdAndStatusChangedAtBetween(
            @Param("courierId") Long courierId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

}
