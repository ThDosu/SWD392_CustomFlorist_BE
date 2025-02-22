package edu.fpt.customflorist.dtos.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    @JsonProperty("name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @JsonProperty("description")
    @NotBlank(message = "Description is required")
    private String description;

    @JsonProperty("price")
    @NotBlank(message = "Price is required")
    private BigDecimal price;

    @JsonProperty("customization_available")
    @NotBlank(message = "Customization available is required")
    private Boolean customizationAvailable;

    @JsonProperty("is_active")
    @NotBlank(message = "Active is required")
    private Boolean isActive;
}
