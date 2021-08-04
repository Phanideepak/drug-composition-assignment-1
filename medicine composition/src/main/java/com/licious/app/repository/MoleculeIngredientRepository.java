package com.licious.app.repository;

import com.licious.app.model.MoleculeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface MoleculeIngredientRepository extends JpaRepository<MoleculeIngredient,Integer> {

    /*
    getting the ids of all ingredients that related to molecule with given molecule_id by using
    the select query on molecule_ingredients table.
     */
    @Query(value = "select ingredient_id from molecule_ingredients where molecule_id=?1",nativeQuery = true)
    public List<Integer> findAllMoleculeIngredientsByMoleculeId(int molecule_id);
}
