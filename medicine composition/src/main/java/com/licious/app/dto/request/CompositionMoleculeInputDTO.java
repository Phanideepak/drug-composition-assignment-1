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
     /* This DTO is a request body that has list of ingredient details and rex_required of
     *  the related molecule.
     *  This DTO is a request body and given as input from postman-client.
     * */
     private List<IngredientDetails> ingredients;
     private boolean rex_required;
}
