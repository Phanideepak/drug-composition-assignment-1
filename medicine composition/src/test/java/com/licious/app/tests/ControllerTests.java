package com.licious.app.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licious.app.controller.CompositionMoleculeController;
import com.licious.app.dto.response.CompositionDetailsDTO;
import com.licious.app.model.Composition;
import com.licious.app.service.CompositionMoleculeService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ControllerTests {

    @Mock
    private CompositionMoleculeService compositionMoleculeService;
    @InjectMocks
    private CompositionMoleculeController compositionMoleculeController;

    private MockMvc mockMvc;


    RestTemplate restTemplate;



   @Autowired
   private WebApplicationContext webApplicationContext;

   private ObjectMapper objectMapper;

    @Before
   public void setUp(){
        MockitoAnnotations.openMocks(this);
       mockMvc= MockMvcBuilders.standaloneSetup(compositionMoleculeController).build();
       objectMapper=new ObjectMapper();
       restTemplate=new RestTemplate();
   }


   @Test
    public void getCompositionDetailsbyCompositionId() throws Exception{
       int componentId=53;
       String urlLink="http://localhost:8080/composition?compositionId=";
       urlLink=urlLink+componentId;


   }
   @Test
    public void getCompositionsByStrengthIngredientUnit() throws Exception{
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();

        // input parameters
           String ingredientName="paracetamol";
           float strength=200;
           String  unit="MG";

       String urlLink="http://localhost:8080/compositions";
       UriComponentsBuilder uriComponentsBuilder=UriComponentsBuilder
               .fromHttpUrl(urlLink)
               .queryParam("ingredientName",ingredientName).queryParam("strength",strength)
               .queryParam("unit",unit);
       HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

       var response = restTemplate.exchange(
               uriComponentsBuilder.toUriString(),
               HttpMethod.GET,
               entity,
               List.class);
       assertEquals(HttpStatus.OK,response.getStatusCode());
       List<Composition> actualCompositionList=objectMapper
               .convertValue(response.getBody(), new TypeReference<List<Composition>>() {});


       // take a test Composition and check whether the composition exist are not..
       // Checking api results with a test case.
       Composition testComposition=new Composition();
       testComposition.setId(25);
       testComposition.setName("paracetamol (200.0MG) + diclofenac (2.5MGG)");
       boolean checkExpression=false;
       for(Composition actualComposition: actualCompositionList){
           if(actualComposition.getName().equals(testComposition.getName())&&actualComposition.getId()==testComposition.getId()){
              checkExpression=true;
              break;
           }
       }
       assertTrue("Given API yields test composition",checkExpression);


   }
}

