package edu.fpt.customflorist.dtos.Bouquet;

import edu.fpt.customflorist.models.Category;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BouquetDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private String imageUrl;
    private List<BouquetCompositionDTO> compositions;
}