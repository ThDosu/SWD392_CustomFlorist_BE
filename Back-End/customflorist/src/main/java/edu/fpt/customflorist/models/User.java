package edu.fpt.customflorist.models;

import jakarta.persistence.*;

@Entity
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

    // Getters and Setters
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getAssignedOrders() {
        return assignedOrders;
    }

    public void setAssignedOrders(int assignedOrders) {
        this.assignedOrders = assignedOrders;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}