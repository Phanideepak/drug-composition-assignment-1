package com.licious.app.service;

import com.licious.app.dto.request.CompositionMoleculeInputDTO;
import com.licious.app.dto.response.CompositionDetailsDTO;
import com.licious.app.dto.IngredientDetails;
import com.licious.app.model.*;
import com.licious.app.repository.*;
import com.licious.app.utils.CommonUtils;
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

    //get details of all ingredients, molecule of given composition
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

    // find the molecule that has same ingredients as given composition.
    // This method takes ingredientIds of given composition as parameter
    //It returns the molecule that has all the ingredients of given composition.
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

    // get all composition that contains given ingredient and dosage.
    public List<Composition> getAllCompositionsFilteredByIngrediantDetails(String ingredientName, float strength,String unit){

        Ingredient ingredient=ingredientsRepository.findOneByName(ingredientName).get();

        int ingredientId=ingredient.getId();
        System.out.println(ingredientId);
        List<Integer> compositionIds=compositionIngredientRepository.
                findAllByIngredientStrengthUnit(ingredientId,strength,unit);
        System.out.println(compositionIds);
        List<Composition> compositionList=new ArrayList<>();
        for(Integer compositionId: compositionIds){
            Composition composition=compositionsRepository.findById(compositionId).get();
            compositionList.add(composition);
        }
        return compositionList;
    }

    /* get all compositions that contains given ingredient with given dosage and molecule with
        given rex_required value */
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
    // adding new compositions to the database.
    public String addCompositonMoleculeDetails(CompositionMoleculeInputDTO inputDTO,String compositionName){
       System.out.println(inputDTO);
       List<String> existingIngredientNames=getIngredientNames();
       List<String> existingMoleculeNames=getMoleculeNames();
       List<String> existingCompositionNames=getCompositeNames();
       Boolean rx_required=inputDTO.isRx_required();

       List<IngredientDetails> ingredientDetailsList=inputDTO.getIngredients();
       for(IngredientDetails ingredient: ingredientDetailsList){
           String ingredientName=ingredient.getName();
           float strength=ingredient.getStrength();
           String unit=ingredient.getUnit();
           if(existingIngredientNames.indexOf(ingredientName)==-1){
               return "ingredient: "+ingredientName+" doesnot exist. Insertion of this composition failure";
               /*
               Ingredient ingredient1=new Ingredient();
               ingredient1.setName(ingredientName);
               ingredientsRepository.save(ingredient1);

                */
           }

       }
       List<String> ingredientNameList=ingredientDetailsList
               .stream().map(i->i.getName()).collect(Collectors.toList());

       String moleculeName= CommonUtils.createMoleculeName(ingredientNameList);

       if(existingMoleculeNames.indexOf(moleculeName)==-1){
           Molecule molecule=new Molecule();
           molecule.setName(moleculeName);
           molecule.setRx_required(rx_required);

           moleculesRepository.save(molecule);
       }

       Molecule molecule=moleculesRepository.findOneByName(moleculeName).get();
       molecule.setRx_required(rx_required);
       moleculesRepository.save(molecule);
       System.out.println(rx_required);

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


   // get a list of names of all the ingredients
   public List<String> getIngredientNames(){
     return ingredientsRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
   }

   // get a list of names of all the molecules.
   public List<String> getMoleculeNames(){
        return moleculesRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
   }

   // get a list of names of all the compositons.
   public List<String> getCompositeNames(){
        return compositionsRepository.findAll().stream().map(t->t.getName()).collect(Collectors.toList());
   }
}
