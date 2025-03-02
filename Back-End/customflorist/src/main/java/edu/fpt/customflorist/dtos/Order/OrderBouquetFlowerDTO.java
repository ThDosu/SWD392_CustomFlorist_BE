package edu.fpt.customflorist.dtos.Order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBouquetFlowerDTO {
    @NotNull(message = "Order Item ID is required")
    private Long orderItemId;

    @NotNull(message = "Flower ID is required")
    private Long flowerId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}