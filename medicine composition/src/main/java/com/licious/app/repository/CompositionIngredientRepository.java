package com.licious.app.repository;

import com.licious.app.dto.IngredientDetails;
import com.licious.app.model.CompositionIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CompositionIngredientRepository extends JpaRepository<CompositionIngredient,Integer> {

    @Query(value="select distinct composition_id from composition_ingredients where ingredient_id=?1 and strength=?2 and unit=?3",
            nativeQuery = true)
    public List<Integer> findAllByIngredientStrengthUnit(int ingredientId, float Strength, String Unit);


    @Query(value="select name,strength,unit,ingredient_id from " +
            "composition_ingredients join ingredients on ingredient_id=ingredients.id where composition_id=?1",
            nativeQuery = true)
    public List<Tuple> findAllIngredientByCompostionId(int compositionId);

    @Query(value="select distinct composition_ingredients.composition_id from compositions \n" +
            "   inner join composition_ingredients on compositions.id=composition_ingredients.composition_id\n" +
            "   inner join molecule_ingredients on composition_ingredients.ingredient_id=molecule_ingredients.ingredient_id\n" +
            "   inner join molecules on molecule_ingredients.molecule_id=molecules.id\n" +
            "   where (molecule_ingredients.ingredient_id=?1 and strength=?2 and unit=?3 and molecules.rx_required=?4);",
    nativeQuery = true)
    public List<Integer> findAllCompositionsByIngredientMoleculeDetails(int ingredientId, float Strength,
                                                                        String Unit,boolean rex_required);
}
