package edu.fpt.customflorist.dtos.Order;

import edu.fpt.customflorist.models.Enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "User ID is required")
    private Long userId;

    private Long promotionId;

    @NotNull(message = "Order date is required")
    private LocalDateTime orderDate;

    @NotNull(message = "Delivery date is required")
    private LocalDateTime deliveryDate;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Total price is required")
    private BigDecimal totalPrice;

    @NotNull(message = "Shipping address is required")
    private String shippingAddress;

    @NotNull(message = "Active status is required")
    private Boolean isActive;

    private List<OrderItemDTO> orderItems;
}