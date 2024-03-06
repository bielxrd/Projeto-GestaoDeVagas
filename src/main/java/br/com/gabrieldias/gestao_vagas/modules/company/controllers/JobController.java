package br.com.gabrieldias.gestao_vagas.modules.company.controllers;

import br.com.gabrieldias.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest httpServletRequest) {

        Object companyId = httpServletRequest.getAttribute("company_id");

        JobEntity jobEntity = JobEntity.builder().benefits(createJobDTO.getBenefits()).companyId(UUID.fromString(companyId.toString())).description(createJobDTO.getDescription()).level(createJobDTO.getLevel()).build();

        JobEntity job = createJobUseCase.execute(jobEntity);

        return ResponseEntity.ok().body(job);

    }

}
