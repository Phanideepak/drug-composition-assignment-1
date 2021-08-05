package com.licious.app;

import com.licious.app.controller.CompositionMoleculeController;
import com.licious.app.model.Composition;
import com.licious.app.repository.CompositionsRepository;
import com.licious.app.repository.IngredientsRepository;
import com.licious.app.service.CompositionMoleculeService;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test.*;
import static org.junit.Assert.*;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AppApplicationTests {


	@MockBean
	CompositionMoleculeService compositionMoleculeService;

	@Autowired
	CompositionsRepository compositionsRepository;
	@Autowired
	IngredientsRepository ingredientsRepository;


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
        Composition testComposition=compositionsRepository.findOneByName(testcompositionName).get();
        assertNotNull(testComposition);


        System.out.println(ingredientsRepository.findOneByName(ingredientName).get());
        //checking whether method related to api works or not
		List<Composition> compositionList=compositionMoleculeService.
				getAllCompositionsFilteredByIngrediantDetails(ingredientName,strength,unit);
		System.out.println(compositionList);

		boolean checkExpression=(compositionList.indexOf(testComposition)>0);

        assertTrue(checkExpression);





	}

}
