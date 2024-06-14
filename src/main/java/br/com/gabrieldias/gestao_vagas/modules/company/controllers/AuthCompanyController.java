package br.com.gabrieldias.gestao_vagas.modules.company.controllers;


import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Login para company", description = "Login da company")
public class AuthCompanyController {

    @Autowired
    AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth")
    @Operation(summary = "Responsável por efetuar o login da company e retornar um JWT Token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthCompanyResponseDTO.class))
            })
    })
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
           AuthCompanyResponseDTO token = this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok().body(token);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
