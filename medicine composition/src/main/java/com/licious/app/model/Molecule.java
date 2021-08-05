package com.licious.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "molecules")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Molecule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private Boolean rx_required;

    @OneToMany(mappedBy = "molecule",fetch = FetchType.LAZY)
    @JsonIgnore(true)
    //@Transient
    private List<MoleculeIngredient> moleculeIngredientList;
}
