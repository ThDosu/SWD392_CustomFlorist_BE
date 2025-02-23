package edu.fpt.customflorist.services.DeliveryHistory;

import edu.fpt.customflorist.dtos.DeliveryHistory.DeleteDeliveryHistoryDTO;
import edu.fpt.customflorist.dtos.DeliveryHistory.DeliveryHistoryDTO;
import edu.fpt.customflorist.dtos.DeliveryHistory.UpdateDeliveryHistoryDTO;
import edu.fpt.customflorist.models.DeliveryHistory;
import edu.fpt.customflorist.models.DeliveryStatusHistory;
import edu.fpt.customflorist.models.Order;
import edu.fpt.customflorist.models.User;
import edu.fpt.customflorist.repositories.DeliveryHistoryRepository;
import edu.fpt.customflorist.repositories.DeliveryStatusHistoryRepository;
import edu.fpt.customflorist.repositories.OrderRepository;
import edu.fpt.customflorist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DeliveryHistoryService implements IDeliveryHistoryService{
    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final DeliveryStatusHistoryRepository deliveryStatusHistoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public DeliveryHistory createDeliveryHistory(DeliveryHistoryDTO dto) throws Exception {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
        User courier = userRepository.findById(dto.getCourierId())
                .orElseThrow(() -> new Exception("Courier not found"));
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new Exception("Order not found"));

        DeliveryHistory deliveryHistory = new DeliveryHistory();
        deliveryHistory.setUser(user);
        deliveryHistory.setCourier(courier);
        deliveryHistory.setOrder(order);
        deliveryHistory.setIsActive(true);

        deliveryHistory = deliveryHistoryRepository.save(deliveryHistory);

        DeliveryStatusHistory statusHistory = new DeliveryStatusHistory();
        statusHistory.setDeliveryHistory(deliveryHistory);
        statusHistory.setStatus(dto.getStatus());
        statusHistory.setNote(dto.getNote());
        statusHistory.setChangedAt(dto.getDeliveryDate());

        deliveryStatusHistoryRepository.save(statusHistory);

        return deliveryHistory;
    }

    @Override
    public DeliveryStatusHistory updateDeliveryHistory(Long deliveryId, UpdateDeliveryHistoryDTO dto) throws Exception {
        DeliveryHistory deliveryHistory = deliveryHistoryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception("Delivery history not found"));

        DeliveryStatusHistory statusHistory = new DeliveryStatusHistory();
        statusHistory.setDeliveryHistory(deliveryHistory);
        statusHistory.setStatus(dto.getStatus());
        statusHistory.setNote(dto.getNote());
        statusHistory.setChangedAt(dto.getDeliveryDate());

        return deliveryStatusHistoryRepository.save(statusHistory);
    }

    @Override
    public DeliveryHistory getDeliveryHistoryById(Long id) throws Exception {
        return deliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Delivery history not found"));
    }

    @Override
    public void deleteDeliveryHistory(Long id, DeleteDeliveryHistoryDTO dto) throws Exception {
        DeliveryHistory deliveryHistory = deliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Delivery history not found"));
        deliveryHistory.setIsActive(dto.getIsActive());

        deliveryHistoryRepository.save(deliveryHistory);
    }

    @Override
    public Page<DeliveryHistory> getAllDeliveryHistories(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        if (startDate == null || endDate == null) {
            return deliveryHistoryRepository.findAll(pageable);
        }
        return deliveryHistoryRepository.findAllByStatusChangedAtBetween(startDate, endDate, pageable);
    }

    @Override
    public Page<DeliveryHistory> getAllByUserId(Long userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        if (startDate == null || endDate == null) {
            return deliveryHistoryRepository.findByUserUserId(userId, pageable);
        }
        return deliveryHistoryRepository.findByUserUserIdAndStatusChangedAtBetween(userId, startDate, endDate, pageable);
    }

    @Override
    public Page<DeliveryHistory> getAllByCourierId(Long courierId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        if (startDate == null || endDate == null) {
            return deliveryHistoryRepository.findByCourierUserId(courierId, pageable);
        }
        return deliveryHistoryRepository.findByCourierUserIdAndStatusChangedAtBetween(courierId, startDate, endDate, pageable);
    }
}
