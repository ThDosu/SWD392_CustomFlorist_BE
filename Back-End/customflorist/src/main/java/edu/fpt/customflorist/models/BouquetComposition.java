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
@Table(name = "BouquetComposition")
public class BouquetComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bouquetCompositionID;

    @ManyToOne
    @JoinColumn(name = "bouquetID", nullable = false)
    private Bouquet bouquet;

    @ManyToOne
    @JoinColumn(name = "flowerID", nullable = false)
    private Flower flower;

    @Column(nullable = false)
    private Integer minQuantity;

    @Column(nullable = false)
    private Integer maxQuantity;

}
