package com.licious.app.controller;

import com.licious.app.dto.request.CompositionMoleculeInputDTO;
import com.licious.app.dto.response.CompositionDetailsDTO;
import com.licious.app.model.Composition;
import com.licious.app.service.CompositionMoleculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/compositions/molecule")
    public List<Composition> getCompositionByIngredientMoleculeDetails(@RequestParam String ingredientName,
                                                               @RequestParam float strength,@RequestParam String unit
   ,@RequestParam boolean rex_required ){
        List<Composition> compositionList=compositionMoleculeService
                .getAllCompositionFilteredByIngredientMoleculeDetails(ingredientName,strength,unit,rex_required);
        return compositionList;
    }
    @PostMapping(value = "/compositions/molecule",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addCompositionMoleculeDetails(@RequestBody List<CompositionMoleculeInputDTO> inputRequestObjects){
        int flag=0;
        for(CompositionMoleculeInputDTO inputDTO : inputRequestObjects){
          String msg=compositionMoleculeService.addCompositonMoleculeDetails(inputDTO);
          if(msg=="success") flag=1;
          System.out.println(msg);
        }
        return (flag==1)? "atleast one composition is added" : "no composition is added";
    }
}
