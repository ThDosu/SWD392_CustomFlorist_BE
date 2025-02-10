package edu.fpt.customflorist.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(length = 50, nullable = false, unique = true)
    private String roleTitle;

    @Column(length = 255)
    private String roleDescription;

}