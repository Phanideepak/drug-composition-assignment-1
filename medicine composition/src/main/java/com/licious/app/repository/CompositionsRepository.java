package com.licious.app.repository;
/*
  @author Doddaka Sai Phani Deepak
 */
import com.licious.app.model.Composition;
import com.licious.app.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompositionsRepository extends JpaRepository<Composition,Integer> {
    // get composition by compostion name.
    public Optional<Composition> findOneByName(String name);
}
