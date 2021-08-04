package com.licious.app.service;

import com.licious.app.dto.response.CompositionDetailsDTO;
import com.licious.app.dto.IngredientDetails;
import com.licious.app.model.Composition;
import com.licious.app.model.Molecule;
import com.licious.app.repository.*;
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
}
