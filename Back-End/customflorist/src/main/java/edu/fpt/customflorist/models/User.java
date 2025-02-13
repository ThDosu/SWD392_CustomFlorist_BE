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

    @Column(name = "userCode")
    private String userCode;

    @Column(name = "email")
    private String email;

    @Column(length = 20, name ="phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "loyaltyPoints")
    private int loyaltyPoints;

    @Column(name = "assignedOrders")
    private int assignedOrders;

    @Column(name = "accountStatus")
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