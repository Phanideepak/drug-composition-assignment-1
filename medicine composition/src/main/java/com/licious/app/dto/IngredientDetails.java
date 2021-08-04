package com.licious.app.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientDetails {
    String name;
    float strength;
    String unit;
}
