package com.licious.app.service;

import com.licious.app.model.Ingredient;
import com.licious.app.repository.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientsRepository ingredientsRepository;
    // get the ingredient by Id
    public Ingredient getIngredientById(int id){
        return ingredientsRepository.getById(id);
    }
    // get the ingredient by Name
    public Ingredient getIngredientByName(String name){
        return ingredientsRepository.findOneByName(name).get();
    }
    // adding ingredient to database
    public void addIngredient(Ingredient ingredient){
        ingredientsRepository.save(ingredient);
    }
    // getting list of all the ingredients.
    public List<Ingredient> getIngredientList(){
        return ingredientsRepository.findAll();
    }

}
