package br.com.gabrieldias.gestao_vagas.modules.candidate.controllers;

import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;


    @PostMapping("/candidate")
    public ResponseEntity login(@RequestBody AuthCandidateDTO authCandidateDTO) {
        try {
            AuthCandidateResponseDTO token = authCandidateUseCase.authenticate(authCandidateDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
