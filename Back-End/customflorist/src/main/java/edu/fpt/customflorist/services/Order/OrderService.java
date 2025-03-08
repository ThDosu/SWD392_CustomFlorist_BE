package edu.fpt.customflorist.services.Order;

import edu.fpt.customflorist.dtos.Order.OrderDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.models.*;
import edu.fpt.customflorist.models.Enums.Status;
import edu.fpt.customflorist.repositories.*;
import edu.fpt.customflorist.responses.Order.OrderBouquetFlowerResponse;
import edu.fpt.customflorist.responses.Order.OrderItemResponse;
import edu.fpt.customflorist.responses.Order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderBouquetFlowerRepository orderBouquetFlowerRepository;
    private final UserRepository userRepository;
    private final PromotionRepository promotionRepository;
    private final BouquetRepository bouquetRepository;
    private final FlowerRepository flowerRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        // Lấy thông tin User
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Lấy thông tin Promotion nếu có
        Promotion promotion = orderDTO.getPromotionId() != null ?
                promotionRepository.findById(orderDTO.getPromotionId())
                        .orElseThrow(() -> new DataNotFoundException("Promotion not found"))
                : null;

        // Tạo Order mới
        Order order = new Order();
        order.setUser(user);
        order.setPromotion(promotion);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setIsActive(true);

        // Lưu order vào DB trước để có orderId
        order = orderRepository.save(order);

        // Tạo danh sách OrderItem từ DTO
        for (var orderItemDTO : orderDTO.getOrderItems()) {
            Bouquet bouquet = bouquetRepository.findById(orderItemDTO.getBouquetId())
                    .orElseThrow(() -> new DataNotFoundException("Bouquet not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBouquet(bouquet);
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setSubTotal(orderItemDTO.getSubTotal());
            orderItem.setIsActive(true);

            // Lưu OrderItem trước để có orderItemId
            orderItem = orderItemRepository.save(orderItem);

            // Tạo danh sách OrderBouquetFlower từ DTO
            for (var bouquetFlowerDTO : orderItemDTO.getOrderBouquetFlowers()) {
                Flower flower = flowerRepository.findById(bouquetFlowerDTO.getFlowerId())
                        .orElseThrow(() -> new DataNotFoundException("Flower not found"));

                OrderBouquetFlower orderBouquetFlower = new OrderBouquetFlower();
                orderBouquetFlower.setOrderItem(orderItem);
                orderBouquetFlower.setFlower(flower);
                orderBouquetFlower.setQuantity(bouquetFlowerDTO.getQuantity());

                orderBouquetFlowerRepository.save(orderBouquetFlower);
            }
        }
        return order;
    }

    @Override
    public void deleteOrder(Long orderId) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        order.setIsActive(false);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws DataNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
    }

    @Override
    public Page<Order> getAllOrdersActive(LocalDateTime minOrderDate, LocalDateTime maxOrderDate,
                                    BigDecimal minPrice, BigDecimal maxPrice, String statusStr, Pageable pageable) {
        Status status = null;
        if (statusStr != null && !statusStr.isEmpty()) {
            try {
                status = Status.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status:" + statusStr);
            }
        }
        return orderRepository.findActiveByFilters(minOrderDate, maxOrderDate, minPrice, maxPrice, status, pageable);
    }

    @Override
    public Page<Order> getAllOrders(LocalDateTime minOrderDate, LocalDateTime maxOrderDate,
                                          BigDecimal minPrice, BigDecimal maxPrice, String statusStr, Pageable pageable) {
        Status status = null;
        if (statusStr != null && !statusStr.isEmpty()) {
            try {
                status = Status.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status:" + statusStr);
            }
        }
        return orderRepository.findAllByFilters(minOrderDate, maxOrderDate, minPrice, maxPrice, status, pageable);
    }

    public OrderResponse convertToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .userName(order.getUser().getName())
                .promotionId(order.getPromotion() != null ? order.getPromotion().getPromotionId() : null)
                .orderDate(order.getOrderDate())
                .status(order.getStatus().name())
                .totalPrice(order.getTotalPrice())
                .shippingAddress(order.getShippingAddress())
                .isActive(order.getIsActive())
                .orderItems(order.getOrderItems().stream().map(this::convertToOrderItemResponse).toList())
                .build();
    }

    private OrderItemResponse convertToOrderItemResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .bouquetId(orderItem.getBouquet().getBouquetId())
                .bouquetName(orderItem.getBouquet().getName())
                .quantity(orderItem.getQuantity())
                .subTotal(orderItem.getSubTotal())
                .isActive(orderItem.getIsActive())
                .bouquetFlowers(orderItem.getOrderBouquetFlowers().stream().map(this::convertToOrderBouquetFlowerResponse).toList())
                .build();
    }

    private OrderBouquetFlowerResponse convertToOrderBouquetFlowerResponse(OrderBouquetFlower obf) {
        return OrderBouquetFlowerResponse.builder()
                .flowerId(obf.getFlower().getFlowerId())
                .flowerName(obf.getFlower().getName())
                .quantity(obf.getQuantity())
                .build();
    }

}