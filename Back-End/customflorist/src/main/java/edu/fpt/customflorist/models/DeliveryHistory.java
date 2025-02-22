package edu.fpt.customflorist.models;

import edu.fpt.customflorist.models.Enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "DeliveryHistory")
public class DeliveryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private User courier;

    @Column(nullable = false, unique = true)
    private String deliveryCode;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean isActive;

    @PrePersist
    public void generateDeliveryCode() {
        if (this.deliveryCode == null || this.deliveryCode.isEmpty()) {
            this.deliveryCode = "DLV-" + UUID.randomUUID().toString().substring(0, 8);
        }
    }

}
