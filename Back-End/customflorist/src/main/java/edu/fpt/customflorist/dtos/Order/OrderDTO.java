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
    private Long userId;
    private Long promotionId;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private List<OrderItemDTO> orderItems;
}