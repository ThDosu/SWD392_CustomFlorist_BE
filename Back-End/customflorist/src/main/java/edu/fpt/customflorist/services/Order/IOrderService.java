package edu.fpt.customflorist.services.Order;

import edu.fpt.customflorist.dtos.Order.OrderDTO;
import edu.fpt.customflorist.dtos.Order.OrderUpdateDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.models.Enums.Status;
import edu.fpt.customflorist.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    void updateOrder(Long orderId, OrderUpdateDTO status) throws DataNotFoundException;
    void deleteOrder(Long orderId) throws DataNotFoundException;
    Order getOrderById(Long orderId) throws DataNotFoundException;
    Page<Order> getAllOrdersActive(Long userId, LocalDateTime minOrderDate,
                             LocalDateTime maxOrderDate,
                             BigDecimal minPrice,
                             BigDecimal maxPrice,
                             String status,
                             Pageable pageable);
    Page<Order> getAllOrders(LocalDateTime minOrderDate,
                             LocalDateTime maxOrderDate,
                             BigDecimal minPrice,
                             BigDecimal maxPrice,
                             String status,
                             Long userId,
                             Pageable pageable);
}
