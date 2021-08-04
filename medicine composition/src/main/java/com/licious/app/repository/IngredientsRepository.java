package com.licious.app.repository;

import com.licious.app.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient,Integer> {
    public Optional<Ingredient> findOneByName(String name);
}
