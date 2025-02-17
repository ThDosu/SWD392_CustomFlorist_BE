package edu.fpt.customflorist.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "paymentID")
    private int paymentID;

    @Column(name = "orderID")
    private int orderID;

    @Column(name = "transactionCode")
    private String transactionCode;




}
