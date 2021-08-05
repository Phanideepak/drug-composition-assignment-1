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

}
