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
    private Long bouquetCompositionId;

    @ManyToOne
    @JoinColumn(name = "bouquet_id", nullable = false)
    private Bouquet bouquet;

    @ManyToOne
    @JoinColumn(name = "flower_id", nullable = false)
    private Flower flower;

    @Column(nullable = false)
    private Integer minQuantity;

    @Column(nullable = false)
    private Integer maxQuantity;

    @Column(nullable = false)
    private Boolean isActive;

}
