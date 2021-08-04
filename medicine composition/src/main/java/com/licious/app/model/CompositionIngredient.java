package com.licious.app.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="composition_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompositionIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="composition_id",referencedColumnName = "id")
    private Composition composition;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="ingredient_id",referencedColumnName = "id")
    private Ingredient ingredient;


    private String unit;
    private float strength;
}
