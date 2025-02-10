package edu.fpt.customflorist.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor

public class Order {

    @Id
    private int orderId;
    private int userId;
    private int promotionId;
    private long orderCode;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private enum Status {
        PENDING, PROCESSING,SHIPPED, DELIVERED, CANCELLED
    }
    private BigDecimal totalPrice;
    @Column(length = 255)
    private String shippingAddress;


}