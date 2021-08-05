/*
  @author Doddaka Sai Phani Deepak
 */
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

    /*
     Getting ids of compositions that contain ingredient with given id, strength and units.
     */
    @Query(value="select distinct composition_id from composition_ingredients where ingredient_id=?1 and strength=?2 and unit=?3",
            nativeQuery = true)
    public List<Integer> findAllByIngredientStrengthUnit(int ingredientId, float Strength, String Unit);


    /*
     Joining the composition_ingredients table with ingredients table and performs select query to
     get details of all ingredients related to given composition. Ingredient details contains
     ingredient name, strength,unit and ingredient id.
     The below method maps every ingredient details  to Tuple data type and returns List of Tuples.
     */
    @Query(value="select name,strength,unit,ingredient_id from " +
            "composition_ingredients join ingredients on ingredient_id=ingredients.id where composition_id=?1",
            nativeQuery = true)
    public List<Tuple> findAllIngredientByCompostionId(int compositionId);


    /* getting the compositon that contains given ingredient and molecule with given rex_required value.
     Joined  composition table to composition_ingredients to get composition name.
     Joined composition_ingredients to molecule_ingredients to find the molecule that contain
       same ingredients as composition.
    Joined molecule_ingredients to molecule to check whether molecule with given rx value exist or not.
    */
    @Query(value="select distinct composition_ingredients.composition_id from compositions \n" +
            "   inner join composition_ingredients on compositions.id=composition_ingredients.composition_id\n" +
            "   inner join molecule_ingredients on composition_ingredients.ingredient_id=molecule_ingredients.ingredient_id\n" +
            "   inner join molecules on molecule_ingredients.molecule_id=molecules.id\n" +
            "   where (molecule_ingredients.ingredient_id=?1 and strength=?2 and unit=?3 and molecules.rx_required=?4);",
    nativeQuery = true)
    public List<Integer> findAllCompositionsByIngredientMoleculeDetails(int ingredientId, float Strength,
                                                                        String Unit,boolean rex_required);
}
