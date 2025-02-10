package edu.fpt.customflorist.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User {

    // Getters and Setters
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

    // No-argument constructor
    public User() {
    }

    // All-argument constructor
    public User(Integer userID, String userCode, String email, String phone, String address, Role role,
                int loyaltyPoints, int assignedOrders, AccountStatus accountStatus) {
        this.userID = userID;
        this.userCode = userCode;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.loyaltyPoints = loyaltyPoints;
        this.assignedOrders = assignedOrders;
        this.accountStatus = accountStatus;
    }

}