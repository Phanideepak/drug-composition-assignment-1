package com.licious.app.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientDetails {
    String ingredientName;
    float strength;
    String unit;
}
