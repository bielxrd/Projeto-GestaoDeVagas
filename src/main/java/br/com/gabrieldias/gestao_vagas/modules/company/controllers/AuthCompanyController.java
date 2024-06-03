package br.com.gabrieldias.gestao_vagas.modules.company.controllers;


import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    @Autowired
    AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
           AuthCompanyResponseDTO token = this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok().body(token);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
