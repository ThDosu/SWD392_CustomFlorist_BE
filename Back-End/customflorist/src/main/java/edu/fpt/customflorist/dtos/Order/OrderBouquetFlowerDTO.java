package edu.fpt.customflorist.dtos.Order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderBouquetFlowerDTO {
    private Long flowerId;
    private Integer quantity;
}
