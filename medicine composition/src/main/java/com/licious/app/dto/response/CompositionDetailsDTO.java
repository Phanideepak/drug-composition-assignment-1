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
    /**
     *  This DTO is an response DTO that holds composition details such as composition name,
     *  list of all the ingredients, molecule name and molecule rx_required value.
     */
    String compositionName;
    List<IngredientDetails> ingredientDetailsList;
    String moleculeName;
    boolean rx_required;
}
