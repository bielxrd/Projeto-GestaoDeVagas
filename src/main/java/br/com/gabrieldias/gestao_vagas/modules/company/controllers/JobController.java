package br.com.gabrieldias.gestao_vagas.modules.company.controllers;

import br.com.gabrieldias.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
@Tag(name = "Vagas", description = "Rotas para cadastro de vagas")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(summary = "Responsável por realizar um cadastro de uma vaga, necessário estar autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest httpServletRequest) {

        Object companyId = httpServletRequest.getAttribute("company_id");

        JobEntity jobEntity = JobEntity.builder().benefits(createJobDTO.getBenefits()).companyId(UUID.fromString(companyId.toString())).description(createJobDTO.getDescription()).level(createJobDTO.getLevel()).build();

        JobEntity job = createJobUseCase.execute(jobEntity);

        return ResponseEntity.ok().body(job);

    }

}
