package com.licious.app.controller;

import com.licious.app.model.Ingredient;
import com.licious.app.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/ingredient")
    public String addIngredient(@RequestParam String name){
        Ingredient ingredient=new Ingredient();
        ingredient.setName(name);
        ingredientService.addIngredient(ingredient);
        return "added";
    }
    @GetMapping("/ingredient/all")
    public List<Ingredient> getAllIngredients(){
        return ingredientService.getIngredientList();
    }
}
