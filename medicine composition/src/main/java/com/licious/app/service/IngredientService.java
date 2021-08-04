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

    public Ingredient getIngredientById(int id){
        return ingredientsRepository.getById(id);
    }
    public Ingredient getIngredientByName(String name){
        return ingredientsRepository.findOneByName(name).get();
    }
    public void addIngredient(Ingredient ingredient){
        ingredientsRepository.save(ingredient);
    }

    public List<Ingredient> getIngredientList(){
        return ingredientsRepository.findAll();
    }

}
