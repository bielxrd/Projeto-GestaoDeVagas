package br.com.gabrieldias.gestao_vagas.modules.company.controllers;

import br.com.gabrieldias.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.gabrieldias.gestao_vagas.utils.TestUtils;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateCompanyControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SneakyThrows
    public void should_be_able_to_create_a_new_company() {

        CompanyEntity company = CompanyEntity.builder()
                .description("COMPANY_DESCRIPTION")
                .email("email@company.com")
                .password("x9440239483")
                .username("companytest")
                .name("COMPANY_NAME")
                .build();

        ResultActions resultActions = mvc.perform(post("/company/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(company)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value(company.getDescription()))
                .andExpect(jsonPath("$.email").value(company.getEmail()));

    }

}
