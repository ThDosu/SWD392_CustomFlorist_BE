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
            "WHERE s.changedAt BETWEEN :startDate AND :endDate")
    Page<DeliveryHistory> findAllByStatusChangedAtBetween(@Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate,
                                                          Pageable pageable);

    @Query("SELECT DISTINCT d FROM DeliveryHistory d " +
            "JOIN d.statusHistories s " +
            "WHERE d.user.userId = :userId " +
            "AND s.changedAt BETWEEN :startDate AND :endDate")
    Page<DeliveryHistory> findByUserUserIdAndStatusChangedAtBetween(@Param("userId") Long userId,
                                                                    @Param("startDate") LocalDateTime startDate,
                                                                    @Param("endDate") LocalDateTime endDate,
                                                                    Pageable pageable);


    @Query("SELECT DISTINCT d FROM DeliveryHistory d " +
            "JOIN d.statusHistories s " +
            "WHERE d.courier.userId = :courierId " +
            "AND s.changedAt BETWEEN :startDate AND :endDate")
    Page<DeliveryHistory> findByCourierUserIdAndStatusChangedAtBetween(@Param("courierId") Long courierId,
                                                                       @Param("startDate") LocalDateTime startDate,
                                                                       @Param("endDate") LocalDateTime endDate,
                                                                       Pageable pageable);

    Page<DeliveryHistory> findByUserUserId(Long userId, Pageable pageable);
    Page<DeliveryHistory> findByCourierUserId(Long courierId, Pageable pageable);
}
