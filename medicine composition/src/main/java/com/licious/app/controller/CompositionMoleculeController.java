package com.licious.app.controller;

import com.licious.app.dto.request.CompositionMoleculeInputDTO;
import com.licious.app.dto.response.CompositionDetailsDTO;
import com.licious.app.dto.response.CompositionInsertionStatus;
import com.licious.app.model.Composition;
import com.licious.app.service.CompositionMoleculeService;
import com.licious.app.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
 It is a controller common for all api related operations on all entities.
 */
@RestController
public class CompositionMoleculeController {
    /* It is service common for all the entities. It handles implementation of
    *  required database operations depending on the api method requirements.
    * It interacts with the repositories of all Entities and handles the CRUD operations.
    * */
    @Autowired
    CompositionMoleculeService compositionMoleculeService;

    /*
     This GET api takes composition_id as parameter and returns
      the DTO object that contains composition name, ingredients with strengths and units,
      molecule and rx_required value.
     */
    @GetMapping("/composition")
    public CompositionDetailsDTO getCompostionDetailsByCompostionId(@RequestParam int compositionId){
         return compositionMoleculeService.getCompositionDetailsByCompositionId(compositionId);
    }


    /*
     This GET API takes ingredient name,strength,unit as parameter and returns the composition
     that  contains given ingredient in given dosage(strength).
     */
    @GetMapping("/compositions")
    public List<Composition> getCompositionByIngredientDetails(@RequestParam String ingredientName,
                                                               @RequestParam float strength,@RequestParam String unit
                                                               ){
        List<Composition> compositionList=compositionMoleculeService
                .getAllCompositionsFilteredByIngrediantDetails(ingredientName,strength,unit);
        return compositionList;
    }


    /*
    This GET API takes ingredient name,strength,unit as parameter and returns the composition
    that  contains given ingredient in given dosage(strength) and molecule that has given rex_required value.
  */
    @GetMapping("/compositions/molecule")
    public List<Composition> getCompositionByIngredientMoleculeDetails(@RequestParam String ingredientName,
                                                               @RequestParam float strength,@RequestParam String unit
   ,@RequestParam boolean rex_required ){
        List<Composition> compositionList=compositionMoleculeService
                .getAllCompositionFilteredByIngredientMoleculeDetails(ingredientName,strength,unit,rex_required);
        return compositionList;
    }


    /*
      This POST API takes a list of input objects as request body parameter.
      The input object contains list of ingredients and rex_required value for a composition.
      Each input object will constitute to  one composition and one molecule.
      This POST API can insert more than one composition to the database.
      It is used to insert multiple different compositions.
     */
    @PostMapping(value = "/compositions/molecule",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CompositionInsertionStatus> addCompositionMoleculeDetails(@RequestBody List<CompositionMoleculeInputDTO> inputRequestObjects){
        int flag=0;
        List<CompositionInsertionStatus> responseArray=new ArrayList<>();

        for(CompositionMoleculeInputDTO inputDTO : inputRequestObjects){
          CompositionInsertionStatus compositionInsertionStatus=new CompositionInsertionStatus();

          String compositionName= CommonUtils.createCompositionName(inputDTO.getIngredients()) ;
          String msg=compositionMoleculeService.addCompositonMoleculeDetails(inputDTO,compositionName);

          if(msg=="success") flag=1;
          compositionInsertionStatus.setCompositionName(compositionName);
          compositionInsertionStatus.setInsertionStatus(msg);
          responseArray.add(compositionInsertionStatus);

        }

        return responseArray;
    }
}
