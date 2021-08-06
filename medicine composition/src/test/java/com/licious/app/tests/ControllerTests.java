package com.licious.app.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licious.app.controller.CompositionMoleculeController;
import com.licious.app.dto.response.CompositionDetailsDTO;
import com.licious.app.service.CompositionMoleculeService;
import org.junit.Before;
import org.junit.Test;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;



@RunWith(MockitoJUnitRunner.class)
public class ControllerTests {

    @Mock
    private CompositionMoleculeService compositionMoleculeService;
    @InjectMocks
    private CompositionMoleculeController compositionMoleculeController;

    private MockMvc mockMvc;



   @Autowired
   private WebApplicationContext webApplicationContext;

   private ObjectMapper objectMapper;

    @Before
   public void setUp(){
        MockitoAnnotations.openMocks(this);
       mockMvc= MockMvcBuilders.standaloneSetup(compositionMoleculeController).build();
       objectMapper=new ObjectMapper();
   }


   @Test
    public void getCompositionDetailsbyCompositionId() throws Exception{
       int componentId=53;


       CompositionDetailsDTO compositionDetailsDTO=new CompositionDetailsDTO();
       when(compositionMoleculeService.getCompositionDetailsByCompositionId(componentId))
               .thenReturn(compositionDetailsDTO);


       MultiValueMap<String,String> requestParams=new LinkedMultiValueMap<>();
       requestParams.add("compositionId",componentId+"");
       String api_url="/composition";

       MvcResult result=mockMvc.perform(get(api_url).
               params(requestParams))
               .andDo(print()).
               andExpect(status().isOk()).andReturn();
       System.out.println(result.getResponse().getContentAsString());

   }
}

