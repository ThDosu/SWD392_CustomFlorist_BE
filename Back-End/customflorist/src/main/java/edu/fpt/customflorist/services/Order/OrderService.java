package edu.fpt.customflorist.services.Order;

import edu.fpt.customflorist.dtos.Order.OrderDTO;
import edu.fpt.customflorist.dtos.Order.OrderItemDTO;
import edu.fpt.customflorist.dtos.Order.OrderBouquetFlowerDTO;
import edu.fpt.customflorist.models.Order;
import edu.fpt.customflorist.models.OrderItem;
import edu.fpt.customflorist.models.OrderBouquetFlower;
import edu.fpt.customflorist.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUser(orderDTO.getUserId());
        order.setPromotion(orderDTO.getPromotionId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setIsActive(orderDTO.getIsActive());
        order.setOrderItems(convertOrderItemDTOsToOrderItems(orderDTO.getOrderItems()));
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setUser(orderDTO.getUserId());
            order.setPromotion(orderDTO.getPromotionId());
            order.setOrderDate(orderDTO.getOrderDate());
            order.setDeliveryDate(orderDTO.getDeliveryDate());
            order.setStatus(orderDTO.getStatus());
            order.setTotalPrice(orderDTO.getTotalPrice());
            order.setShippingAddress(orderDTO.getShippingAddress());
            order.setIsActive(orderDTO.getIsActive());
            order.setOrderItems(convertOrderItemDTOsToOrderItems(orderDTO.getOrderItems()));
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
                .userId(order.getUser().getId())
                .promotionId(order.getPromotion() != null ? order.getPromotion().getId() : null)
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .shippingAddress(order.getShippingAddress())
                .isActive(order.getIsActive())
                .orderItems(order.getOrderItems().stream()
                        .map(this::convertOrderItemToDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderItemDTO convertOrderItemToDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .bouquetId(orderItem.getBouquet().getId())
                .orderId(orderItem.getOrder().getOrderId())
                .quantity(orderItem.getQuantity())
                .subTotal(orderItem.getSubTotal())
                .isActive(orderItem.getIsActive())
                .orderBouquetFlowers(orderItem.getOrderBouquetFlowers().stream()
                        .map(this::convertOrderBouquetFlowerToDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderBouquetFlowerDTO convertOrderBouquetFlowerToDTO(OrderBouquetFlower orderBouquetFlower) {
        return OrderBouquetFlowerDTO.builder()
                .orderItemId(orderBouquetFlower.getOrderItem().getOrderItemId())
                .flowerId(orderBouquetFlower.getFlower().getId())
                .quantity(orderBouquetFlower.getQuantity())
                .build();
    }

    private List<OrderItem> convertOrderItemDTOsToOrderItems(List<OrderItemDTO> orderItemDTOs) {
        return orderItemDTOs.stream()
                .map(this::convertOrderItemDTOToOrderItem)
                .collect(Collectors.toList());
    }

    private OrderItem convertOrderItemDTOToOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBouquet(orderItemDTO.getBouquetId());
        orderItem.setOrder(orderItemDTO.getOrderId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setSubTotal(orderItemDTO.getSubTotal());
        orderItem.setIsActive(orderItemDTO.getIsActive());
        orderItem.setOrderBouquetFlowers(orderItemDTO.getOrderBouquetFlowers().stream()
                .map(this::convertOrderBouquetFlowerDTOToOrderBouquetFlower)
                .collect(Collectors.toList()));
        return orderItem;
    }

    private OrderBouquetFlower convertOrderBouquetFlowerDTOToOrderBouquetFlower(OrderBouquetFlowerDTO orderBouquetFlowerDTO) {
        OrderBouquetFlower orderBouquetFlower = new OrderBouquetFlower();
        orderBouquetFlower.setOrderItem(orderBouquetFlowerDTO.getOrderItemId());
        orderBouquetFlower.setFlower(orderBouquetFlowerDTO.getFlowerId());
        orderBouquetFlower.setQuantity(orderBouquetFlowerDTO.getQuantity());
        return orderBouquetFlower;
    }
}