package edu.fpt.customflorist.responses.Order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String userName;
    private Long promotionId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private Boolean isActive;
    private List<OrderItemResponse> orderItems;
}
