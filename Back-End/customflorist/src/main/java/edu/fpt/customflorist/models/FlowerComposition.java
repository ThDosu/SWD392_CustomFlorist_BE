package edu.fpt.customflorist.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "FlowerComposition")
public class FlowerComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flowerCompositionID;

    @ManyToOne
    @JoinColumn(name = "flowerID", nullable = false)
    private Flower flower;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

}
