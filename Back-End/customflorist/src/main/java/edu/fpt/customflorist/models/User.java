package edu.fpt.customflorist.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(length = 255)
    private String userCode;

    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int loyaltyPoints;

    private int assignedOrders;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    // Enum for user roles
    public enum Role {
        ADMIN, CUSTOMER, SHIPPER, MANAGER
    }

    // Enum for account status
    public enum AccountStatus {
        ACTIVE, INACTIVE, BANNED
    }



}