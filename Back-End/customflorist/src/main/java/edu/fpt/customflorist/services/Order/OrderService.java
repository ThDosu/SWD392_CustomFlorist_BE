package edu.fpt.customflorist.services.Order;

import edu.fpt.customflorist.dtos.Order.OrderDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.models.Order;
import edu.fpt.customflorist.models.Promotion;
import edu.fpt.customflorist.models.User;
import edu.fpt.customflorist.repositories.OrderRepository;
import edu.fpt.customflorist.repositories.PromotionRepository;
import edu.fpt.customflorist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PromotionRepository promotionRepository;

    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Promotion promotion = null;
        if (orderDTO.getPromotionId() != null) {
            promotion = promotionRepository.findById(orderDTO.getPromotionId())
                    .orElseThrow(() -> new DataNotFoundException("Promotion not found"));
        }

        Order order = new Order();
        order.setUser(user);
        order.setPromotion(promotion);
        order.setOrderDate(orderDTO.getOrderDate());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setIsActive(orderDTO.getIsActive());
        order.setOrderItems(orderDTO.getOrderItems());

        return orderRepository.save(order);
    }

    public Order updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Promotion promotion = null;
        if (orderDTO.getPromotionId() != null) {
            promotion = promotionRepository.findById(orderDTO.getPromotionId())
                    .orElseThrow(() -> new DataNotFoundException("Promotion not found"));
        }

        order.setUser(user);
        order.setPromotion(promotion);
        order.setOrderDate(orderDTO.getOrderDate());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setIsActive(orderDTO.getIsActive());
        order.setOrderItems(orderDTO.getOrderItems());

        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        orderRepository.delete(order);
    }

    public Order getOrderById(Long orderId) throws DataNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}