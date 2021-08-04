package com.licious.app.service;

import com.licious.app.dto.request.CompositionMoleculeInputDTO;
import com.licious.app.dto.response.CompositionDetailsDTO;
import com.licious.app.dto.IngredientDetails;
import com.licious.app.model.*;
import com.licious.app.repository.*;
import com.licious.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompositionMoleculeService {

    @Autowired
    private CompositionIngredientRepository compositionIngredientRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private MoleculesRepository moleculesRepository;
    @Autowired
    private MoleculeIngredientRepository moleculeIngredientRepository;
    @Autowired
    private CompositionsRepository compositionsRepository;
    
    public CompositionDetailsDTO getCompositionDetailsByCompositionId(int compositionId){
        CompositionDetailsDTO compositionDetailsDTO=new CompositionDetailsDTO();

        String compositionName=compositionsRepository.findById(compositionId).get().getName();
        List<Tuple> ingredientDetailsTuple=compositionIngredientRepository.findAllIngredientByCompostionId(compositionId);

        List<IngredientDetails> ingredientDetailsList=ingredientDetailsTuple.stream()
                .map(t->new IngredientDetails(t.get(0,String.class),t.get(1,Float.class),t.get(2,String.class))).collect(Collectors.toList());

        List<Integer> ingredientIds=ingredientDetailsTuple.stream().map(t->t.get(3,Integer.class)).collect(Collectors.toList());
        //System.out.println(ingredientDetailsList);
        Molecule molecule=findMoleculeWithGivenIngredients(ingredientIds);

        compositionDetailsDTO.setCompositionName(compositionName);
        compositionDetailsDTO.setIngredientDetailsList(ingredientDetailsList);
        compositionDetailsDTO.setMoleculeName(molecule.getName());
        compositionDetailsDTO.setRx_required(molecule.getRx_required());

        return compositionDetailsDTO;
    }
    public Molecule findMoleculeWithGivenIngredients(List<Integer> ingredientIds){

        List<Molecule> moleculeList=moleculesRepository.findAll();

        for(Molecule m:moleculeList){
            //finding corresponding ingredientList
            List<Integer> moleculeIngredientIds=moleculeIngredientRepository
                    .findAllMoleculeIngredientsByMoleculeId(m.getId());
            System.out.println(m.getId());
            System.out.println(moleculeIngredientIds);
            if(moleculeIngredientIds.containsAll(ingredientIds)&&ingredientIds.containsAll(moleculeIngredientIds)){
                return moleculesRepository.getById(m.getId());
            }
        }
      return null;
    }
    public List<Composition> getAllCompositionsFilteredByIngrediantDetails(String ingredientName, float strength,String unit){
        int ingredientId=ingredientsRepository.findOneByName(ingredientName).get().getId();
        List<Integer> compositionIds=compositionIngredientRepository.
                findAllByIngredientStrengthUnit(ingredientId,strength,unit);
        List<Composition> compositionList=new ArrayList<>();
        for(Integer compositionId: compositionIds){
            Composition composition=compositionsRepository.findById(compositionId).get();
            compositionList.add(composition);
        }
        return compositionList;
    }
    public List<Composition> getAllCompositionFilteredByIngredientMoleculeDetails(String ingredientName, float strength,String unit,
                                                                                  boolean rex_required){
        int ingredientId=ingredientsRepository.findOneByName(ingredientName).get().getId();
        List<Integer> compositionIds=compositionIngredientRepository
                .findAllCompositionsByIngredientMoleculeDetails(ingredientId,strength,unit,rex_required);
        List<Composition> compositionList=new ArrayList<>();
        for(Integer compositionId: compositionIds){
            Composition composition=compositionsRepository.findById(compositionId).get();
            compositionList.add(composition);
        }
        return compositionList;
    }

    public String addCompositonMoleculeDetails(CompositionMoleculeInputDTO inputDTO){
       System.out.println(inputDTO.getIngredients());
       List<String> existingIngredientNames=getIngredientNames();
       List<String> existingMoleculeNames=getMoleculeNames();
       List<String> existingCompositionNames=getCompositeNames();

       List<IngredientDetails> ingredientDetailsList=inputDTO.getIngredients();
       for(IngredientDetails ingredient: ingredientDetailsList){
           String ingredientName=ingredient.getName();
           float strength=ingredient.getStrength();
           String unit=ingredient.getUnit();
           if(existingIngredientNames.indexOf(ingredientName)==-1){
               Ingredient ingredient1=new Ingredient();
               ingredient1.setName(ingredientName);
               ingredientsRepository.save(ingredient1);
           }

       }
       List<String> ingredientNameList=ingredientDetailsList
               .stream().map(i->i.getName()).collect(Collectors.toList());

       String moleculeName= Utils.createMoleculeName(ingredientNameList);
       String compositionName=Utils.createCompositionName(ingredientDetailsList);
       if(existingMoleculeNames.indexOf(moleculeName)==-1){
           Molecule molecule=new Molecule();
           molecule.setName(moleculeName);
           molecule.setRx_required(inputDTO.isRex_required());
           moleculesRepository.save(molecule);
       }
       Molecule molecule=moleculesRepository.findOneByName(moleculeName).get();
       int moleculeId=molecule.getId();
       if(existingCompositionNames.indexOf(compositionName)>0) {
            return "composition already exists";
       }
       else {
           Composition composition=new Composition();
           composition.setName(compositionName);
           compositionsRepository.save(composition);
       }
       Composition composition=compositionsRepository.findOneByName(compositionName).get();
       int compositionId=composition.getId();
       for(IngredientDetails ingredientDetail:ingredientDetailsList){
           Ingredient ingredient=ingredientsRepository.findOneByName(ingredientDetail.getName()).get();

           composition=null;
           composition=compositionsRepository.findById(compositionId).get();

           CompositionIngredient compositionIngredient=new CompositionIngredient();
           compositionIngredient.setComposition(composition);
           compositionIngredient.setIngredient(ingredient);
           compositionIngredient.setStrength(ingredientDetail.getStrength());
           compositionIngredient.setUnit(ingredientDetail.getUnit());
           compositionIngredientRepository.save(compositionIngredient);

           MoleculeIngredient moleculeIngredient=new MoleculeIngredient();
           moleculeIngredient.setMolecule(molecule);
           moleculeIngredient.setIngredient(ingredient);
           moleculeIngredientRepository.save(moleculeIngredient);

       }
       return "success";
    }



   public List<String> getIngredientNames(){
     return ingredientsRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
   }
   public List<String> getMoleculeNames(){
        return moleculesRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
   }
   public List<String> getCompositeNames(){
        return compositionsRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
   }
}
