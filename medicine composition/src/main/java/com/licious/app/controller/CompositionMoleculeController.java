package com.licious.app.controller;

import com.licious.app.dto.response.CompositionDetailsDTO;
import com.licious.app.model.Composition;
import com.licious.app.service.CompositionMoleculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompositionMoleculeController {

    @Autowired
    CompositionMoleculeService compositionMoleculeService;

    @GetMapping("/composition")
    public CompositionDetailsDTO getCompostionDetailsByCompostionId(@RequestParam int compositionId){
         return compositionMoleculeService.getCompositionDetailsByCompositionId(compositionId);
    }

    @GetMapping("/compositions")
    public List<Composition> getCompositionByIngredientDetails(@RequestParam String ingredientName,
                                                               @RequestParam float strength,@RequestParam String unit
                                                               ){
        List<Composition> compositionList=compositionMoleculeService
                .getAllCompositionsFilteredByIngrediantDetails(ingredientName,strength,unit);
        return compositionList;
    }
}
