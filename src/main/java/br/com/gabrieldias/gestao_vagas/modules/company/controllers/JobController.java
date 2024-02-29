package br.com.gabrieldias.gestao_vagas.modules.company.controllers;

import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody JobEntity jobEntity) {

        JobEntity job = createJobUseCase.execute(jobEntity);

        return ResponseEntity.ok().body(job);

    }

}
