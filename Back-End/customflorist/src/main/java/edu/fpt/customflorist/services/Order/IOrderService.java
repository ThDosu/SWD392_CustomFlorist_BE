package edu.fpt.customflorist.services.Order;

import edu.fpt.customflorist.dtos.Order.OrderDTO;
import edu.fpt.customflorist.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO);
    Order getOrderById(Long id);
    Order updateOrder(Long id, OrderDTO orderDTO);
    void deleteOrder(Long id);
    List<OrderDTO> getAllOrders();
}