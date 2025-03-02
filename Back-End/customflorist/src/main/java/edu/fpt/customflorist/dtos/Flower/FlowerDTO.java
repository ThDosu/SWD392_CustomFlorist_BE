package edu.fpt.customflorist.dtos.Flower;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowerDTO {
    @NotNull(message = "Name is required")
    @Size(max = 255, message = "Name should not exceed 255 characters")
    private String name;

    @NotNull(message = "Flower type is required")
    @Size(max = 50, message = "Flower type should not exceed 50 characters")
    private String flowerType;

    @NotNull(message = "Color is required")
    @Size(max = 50, message = "Color should not exceed 50 characters")
    private String color;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Image is required")
    @Size(max = 255, message = "Image URL should not exceed 255 characters")
    private String image;

    @NotNull(message = "Active status is required")
    private Boolean isActive;
}