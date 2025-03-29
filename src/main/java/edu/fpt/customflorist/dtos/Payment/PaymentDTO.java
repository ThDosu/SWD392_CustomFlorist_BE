package edu.fpt.customflorist.dtos.Payment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    @Min(value = 11000, message = "Price must be greater than to 10000")
    private BigDecimal finalAmount;

    @NotEmpty(message = "Bank code is required")
    @Schema(description = "Bank code for the transaction", example = "VNBANK")
    private String bankCode;

    @NotNull(message = "orderId is required")
    private Long orderId;
}
