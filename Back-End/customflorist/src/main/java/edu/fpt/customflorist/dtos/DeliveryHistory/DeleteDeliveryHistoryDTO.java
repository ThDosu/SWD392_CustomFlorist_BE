package edu.fpt.customflorist.dtos.DeliveryHistory;

import edu.fpt.customflorist.models.Enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDeliveryHistoryDTO {
    private Boolean isActive;
}
