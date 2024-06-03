package br.com.gabrieldias.gestao_vagas.modules.candidate.controllers;


import br.com.gabrieldias.gestao_vagas.exceptions.UserFoundException;
import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.gabrieldias.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.gabrieldias.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            CandidateEntity candidate = createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/profile/{idCandidate}")
    public ResponseEntity<ProfileCandidateResponseDTO> getCandidateProfile(HttpServletRequest request) {

        Object idCandidate = request.getAttribute("candidate_id");

        ProfileCandidateResponseDTO candidateProfile = this.profileCandidateUseCase.getCandidateProfile(UUID.fromString(idCandidate.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(candidateProfile);

    }


}
