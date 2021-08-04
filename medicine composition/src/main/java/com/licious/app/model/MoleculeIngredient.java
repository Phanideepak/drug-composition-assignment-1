package com.licious.app.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="molecule_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoleculeIngredient {
    @Id
    private int id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="molecule_id",referencedColumnName = "id")
    private Molecule molecule;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="ingredient_id",referencedColumnName = "id")
    private Ingredient ingredient;
}
