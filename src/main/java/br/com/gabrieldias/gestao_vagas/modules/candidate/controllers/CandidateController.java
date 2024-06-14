package br.com.gabrieldias.gestao_vagas.modules.candidate.controllers;


import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.gabrieldias.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.gabrieldias.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.gabrieldias.gestao_vagas.modules.company.dto.ListAllJobsDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.useCases.ListAllJobsUseCase;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Rotas do Candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsUseCase listAllJobsUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @PostMapping("/")
    @Operation(summary = "Rota para cadastrar candidato.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            })
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            CandidateEntity candidate = createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/profile/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Rota para obter perfil do candidato logado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ProfileCandidateResponseDTO> getCandidateProfile(HttpServletRequest request) {

        Object idCandidate = request.getAttribute("candidate_id");

        ProfileCandidateResponseDTO candidateProfile = this.profileCandidateUseCase.getCandidateProfile(UUID.fromString(idCandidate.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(candidateProfile);

    }

    @GetMapping("/jobs/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Rota para listar todas as vagas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ListAllJobsDTO.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ListAllJobsDTO> getAllJobs() {

        ListAllJobsDTO jobs = this.listAllJobsUseCase.listAllJobs();

        return ResponseEntity.ok().body(jobs);
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Rota para listar todas as vagas com base em um filtro (description da vaga).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }
}
