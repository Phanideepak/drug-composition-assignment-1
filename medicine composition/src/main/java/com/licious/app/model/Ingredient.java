package com.licious.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ingredients")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore(true)
    private List<CompositionIngredient> compositionIngredientList;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore(true)
    private List<MoleculeIngredient> moleculeIngredientList;
}
