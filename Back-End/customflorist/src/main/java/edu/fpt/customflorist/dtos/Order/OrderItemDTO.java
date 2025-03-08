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
    private Long bouquetId;
    private Integer quantity;
    private BigDecimal subTotal;
    private List<OrderBouquetFlowerDTO> orderBouquetFlowers;
}