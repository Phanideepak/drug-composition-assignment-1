package com.licious.app.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientDetails {
    // This class is used for holding an ingredient information like name, strength,unit.
    String name;
    float strength;
    String unit;
}
