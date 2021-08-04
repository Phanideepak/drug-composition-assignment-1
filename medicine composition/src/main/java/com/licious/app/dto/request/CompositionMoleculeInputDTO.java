package com.licious.app.dto.request;

import com.licious.app.dto.IngredientDetails;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompositionMoleculeInputDTO {
     private List<IngredientDetails> ingredients;
     private boolean rex_required;
}
