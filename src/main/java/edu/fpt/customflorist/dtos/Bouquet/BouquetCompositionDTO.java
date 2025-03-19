package edu.fpt.customflorist.dtos.Bouquet;

import lombok.Data;

@Data
public class BouquetCompositionDTO {
    private Long id;
    private Long flowerId;
    private String flowerName;
    private Integer quantity;
}
