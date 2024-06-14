package br.com.gabrieldias.gestao_vagas.modules.candidate.controllers;

import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Login para candidato", description = "Login do candidato")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;


    @PostMapping("/auth")
    @Operation(summary = "Responsável por efetuar o login do candidato e retornar um JWT Token.")
    public ResponseEntity login(@RequestBody AuthCandidateDTO authCandidateDTO) {
        try {
            AuthCandidateResponseDTO token = authCandidateUseCase.authenticate(authCandidateDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
