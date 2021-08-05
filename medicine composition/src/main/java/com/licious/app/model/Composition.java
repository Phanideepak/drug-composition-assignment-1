package com.licious.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="compositions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "composition",fetch = FetchType.LAZY)
    @JsonIgnore(true)
    //@Transient
    private List<CompositionIngredient> compositionIngredientList;
}
