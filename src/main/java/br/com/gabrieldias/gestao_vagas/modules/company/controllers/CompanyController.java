package br.com.gabrieldias.gestao_vagas.modules.company.controllers;

import br.com.gabrieldias.gestao_vagas.exceptions.UserFoundException;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
        try {
            CompanyEntity company = createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(company);
        } catch (UserFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
