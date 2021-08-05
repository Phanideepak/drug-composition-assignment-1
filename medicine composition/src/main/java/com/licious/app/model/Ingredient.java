package com.licious.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "ingredient",fetch = FetchType.LAZY)
    @JsonIgnore(true)
    //@Transient
    private List<CompositionIngredient> compositionIngredientList;

    @OneToMany(mappedBy = "ingredient",fetch = FetchType.LAZY)
    @JsonIgnore(true)
    //@Transient
    private List<MoleculeIngredient> moleculeIngredientList;

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
