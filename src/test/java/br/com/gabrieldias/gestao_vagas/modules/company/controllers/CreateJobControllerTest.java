package br.com.gabrieldias.gestao_vagas.modules.company.controllers;

import br.com.gabrieldias.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.gabrieldias.gestao_vagas.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateJobControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SneakyThrows
    public void should_be_able_to_create_a_new_job() {

        CreateJobDTO createJob = CreateJobDTO.builder()
                .benefits("BENEFIST_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();


        ResultActions resultActions = mockMvc.perform(post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(createJob))
                        .header("Authorization", TestUtils.generateTokenTest(UUID.randomUUID())))
                .andExpect(status().isOk());

        System.out.println(resultActions);
    }

}
