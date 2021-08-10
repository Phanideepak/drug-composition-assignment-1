package com.licious.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="compositions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Composition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "composition",fetch = FetchType.LAZY)
    @JsonIgnore(true)
    @ToString.Exclude
    //@Transient
    private List<CompositionIngredient> compositionIngredientList;
    
}
