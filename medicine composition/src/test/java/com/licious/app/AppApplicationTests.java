package com.licious.app;

import com.licious.app.controller.CompositionMoleculeController;
import com.licious.app.model.Composition;
import com.licious.app.repository.*;
import com.licious.app.service.CompositionMoleculeService;
//import org.junit.jupiter.api.Test;
import com.licious.app.service.CompositionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test.*;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AppApplicationTests {


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


		boolean checkExpression=(compositionIds.indexOf(testCompositionId)>0);

        assertTrue(checkExpression);





	}

}
