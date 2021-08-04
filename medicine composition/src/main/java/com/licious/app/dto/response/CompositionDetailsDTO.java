package com.licious.app.dto.response;

import com.licious.app.dto.IngredientDetails;
import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompositionDetailsDTO implements Serializable {
    String compositionName;
    List<IngredientDetails> ingredientDetailsList;
    String moleculeName;
    boolean rx_required;
}
