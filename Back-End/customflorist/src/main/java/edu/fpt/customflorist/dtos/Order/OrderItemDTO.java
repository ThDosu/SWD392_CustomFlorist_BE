package edu.fpt.customflorist.dtos.Order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    @NotNull(message = "Bouquet ID is required")
    private Long bouquetId;

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "SubTotal is required")
    private BigDecimal subTotal;

    @NotNull(message = "Active status is required")
    private Boolean isActive;

    private List<OrderBouquetFlowerDTO> orderBouquetFlowers;
}