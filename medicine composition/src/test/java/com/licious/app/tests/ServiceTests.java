package com.licious.app.tests;

import com.licious.app.model.Composition;
import com.licious.app.repository.*;
import com.licious.app.service.CompositionMoleculeService;
import com.licious.app.service.CompositionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTests {
    @Autowired
    private CompositionMoleculeService compositionMoleculeService;

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
    @Autowired
    private CompositionService compositionsService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void getCompositionDetailsByCompositionId(){

    }

    @Test
    public void getCompositionsByIngredientStrengthUnit(){
        String ingredientName="paracetamol";
        String unit="MG";
        float strength=200;

        // taking a existing composition from database for testing.
        String testcompositionName="paracetamol (200.0MG) + diclofenac (2.5MGG) + aspirin (2.0v/v)";

        Composition testComposition=compositionsService.getCompositionByName(testcompositionName);
        int testCompositionId=testComposition.getId();
        assertNotNull(testComposition);


        //checking whether method related to api works or not
        List<Composition> compositionList=compositionMoleculeService.
                getAllCompositionsFilteredByIngrediantDetails(ingredientName,strength,unit);
        List<Integer> compositionIds=compositionList.stream().map(t->t.getId()).collect(Collectors.toList());


        boolean checkExpression=(compositionIds.indexOf(testCompositionId)>=0);

        assertTrue(checkExpression);


    }
    @Test
    public void getCompositionsByIngredientMoleculeDetails(){
        String ingredientName="paracetamol";
        String unit="MG";
        float strength=200;
        boolean rx_required=false;

        // taking a existing composition from database for testing.
        String testcompositionName="paracetamol (200.0MG) + diclofenac (2.5MGG) + aspirin (2.0v/v)";
        Composition testComposition=compositionsService.getCompositionByName(testcompositionName);
        int testCompositionId=testComposition.getId();
        assertNotNull(testComposition);


        //checking whether method related to api works or not
        List<Composition> compositionList=compositionMoleculeService
                .getAllCompositionFilteredByIngredientMoleculeDetails(ingredientName,strength,unit,rx_required);
        List<Integer> compositionIds=compositionList.stream().map(t->t.getId()).collect(Collectors.toList());

        System.out.println(compositionIds);
        System.out.println(testCompositionId);
        boolean checkExpression=(compositionIds.indexOf(testCompositionId)>=0);

        assertTrue(checkExpression);
    }

    @Test
    public void addNewCompositionDetails(){

    }
}
